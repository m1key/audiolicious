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

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.commons.qualifiers.AggregateMapper;
import me.m1key.audiolicious.domain.to.TrackTo;

@Singleton
@Local(AggregateTrackMapper.class)
public class DefaultAggregateTrackMapper implements AggregateTrackMapper {

	@Inject
	@AggregateMapper
	private Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappersThemselves;

	@Override
	public TrackTo map(Map<XmlNodeName, String> trackValues) {
		for (Class<? extends TrackTo> mapperKey : mappersThemselves.keySet()) {
			TrackMapper<? extends TrackTo> mapper = mappersThemselves
					.get(mapperKey);
			if (mapper.canMap(trackValues)) {
				return mapper.map(trackValues);
			}
		}

		throw new ObjectMappingException(trackValues);
	}

	@Override
	public boolean canMap(Map<XmlNodeName, String> trackValues) {
		for (Class<? extends TrackTo> mapperKey : mappersThemselves.keySet()) {
			TrackMapper<? extends TrackTo> mapper = mappersThemselves
					.get(mapperKey);
			if (mapper.canMap(trackValues)) {
				return true;
			}
		}

		return false;
	}

	public void setMappers(
			Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers) {
		this.mappersThemselves = mappers;
	}
}
