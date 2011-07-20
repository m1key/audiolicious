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

package me.m1key.audiolicious.objecthandler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Produces;

import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.AudiobookToBuilder;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.PodcastToBuilder;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.SongToBuilder;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.domain.to.VideoToBuilder;
import me.m1key.audiolicious.libraryparser.LibraryParser;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.TrackHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultObjectTrackDataHandlerTest {

	private DefaultObjectTrackDataHandler handler;

	@Mock
	private LibraryParser libraryParser;
	@Mock
	private TrackHandler<AudiobookTo> audiobookHandler;
	@Mock
	private TrackHandler<PodcastTo> podcastHandler;
	@Mock
	private TrackHandler<SongTo> songHandler;
	@Mock
	private TrackHandler<VideoTo> videoHandler;

	@Before
	public void setup() {
		handler = new DefaultObjectTrackDataHandler(libraryParser,
				getTrackHandlers());
	}

	@Test
	public void audiobookHandlerShouldHandleAudiobook() {
		AudiobookTo audiobook = new AudiobookToBuilder("Invisible").build();

		handler.handle(audiobook);

		verify(audiobookHandler, times(1)).handle(audiobook);
	}

	@Test
	public void podcastHandlerShouldHandlePodcast() {
		PodcastTo podcast = new PodcastToBuilder("Invisible").build();

		handler.handle(podcast);

		verify(podcastHandler, times(1)).handle(podcast);
	}

	@Test
	public void songHandlerShouldHandleSong() {
		SongTo song = new SongToBuilder("Invisible").build();

		handler.handle(song);

		verify(songHandler, times(1)).handle(song);
	}

	@Test
	public void videoHandlerShouldHandleVideo() {
		VideoTo video = new VideoToBuilder("Invisible").build();

		handler.handle(video);

		verify(videoHandler, times(1)).handle(video);
	}

	@Produces
	public Map<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>> getTrackHandlers() {
		Map<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>> handlers = new HashMap<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>>();
		handlers.put(AudiobookTo.class, audiobookHandler);
		handlers.put(PodcastTo.class, podcastHandler);
		handlers.put(SongTo.class, songHandler);
		handlers.put(VideoTo.class, videoHandler);
		return handlers;
	}
}
