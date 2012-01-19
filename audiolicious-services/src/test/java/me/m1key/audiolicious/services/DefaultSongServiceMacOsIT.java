/* 
 * Audiolicious - Your Music Library Statistics
 * Copyright (C) 2011, Michal Huniewicz
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.m1key.me
 */

package me.m1key.audiolicious.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.ejb.EJB;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.commons.qualifiers.NullLibrary;
import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.AlbumAndSongInfo;
import me.m1key.audiolicious.domain.entities.AlbumInfo;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;
import me.m1key.audiolicious.domain.entities.Rating;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.domain.entities.SongInfo;
import me.m1key.audiolicious.domain.entities.Stat;
import me.m1key.audiolicious.domain.entities.StatInfo;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.LibraryTo;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.TrackToType;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.libraryparser.LibraryParser;
import me.m1key.audiolicious.libraryparser.RawTrackDataHandler;
import me.m1key.audiolicious.libraryparser.VtdItunesLibraryParser;
import me.m1key.audiolicious.libraryparser.XmlParseException;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.DefaultRawTrackDataHandler;
import me.m1key.audiolicious.objecthandler.ObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.factories.TrackHandlersFactory;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactory;
import me.m1key.audiolicious.objecthandler.handlers.AudiobookHandler;
import me.m1key.audiolicious.objecthandler.handlers.DefaultNoopTrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.DefaultSongHandler;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.PodcastHandler;
import me.m1key.audiolicious.objecthandler.handlers.SongHandler;
import me.m1key.audiolicious.objecthandler.handlers.SongService;
import me.m1key.audiolicious.objecthandler.handlers.VideoHandler;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.CannotMapTrackValuesException;
import me.m1key.audiolicious.objectmapper.DefaultAggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.ObjectMappingException;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.extractor.DataExtractor;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.EnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractor;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultAudiobookMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultPodcastMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultSongMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultVideoMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.NonAggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.PodcastMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.SongMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.VideoMapper;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DefaultSongServiceMacOsIT {

	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/MacOsExportedLibrary-2011-07-28.xml";

	@EJB
	private LibraryImporter libraryImporter;
	@EJB
	private StubArtistRepository stubArtistRepository;
	@EJB
	private ServicesTestHelperBean servicesTestHelperBean;
	@EJB
	private LibraryService libraryService;

	private static boolean handlerHasNotRunYet = true;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						DefaultSongServiceMacOsIT.class.getSimpleName()
								+ ".war")
				.addAsResource(
						new File(
								"../audiolicious-object-mappers/src/main/resources/englishValues.properties"),
						"englishValues.properties")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsResource("log4j.xml", "log4j.xml")
				.addClasses(AggregateTrackMapper.class, Album.class,
						AlbumAndSongInfo.class, AlbumInfo.class, Artist.class,
						ArtistRepository.class, AudiobookHandler.class,
						AudiobookMapper.class, AudiobookTo.class,
						CacheableSongService.class,
						CacheAwareLibraryImporter.class,
						CannotMapTrackValuesException.class,
						DefaultAggregateTrackMapper.class,
						DefaultAudiobookMapper.class, DataExtractor.class,
						DefaultEnglishValuesProvider.class,
						DefaultNoopTrackHandler.class,
						DefaultObjectTrackDataHandler.class,
						DefaultPodcastMapper.class,
						DefaultRawTrackDataHandler.class,
						DefaultSongHandler.class, DefaultSongMapper.class,
						DefaultVideoMapper.class, EnglishValuesProvider.class,
						I18nDataExtractor.class,
						InMemoryCacheableSongService.class, FullStatInfo.class,
						Library.class, LibraryImporter.class,
						LibraryParser.class, LibraryService.class,
						LibraryRepository.class, LibraryTo.class,
						NonAggregateTrackMapper.class, NoopTrackHandler.class,
						NullArtist.class, NullEntitiesFactory.class,
						NullLibrary.class, ObjectMappingException.class,
						ObjectTrackDataHandler.class, PodcastHandler.class,
						PodcastMapper.class, PodcastTo.class, Rating.class,
						RatingTo.class, RawTrackDataHandler.class,
						ServicesTestHelperBean.class, Song.class,
						SongHandler.class, StatInfo.class, SongInfo.class,
						SongMapper.class, SongService.class, SongTo.class,
						Stat.class, StubArtistRepository.class,
						StubLibraryRepository.class,
						StubLibraryRepositoryWithInfo.class,
						StubLibraryService.class, ToBasedStatInfo.class,
						TrackHandler.class, TrackHandlersFactory.class,
						TrackMapper.class, TrackMappersFactory.class,
						TrackTo.class, TrackToType.class, VideoHandler.class,
						VideoMapper.class, VideoTo.class,
						VtdItunesLibraryParser.class, XmlNodeName.class,
						XmlParseException.class)
				.addAsLibraries(
						DependencyResolvers
								.use(MavenDependencyResolver.class)
								.artifacts("com.ximpleware:vtd-xml:2.10",
										"joda-time:joda-time:1.6.2",
										"org.slf4j:slf4j-api:1.6.1",
										"org.slf4j:slf4j-log4j12:1.6.1",
										"commons-lang:commons-lang:2.6")
								.resolveAsFiles());
	}

	@Before
	public void setUp() throws Exception {
		if (handlerHasNotRunYet) {
			File xmlLibraryFile = new File(pathToFile);
			libraryImporter.importLibrary(xmlLibraryFile);
			handlerHasNotRunYet = false;
		}
	}

	@Test
	public void testAlbum1Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		assertNotNull(album);
		assertNotNull(album.getUuid());
		assertEquals("Monster Magnet", album.getArtist().getName());
		assertEquals("Spine Of God", album.getName());
		assertEquals(9, album.getSongs().size());
	}

	@Test
	public void testAlbum2Correct() {
		Album album = stubArtistRepository.getToolAenima();
		assertNotNull(album);
		assertNotNull(album.getUuid());
		assertEquals("Tool", album.getArtist().getName());
		assertEquals("Ænima", album.getName());
		assertEquals(15, album.getSongs().size());
	}

	@Test
	public void testCorrectNumberOfSongsInKornIssues() {
		assertEquals(
				Integer.valueOf(16),
				Integer.valueOf(stubArtistRepository.getKornIssues().getSongs()
						.size()));
	}

	@Test
	public void testAlbum3Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		assertNotNull(album);
		assertNotNull(album.getUuid());
		assertEquals("Fleet Foxes", album.getArtist().getName());
		assertEquals("Fleet Foxes (Deluxe Edition)", album.getName());
		assertEquals(19, album.getSongs().size());
	}

	@Test
	public void testAlbum3Song1Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song1 = getSongByTitle(album, "Sun It Rises");
		assertNotNull(song1);

		assertEquals("Fleet Foxes (Deluxe Edition)", song1.getAlbum().getName());
		assertEquals("Fleet Foxes", song1.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song1.getAlbum().getRating());
		assertEquals("Fleet Foxes", song1.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song1,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833366000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833386000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song1.getGenre());
		assertEquals("Sun It Rises", song1.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song1.getAlbum());
		assertEquals(2009, song1.getYear());
	}

	@Test
	public void testAlbum3Song2Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song2 = getSongByTitleAndVideo(album, "He Doesn't Know Why", false);
		assertNotNull(song2);

		assertEquals("Fleet Foxes (Deluxe Edition)", song2.getAlbum().getName());
		assertEquals("Fleet Foxes", song2.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song2.getAlbum().getRating());
		assertEquals("Fleet Foxes", song2.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song2,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833479000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song2.getGenre());
		assertEquals("He Doesn't Know Why", song2.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song2.getAlbum());
		assertEquals(2009, song2.getYear());
	}

	@Test
	public void testAlbum3Song3Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song3 = getSongByTitleAndVideo(album, "White Winter Hymnal", false);
		assertNotNull(song3);

		assertEquals("Fleet Foxes (Deluxe Edition)", song3.getAlbum().getName());
		assertEquals("Fleet Foxes", song3.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song3.getAlbum().getRating());
		assertEquals("Fleet Foxes", song3.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song3,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833399000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song3.getGenre());
		assertEquals("White Winter Hymnal", song3.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song3.getAlbum());
		assertEquals(2009, song3.getYear());
	}

	@Test
	public void testAlbum3Song4Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song4 = getSongByTitle(album, "Ragged Wood");
		assertNotNull(song4);

		assertEquals("Fleet Foxes (Deluxe Edition)", song4.getAlbum().getName());
		assertEquals("Fleet Foxes", song4.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song4.getAlbum().getRating());
		assertEquals("Fleet Foxes", song4.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song4,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833418000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song4.getGenre());
		assertEquals("Ragged Wood", song4.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song4.getAlbum());
		assertEquals(2009, song4.getYear());
	}

	@Test
	public void testAlbum3Song5Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song5 = getSongByTitle(album, "Tiger Mountain Peasant Song");
		assertNotNull(song5);

		assertEquals("Fleet Foxes (Deluxe Edition)", song5.getAlbum().getName());
		assertEquals("Fleet Foxes", song5.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song5.getAlbum().getRating());
		assertEquals("Fleet Foxes", song5.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song5,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833433000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song5.getGenre());
		assertEquals("Tiger Mountain Peasant Song", song5.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song5.getAlbum());
		assertEquals(2009, song5.getYear());
	}

	@Test
	public void testAlbum3Song6Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song6 = getSongByTitle(album, "Quiet Houses");
		assertNotNull(song6);

		assertEquals("Fleet Foxes (Deluxe Edition)", song6.getAlbum().getName());
		assertEquals("Fleet Foxes", song6.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song6.getAlbum().getRating());
		assertEquals("Fleet Foxes", song6.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song6,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833453000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song6.getGenre());
		assertEquals("Quiet Houses", song6.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song6.getAlbum());
		assertEquals(2009, song6.getYear());
	}

	@Test
	public void testAlbum3Song7Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song7 = getSongByTitle(album, "Heard Them Stirring");
		assertNotNull(song7);

		assertEquals("Fleet Foxes (Deluxe Edition)", song7.getAlbum().getName());
		assertEquals("Fleet Foxes", song7.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song7.getAlbum().getRating());
		assertEquals("Fleet Foxes", song7.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song7,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833467000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song7.getGenre());
		assertEquals("Heard Them Stirring", song7.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song7.getAlbum());
		assertEquals(2009, song7.getYear());
	}

	@Test
	public void testAlbum3Song8Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song8 = getSongByTitle(album, "Your Protector");
		assertNotNull(song8);

		assertEquals("Fleet Foxes (Deluxe Edition)", song8.getAlbum().getName());
		assertEquals("Fleet Foxes", song8.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song8.getAlbum().getRating());
		assertEquals("Fleet Foxes", song8.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song8,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833493000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song8.getGenre());
		assertEquals("Your Protector", song8.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song8.getAlbum());
		assertEquals(2009, song8.getYear());
	}

	@Test
	public void testAlbum3Song9Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song9 = getSongByTitle(album, "Meadowlarks");
		assertNotNull(song9);

		assertEquals("Fleet Foxes (Deluxe Edition)", song9.getAlbum().getName());
		assertEquals("Fleet Foxes", song9.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song9.getAlbum().getRating());
		assertEquals("Fleet Foxes", song9.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song9,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833497000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song9.getGenre());
		assertEquals("Meadowlarks", song9.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(60), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song9.getAlbum());
		assertEquals(2009, song9.getYear());
	}

	@Test
	public void testAlbum3Song10Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song10 = getSongByTitle(album, "Blue Ridge Mountains");
		assertNotNull(song10);

		assertEquals("Fleet Foxes (Deluxe Edition)", song10.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song10.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song10.getAlbum().getRating());
		assertEquals("Fleet Foxes", song10.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song10,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833516000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song10.getGenre());
		assertEquals("Blue Ridge Mountains", song10.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song10.getAlbum());
		assertEquals(2009, song10.getYear());
	}

	@Test
	public void testAlbum3Song11Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song11 = getSongByTitle(album, "Oliver James");
		assertNotNull(song11);

		assertEquals("Fleet Foxes (Deluxe Edition)", song11.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song11.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song11.getAlbum().getRating());
		assertEquals("Fleet Foxes", song11.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song11,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833513000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song11.getGenre());
		assertEquals("Oliver James", song11.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(60), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song11.getAlbum());
		assertEquals(2009, song11.getYear());
	}

	@Test
	public void testAlbum3Song12Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song12 = getSongByTitle(album, "Sun Giant");
		assertNotNull(song12);

		assertEquals("Fleet Foxes (Deluxe Edition)", song12.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song12.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song12.getAlbum().getRating());
		assertEquals("Fleet Foxes", song12.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song12,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833518000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song12.getGenre());
		assertEquals("Sun Giant", song12.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(60), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song12.getAlbum());
		assertEquals(2009, song12.getYear());
	}

	@Test
	public void testAlbum3Song13Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song13 = getSongByTitle(album, "Drops In River");
		assertNotNull(song13);

		assertEquals("Fleet Foxes (Deluxe Edition)", song13.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song13.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song13.getAlbum().getRating());
		assertEquals("Fleet Foxes", song13.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song13,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1305833367000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1305833534000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Alternative", song13.getGenre());
		assertEquals("Drops In River", song13.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song13.getAlbum());
		assertEquals(2009, song13.getYear());
	}

	@Test
	public void testAlbum3Song14Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song14 = getSongByTitle(album, "English House");
		assertNotNull(song14);

		assertEquals("Fleet Foxes (Deluxe Edition)", song14.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song14.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song14.getAlbum().getRating());
		assertEquals("Fleet Foxes", song14.getArtistName());
		assertEquals(1305833367000L, song14.getStats().iterator().next()
				.getDateAdded().getTime());
		assertEquals(1305833540000L, song14.getStats().iterator().next()
				.getDateModified().getTime());
		assertEquals("Alternative", song14.getGenre());
		assertEquals("English House", song14.getName());
		assertEquals(1, song14.getStats().iterator().next().getPlayCount());
		assertEquals(new Rating(80), song14.getStats().iterator().next()
				.getRating());
		assertEquals(0, song14.getStats().iterator().next().getSkipCount());
		assertEquals(null, song14.getStats().iterator().next().getDateSkipped());
		assertEquals(album, song14.getAlbum());
		assertEquals(2009, song14.getYear());
	}

	@Test
	public void testAlbum3Song15Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song15 = getSongByTitle(album, "Mykonos");
		assertNotNull(song15);

		assertEquals("Fleet Foxes (Deluxe Edition)", song15.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song15.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song15.getAlbum().getRating());
		assertEquals("Fleet Foxes", song15.getArtistName());
		assertEquals(1305833367000L, song15.getStats().iterator().next()
				.getDateAdded().getTime());
		assertEquals(1305833538000L, song15.getStats().iterator().next()
				.getDateModified().getTime());
		assertEquals("Alternative", song15.getGenre());
		assertEquals("Mykonos", song15.getName());
		assertEquals(1, song15.getStats().iterator().next().getPlayCount());
		assertEquals(new Rating(100), song15.getStats().iterator().next()
				.getRating());
		assertEquals(0, song15.getStats().iterator().next().getSkipCount());
		assertEquals(null, song15.getStats().iterator().next().getDateSkipped());
		assertEquals(album, song15.getAlbum());
		assertEquals(2009, song15.getYear());
	}

	@Test
	public void testAlbum3Song16Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song16 = getSongByTitle(album, "Innocent Son");
		assertNotNull(song16);

		assertEquals("Fleet Foxes (Deluxe Edition)", song16.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song16.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song16.getAlbum().getRating());
		assertEquals("Fleet Foxes", song16.getArtistName());
		assertEquals(1305833367000L, song16.getStats().iterator().next()
				.getDateAdded().getTime());
		assertEquals(1305833548000L, song16.getStats().iterator().next()
				.getDateModified().getTime());
		assertEquals("Alternative", song16.getGenre());
		assertEquals("Innocent Son", song16.getName());
		assertEquals(1, song16.getStats().iterator().next().getPlayCount());
		assertEquals(new Rating(80), song16.getStats().iterator().next()
				.getRating());
		assertEquals(0, song16.getStats().iterator().next().getSkipCount());
		assertEquals(null, song16.getStats().iterator().next().getDateSkipped());
		assertEquals(album, song16.getAlbum());
		assertEquals(2009, song16.getYear());
	}

	@Test
	public void testAlbum3Song17Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song17 = getSongByTitle(album, "False Knight On the Road");
		assertNotNull(song17);

		assertEquals("Fleet Foxes (Deluxe Edition)", song17.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song17.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song17.getAlbum().getRating());
		assertEquals("Fleet Foxes", song17.getArtistName());
		assertEquals(1305833367000L, song17.getStats().iterator().next()
				.getDateAdded().getTime());
		assertEquals(1305833551000L, song17.getStats().iterator().next()
				.getDateModified().getTime());
		assertEquals("Alternative", song17.getGenre());
		assertEquals("False Knight On the Road", song17.getName());
		assertEquals(1, song17.getStats().iterator().next().getPlayCount());
		assertEquals(new Rating(60), song17.getStats().iterator().next()
				.getRating());
		assertEquals(0, song17.getStats().iterator().next().getSkipCount());
		assertEquals(null, song17.getStats().iterator().next().getDateSkipped());
		assertEquals(album, song17.getAlbum());
		assertEquals(2009, song17.getYear());
	}

	@Test
	public void testAlbum3Song18Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song18 = getSongByTitleAndVideo(album, "He Doesn't Know Why", true);
		assertNotNull(song18);

		assertEquals("Fleet Foxes (Deluxe Edition)", song18.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song18.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song18.getAlbum().getRating());
		assertEquals("Fleet Foxes", song18.getArtistName());
		assertEquals(1305833367000L, song18.getStats().iterator().next()
				.getDateAdded().getTime());
		assertEquals(1305833498000L, song18.getStats().iterator().next()
				.getDateModified().getTime());
		assertEquals("Alternative", song18.getGenre());
		assertEquals("He Doesn't Know Why", song18.getName());
		assertEquals(2, song18.getStats().iterator().next().getPlayCount());
		assertEquals(new Rating(80), song18.getStats().iterator().next()
				.getRating());
		assertEquals(1, song18.getStats().iterator().next().getSkipCount());
		assertEquals(1305920550000L, song18.getStats().iterator().next()
				.getDateSkipped().getTime());
		assertEquals(album, song18.getAlbum());
		assertEquals(2003, song18.getYear());
	}

	@Test
	public void testAlbum3Song19Correct() {
		Album album = stubArtistRepository.getFleetFoxesFleetFoxes();
		Song song19 = getSongByTitleAndVideo(album, "White Winter Hymnal", true);
		assertNotNull(song19);

		assertEquals("Fleet Foxes (Deluxe Edition)", song19.getAlbum()
				.getName());
		assertEquals("Fleet Foxes", song19.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song19.getAlbum().getRating());
		assertEquals("Fleet Foxes", song19.getArtistName());
		assertEquals(1305833366000L, song19.getStats().iterator().next()
				.getDateAdded().getTime());
		assertEquals(1305833452000L, song19.getStats().iterator().next()
				.getDateModified().getTime());
		assertEquals("Alternative", song19.getGenre());
		assertEquals("White Winter Hymnal", song19.getName());
		assertEquals(2, song19.getStats().iterator().next().getPlayCount());
		assertEquals(new Rating(80), song19.getStats().iterator().next()
				.getRating());
		assertEquals(0, song19.getStats().iterator().next().getSkipCount());
		assertEquals(null, song19.getStats().iterator().next().getDateSkipped());
		assertEquals(album, song19.getAlbum());
		assertEquals(2003, song19.getYear());
	}

	@Test
	public void testAlbum4Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		assertNotNull(album);
		assertNotNull(album.getUuid());
		assertEquals("Various Artists", album.getArtist().getName());
		assertEquals("Animatrix: The Album", album.getName());
		assertEquals(12, album.getSongs().size());
	}

	@Test
	public void testAlbum4Song1Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song1 = getSongByTitle(album, "Who Am I (Animatrix Edit)");
		assertNotNull(song1);

		assertEquals("Animatrix: The Album", song1.getAlbum().getName());
		assertEquals("Various Artists", song1.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song1.getAlbum().getRating());
		assertEquals("Peace Orchestra Feat. Hubert Tubbs",
				song1.getArtistName());
		assertEquals(1255633757000L, song1.getStats().iterator().next()
				.getDateAdded().getTime());
		assertEquals(1255631063000L, song1.getStats().iterator().next()
				.getDateModified().getTime());
		assertEquals("Soundtrack", song1.getGenre());
		assertEquals("Who Am I (Animatrix Edit)", song1.getName());
		assertEquals(9, song1.getStats().iterator().next().getPlayCount());
		assertEquals(new Rating(100), song1.getStats().iterator().next()
				.getRating());
		assertEquals(0, song1.getStats().iterator().next().getSkipCount());
		assertEquals(null, song1.getStats().iterator().next().getDateSkipped());
		assertEquals(album, song1.getAlbum());
		assertEquals(2003, song1.getYear());
	}

	@Test
	public void testAlbum4Song2Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song2 = getSongByTitle(album, "Big Wednesday");
		assertNotNull(song2);

		assertEquals("Animatrix: The Album", song2.getAlbum().getName());
		assertEquals("Various Artists", song2.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song2.getAlbum().getRating());
		assertEquals("Freeland", song2.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song2,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255633820000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song2.getGenre());
		assertEquals("Big Wednesday", song2.getName());
		assertEquals(10, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song2.getAlbum());
		assertEquals(2003, song2.getYear());
	}

	@Test
	public void testAlbum4Song3Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song3 = getSongByTitle(album, "Blind Tiger");
		assertNotNull(song3);

		assertEquals("Animatrix: The Album", song3.getAlbum().getName());
		assertEquals("Various Artists", song3.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song3.getAlbum().getRating());
		assertEquals("Layo & Bushwacka!", song3.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song3,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255633866000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song3.getGenre());
		assertEquals("Blind Tiger", song3.getName());
		assertEquals(7, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song3.getAlbum());
		assertEquals(2003, song3.getYear());
	}

	@Test
	public void testAlbum4Song4Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song4 = getSongByTitle(album, "Under The Gun");
		assertNotNull(song4);

		assertEquals("Animatrix: The Album", song4.getAlbum().getName());
		assertEquals("Various Artists", song4.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song4.getAlbum().getRating());
		assertEquals("Supreme Beings Of Leisure", song4.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song4,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255633919000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song4.getGenre());
		assertEquals("Under The Gun", song4.getName());
		assertEquals(8, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song4.getAlbum());
		assertEquals(2003, song4.getYear());
	}

	@Test
	public void testAlbum4Song5Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song5 = getSongByTitle(album, "Martenot Waves");
		assertNotNull(song5);

		assertEquals("Animatrix: The Album", song5.getAlbum().getName());
		assertEquals("Various Artists", song5.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song5.getAlbum().getRating());
		assertEquals("Meat Beat Manifesto", song5.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song5,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255633946000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song5.getGenre());
		assertEquals("Martenot Waves", song5.getName());
		assertEquals(7, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song5.getAlbum());
		assertEquals(2003, song5.getYear());
	}

	@Test
	public void testAlbum4Song6Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song6 = getSongByTitle(album, "Ren 2");
		assertNotNull(song6);

		assertEquals("Animatrix: The Album", song6.getAlbum().getName());
		assertEquals("Various Artists", song6.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song6.getAlbum().getRating());
		assertEquals("Photek", song6.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song6,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255634002000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song6.getGenre());
		assertEquals("Ren 2", song6.getName());
		assertEquals(8, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song6.getAlbum());
		assertEquals(2003, song6.getYear());
	}

	@Test
	public void testAlbum4Song7Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song7 = getSongByTitle(album, "Hands Around My Throat");
		assertNotNull(song7);

		assertEquals("Animatrix: The Album", song7.getAlbum().getName());
		assertEquals("Various Artists", song7.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song7.getAlbum().getRating());
		assertEquals("Death In Vegas", song7.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song7,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255634030000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song7.getGenre());
		assertEquals("Hands Around My Throat", song7.getName());
		assertEquals(6, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song7.getAlbum());
		assertEquals(2003, song7.getYear());
	}

	@Test
	public void testAlbum4Song8Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song8 = getSongByTitle(album,
				"Beauty Never Fades (Animatrix Edit)");
		assertNotNull(song8);

		assertEquals("Animatrix: The Album", song8.getAlbum().getName());
		assertEquals("Various Artists", song8.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song8.getAlbum().getRating());
		assertEquals("Junkie XL Feat. Saffron", song8.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song8,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255634063000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song8.getGenre());
		assertEquals("Beauty Never Fades (Animatrix Edit)", song8.getName());
		assertEquals(8, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song8.getAlbum());
		assertEquals(2003, song8.getYear());
	}

	@Test
	public void testAlbum4Song9Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song9 = getSongByTitle(album, "Supermoves (Animatrix)");
		assertNotNull(song9);

		assertEquals("Animatrix: The Album", song9.getAlbum().getName());
		assertEquals("Various Artists", song9.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song9.getAlbum().getRating());
		assertEquals("Overseer", song9.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song9,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255634102000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song9.getGenre());
		assertEquals("Supermoves (Animatrix)", song9.getName());
		assertEquals(7, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song9.getAlbum());
		assertEquals(2003, song9.getYear());
	}

	@Test
	public void testAlbum4Song10Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song10 = getSongByTitle(album, "Conga Fury (Animatrix Edit)");
		assertNotNull(song10);

		assertEquals("Animatrix: The Album", song10.getAlbum().getName());
		assertEquals("Various Artists", song10.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song10.getAlbum().getRating());
		assertEquals("Juno Reactor Feat. Keanu Reeves & Laurence Fishburne",
				song10.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song10,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255634128000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song10.getGenre());
		assertEquals("Conga Fury (Animatrix Edit)", song10.getName());
		assertEquals(7, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song10.getAlbum());
		assertEquals(2003, song10.getYear());
	}

	@Test
	public void testAlbum4Song11Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song11 = getSongByTitle(album, "Red Pill, Blue Pill");
		assertNotNull(song11);

		assertEquals("Animatrix: The Album", song11.getAlbum().getName());
		assertEquals("Various Artists", song11.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song11.getAlbum().getRating());
		assertEquals(
				"Junkie XL/Don Davis Feat. Keanu Reeves And Laurence Fishburne",
				song11.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song11,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255634169000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song11.getGenre());
		assertEquals("Red Pill, Blue Pill", song11.getName());
		assertEquals(4, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song11.getAlbum());
		assertEquals(2003, song11.getYear());
	}

	@Test
	public void testAlbum4Song12Correct() {
		Album album = stubArtistRepository.getVariousArtistsAnimatrix();
		Song song12 = getSongByTitle(album, "The Real");
		assertNotNull(song12);

		assertEquals("Animatrix: The Album", song12.getAlbum().getName());
		assertEquals("Various Artists", song12.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song12.getAlbum().getRating());
		assertEquals(
				"Tech Itch/Don Davis Feat. Laurence Fishburne & Lambert Wilson",
				song12.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song12,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1255634217000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1255631063000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Soundtrack", song12.getGenre());
		assertEquals("The Real", song12.getName());
		assertEquals(5, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song12.getAlbum());
		assertEquals(2003, song12.getYear());
	}

	private Song getSongByTitle(Album album, String songName) {
		for (Song song : album.getSongs()) {
			if (song.getName().equals(songName)) {
				return song;
			}
		}
		return null;
	}

	private Song getSongByTitleAndVideo(Album album, String songName,
			boolean hasVideo) {
		for (Song song : album.getSongs()) {
			if (song.getName().equals(songName) && song.hasVideo() == hasVideo) {
				return song;
			}
		}
		return null;
	}

}
