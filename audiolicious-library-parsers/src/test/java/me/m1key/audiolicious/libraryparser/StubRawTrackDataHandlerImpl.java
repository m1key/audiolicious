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

package me.m1key.audiolicious.libraryparser;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import me.m1key.audiolicious.commons.XmlNodeName;

@Stateless
@Local({ StubRawTrackDataHandler.class, RawTrackDataHandler.class })
public class StubRawTrackDataHandlerImpl implements StubRawTrackDataHandler {

	private List<Map<XmlNodeName, String>> rawTrackData;

	public StubRawTrackDataHandlerImpl() {
		rawTrackData = new ArrayList<Map<XmlNodeName, String>>();
	}

	@Override
	public void handle(Map<XmlNodeName, String> trackValues, String libraryUuid) {
		rawTrackData.add(trackValues);
	}

	public List<Map<XmlNodeName, String>> getRawTrackData() {
		return rawTrackData;
	}

	public Map<XmlNodeName, String> getTrack(String trackId) {
		Map<XmlNodeName, String> matchingTrack = null;
		for (Map<XmlNodeName, String> track : getRawTrackData()) {
			if (trackId.equals(track.get(XmlNodeName.TRACK_ID))) {
				matchingTrack = track;
			}
		}
		assertNotNull(String.format(
				"Track [%s] does not exist in the library.", trackId),
				matchingTrack);
		return matchingTrack;
	}
}