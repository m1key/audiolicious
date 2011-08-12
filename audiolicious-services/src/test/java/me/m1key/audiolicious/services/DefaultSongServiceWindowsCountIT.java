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
import me.m1key.audiolicious.libraryparser.VtdItunesLibraryParser;
import me.m1key.audiolicious.libraryparser.XmlParseException;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.ObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.RawTrackDataHandler;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandlerCdiAlternative;
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
public class DefaultSongServiceWindowsCountIT {

	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/WindowsExportedLibrary-2011-06-18.xml";
	private static final int TOTAL_SONGS = 10256;
	private static final int TOTAL_MUSIC_VIDEOS = 6;
	private static final int TOTAL_PODCASTS = 0;
	private static final int TOTAL_AUDIOBOOKS = 31;
	private static final int TOTAL_VIDEOS = 0;

	@Inject
	private LibraryImporter libraryImporter;
	@Inject
	private StubSongHandler songHandler;
	@Inject
	private StubAudiobookHandler audiobookHandler;
	@Inject
	private StubPodcastHandler podcastHandler;
	@Inject
	private StubVideoHandler videoHandler;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						DefaultSongServiceWindowsCountIT.class.getSimpleName()
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
						DefaultLibraryImporter.class,
						DefaultObjectTrackDataHandler.class,
						DefaultSongServiceCdiAlternative.class,
						EnglishValuesProvider.class, I18nDataExtractor.class,
						LibraryImporter.class, LibraryParser.class,
						NonAggregateTrackMapper.class, NoopHandler.class,
						NoopTrackHandlerCdiAlternative.class, NullAlbum.class,
						NullArtist.class, NullEntitiesFactory.class,
						ObjectMappingException.class,
						ObjectTrackDataHandler.class,
						PodcastMapperCdiAlternative.class, PodcastTo.class,
						Rating.class, RatingTo.class,
						RawTrackDataCallback.class, RawTrackDataHandler.class,
						Song.class, SongMapperCdiAlternative.class,
						SongRepository.class, SongService.class, SongTo.class,
						StubAlbumRepository.class, StubArtistRepository.class,
						StubAudiobookHandler.class, StubPodcastHandler.class,
						StubSongHandler.class, StubSongRepository.class,
						StubTrackHandlersFactory.class, StubVideoHandler.class,
						TrackHandler.class, TrackMapper.class,
						TrackMappersFactoryCdiAlternative.class, TrackTo.class,
						TrackToType.class, VideoMapperCdiAlternative.class,
						VideoTo.class, VtdItunesLibraryParser.class,
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
		File xmlLibraryFile = new File(pathToFile);
		libraryImporter.importLibrary(xmlLibraryFile);
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
	}
}
