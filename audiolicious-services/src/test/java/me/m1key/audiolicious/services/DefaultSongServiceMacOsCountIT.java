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

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

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
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactory;
import me.m1key.audiolicious.objecthandler.handlers.AudiobookHandler;
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
import me.m1key.audiolicious.services.handlers.StubAudiobookHandler;
import me.m1key.audiolicious.services.handlers.StubPodcastHandler;
import me.m1key.audiolicious.services.handlers.StubSongHandler;
import me.m1key.audiolicious.services.handlers.StubTrackHandlersFactory;
import me.m1key.audiolicious.services.handlers.StubVideoHandler;

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
public class DefaultSongServiceMacOsCountIT {

	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/MacOsExportedLibrary-2011-07-28.xml";
	private static final int TOTAL_SONGS = 10732;
	private static final int TOTAL_MUSIC_VIDEOS = 6;
	private static final int TOTAL_PODCASTS = 55;
	private static final int TOTAL_AUDIOBOOKS = 35;
	private static final int TOTAL_VIDEOS = 1;

	@Inject
	private LibraryImporter importer;
	@Inject
	private StubSongHandler songHandler;
	@Inject
	private StubAudiobookHandler audiobookHandler;
	@Inject
	private StubPodcastHandler podcastHandler;
	@Inject
	private StubVideoHandler videoHandler;
	@Inject
	private StubLibraryRepositoryWithInfo libraryRepository;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						DefaultSongServiceMacOsCountIT.class.getSimpleName()
								+ ".war")
				.addAsResource(
						new File(
								"../audiolicious-object-mappers/src/main/resources/englishValues.properties"),
						"englishValues.properties")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsResource("log4j.xml", "log4j.xml")
				.addClasses(AggregateTrackMapper.class, Album.class,
						AlbumInfo.class, AlbumAndSongInfo.class, Artist.class,
						ArtistRepository.class, AudiobookHandler.class,
						AudiobookMapper.class, AudiobookTo.class,
						CacheableSongService.class,
						CacheAwareLibraryImporter.class,
						CannotMapTrackValuesException.class,
						DataExtractor.class, DefaultAggregateTrackMapper.class,
						DefaultAudiobookMapper.class,
						DefaultEnglishValuesProvider.class,
						DefaultLibraryService.class,
						DefaultObjectTrackDataHandler.class,
						DefaultPodcastMapper.class, DefaultSongMapper.class,
						DefaultVideoMapper.class, EnglishValuesProvider.class,
						FullStatInfo.class, I18nDataExtractor.class,
						InMemoryCacheableSongService.class, Library.class,
						LibraryImporter.class, LibraryParser.class,
						LibraryService.class, LibraryRepository.class,
						LibraryTo.class, NonAggregateTrackMapper.class,
						NullArtist.class, NullEntitiesFactory.class,
						NullLibrary.class, ObjectMappingException.class,
						ObjectTrackDataHandler.class, PodcastHandler.class,
						PodcastMapper.class, PodcastTo.class, Rating.class,
						RatingTo.class, RawTrackDataHandler.class,
						DefaultRawTrackDataHandler.class, Song.class,
						SongHandler.class, SongInfo.class, SongMapper.class,
						SongService.class, SongTo.class, Stat.class,
						StatInfo.class, StubArtistRepository.class,
						StubAudiobookHandler.class,
						StubLibraryRepository.class,
						StubLibraryRepositoryWithInfo.class,
						StubPodcastHandler.class, StubSongHandler.class,
						StubTrackHandlersFactory.class, StubVideoHandler.class,
						TrackHandler.class, TrackMapper.class,
						TrackMappersFactory.class, TrackTo.class,
						TrackToType.class, VideoHandler.class,
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
		File xmlLibraryFile = new File(pathToFile);
		importer.importLibrary(xmlLibraryFile);
	}

	@Test
	public void testCorrectNumberOfTracksProcessed() {
		assertEquals(TOTAL_AUDIOBOOKS, audiobookHandler.getCount());
		assertEquals(TOTAL_PODCASTS, podcastHandler.getCount());
		assertEquals(TOTAL_SONGS + TOTAL_MUSIC_VIDEOS, songHandler.getCount());
		assertEquals(TOTAL_VIDEOS, videoHandler.getCount());
		assertEquals(TOTAL_AUDIOBOOKS + TOTAL_PODCASTS + TOTAL_SONGS
				+ TOTAL_MUSIC_VIDEOS + TOTAL_VIDEOS,
				audiobookHandler.getCount() + podcastHandler.getCount()
						+ songHandler.getCount() + videoHandler.getCount());

		assertEquals(1, libraryRepository.getSaveCalledTimes());
	}
}
