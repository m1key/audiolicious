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

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.commons.qualifiers.AggregateMapper;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.libraryparser.RawTrackDataCallback;
import me.m1key.audiolicious.objectmapper.TrackMapper;

@RequestScoped
public class RawTrackDataHandler implements RawTrackDataCallback {

	private TrackMapper<? extends TrackTo> mapper;
	private ObjectTrackDataHandler objectTrackDataHandler;

	// For proxying.
	protected RawTrackDataHandler() {
	}

	@Inject
	public RawTrackDataHandler(
			@AggregateMapper TrackMapper<? extends TrackTo> mapper,
			ObjectTrackDataHandler objectTrackDataHandler) {
		this.mapper = mapper;
		this.objectTrackDataHandler = objectTrackDataHandler;
	}

	@Override
	public void feed(Map<XmlNodeName, String> receivedTrackValues) {
		TrackTo track = mapper.map(receivedTrackValues);
		objectTrackDataHandler.handle(track);
	}

}
