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

import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;
import me.m1key.audiolicious.libraryparser.VtdItunesLibraryParserCdiAlternative;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.RawTrackDataHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.factories.TrackHandlersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandlerCdiAlternative;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProviderCdiAlternative;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractorCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapperCdiAlternative;
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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
	private DefaultObjectTrackDataHandlerCdiAlternative handler;
	@Inject
	private StubSongHandler songHandler;
	@Inject
	private StubAudiobookHandler audiobookHandler;
	@Inject
	private StubPodcastHandler podcastHandler;
	@Inject
	private StubVideoHandler videoHandler;

	@Deployment
	public static JavaArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(JavaArchive.class,
						DefaultSongServiceMacOsCountIT.class.getSimpleName()
								+ ".jar")
				.addAsManifestResource(
						new File(
								"src/test/resources/META-INF/stubHandlersBeans.xml"),
						ArchivePaths.create("beans.xml"))
				.addClasses(AudiobookMapperCdiAlternative.class,
						DefaultEnglishValuesProviderCdiAlternative.class,
						DefaultObjectTrackDataHandlerCdiAlternative.class,
						I18nDataExtractorCdiAlternative.class,
						NoopTrackHandlerCdiAlternative.class, NullEntitiesFactory.class,
						PodcastMapperCdiAlternative.class,
						RawTrackDataHandlerCdiAlternative.class,
						SongMapperCdiAlternative.class,
						StubAudiobookHandler.class, StubPodcastHandler.class,
						StubSongHandler.class, StubSongRepository.class,
						StubTrackHandlersFactory.class, StubVideoHandler.class,
						TrackHandlersFactoryCdiAlternative.class, TrackMappersFactoryCdiAlternative.class,
						VideoMapperCdiAlternative.class,
						VtdItunesLibraryParserCdiAlternative.class);
	}

	@Before
	public void setUp() throws Exception {
		File xmlLibraryFile = new File(pathToFile);
		handler.execute(xmlLibraryFile);
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
