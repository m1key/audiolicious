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
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.TrackToType;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractor;

import org.junit.Before;
import org.junit.Test;

public class AudiobookMapperTest {

	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private DefaultAudiobookMapper audiobookMapper;

	@Before
	public void setup() {
		audiobookMapper = new DefaultAudiobookMapper();
		I18nDataExtractor dataExtractor = new I18nDataExtractor();
		dataExtractor
				.setEnglishValuesProvider(new DefaultEnglishValuesProvider());
		audiobookMapper.setDataExtractor(dataExtractor);
	}

	@Test
	public void testCannotMapEmptyTrackValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		assertFalse(audiobookMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapPodcast() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, TRUE);
		assertFalse(audiobookMapper.canMap(trackValues));
	}

	@Test
	public void testCanMapM4b() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.LOCATION, "something.m4b");
		assertTrue(audiobookMapper.canMap(trackValues));
	}

	@Test
	public void testCanMapGenreAudiobook() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.LOCATION, "something.mp3");
		trackValues.put(XmlNodeName.GENRE, "Audiobook");
		assertTrue(audiobookMapper.canMap(trackValues));
	}

	@Test
	public void testCanMapGenreAudiobookDutch() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.LOCATION, "something.mp3");
		trackValues.put(XmlNodeName.GENRE, "Audioboek");
		assertTrue(audiobookMapper.canMap(trackValues));
	}

	@Test
	public void testMapAudiobookValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4b");
		trackValues.put(XmlNodeName.MOVIE, FALSE);
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
		trackValues.put(XmlNodeName.PLAY_COUNT, "1");
		trackValues.put(XmlNodeName.COMMENTS, "Comments");
		AudiobookTo audiobook = audiobookMapper.map(trackValues);

		assertNotNull(audiobook);
		assertEquals("Death Magnetic", audiobook.getAlbumName());
		assertEquals("Metallica album artist", audiobook.getAlbumArtist());
		assertEquals("Metallica artist", audiobook.getArtist());
		assertEquals(1239312711000L, audiobook.getDateAdded().getTime());
		assertEquals(1237323588000L, audiobook.getDateModified().getTime());
		assertEquals("Rock", audiobook.getGenre());
		assertEquals("That Was Just Your Life", audiobook.getName());
		assertEquals(1, audiobook.getPlayCount());
		assertEquals(80, audiobook.getRating().getPercentage());
		assertEquals(new RatingTo(80), audiobook.getRating());
		assertEquals(TrackToType.AUDIOBOOK, audiobook.getType());
		assertEquals(2008, audiobook.getYear());
		assertEquals(false, audiobook.isHasVideo());
		assertEquals(false, audiobook.isPodcast());
		assertEquals("Comments", audiobook.getComments());
	}

}
