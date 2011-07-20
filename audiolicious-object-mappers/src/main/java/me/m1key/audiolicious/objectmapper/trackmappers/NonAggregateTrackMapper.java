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

import java.util.Map;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.extractor.DataExtractor;

public abstract class NonAggregateTrackMapper<T extends TrackTo> extends
		TrackMapper<T> {

	protected abstract DataExtractor getDataExtractor();

	protected boolean genreEquals(Map<XmlNodeName, String> trackValues,
			String value) {
		return value.equals(getDataExtractor().extractString(trackValues,
				XmlNodeName.GENRE));
	}

	protected boolean fileFolderCountNotEquals(
			Map<XmlNodeName, String> trackValues, String value) {
		return !value.equals(getDataExtractor().extractString(trackValues,
				XmlNodeName.FILE_FOLDER_COUNT));
	}

	protected boolean isMovie(Map<XmlNodeName, String> trackValues) {
		return ((boolean) getDataExtractor().extractBoolean(trackValues,
				XmlNodeName.MOVIE));
	}

	protected boolean locationsEndsWith(Map<XmlNodeName, String> trackValues,
			String endsWith) {
		return getDataExtractor().extractString(trackValues,
				XmlNodeName.LOCATION).endsWith(endsWith);
	}

	protected boolean isPodcast(Map<XmlNodeName, String> trackValues) {
		return ((boolean) getDataExtractor().extractBoolean(trackValues,
				XmlNodeName.PODCAST));
	}

	protected boolean hasVideo(Map<XmlNodeName, String> trackValues) {
		return ((boolean) getDataExtractor().extractBoolean(trackValues,
				XmlNodeName.HAS_VIDEO));
	}

	protected boolean kindContains(Map<XmlNodeName, String> trackValues,
			String value) {
		String kind = getDataExtractor().extractString(trackValues,
				XmlNodeName.KIND);
		if (kind == null) {
			return false;
		} else {
			return kind.contains(value);
		}
	}

	protected boolean hasNonZeroVideoDimensions(
			Map<XmlNodeName, String> trackValues) {
		return getDataExtractor().extractInt(trackValues,
				XmlNodeName.VIDEO_HEIGHT) > 0
				&& getDataExtractor().extractInt(trackValues,
						XmlNodeName.VIDEO_WIDTH) > 0;
	}

}
