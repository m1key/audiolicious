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

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.libraryparser.RawTrackDataHandler;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapper;

@Singleton
@Local(RawTrackDataHandler.class)
public class DefaultRawTrackDataHandler implements RawTrackDataHandler {

	@EJB
	private AggregateTrackMapper mapper;
	@EJB
	private ObjectTrackDataHandler objectTrackDataHandler;

	@Override
	public void handle(Map<XmlNodeName, String> rawTrackData, String libraryUuid) {
		TrackTo track = mapper.map(rawTrackData);
		objectTrackDataHandler.handle(track, libraryUuid);
	}

	public void setMapper(AggregateTrackMapper mapper) {
		this.mapper = mapper;
	}

	public void setObjectTrackDataHandler(
			ObjectTrackDataHandler objectTrackDataHandler) {
		this.objectTrackDataHandler = objectTrackDataHandler;
	}

}
