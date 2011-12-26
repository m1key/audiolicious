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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackToType;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractor;

import org.junit.Before;
import org.junit.Test;

public class SongMapperTest {

	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private DefaultSongMapper songMapper;

	@Before
	public void setup() {
		I18nDataExtractor dataExtractor = new I18nDataExtractor();
		dataExtractor
				.setEnglishValuesProvider(new DefaultEnglishValuesProvider());
		songMapper = new DefaultSongMapper();
		songMapper.setDataExtractor(dataExtractor);
	}

	@Test
	public void testCannotMapEmptyTrackValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		assertFalse(songMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapPodcast() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, TRUE);
		assertFalse(songMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapM4b() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4b");
		assertFalse(songMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapMovie() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, TRUE);
		assertFalse(songMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapFileFolderCount() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, FALSE);
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "3");
		assertFalse(songMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapAudiobook() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, FALSE);
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "4");
		trackValues.put(XmlNodeName.GENRE, "Audiobook");
		assertFalse(songMapper.canMap(trackValues));
	}

	@Test
	public void testCanMapSongValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, FALSE);
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "4");
		trackValues.put(XmlNodeName.GENRE, "rock");
		assertTrue(songMapper.canMap(trackValues));
	}

	@Test
	public void testMapSongValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, FALSE);
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "4");
		trackValues.put(XmlNodeName.GENRE, "Rock");
		trackValues.put(XmlNodeName.ARTIST, "Metallica artist");
		trackValues.put(XmlNodeName.ALBUM_ARTIST, "Metallica album artist");
		trackValues.put(XmlNodeName.ALBUM, "Death Magnetic");
		trackValues.put(XmlNodeName.YEAR, "2008");
		trackValues.put(XmlNodeName.RATING, "80");
		trackValues.put(XmlNodeName.ALBUM_RATING, "100");
		trackValues.put(XmlNodeName.DATE_MODIFIED, "2009-03-17T20:59:48Z");
		trackValues.put(XmlNodeName.DATE_ADDED, "2009-04-09T21:31:51Z");
		trackValues.put(XmlNodeName.NAME, "That Was Just Your Life");
		trackValues.put(XmlNodeName.ALBUM_RATING_COMPUTED, "true");
		trackValues.put(XmlNodeName.PLAY_COUNT, "1");
		trackValues.put(XmlNodeName.COMPOSER, "Dio");
		trackValues.put(XmlNodeName.SKIP_COUNT, "3");
		trackValues.put(XmlNodeName.SKIP_DATE, "2009-04-09T21:31:52Z");
		trackValues.put(XmlNodeName.TRACK_NUMBER, "9");
		trackValues.put(XmlNodeName.DISC_NUMBER, "2");
		trackValues.put(XmlNodeName.TOTAL_TIME, "200");
		SongTo song = songMapper.map(trackValues);

		assertNotNull(song);
		assertEquals("Death Magnetic", song.getAlbumName());
		assertEquals("Metallica album artist", song.getAlbumArtist());
		assertEquals(100, song.getAlbumRating().getPercentage());
		assertEquals(new RatingTo(100), song.getAlbumRating());
		assertEquals("Metallica artist", song.getArtist());
		assertEquals(1239312711000L, song.getDateAdded().getTime());
		assertEquals(1237323588000L, song.getDateModified().getTime());
		assertEquals("Rock", song.getGenre());
		assertEquals("That Was Just Your Life", song.getName());
		assertEquals(1, song.getPlayCount());
		assertEquals(80, song.getRating().getPercentage());
		assertEquals(new RatingTo(80), song.getRating());
		assertEquals(TrackToType.SONG, song.getType());
		assertEquals(2008, song.getYear());
		assertEquals(true, song.isAlbumRatingComputed());
		assertEquals(false, song.isHasVideo());
		assertEquals(false, song.isPodcast());
		assertEquals(3, song.getSkipCount());
		assertEquals(9, song.getTrackNumber());
		assertEquals(2, song.getDiscNumber());
		assertEquals(200, song.getTotalTime());
		assertEquals(1239312712000L, song.getSkipDate().getTime());
	}

}
