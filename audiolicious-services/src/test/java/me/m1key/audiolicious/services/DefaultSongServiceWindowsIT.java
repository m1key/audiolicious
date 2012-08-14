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
import me.m1key.audiolicious.objecthandler.handlers.StatefulSongService;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Ignore
public class DefaultSongServiceWindowsIT {

	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/WindowsExportedLibrary-2011-06-18.xml";

	@EJB
	private LibraryImporter libraryImport;
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
						DefaultSongServiceWindowsIT.class.getSimpleName()
								+ ".war")
				.addAsResource(
						new File(
								"../audiolicious-object-mappers/src/main/resources/englishValues.properties"),
						"englishValues.properties")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsResource("log4j.xml", "log4j.xml")
				.addClasses(AggregateTrackMapper.class, Album.class,
						AlbumAndSongInfo.class, AlbumInfo.class,
						ApplicationConversation.class, Artist.class,
						ArtistRepository.class, AudiobookHandler.class,
						AudiobookMapper.class, AudiobookTo.class,
						CacheableSongService.class,
						CacheAwareLibraryImporter.class,
						CannotMapTrackValuesException.class,
						DefaultAggregateTrackMapper.class,
						DefaultApplicationConversation.class,
						DefaultAudiobookMapper.class,
						DefaultEnglishValuesProvider.class,
						DataExtractor.class, DefaultNoopTrackHandler.class,
						DefaultObjectTrackDataHandler.class,
						DefaultPodcastMapper.class,
						DefaultRawTrackDataHandler.class,
						DefaultSongHandler.class, DefaultSongMapper.class,
						DefaultVideoMapper.class, EnglishValuesProvider.class,
						I18nDataExtractor.class,
						InMemoryCacheableSongService.class, Library.class,
						LibraryImporter.class, LibraryParser.class,
						LibraryService.class, LibraryRepository.class,
						LibraryTo.class, NonAggregateTrackMapper.class,
						NoopTrackHandler.class, NullArtist.class,
						NullEntitiesFactory.class, NullLibrary.class,
						ObjectMappingException.class,
						ObjectTrackDataHandler.class, PodcastHandler.class,
						PodcastMapper.class, PodcastTo.class, Rating.class,
						RatingTo.class, RawTrackDataHandler.class,
						ServicesTestHelperBean.class, Song.class,
						SongHandler.class, SongInfo.class, SongMapper.class,
						SongTo.class, Stat.class, StatefulSongService.class,
						StatInfo.class, StubArtistRepository.class,
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
			libraryImport.importLibrary(xmlLibraryFile);
			handlerHasNotRunYet = false;
		}
	}

	@Test
	public void testAlbum1Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		assertNotNull(album);
		assertEquals("Monster Magnet", album.getArtist().getName());
		assertEquals("Spine Of God", album.getName());
		assertEquals(9, album.getSongs().size());
	}

	@Test
	public void testAlbum1Song1Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song1 = getSongByTitle(album, "Pill Shovel");
		assertNotNull(song1);

		assertEquals("Spine Of God", song1.getAlbum().getName());
		assertEquals("Monster Magnet", song1.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song1.getAlbum().getRating());
		assertEquals("Monster Magnet", song1.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song1,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song1.getGenre());
		assertEquals("Pill Shovel", song1.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song1.getAlbum());
		assertEquals(1992, song1.getYear());
	}

	@Test
	public void testAlbum1Song2Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song2 = getSongByTitle(album, "Medicine");
		assertNotNull(song2);

		assertEquals("Spine Of God", song2.getAlbum().getName());
		assertEquals("Monster Magnet", song2.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song2.getAlbum().getRating());
		assertEquals("Monster Magnet", song2.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song2,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song2.getGenre());
		assertEquals("Medicine", song2.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song2.getAlbum());
		assertEquals(1992, song2.getYear());
	}

	@Test
	public void testAlbum1Song3Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song3 = getSongByTitle(album, "Nod Scene");
		assertNotNull(song3);

		assertEquals("Spine Of God", song3.getAlbum().getName());
		assertEquals("Monster Magnet", song3.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song3.getAlbum().getRating());
		assertEquals("Monster Magnet", song3.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song3,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song3.getGenre());
		assertEquals("Nod Scene", song3.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song3.getAlbum());
		assertEquals(1992, song3.getYear());
	}

	@Test
	public void testAlbum1Song4Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song4 = getSongByTitle(album, "Black Mastermind");
		assertNotNull(song4);

		assertEquals("Spine Of God", song4.getAlbum().getName());
		assertEquals("Monster Magnet", song4.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song4.getAlbum().getRating());
		assertEquals("Monster Magnet", song4.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song4,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song4.getGenre());
		assertEquals("Black Mastermind", song4.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song4.getAlbum());
		assertEquals(1992, song4.getYear());
	}

	@Test
	public void testAlbum1Song5Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song5 = getSongByTitle(album, "Zodiac Lung");
		assertNotNull(song5);

		assertEquals("Spine Of God", song5.getAlbum().getName());
		assertEquals("Monster Magnet", song5.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song5.getAlbum().getRating());
		assertEquals("Monster Magnet", song5.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song5,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song5.getGenre());
		assertEquals("Zodiac Lung", song5.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song5.getAlbum());
		assertEquals(1992, song5.getYear());
	}

	@Test
	public void testAlbum1Song6Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song6 = getSongByTitle(album, "Spine of God");
		assertNotNull(song6);

		assertEquals("Spine Of God", song6.getAlbum().getName());
		assertEquals("Monster Magnet", song6.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song6.getAlbum().getRating());
		assertEquals("Monster Magnet", song6.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song6,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song6.getGenre());
		assertEquals("Spine of God", song6.getName());
		assertEquals(2, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song6.getAlbum());
		assertEquals(1992, song6.getYear());
	}

	@Test
	public void testAlbum1Song7Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song7 = getSongByTitle(album, "Snake Dance");
		assertNotNull(song7);

		assertEquals("Spine Of God", song7.getAlbum().getName());
		assertEquals("Monster Magnet", song7.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song7.getAlbum().getRating());
		assertEquals("Monster Magnet", song7.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song7,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song7.getGenre());
		assertEquals("Snake Dance", song7.getName());
		assertEquals(2, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song7.getAlbum());
		assertEquals(1992, song7.getYear());
	}

	@Test
	public void testAlbum1Song8Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song8 = getSongByTitle(album, "Sin's a Good Man's Brother");
		assertNotNull(song8);

		assertEquals("Spine Of God", song8.getAlbum().getName());
		assertEquals("Monster Magnet", song8.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song8.getAlbum().getRating());
		assertEquals("Monster Magnet", song8.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song8,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song8.getGenre());
		assertEquals("Sin's a Good Man's Brother", song8.getName());
		assertEquals(3, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song8.getAlbum());
		assertEquals(1992, song8.getYear());
	}

	@Test
	public void testAlbum1Song9Correct() {
		Album album = stubArtistRepository.getMonsterMagnetSpineOfGod();
		Song song9 = getSongByTitle(album, "Ozium");
		assertNotNull(song9);

		assertEquals("Spine Of God", song9.getAlbum().getName());
		assertEquals("Monster Magnet", song9.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song9.getAlbum().getRating());
		assertEquals("Monster Magnet", song9.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song9,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381386000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1261327910000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song9.getGenre());
		assertEquals("Ozium", song9.getName());
		assertEquals(2, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song9.getAlbum());
		assertEquals(1992, song9.getYear());
	}

	@Test
	public void testAlbum2Correct() {
		Album album = stubArtistRepository.getToolAenima();
		assertNotNull(album);
		assertEquals("Tool", album.getArtist().getName());
		assertEquals("Ænima", album.getName());
		assertEquals(15, album.getSongs().size());
	}

	@Test
	public void testAlbum2Song1Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song1 = getSongByTitle(album, "Stinkfist");
		assertNotNull(song1);

		assertEquals("Ænima", song1.getAlbum().getName());
		assertEquals("Tool", song1.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song1.getAlbum().getRating());
		assertEquals("Tool", song1.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song1,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song1.getGenre());
		assertEquals("Stinkfist", song1.getName());
		assertEquals(4, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(100), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song1.getAlbum());
		assertEquals(1996, song1.getYear());
	}

	@Test
	public void testAlbum2Song2Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song2 = getSongByTitle(album, "Eulogy");
		assertNotNull(song2);

		assertEquals("Ænima", song2.getAlbum().getName());
		assertEquals("Tool", song2.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song2.getAlbum().getRating());
		assertEquals("Tool", song2.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song2,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song2.getGenre());
		assertEquals("Eulogy", song2.getName());
		assertEquals(2, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song2.getAlbum());
		assertEquals(1996, song2.getYear());
	}

	@Test
	public void testAlbum2Song3Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song3 = getSongByTitle(album, "H.");
		assertNotNull(song3);

		assertEquals("Ænima", song3.getAlbum().getName());
		assertEquals("Tool", song3.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song3.getAlbum().getRating());
		assertEquals("Tool", song3.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song3,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song3.getGenre());
		assertEquals("H.", song3.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song3.getAlbum());
		assertEquals(1996, song3.getYear());
	}

	@Test
	public void testAlbum2Song4Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song4 = getSongByTitle(album, "Useful Idiot");
		assertNotNull(song4);

		assertEquals("Ænima", song4.getAlbum().getName());
		assertEquals("Tool", song4.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song4.getAlbum().getRating());
		assertEquals("Tool", song4.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song4,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song4.getGenre());
		assertEquals("Useful Idiot", song4.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song4.getAlbum());
		assertEquals(1996, song4.getYear());
	}

	@Test
	public void testAlbum2Song5Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song5 = getSongByTitle(album, "Forty Six & 2");
		assertNotNull(song5);

		assertEquals("Ænima", song5.getAlbum().getName());
		assertEquals("Tool", song5.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song5.getAlbum().getRating());
		assertEquals("Tool", song5.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song5,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song5.getGenre());
		assertEquals("Forty Six & 2", song5.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song5.getAlbum());
		assertEquals(1996, song5.getYear());
	}

	@Test
	public void testAlbum2Song6Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song6 = getSongByTitle(album, "Message To Harry Manback");
		assertNotNull(song6);

		assertEquals("Ænima", song6.getAlbum().getName());
		assertEquals("Tool", song6.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song6.getAlbum().getRating());
		assertEquals("Tool", song6.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song6,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song6.getGenre());
		assertEquals("Message To Harry Manback", song6.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song6.getAlbum());
		assertEquals(1996, song6.getYear());
	}

	@Test
	public void testAlbum2Song7Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song7 = getSongByTitle(album, "Hooker With A Penis");
		assertNotNull(song7);

		assertEquals("Ænima", song7.getAlbum().getName());
		assertEquals("Tool", song7.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song7.getAlbum().getRating());
		assertEquals("Tool", song7.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song7,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song7.getGenre());
		assertEquals("Hooker With A Penis", song7.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song7.getAlbum());
		assertEquals(1996, song7.getYear());
	}

	@Test
	public void testAlbum2Song8Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song8 = getSongByTitle(album, "Intermission");
		assertNotNull(song8);

		assertEquals("Ænima", song8.getAlbum().getName());
		assertEquals("Tool", song8.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song8.getAlbum().getRating());
		assertEquals("Tool", song8.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song8,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song8.getGenre());
		assertEquals("Intermission", song8.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song8.getAlbum());
		assertEquals(1996, song8.getYear());
	}

	@Test
	public void testAlbum2Song9Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song9 = getSongByTitle(album, "Jimmy");
		assertNotNull(song9);

		assertEquals("Ænima", song9.getAlbum().getName());
		assertEquals("Tool", song9.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song9.getAlbum().getRating());
		assertEquals("Tool", song9.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song9,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song9.getGenre());
		assertEquals("Jimmy", song9.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song9.getAlbum());
		assertEquals(1996, song9.getYear());
	}

	@Test
	public void testAlbum2Song10Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song10 = getSongByTitle(album, "Die Eier Von Satan");
		assertNotNull(song10);

		assertEquals("Ænima", song10.getAlbum().getName());
		assertEquals("Tool", song10.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song10.getAlbum().getRating());
		assertEquals("Tool", song10.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song10,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song10.getGenre());
		assertEquals("Die Eier Von Satan", song10.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song10.getAlbum());
		assertEquals(1996, song10.getYear());
	}

	@Test
	public void testAlbum2Song11Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song11 = getSongByTitle(album, "Pushit");
		assertNotNull(song11);

		assertEquals("Ænima", song11.getAlbum().getName());
		assertEquals("Tool", song11.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song11.getAlbum().getRating());
		assertEquals("Tool", song11.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song11,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song11.getGenre());
		assertEquals("Pushit", song11.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song11.getAlbum());
		assertEquals(1996, song11.getYear());
	}

	@Test
	public void testAlbum2Song12Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song12 = getSongByTitle(album, "Cesaro Summability");
		assertNotNull(song12);

		assertEquals("Ænima", song12.getAlbum().getName());
		assertEquals("Tool", song12.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song12.getAlbum().getRating());
		assertEquals("Tool", song12.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song12,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song12.getGenre());
		assertEquals("Cesaro Summability", song12.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song12.getAlbum());
		assertEquals(1996, song12.getYear());
	}

	@Test
	public void testAlbum2Song13Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song13 = getSongByTitle(album, "Ænema");
		assertNotNull(song13);

		assertEquals("Ænima", song13.getAlbum().getName());
		assertEquals("Tool", song13.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song13.getAlbum().getRating());
		assertEquals("Tool", song13.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song13,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song13.getGenre());
		assertEquals("Ænema", song13.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song13.getAlbum());
		assertEquals(1996, song13.getYear());
	}

	@Test
	public void testAlbum2Song14Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song14 = getSongByTitle(album, "(-) Ions");
		assertNotNull(song14);

		assertEquals("Ænima", song14.getAlbum().getName());
		assertEquals("Tool", song14.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song14.getAlbum().getRating());
		assertEquals("Tool", song14.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song14,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song14.getGenre());
		assertEquals("(-) Ions", song14.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(20), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song14.getAlbum());
		assertEquals(1996, song14.getYear());
	}

	@Test
	public void testAlbum2Song15Correct() {
		Album album = stubArtistRepository.getToolAenima();
		Song song15 = getSongByTitle(album, "Third Eye");
		assertNotNull(song15);

		assertEquals("Ænima", song15.getAlbum().getName());
		assertEquals("Tool", song15.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song15.getAlbum().getRating());
		assertEquals("Tool", song15.getArtistName());
		Set<Stat> stats = servicesTestHelperBean.getStats(song15,
				libraryService.getByUuid("Library UUID"));
		assertEquals(1279381466000L, stats.iterator().next().getDateAdded()
				.getTime());
		assertEquals(1276755858000L, stats.iterator().next().getDateModified()
				.getTime());
		assertEquals("Rock", song15.getGenre());
		assertEquals("Third Eye", song15.getName());
		assertEquals(1, stats.iterator().next().getPlayCount());
		assertEquals(new Rating(80), stats.iterator().next().getRating());
		assertEquals(0, stats.iterator().next().getSkipCount());
		assertEquals(null, stats.iterator().next().getDateSkipped());
		assertEquals(album, song15.getAlbum());
		assertEquals(1996, song15.getYear());
	}

	@Test
	public void testCorrectNumberOfSongsInKornIssues() {
		assertEquals(
				Integer.valueOf(16),
				Integer.valueOf(stubArtistRepository.getKornIssues().getSongs()
						.size()));
	}

	private Song getSongByTitle(Album album, String songName) {
		for (Song song : album.getSongs()) {
			if (song.getName().equals(songName)) {
				return song;
			}
		}
		return null;
	}
}
