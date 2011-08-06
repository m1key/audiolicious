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

import me.m1key.audiolicious.commons.qualifiers.AggregateMapper;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.TrackMapper;

@ApplicationScoped
public class TrackMappersFactoryCdiAlternative {

	@Inject
	private TrackMapper<AudiobookTo> audiobookMapper;
	@Inject
	private TrackMapper<PodcastTo> podcastMapper;
	@Inject
	private TrackMapper<SongTo> songMapper;
	@Inject
	private TrackMapper<VideoTo> videoMapper;

	@Produces
	@AggregateMapper
	public TrackMapper<TrackTo> getAggregateTrackMapper() {
		Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers = getAllKnownTracksMappers();
		return new AggregateTrackMapperCdiAlternative(mappers);
	}

	private Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> getAllKnownTracksMappers() {
		Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers = new HashMap<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>>();
		mappers.put(AudiobookTo.class, audiobookMapper);
		mappers.put(PodcastTo.class, podcastMapper);
		mappers.put(SongTo.class, songMapper);
		mappers.put(VideoTo.class, videoMapper);
		return mappers;
	}

}
