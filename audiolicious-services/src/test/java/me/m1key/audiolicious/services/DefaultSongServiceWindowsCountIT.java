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
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.RawTrackDataHandler;
import me.m1key.audiolicious.objecthandler.factories.TrackHandlersFactory;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactory;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandler;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProviderCdiAlternative;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractorCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapper;
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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
	private DefaultObjectTrackDataHandler handler;
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
						DefaultSongServiceWindowsCountIT.class.getSimpleName()
								+ ".jar")
				.addAsManifestResource(
						new File(
								"src/test/resources/META-INF/stubHandlersBeans.xml"),
						ArchivePaths.create("beans.xml"))
				.addClasses(AudiobookMapper.class,
						DefaultEnglishValuesProviderCdiAlternative.class,
						DefaultObjectTrackDataHandler.class,
						I18nDataExtractorCdiAlternative.class,
						NoopTrackHandler.class, NullEntitiesFactory.class,
						PodcastMapper.class, RawTrackDataHandler.class,
						SongMapper.class, StubAudiobookHandler.class,
						StubPodcastHandler.class, StubSongHandler.class,
						StubSongRepository.class,
						StubTrackHandlersFactory.class, StubVideoHandler.class,
						TrackHandlersFactory.class, TrackMappersFactory.class,
						VideoMapper.class,
						VtdItunesLibraryParserCdiAlternative.class);
	}

	@Before
	public void setUp() throws Exception {
		File xmlLibraryFile = new File(pathToFile);
		handler.execute(xmlLibraryFile);
	}

	@Test
	public void testCorrectNumberOfTracksProcessed() {
		assertEquals(TOTAL_AUDIOBOOKS + TOTAL_PODCASTS + TOTAL_SONGS
				+ TOTAL_MUSIC_VIDEOS + TOTAL_VIDEOS,
				audiobookHandler.getCount() + podcastHandler.getCount()
						+ songHandler.getCount() + videoHandler.getCount());
		assertEquals(TOTAL_AUDIOBOOKS, audiobookHandler.getCount());
		assertEquals(TOTAL_PODCASTS, podcastHandler.getCount());
		assertEquals(TOTAL_SONGS + TOTAL_MUSIC_VIDEOS, songHandler.getCount());
		assertEquals(TOTAL_VIDEOS, videoHandler.getCount());
	}
}
