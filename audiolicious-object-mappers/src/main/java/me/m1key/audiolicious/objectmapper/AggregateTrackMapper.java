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

package me.m1key.audiolicious.objectmapper;

import java.util.Map;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.TrackTo;

public class AggregateTrackMapper extends TrackMapper<TrackTo> {

	private Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers;

	public AggregateTrackMapper(
			Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers) {
		this.mappers = mappers;
	}

	@Override
	public TrackTo map(Map<XmlNodeName, String> trackValues) {
		for (Class<? extends TrackTo> mapperKey : mappers.keySet()) {
			TrackMapper<? extends TrackTo> mapper = mappers.get(mapperKey);
			if (mapper.canMap(trackValues)) {
				return mapper.map(trackValues);
			}
		}

		throw new ObjectMappingException(trackValues);
	}

	@Override
	public boolean canMap(Map<XmlNodeName, String> trackValues) {
		for (Class<? extends TrackTo> mapperKey : mappers.keySet()) {
			TrackMapper<? extends TrackTo> mapper = mappers.get(mapperKey);
			if (mapper.canMap(trackValues)) {
				return true;
			}
		}

		return false;
	}
}
