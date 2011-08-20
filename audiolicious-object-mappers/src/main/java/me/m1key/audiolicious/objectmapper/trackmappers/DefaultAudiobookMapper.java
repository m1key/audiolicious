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

import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.objectmapper.CannotMapTrackValuesException;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.extractor.DataExtractor;

@Singleton
@Local({ AudiobookMapper.class, TrackMapper.class })
public class DefaultAudiobookMapper extends
		NonAggregateTrackMapper<AudiobookTo> implements AudiobookMapper {

	@EJB
	private DataExtractor dataExtractor;

	@Override
	public AudiobookTo map(Map<XmlNodeName, String> trackValues) {
		if (!canMap(trackValues)) {
			throw new CannotMapTrackValuesException(this, trackValues);
		}

		Date dateAdded = dataExtractor.extractDate(trackValues,
				XmlNodeName.DATE_ADDED);
		Date dateModified = dataExtractor.extractDate(trackValues,
				XmlNodeName.DATE_MODIFIED);
		String album = dataExtractor.extractString(trackValues,
				XmlNodeName.ALBUM);
		String albumArtist = dataExtractor.extractString(trackValues,
				XmlNodeName.ALBUM_ARTIST);
		String artist = dataExtractor.extractString(trackValues,
				XmlNodeName.ARTIST);
		String genre = dataExtractor.extractString(trackValues,
				XmlNodeName.GENRE);
		String name = dataExtractor
				.extractString(trackValues, XmlNodeName.NAME);
		String comments = dataExtractor.extractString(trackValues,
				XmlNodeName.COMMENTS);
		RatingTo rating = dataExtractor.extractRating(trackValues,
				XmlNodeName.RATING);
		int playCount = dataExtractor.extractInt(trackValues,
				XmlNodeName.PLAY_COUNT);
		int year = dataExtractor.extractInt(trackValues, XmlNodeName.YEAR);

		return new AudiobookTo(name, album, artist, albumArtist, year, genre,
				dateAdded, dateModified, rating, playCount, comments);
	}

	@Override
	public boolean canMap(Map<XmlNodeName, String> trackValues) {
		if (trackValues.isEmpty()) {
			return false;
		}

		if (isPodcast(trackValues)) {
			return false;
		}

		if (isMovie(trackValues)) {
			return false;
		}

		if (fileFolderCountNotEquals(trackValues, "4")) {
			return false;
		}

		if (locationsEndsWith(trackValues, ".m4b")) {
			return true;
		}

		if (genreEquals(trackValues, "Audiobook")) {
			return true;
		}

		if (kindContains(trackValues, "Audible")) {
			return true;
		}

		return false;
	}

	@Override
	protected DataExtractor getDataExtractor() {
		return dataExtractor;
	}

	public void setDataExtractor(DataExtractor extractor) {
		this.dataExtractor = extractor;
	}

}
