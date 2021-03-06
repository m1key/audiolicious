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

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;

import me.m1key.audiolicious.commons.qualifiers.AggregateMapper;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.PodcastMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.SongMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.VideoMapper;

@Singleton
public class TrackMappersFactory {

	@EJB
	private AudiobookMapper audiobookMapper;
	@EJB
	private PodcastMapper podcastMapper;
	@EJB
	private SongMapper songMapper;
	@EJB
	private VideoMapper videoMapper;

	@Produces
	@AggregateMapper
	public Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> getAggregateTrackMapper() {
		return getAllKnownTracksMappers();
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
