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

package me.m1key.audiolicious.objecthandler.handlers;

import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.AudiobookToBuilder;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.PodcastToBuilder;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.SongToBuilder;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.domain.to.VideoToBuilder;

import org.junit.Before;
import org.junit.Test;

public class NoopTrackHandlerTest {

	private NoopTrackHandler handler;

	@Before
	public void setup() {
		handler = new NoopTrackHandler();
	}

	@Test
	public void canHandleNull() {
		handler.handle(null);
	}

	@Test
	public void canHandleAudiobookTo() {
		AudiobookTo audiobookTo = new AudiobookToBuilder("Invisible").build();
		handler.handle(audiobookTo);
	}

	@Test
	public void canHandlePodcastTo() {
		PodcastTo podcastTo = new PodcastToBuilder("Invisible").build();
		handler.handle(podcastTo);
	}

	@Test
	public void canHandleSongTo() {
		SongTo songTo = new SongToBuilder("Invisible").build();
		handler.handle(songTo);
	}

	@Test
	public void canHandleVideoTo() {
		VideoTo videoTo = new VideoToBuilder("Invisible").build();
		handler.handle(videoTo);
	}
}
