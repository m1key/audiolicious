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
import me.m1key.audiolicious.domain.to.TrackToType;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractor;
import me.m1key.audiolicious.objectmapper.trackmappers.VideoMapper;

import org.junit.Before;
import org.junit.Test;

public class VideoMapperTest {

	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private TrackMapper<VideoTo> videoMapper;

	@Before
	public void setup() {
		videoMapper = new VideoMapper(new I18nDataExtractor(
				new DefaultEnglishValuesProvider()));
	}

	@Test
	public void testCannotMapEmptyTrackValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapPodcast() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, TRUE);
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapM4b() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4b");
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapFileFolderCount() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, FALSE);
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "4");
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapNotMovie() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, FALSE);
		trackValues.put(XmlNodeName.HAS_VIDEO, TRUE);
		trackValues.put(XmlNodeName.VIDEO_HEIGHT, "240");
		trackValues.put(XmlNodeName.VIDEO_WIDTH, "320");
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "3");
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapHasNoVideo() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, TRUE);
		trackValues.put(XmlNodeName.HAS_VIDEO, FALSE);
		trackValues.put(XmlNodeName.VIDEO_HEIGHT, "240");
		trackValues.put(XmlNodeName.VIDEO_WIDTH, "320");
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "3");
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapHasZeroHeight() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, TRUE);
		trackValues.put(XmlNodeName.HAS_VIDEO, TRUE);
		trackValues.put(XmlNodeName.VIDEO_HEIGHT, "0");
		trackValues.put(XmlNodeName.VIDEO_WIDTH, "320");
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "3");
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCannotMapHasZeroWidth() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, TRUE);
		trackValues.put(XmlNodeName.HAS_VIDEO, TRUE);
		trackValues.put(XmlNodeName.VIDEO_HEIGHT, "240");
		trackValues.put(XmlNodeName.VIDEO_WIDTH, "0");
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "3");
		assertFalse(videoMapper.canMap(trackValues));
	}

	@Test
	public void testCanMapVideo() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, TRUE);
		trackValues.put(XmlNodeName.HAS_VIDEO, TRUE);
		trackValues.put(XmlNodeName.VIDEO_HEIGHT, "240");
		trackValues.put(XmlNodeName.VIDEO_WIDTH, "320");
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "3");
		assertTrue(videoMapper.canMap(trackValues));
	}

	@Test
	public void testMapPodcastValues() {
		Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();
		trackValues.put(XmlNodeName.PODCAST, FALSE);
		trackValues.put(XmlNodeName.LOCATION, "a.m4a");
		trackValues.put(XmlNodeName.MOVIE, TRUE);
		trackValues.put(XmlNodeName.HAS_VIDEO, TRUE);
		trackValues.put(XmlNodeName.FILE_FOLDER_COUNT, "3");
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
		trackValues.put(XmlNodeName.VIDEO_HEIGHT, "240");
		trackValues.put(XmlNodeName.VIDEO_WIDTH, "320");
		trackValues.put(XmlNodeName.SKIP_DATE, "2009-04-09T21:31:52Z");
		VideoTo video = videoMapper.map(trackValues);

		assertNotNull(video);
		assertEquals("Death Magnetic", video.getAlbumName());
		assertEquals("Metallica album artist", video.getAlbumArtist());
		assertEquals("Metallica artist", video.getArtist());
		assertEquals(1239312711000L, video.getDateAdded().getTime());
		assertEquals(1237323588000L, video.getDateModified().getTime());
		assertEquals("Rock", video.getGenre());
		assertEquals("That Was Just Your Life", video.getName());
		assertEquals(1, video.getPlayCount());
		assertEquals(80, video.getRating().getPercentage());
		assertEquals(new RatingTo(80), video.getRating());
		assertEquals(TrackToType.VIDEO, video.getType());
		assertEquals(240, video.getVideoHeight());
		assertEquals(320, video.getVideoWidth());
		assertEquals(2008, video.getYear());
		assertEquals(true, video.isHasVideo());
		assertEquals(false, video.isHd());
		assertEquals(false, video.isPodcast());
	}

}
