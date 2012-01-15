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

package me.m1key.audiolicious.repositories;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

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
import me.m1key.audiolicious.services.ArtistRepository;
import me.m1key.audiolicious.services.CacheAwareLibraryImporter;
import me.m1key.audiolicious.services.CacheableSongService;
import me.m1key.audiolicious.services.DefaultLibraryService;
import me.m1key.audiolicious.services.FullStatInfo;
import me.m1key.audiolicious.services.InMemoryCacheableSongService;
import me.m1key.audiolicious.services.LibraryImporter;
import me.m1key.audiolicious.services.LibraryRepository;
import me.m1key.audiolicious.services.LibraryService;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MacOsLibraryWithServiceIT {

	private static final Long TOTAL_ARTISTS = Long.valueOf(449);
	private static final Long TOTAL_ALBUMS = Long.valueOf(704);
	private static final Long TOTAL_SONGS = Long.valueOf(10732);
	private static final Long TOTAL_VIDEOS = Long.valueOf(6);
	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/MacOsExportedLibrary-2011-07-28.xml";

	@EJB
	private LibraryImporter libraryImporter;
	@EJB
	private TestHelperBean testHelperBean;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						MacOsLibraryWithServiceIT.class.getSimpleName()
								+ ".war")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsResource(
						new File(
								"../audiolicious-object-mappers/src/main/resources/englishValues.properties"),
						"englishValues.properties")
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("log4j.xml", "log4j.xml")
				.addClasses(AggregateTrackMapper.class, Album.class,
						AlbumAndSongInfo.class, AlbumInfo.class, Artist.class,
						ArtistRepository.class, AudiobookHandler.class,
						AudiobookMapper.class, AudiobookTo.class,
						CacheableSongService.class,
						CacheAwareLibraryImporter.class,
						CannotMapTrackValuesException.class,
						DataExtractor.class, DefaultAggregateTrackMapper.class,
						DefaultAudiobookMapper.class,
						DefaultLibraryService.class,
						DefaultNoopTrackHandler.class,
						DefaultPodcastMapper.class, DefaultSongMapper.class,
						DefaultVideoMapper.class,
						DefaultEnglishValuesProvider.class,
						DefaultObjectTrackDataHandler.class,
						DefaultRawTrackDataHandler.class,
						DefaultSongHandler.class, EnglishValuesProvider.class,
						FullStatInfo.class, I18nDataExtractor.class,
						InMemoryCacheableSongService.class,
						JpaArtistRepository.class, JpaLibraryRepository.class,
						Library.class, LibraryImporter.class,
						LibraryParser.class, LibraryService.class,
						LibraryRepository.class, LibraryTo.class,
						NonAggregateTrackMapper.class, NoopTrackHandler.class,
						NullArtist.class, NullEntitiesFactory.class,
						NullLibrary.class, ObjectMappingException.class,
						ObjectTrackDataHandler.class, PodcastHandler.class,
						PodcastMapper.class, PodcastTo.class, Rating.class,
						RatingTo.class, RawTrackDataHandler.class, Song.class,
						SongHandler.class, SongInfo.class, SongMapper.class,
						SongService.class, SongTo.class, Stat.class,
						StatInfo.class, TestHelperBean.class,
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
		assertEquals("There should be no artists before the test.",
				Long.valueOf(0), testHelperBean.totalArtists());

		File xmlLibraryFile = new File(pathToFile);
		libraryImporter.importLibrary(xmlLibraryFile);
	}

	@Test
	public void testCorrectNumberOfEverything() {
		assertEquals("There should be the right number of artists in the DB.",
				TOTAL_ARTISTS, testHelperBean.totalArtists());
		assertEquals("There should be the right number of albums in the DB.",
				TOTAL_ALBUMS, testHelperBean.totalAlbums());
		assertEquals("There should be the right number of songs in the DB.",
				Long.valueOf(TOTAL_SONGS + TOTAL_VIDEOS),
				testHelperBean.totalSongs());
	}

	@After
	public void cleanup() {
		testHelperBean.deleteAllArtists();
	}
}
