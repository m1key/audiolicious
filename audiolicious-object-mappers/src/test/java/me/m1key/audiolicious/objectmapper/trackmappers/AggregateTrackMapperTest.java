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

package me.m1key.audiolicious.objectmapper.trackmappers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapperBean;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractor;

import org.junit.Before;
import org.junit.Test;

public class AggregateTrackMapperTest {

	private static final String FALSE = "false";

	private AggregateTrackMapperBean trackMapper;

	@Before
	public void setup() {
		Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers = new HashMap<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>>();
		I18nDataExtractor dataExtractor = new I18nDataExtractor();
		dataExtractor
				.setEnglishValuesProvider(new DefaultEnglishValuesProvider());
		mappers.put(SongTo.class, new SongMapperCdiAlternative(dataExtractor));
		trackMapper = new AggregateTrackMapperBean();
		trackMapper.setMappers(mappers);
	}

	@Test
	public void testEmptyTrackValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		assertFalse(trackMapper.canMap(trackValues));
	}

	@Test
	public void testSongTrackValues() {
		Map<XmlNodeName, String> songValues = new HashMap<XmlNodeName, String>();
		songValues.put(XmlNodeName.PODCAST, FALSE);
		songValues.put(XmlNodeName.LOCATION, "a.m4a");
		songValues.put(XmlNodeName.MOVIE, FALSE);
		songValues.put(XmlNodeName.FILE_FOLDER_COUNT, "4");
		songValues.put(XmlNodeName.GENRE, "rock");
		assertTrue(trackMapper.canMap(songValues));
	}

}
