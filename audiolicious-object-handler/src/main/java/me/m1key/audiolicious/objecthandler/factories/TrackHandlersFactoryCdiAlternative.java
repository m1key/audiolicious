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

package me.m1key.audiolicious.objecthandler.factories;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NoopHandler;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;

@ApplicationScoped
public class TrackHandlersFactoryCdiAlternative {

	@Inject
	@NoopHandler
	private TrackHandler<TrackTo> audiobookHandler;
	@Inject
	@NoopHandler
	private TrackHandler<TrackTo> podcastHandler;
	@Inject
	private TrackHandler<SongTo> songHandler;
	@Inject
	@NoopHandler
	private TrackHandler<TrackTo> videoHandler;

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
