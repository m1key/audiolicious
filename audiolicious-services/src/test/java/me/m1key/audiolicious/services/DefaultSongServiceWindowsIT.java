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

import javax.inject.Inject;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.commons.qualifiers.NoopHandler;
import me.m1key.audiolicious.commons.qualifiers.NullAlbum;
import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;
import me.m1key.audiolicious.domain.entities.Rating;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.TrackToType;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.libraryparser.LibraryParser;
import me.m1key.audiolicious.libraryparser.RawTrackDataCallback;
import me.m1key.audiolicious.libraryparser.VtdItunesLibraryParserCdiAlternative;
import me.m1key.audiolicious.libraryparser.XmlParseException;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.ObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.RawTrackDataHandler;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.factories.TrackHandlersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.SongHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.SongService;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapperBean;
import me.m1key.audiolicious.objectmapper.CannotMapTrackValuesException;
import me.m1key.audiolicious.objectmapper.ObjectMappingException;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.extractor.DataExtractor;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.EnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractor;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.NonAggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.PodcastMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.SongMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.VideoMapperCdiAlternative;

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
public class DefaultSongServiceWindowsIT {

	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/WindowsExportedLibrary-2011-06-18.xml";
	@Inject
	private ObjectTrackDataHandler handler;
	@Inject
	private StubSongRepository stubSongRepository;
	@Inject
	private StubAlbumRepository stubAlbumRepository;

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
				.addClasses(AggregateTrackMapper.class,
						AggregateTrackMapperBean.class, Album.class,
						AlbumRepository.class, Artist.class,
						ArtistRepository.class, AudiobookTo.class,
						AudiobookMapperCdiAlternative.class,
						CannotMapTrackValuesException.class,
						DataExtractor.class,
						DefaultEnglishValuesProvider.class,
						DefaultObjectTrackDataHandler.class,
						DefaultSongServiceCdiAlternative.class,
						EnglishValuesProvider.class, I18nDataExtractor.class,
						LibraryParser.class, NonAggregateTrackMapper.class,
						NoopHandler.class,
						NoopTrackHandlerCdiAlternative.class, NullAlbum.class,
						NullArtist.class, NullEntitiesFactory.class,
						ObjectMappingException.class,
						ObjectTrackDataHandler.class,
						PodcastMapperCdiAlternative.class, PodcastTo.class,
						Rating.class, RatingTo.class,
						RawTrackDataCallback.class, RawTrackDataHandler.class,
						Song.class, SongHandlerCdiAlternative.class,
						SongMapperCdiAlternative.class, SongRepository.class,
						SongService.class, SongTo.class,
						StubAlbumRepository.class, StubArtistRepository.class,
						StubSongRepository.class, TrackHandler.class,
						TrackHandlersFactoryCdiAlternative.class,
						TrackMapper.class,
						TrackMappersFactoryCdiAlternative.class, TrackTo.class,
						TrackToType.class, VideoMapperCdiAlternative.class,
						VideoTo.class,
						VtdItunesLibraryParserCdiAlternative.class,
						XmlNodeName.class, XmlParseException.class)
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
			handler.execute(xmlLibraryFile);
			handlerHasNotRunYet = false;
		}
	}

	@Test
	public void testAlbum1Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		assertNotNull(album);
		assertNotNull(album.getUuid());
		assertEquals("Monster Magnet", album.getArtist().getName());
		assertEquals("Spine Of God", album.getName());
		assertEquals(9, album.getSongs().size());
	}

	@Test
	public void testAlbum1Song1Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song1 = getSongByTitle(album, "Pill Shovel");
		assertNotNull(song1);

		assertEquals("Spine Of God", song1.getAlbum().getName());
		assertEquals("Monster Magnet", song1.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song1.getAlbum().getRating());
		assertEquals("Monster Magnet", song1.getArtistName());
		assertEquals("John McBain/David Wyndorf", song1.getComposer());
		assertEquals(1279381386000L, song1.getDateAdded().getTime());
		assertEquals(1261327910000L, song1.getDateModified().getTime());
		assertEquals("Rock", song1.getGenre());
		assertEquals("Pill Shovel", song1.getName());
		assertEquals(1, song1.getPlayCount());
		assertEquals(new Rating(80), song1.getRating());
		assertEquals(0, song1.getSkipCount());
		assertEquals(null, song1.getDateSkipped());
		assertEquals(album, song1.getAlbum());
		assertEquals(0, song1.getVideoHeight());
		assertEquals(0, song1.getVideoWidth());
		assertEquals(1992, song1.getYear());
	}

	@Test
	public void testAlbum1Song2Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song2 = getSongByTitle(album, "Medicine");
		assertNotNull(song2);

		assertEquals("Spine Of God", song2.getAlbum().getName());
		assertEquals("Monster Magnet", song2.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song2.getAlbum().getRating());
		assertEquals("Monster Magnet", song2.getArtistName());
		assertEquals(null, song2.getComposer());
		assertEquals(1279381386000L, song2.getDateAdded().getTime());
		assertEquals(1261327910000L, song2.getDateModified().getTime());
		assertEquals("Rock", song2.getGenre());
		assertEquals("Medicine", song2.getName());
		assertEquals(1, song2.getPlayCount());
		assertEquals(new Rating(80), song2.getRating());
		assertEquals(0, song2.getSkipCount());
		assertEquals(null, song2.getDateSkipped());
		assertEquals(album, song2.getAlbum());
		assertEquals(0, song2.getVideoHeight());
		assertEquals(0, song2.getVideoWidth());
		assertEquals(1992, song2.getYear());
	}

	@Test
	public void testAlbum1Song3Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song3 = getSongByTitle(album, "Nod Scene");
		assertNotNull(song3);

		assertEquals("Spine Of God", song3.getAlbum().getName());
		assertEquals("Monster Magnet", song3.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song3.getAlbum().getRating());
		assertEquals("Monster Magnet", song3.getArtistName());
		assertEquals(null, song3.getComposer());
		assertEquals(1279381386000L, song3.getDateAdded().getTime());
		assertEquals(1261327910000L, song3.getDateModified().getTime());
		assertEquals("Rock", song3.getGenre());
		assertEquals("Nod Scene", song3.getName());
		assertEquals(1, song3.getPlayCount());
		assertEquals(new Rating(100), song3.getRating());
		assertEquals(0, song3.getSkipCount());
		assertEquals(null, song3.getDateSkipped());
		assertEquals(album, song3.getAlbum());
		assertEquals(0, song3.getVideoHeight());
		assertEquals(0, song3.getVideoWidth());
		assertEquals(1992, song3.getYear());
	}

	@Test
	public void testAlbum1Song4Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song4 = getSongByTitle(album, "Black Mastermind");
		assertNotNull(song4);

		assertEquals("Spine Of God", song4.getAlbum().getName());
		assertEquals("Monster Magnet", song4.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song4.getAlbum().getRating());
		assertEquals("Monster Magnet", song4.getArtistName());
		assertEquals("John McBain/David Wyndorf", song4.getComposer());
		assertEquals(1279381386000L, song4.getDateAdded().getTime());
		assertEquals(1261327910000L, song4.getDateModified().getTime());
		assertEquals("Rock", song4.getGenre());
		assertEquals("Black Mastermind", song4.getName());
		assertEquals(1, song4.getPlayCount());
		assertEquals(new Rating(100), song4.getRating());
		assertEquals(0, song4.getSkipCount());
		assertEquals(null, song4.getDateSkipped());
		assertEquals(album, song4.getAlbum());
		assertEquals(0, song4.getVideoHeight());
		assertEquals(0, song4.getVideoWidth());
		assertEquals(1992, song4.getYear());
	}

	@Test
	public void testAlbum1Song5Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song5 = getSongByTitle(album, "Zodiac Lung");
		assertNotNull(song5);

		assertEquals("Spine Of God", song5.getAlbum().getName());
		assertEquals("Monster Magnet", song5.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song5.getAlbum().getRating());
		assertEquals("Monster Magnet", song5.getArtistName());
		assertEquals("David Wyndorf", song5.getComposer());
		assertEquals(1279381386000L, song5.getDateAdded().getTime());
		assertEquals(1261327910000L, song5.getDateModified().getTime());
		assertEquals("Rock", song5.getGenre());
		assertEquals("Zodiac Lung", song5.getName());
		assertEquals(1, song5.getPlayCount());
		assertEquals(new Rating(100), song5.getRating());
		assertEquals(0, song5.getSkipCount());
		assertEquals(null, song5.getDateSkipped());
		assertEquals(album, song5.getAlbum());
		assertEquals(0, song5.getVideoHeight());
		assertEquals(0, song5.getVideoWidth());
		assertEquals(1992, song5.getYear());
	}

	@Test
	public void testAlbum1Song6Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song6 = getSongByTitle(album, "Spine of God");
		assertNotNull(song6);

		assertEquals("Spine Of God", song6.getAlbum().getName());
		assertEquals("Monster Magnet", song6.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song6.getAlbum().getRating());
		assertEquals("Monster Magnet", song6.getArtistName());
		assertEquals(null, song6.getComposer());
		assertEquals(1279381386000L, song6.getDateAdded().getTime());
		assertEquals(1261327910000L, song6.getDateModified().getTime());
		assertEquals("Rock", song6.getGenre());
		assertEquals("Spine of God", song6.getName());
		assertEquals(2, song6.getPlayCount());
		assertEquals(new Rating(100), song6.getRating());
		assertEquals(0, song6.getSkipCount());
		assertEquals(null, song6.getDateSkipped());
		assertEquals(album, song6.getAlbum());
		assertEquals(0, song6.getVideoHeight());
		assertEquals(0, song6.getVideoWidth());
		assertEquals(1992, song6.getYear());
	}

	@Test
	public void testAlbum1Song7Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song7 = getSongByTitle(album, "Snake Dance");
		assertNotNull(song7);

		assertEquals("Spine Of God", song7.getAlbum().getName());
		assertEquals("Monster Magnet", song7.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song7.getAlbum().getRating());
		assertEquals("Monster Magnet", song7.getArtistName());
		assertEquals(null, song7.getComposer());
		assertEquals(1279381386000L, song7.getDateAdded().getTime());
		assertEquals(1261327910000L, song7.getDateModified().getTime());
		assertEquals("Rock", song7.getGenre());
		assertEquals("Snake Dance", song7.getName());
		assertEquals(2, song7.getPlayCount());
		assertEquals(new Rating(100), song7.getRating());
		assertEquals(0, song7.getSkipCount());
		assertEquals(null, song7.getDateSkipped());
		assertEquals(album, song7.getAlbum());
		assertEquals(0, song7.getVideoHeight());
		assertEquals(0, song7.getVideoWidth());
		assertEquals(1992, song7.getYear());
	}

	@Test
	public void testAlbum1Song8Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song8 = getSongByTitle(album, "Sin's a Good Man's Brother");
		assertNotNull(song8);

		assertEquals("Spine Of God", song8.getAlbum().getName());
		assertEquals("Monster Magnet", song8.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song8.getAlbum().getRating());
		assertEquals("Monster Magnet", song8.getArtistName());
		assertEquals(null, song8.getComposer());
		assertEquals(1279381386000L, song8.getDateAdded().getTime());
		assertEquals(1261327910000L, song8.getDateModified().getTime());
		assertEquals("Rock", song8.getGenre());
		assertEquals("Sin's a Good Man's Brother", song8.getName());
		assertEquals(3, song8.getPlayCount());
		assertEquals(new Rating(100), song8.getRating());
		assertEquals(0, song8.getSkipCount());
		assertEquals(null, song8.getDateSkipped());
		assertEquals(album, song8.getAlbum());
		assertEquals(0, song8.getVideoHeight());
		assertEquals(0, song8.getVideoWidth());
		assertEquals(1992, song8.getYear());
	}

	@Test
	public void testAlbum1Song9Correct() {
		Album album = stubAlbumRepository.getMonsterMagnetSpineOfGod();
		Song song9 = getSongByTitle(album, "Ozium");
		assertNotNull(song9);

		assertEquals("Spine Of God", song9.getAlbum().getName());
		assertEquals("Monster Magnet", song9.getAlbum().getArtist().getName());
		assertEquals(new Rating(100), song9.getAlbum().getRating());
		assertEquals("Monster Magnet", song9.getArtistName());
		assertEquals(null, song9.getComposer());
		assertEquals(1279381386000L, song9.getDateAdded().getTime());
		assertEquals(1261327910000L, song9.getDateModified().getTime());
		assertEquals("Rock", song9.getGenre());
		assertEquals("Ozium", song9.getName());
		assertEquals(2, song9.getPlayCount());
		assertEquals(new Rating(100), song9.getRating());
		assertEquals(0, song9.getSkipCount());
		assertEquals(null, song9.getDateSkipped());
		assertEquals(album, song9.getAlbum());
		assertEquals(0, song9.getVideoHeight());
		assertEquals(0, song9.getVideoWidth());
		assertEquals(1992, song9.getYear());
	}

	@Test
	public void testAlbum2Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		assertNotNull(album);
		assertNotNull(album.getUuid());
		assertEquals("Tool", album.getArtist().getName());
		assertEquals("Ænima", album.getName());
		assertEquals(15, album.getSongs().size());
	}

	@Test
	public void testAlbum2Song1Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song1 = getSongByTitle(album, "Stinkfist");
		assertNotNull(song1);

		assertEquals("Ænima", song1.getAlbum().getName());
		assertEquals("Tool", song1.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song1.getAlbum().getRating());
		assertEquals("Tool", song1.getArtistName());
		assertEquals("Tool", song1.getComposer());
		assertEquals(1279381466000L, song1.getDateAdded().getTime());
		assertEquals(1276755858000L, song1.getDateModified().getTime());
		assertEquals("Rock", song1.getGenre());
		assertEquals("Stinkfist", song1.getName());
		assertEquals(4, song1.getPlayCount());
		assertEquals(new Rating(100), song1.getRating());
		assertEquals(0, song1.getSkipCount());
		assertEquals(null, song1.getDateSkipped());
		assertEquals(album, song1.getAlbum());
		assertEquals(0, song1.getVideoHeight());
		assertEquals(0, song1.getVideoWidth());
		assertEquals(1996, song1.getYear());
	}

	@Test
	public void testAlbum2Song2Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song2 = getSongByTitle(album, "Eulogy");
		assertNotNull(song2);

		assertEquals("Ænima", song2.getAlbum().getName());
		assertEquals("Tool", song2.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song2.getAlbum().getRating());
		assertEquals("Tool", song2.getArtistName());
		assertEquals("Tool", song2.getComposer());
		assertEquals(1279381466000L, song2.getDateAdded().getTime());
		assertEquals(1276755858000L, song2.getDateModified().getTime());
		assertEquals("Rock", song2.getGenre());
		assertEquals("Eulogy", song2.getName());
		assertEquals(2, song2.getPlayCount());
		assertEquals(new Rating(80), song2.getRating());
		assertEquals(0, song2.getSkipCount());
		assertEquals(null, song2.getDateSkipped());
		assertEquals(album, song2.getAlbum());
		assertEquals(0, song2.getVideoHeight());
		assertEquals(0, song2.getVideoWidth());
		assertEquals(1996, song2.getYear());
	}

	@Test
	public void testAlbum2Song3Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song3 = getSongByTitle(album, "H.");
		assertNotNull(song3);

		assertEquals("Ænima", song3.getAlbum().getName());
		assertEquals("Tool", song3.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song3.getAlbum().getRating());
		assertEquals("Tool", song3.getArtistName());
		assertEquals("Tool", song3.getComposer());
		assertEquals(1279381466000L, song3.getDateAdded().getTime());
		assertEquals(1276755858000L, song3.getDateModified().getTime());
		assertEquals("Rock", song3.getGenre());
		assertEquals("H.", song3.getName());
		assertEquals(1, song3.getPlayCount());
		assertEquals(new Rating(80), song3.getRating());
		assertEquals(0, song3.getSkipCount());
		assertEquals(null, song3.getDateSkipped());
		assertEquals(album, song3.getAlbum());
		assertEquals(0, song3.getVideoHeight());
		assertEquals(0, song3.getVideoWidth());
		assertEquals(1996, song3.getYear());
	}

	@Test
	public void testAlbum2Song4Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song4 = getSongByTitle(album, "Useful Idiot");
		assertNotNull(song4);

		assertEquals("Ænima", song4.getAlbum().getName());
		assertEquals("Tool", song4.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song4.getAlbum().getRating());
		assertEquals("Tool", song4.getArtistName());
		assertEquals("Tool", song4.getComposer());
		assertEquals(1279381466000L, song4.getDateAdded().getTime());
		assertEquals(1276755858000L, song4.getDateModified().getTime());
		assertEquals("Rock", song4.getGenre());
		assertEquals("Useful Idiot", song4.getName());
		assertEquals(1, song4.getPlayCount());
		assertEquals(new Rating(80), song4.getRating());
		assertEquals(0, song4.getSkipCount());
		assertEquals(null, song4.getDateSkipped());
		assertEquals(album, song4.getAlbum());
		assertEquals(0, song4.getVideoHeight());
		assertEquals(0, song4.getVideoWidth());
		assertEquals(1996, song4.getYear());
	}

	@Test
	public void testAlbum2Song5Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song5 = getSongByTitle(album, "Forty Six & 2");
		assertNotNull(song5);

		assertEquals("Ænima", song5.getAlbum().getName());
		assertEquals("Tool", song5.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song5.getAlbum().getRating());
		assertEquals("Tool", song5.getArtistName());
		assertEquals("Tool", song5.getComposer());
		assertEquals(1279381466000L, song5.getDateAdded().getTime());
		assertEquals(1276755858000L, song5.getDateModified().getTime());
		assertEquals("Rock", song5.getGenre());
		assertEquals("Forty Six & 2", song5.getName());
		assertEquals(1, song5.getPlayCount());
		assertEquals(new Rating(80), song5.getRating());
		assertEquals(0, song5.getSkipCount());
		assertEquals(null, song5.getDateSkipped());
		assertEquals(album, song5.getAlbum());
		assertEquals(0, song5.getVideoHeight());
		assertEquals(0, song5.getVideoWidth());
		assertEquals(1996, song5.getYear());
	}

	@Test
	public void testAlbum2Song6Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song6 = getSongByTitle(album, "Message To Harry Manback");
		assertNotNull(song6);

		assertEquals("Ænima", song6.getAlbum().getName());
		assertEquals("Tool", song6.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song6.getAlbum().getRating());
		assertEquals("Tool", song6.getArtistName());
		assertEquals("Tool", song6.getComposer());
		assertEquals(1279381466000L, song6.getDateAdded().getTime());
		assertEquals(1276755858000L, song6.getDateModified().getTime());
		assertEquals("Rock", song6.getGenre());
		assertEquals("Message To Harry Manback", song6.getName());
		assertEquals(1, song6.getPlayCount());
		assertEquals(new Rating(80), song6.getRating());
		assertEquals(0, song6.getSkipCount());
		assertEquals(null, song6.getDateSkipped());
		assertEquals(album, song6.getAlbum());
		assertEquals(0, song6.getVideoHeight());
		assertEquals(0, song6.getVideoWidth());
		assertEquals(1996, song6.getYear());
	}

	@Test
	public void testAlbum2Song7Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song7 = getSongByTitle(album, "Hooker With A Penis");
		assertNotNull(song7);

		assertEquals("Ænima", song7.getAlbum().getName());
		assertEquals("Tool", song7.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song7.getAlbum().getRating());
		assertEquals("Tool", song7.getArtistName());
		assertEquals("Tool", song7.getComposer());
		assertEquals(1279381466000L, song7.getDateAdded().getTime());
		assertEquals(1276755858000L, song7.getDateModified().getTime());
		assertEquals("Rock", song7.getGenre());
		assertEquals("Hooker With A Penis", song7.getName());
		assertEquals(1, song7.getPlayCount());
		assertEquals(new Rating(80), song7.getRating());
		assertEquals(0, song7.getSkipCount());
		assertEquals(null, song7.getDateSkipped());
		assertEquals(album, song7.getAlbum());
		assertEquals(0, song7.getVideoHeight());
		assertEquals(0, song7.getVideoWidth());
		assertEquals(1996, song7.getYear());
	}

	@Test
	public void testAlbum2Song8Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song8 = getSongByTitle(album, "Intermission");
		assertNotNull(song8);

		assertEquals("Ænima", song8.getAlbum().getName());
		assertEquals("Tool", song8.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song8.getAlbum().getRating());
		assertEquals("Tool", song8.getArtistName());
		assertEquals("Tool", song8.getComposer());
		assertEquals(1279381466000L, song8.getDateAdded().getTime());
		assertEquals(1276755858000L, song8.getDateModified().getTime());
		assertEquals("Rock", song8.getGenre());
		assertEquals("Intermission", song8.getName());
		assertEquals(1, song8.getPlayCount());
		assertEquals(new Rating(80), song8.getRating());
		assertEquals(0, song8.getSkipCount());
		assertEquals(null, song8.getDateSkipped());
		assertEquals(album, song8.getAlbum());
		assertEquals(0, song8.getVideoHeight());
		assertEquals(0, song8.getVideoWidth());
		assertEquals(1996, song8.getYear());
	}

	@Test
	public void testAlbum2Song9Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song9 = getSongByTitle(album, "Jimmy");
		assertNotNull(song9);

		assertEquals("Ænima", song9.getAlbum().getName());
		assertEquals("Tool", song9.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song9.getAlbum().getRating());
		assertEquals("Tool", song9.getArtistName());
		assertEquals("Tool", song9.getComposer());
		assertEquals(1279381466000L, song9.getDateAdded().getTime());
		assertEquals(1276755858000L, song9.getDateModified().getTime());
		assertEquals("Rock", song9.getGenre());
		assertEquals("Jimmy", song9.getName());
		assertEquals(1, song9.getPlayCount());
		assertEquals(new Rating(80), song9.getRating());
		assertEquals(0, song9.getSkipCount());
		assertEquals(null, song9.getDateSkipped());
		assertEquals(album, song9.getAlbum());
		assertEquals(0, song9.getVideoHeight());
		assertEquals(0, song9.getVideoWidth());
		assertEquals(1996, song9.getYear());
	}

	@Test
	public void testAlbum2Song10Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song10 = getSongByTitle(album, "Die Eier Von Satan");
		assertNotNull(song10);

		assertEquals("Ænima", song10.getAlbum().getName());
		assertEquals("Tool", song10.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song10.getAlbum().getRating());
		assertEquals("Tool", song10.getArtistName());
		assertEquals("Tool", song10.getComposer());
		assertEquals(1279381466000L, song10.getDateAdded().getTime());
		assertEquals(1276755858000L, song10.getDateModified().getTime());
		assertEquals("Rock", song10.getGenre());
		assertEquals("Die Eier Von Satan", song10.getName());
		assertEquals(1, song10.getPlayCount());
		assertEquals(new Rating(80), song10.getRating());
		assertEquals(0, song10.getSkipCount());
		assertEquals(null, song10.getDateSkipped());
		assertEquals(album, song10.getAlbum());
		assertEquals(0, song10.getVideoHeight());
		assertEquals(0, song10.getVideoWidth());
		assertEquals(1996, song10.getYear());
	}

	@Test
	public void testAlbum2Song11Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song11 = getSongByTitle(album, "Pushit");
		assertNotNull(song11);

		assertEquals("Ænima", song11.getAlbum().getName());
		assertEquals("Tool", song11.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song11.getAlbum().getRating());
		assertEquals("Tool", song11.getArtistName());
		assertEquals("Tool", song11.getComposer());
		assertEquals(1279381466000L, song11.getDateAdded().getTime());
		assertEquals(1276755858000L, song11.getDateModified().getTime());
		assertEquals("Rock", song11.getGenre());
		assertEquals("Pushit", song11.getName());
		assertEquals(1, song11.getPlayCount());
		assertEquals(new Rating(80), song11.getRating());
		assertEquals(0, song11.getSkipCount());
		assertEquals(null, song11.getDateSkipped());
		assertEquals(album, song11.getAlbum());
		assertEquals(0, song11.getVideoHeight());
		assertEquals(0, song11.getVideoWidth());
		assertEquals(1996, song11.getYear());
	}

	@Test
	public void testAlbum2Song12Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song12 = getSongByTitle(album, "Cesaro Summability");
		assertNotNull(song12);

		assertEquals("Ænima", song12.getAlbum().getName());
		assertEquals("Tool", song12.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song12.getAlbum().getRating());
		assertEquals("Tool", song12.getArtistName());
		assertEquals("Tool", song12.getComposer());
		assertEquals(1279381466000L, song12.getDateAdded().getTime());
		assertEquals(1276755858000L, song12.getDateModified().getTime());
		assertEquals("Rock", song12.getGenre());
		assertEquals("Cesaro Summability", song12.getName());
		assertEquals(1, song12.getPlayCount());
		assertEquals(new Rating(80), song12.getRating());
		assertEquals(0, song12.getSkipCount());
		assertEquals(null, song12.getDateSkipped());
		assertEquals(album, song12.getAlbum());
		assertEquals(0, song12.getVideoHeight());
		assertEquals(0, song12.getVideoWidth());
		assertEquals(1996, song12.getYear());
	}

	@Test
	public void testAlbum2Song13Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song13 = getSongByTitle(album, "Ænema");
		assertNotNull(song13);

		assertEquals("Ænima", song13.getAlbum().getName());
		assertEquals("Tool", song13.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song13.getAlbum().getRating());
		assertEquals("Tool", song13.getArtistName());
		assertEquals("Tool", song13.getComposer());
		assertEquals(1279381466000L, song13.getDateAdded().getTime());
		assertEquals(1276755858000L, song13.getDateModified().getTime());
		assertEquals("Rock", song13.getGenre());
		assertEquals("Ænema", song13.getName());
		assertEquals(1, song13.getPlayCount());
		assertEquals(new Rating(80), song13.getRating());
		assertEquals(0, song13.getSkipCount());
		assertEquals(null, song13.getDateSkipped());
		assertEquals(album, song13.getAlbum());
		assertEquals(0, song13.getVideoHeight());
		assertEquals(0, song13.getVideoWidth());
		assertEquals(1996, song13.getYear());
	}

	@Test
	public void testAlbum2Song14Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song14 = getSongByTitle(album, "(-) Ions");
		assertNotNull(song14);

		assertEquals("Ænima", song14.getAlbum().getName());
		assertEquals("Tool", song14.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song14.getAlbum().getRating());
		assertEquals("Tool", song14.getArtistName());
		assertEquals("Tool", song14.getComposer());
		assertEquals(1279381466000L, song14.getDateAdded().getTime());
		assertEquals(1276755858000L, song14.getDateModified().getTime());
		assertEquals("Rock", song14.getGenre());
		assertEquals("(-) Ions", song14.getName());
		assertEquals(1, song14.getPlayCount());
		assertEquals(new Rating(20), song14.getRating());
		assertEquals(0, song14.getSkipCount());
		assertEquals(null, song14.getDateSkipped());
		assertEquals(album, song14.getAlbum());
		assertEquals(0, song14.getVideoHeight());
		assertEquals(0, song14.getVideoWidth());
		assertEquals(1996, song14.getYear());
	}

	@Test
	public void testAlbum2Song15Correct() {
		Album album = stubAlbumRepository.getToolAenima();
		Song song15 = getSongByTitle(album, "Third Eye");
		assertNotNull(song15);

		assertEquals("Ænima", song15.getAlbum().getName());
		assertEquals("Tool", song15.getAlbum().getArtist().getName());
		assertEquals(new Rating(80), song15.getAlbum().getRating());
		assertEquals("Tool", song15.getArtistName());
		assertEquals("Tool", song15.getComposer());
		assertEquals(1279381466000L, song15.getDateAdded().getTime());
		assertEquals(1276755858000L, song15.getDateModified().getTime());
		assertEquals("Rock", song15.getGenre());
		assertEquals("Third Eye", song15.getName());
		assertEquals(1, song15.getPlayCount());
		assertEquals(new Rating(80), song15.getRating());
		assertEquals(0, song15.getSkipCount());
		assertEquals(null, song15.getDateSkipped());
		assertEquals(album, song15.getAlbum());
		assertEquals(0, song15.getVideoHeight());
		assertEquals(0, song15.getVideoWidth());
		assertEquals(1996, song15.getYear());
	}

	@Test
	public void testCorrectNumberOfSongsInKornIssues() {
		assertEquals(new Integer(16),
				stubSongRepository.getNumberOfSongs("Korn", "Issues"));
	}

	@Test
	public void testCorrectNumberOfSongsInMarilynMansonMechanicalAnimals() {
		assertEquals(new Integer(14), stubSongRepository.getNumberOfSongs(
				"Marilyn Manson", "Mechanical Animals"));
	}

	@Test
	public void testCorrectNumberOfSongsInVariousArtistsTheRoughGuideToTheMusicOfTheSahara() {
		assertEquals(new Integer(13),
				stubSongRepository.getNumberOfSongs("Various Artists",
						"The Rough Guide To The Music Of The Sahara"));
	}

	@Test
	public void testCorrectNumberOfSongsInBerlinerPhilharmonikerWagnerDeRingDerNibelungen() {
		assertEquals(new Integer(189), stubSongRepository.getNumberOfSongs(
				"Berliner Philharmoniker & Herbert von Karajan",
				"Wagner: Der Ring der Nibelungen (iTunes)"));
	}

	@Test
	public void testCorrectNumberOfAlbums() {
		assertEquals(new Integer(674), stubSongRepository.getNumberOfAlbums());
	}

	@Test
	public void testCorrectNumberOfSongsInTheRollingStonesExileOnMainSt() {
		assertEquals(new Integer(30), stubSongRepository.getNumberOfSongs(
				"The Rolling Stones", "Exile On Main St"));
	}

	@Test
	public void testCorrectNumberOfArtists() {
		assertEquals(new Integer(422), stubSongRepository.getNumberOfArtists());
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
