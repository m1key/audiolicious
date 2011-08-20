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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Map;

import me.m1key.audiolicious.commons.XmlNodeName;

import org.junit.BeforeClass;
import org.junit.Test;

public class VtdItunesLibraryParserVariousTest {

	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/Fragment_1.xml";
	private static VtdItunesLibraryParser parser;
	private static StubRawTrackDataHandler stubRawTrackDataHandler;

	@BeforeClass
	public static void setUp() throws Exception {
		stubRawTrackDataHandler = new StubRawTrackDataHandlerImpl();
		File file = new File(pathToFile);
		parser = new VtdItunesLibraryParser();
		parser.setCallback(stubRawTrackDataHandler);
		parser.process(file);
	}

	@Test
	public void testTracksCorrectNumberOfTracks() {
		assertEquals(10, stubRawTrackDataHandler.getRawTrackData().size());
	}

	@Test
	public void testVariousCorrect01() {
		Map<XmlNodeName, String> track01 = stubRawTrackDataHandler
				.getTrack("18925");
		assertEquals("The Kite Runner", track01.get(XmlNodeName.NAME));
		assertEquals("Marc Forster", track01.get(XmlNodeName.ARTIST));
		assertEquals("Drama", track01.get(XmlNodeName.GENRE));
		assertEquals("Beveiligd MPEG-4-videobestand",
				track01.get(XmlNodeName.KIND));
		assertEquals("1441398693", track01.get(XmlNodeName.SIZE));
		assertEquals("7672047", track01.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2009", track01.get(XmlNodeName.YEAR));
		assertEquals("1", track01.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3376329604", track01.get(XmlNodeName.PLAY_DATE));
		assertEquals("2010-12-27T20:20:04Z",
				track01.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2009-11-18T08:00:00Z",
				track01.get(XmlNodeName.RELEASE_DATE));
		assertEquals("80", track01.get(XmlNodeName.RATING));
		assertEquals("80", track01.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track01.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("Kite Runner", track01.get(XmlNodeName.SORT_NAME));
		assertEquals("2010-12-27T17:10:21Z",
				track01.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-12-27T16:20:00Z",
				track01.get(XmlNodeName.DATE_ADDED));
		assertEquals("110", track01.get(XmlNodeName.BIT_RATE));
		assertEquals("1", track01.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("4F91665BFC6DDF2F", track01.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", track01.get(XmlNodeName.TRACK_TYPE));
		assertEquals("uk-movie|12|300|Contains strong language "
				+ "and infrequent strong violence.",
				track01.get(XmlNodeName.CONTENT_RATING));
		assertEquals("true", track01.get(XmlNodeName.PURCHASED));
		assertEquals("true", track01.get(XmlNodeName.HAS_VIDEO));
		assertEquals("false", track01.get(XmlNodeName.HD));
		assertEquals("640", track01.get(XmlNodeName.VIDEO_WIDTH));
		assertEquals("364", track01.get(XmlNodeName.VIDEO_HEIGHT));
		assertEquals(
				"file://localhost/Users/michalhuniewicz/Music/iTunes/iTunes%20Music/Movies/The%20Kite%20Runner.m4v",
				track01.get(XmlNodeName.LOCATION));
		assertEquals("3", track01.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track01.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track01.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect02() {
		Map<XmlNodeName, String> track02 = stubRawTrackDataHandler
				.getTrack("12985");
		assertEquals("Tree Ring Science and Tomorrow's Water",
				track02.get(XmlNodeName.NAME));
		assertEquals("Scientific American", track02.get(XmlNodeName.ARTIST));
		assertEquals("Scientific American Podcast",
				track02.get(XmlNodeName.ALBUM));
		assertEquals("Podcast", track02.get(XmlNodeName.GENRE));
		assertEquals("MPEG-audiobestand", track02.get(XmlNodeName.KIND));
		assertEquals("11587043", track02.get(XmlNodeName.SIZE));
		assertEquals("1448124", track02.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2009", track02.get(XmlNodeName.YEAR));
		assertEquals("1", track02.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3357141508", track02.get(XmlNodeName.PLAY_DATE));
		assertEquals("2010-05-19T18:18:28Z",
				track02.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2009-11-18T16:42:08Z",
				track02.get(XmlNodeName.RELEASE_DATE));
		assertEquals("2010-04-03T22:33:57Z",
				track02.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-04-03T22:33:57Z",
				track02.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", track02.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", track02.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("F28F93BB54F82F33", track02.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", track02.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track02.get(XmlNodeName.PODCAST));
		assertEquals(
				"file://localhost/Users/michalhuniewicz/Music/iTunes/iTunes%20Music/Podcasts/Scientific%20American%20Podcast/Tree%20Ring%20Science%20and%20Tomorrow's%20Water.mp3",
				track02.get(XmlNodeName.LOCATION));
		assertEquals("4", track02.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track02.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertEquals("Join host Steve Mirsky each week as he explores the "
				+ "latest developments in science and technology through "
				+ "interviews with leading scientists and journalists.",
				track02.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect03() {
		Map<XmlNodeName, String> track03 = stubRawTrackDataHandler
				.getTrack("1160");
		assertEquals("The Rubaiyat (Unabridged)", track03.get(XmlNodeName.NAME));
		assertEquals("Omar Khayyam", track03.get(XmlNodeName.ARTIST));
		assertEquals("Omar Khayyam", track03.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Rubaiyat (Unabridged)",
				track03.get(XmlNodeName.ALBUM));
		assertEquals("Classics", track03.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file", track03.get(XmlNodeName.KIND));
		assertEquals("6632521", track03.get(XmlNodeName.SIZE));
		assertEquals("1670568", track03.get(XmlNodeName.TOTAL_TIME));
		assertNull(track03.get(XmlNodeName.YEAR));
		assertEquals("1", track03.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3340725993", track03.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-10T18:26:33Z",
				track03.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("80", track03.get(XmlNodeName.RATING));
		assertEquals("80", track03.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track03.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", track03.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", track03.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", track03.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", track03.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2009-03-17T17:42:40Z",
				track03.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:39:58Z",
				track03.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", track03.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", track03.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", track03.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("BB894F4BB38E619E", track03.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("1149", track03.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", track03.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track03.get(XmlNodeName.PROTECTED));
		assertEquals("true", track03.get(XmlNodeName.PURCHASED));
		assertEquals("Rubaiyat (Unabridged)",
				track03.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Rubaiyat (Unabridged)",
				track03.get(XmlNodeName.SORT_NAME));
		assertEquals(
				"file://localhost/E:/iTunes/Omar%20Khayyam/The%20Rubaiyat%20(Unabridged)/01%20The%20Rubaiyat%20(Unabridged).m4b",
				track03.get(XmlNodeName.LOCATION));
		assertEquals("4", track03.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track03.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track03.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect04() {
		Map<XmlNodeName, String> track04 = stubRawTrackDataHandler
				.getTrack("1162");
		assertEquals("Revelations", track04.get(XmlNodeName.NAME));
		assertEquals("Audioslave", track04.get(XmlNodeName.ARTIST));
		assertEquals("Audioslave", track04.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Revelations", track04.get(XmlNodeName.ALBUM));
		assertEquals("Alternative", track04.get(XmlNodeName.GENRE));
		assertEquals("Purchased AAC audio file", track04.get(XmlNodeName.KIND));
		assertEquals("8818831", track04.get(XmlNodeName.SIZE));
		assertEquals("252413", track04.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2006", track04.get(XmlNodeName.YEAR));
		assertEquals("2", track04.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3341067375", track04.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-14T17:16:15Z",
				track04.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2006-09-01T07:00:00Z",
				track04.get(XmlNodeName.RELEASE_DATE));
		assertEquals("100", track04.get(XmlNodeName.RATING));
		assertEquals("80", track04.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track04.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", track04.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", track04.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", track04.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("2009-04-04T19:54:36Z",
				track04.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:39:58Z",
				track04.get(XmlNodeName.DATE_ADDED));
		assertEquals("256", track04.get(XmlNodeName.BIT_RATE));
		assertEquals("44100", track04.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", track04.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("50F6CE431D13761A", track04.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("8175", track04.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", track04.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track04.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Audioslave/Revelations/01%20Revelations.m4a",
				track04.get(XmlNodeName.LOCATION));
		assertEquals("4", track04.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track04.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track04.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect05() {
		Map<XmlNodeName, String> track05 = stubRawTrackDataHandler
				.getTrack("1164");
		assertEquals("One and the Same", track05.get(XmlNodeName.NAME));
		assertEquals("Audioslave", track05.get(XmlNodeName.ARTIST));
		assertEquals("Audioslave", track05.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Revelations", track05.get(XmlNodeName.ALBUM));
		assertEquals("Alternative", track05.get(XmlNodeName.GENRE));
		assertEquals("Purchased AAC audio file", track05.get(XmlNodeName.KIND));
		assertEquals("7682827", track05.get(XmlNodeName.SIZE));
		assertEquals("217773", track05.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2006", track05.get(XmlNodeName.YEAR));
		assertEquals("2", track05.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3341067589", track05.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-14T17:19:49Z",
				track05.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2006-09-01T07:00:00Z",
				track05.get(XmlNodeName.RELEASE_DATE));
		assertEquals("100", track05.get(XmlNodeName.RATING));
		assertEquals("80", track05.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track05.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", track05.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", track05.get(XmlNodeName.DISC_COUNT));
		assertEquals("2", track05.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("2009-04-04T20:56:22Z",
				track05.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:39:58Z",
				track05.get(XmlNodeName.DATE_ADDED));
		assertEquals("256", track05.get(XmlNodeName.BIT_RATE));
		assertEquals("44100", track05.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", track05.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("69D146B49228D2A6", track05.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("8436", track05.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", track05.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track05.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Audioslave/Revelations/02%20One%20and%20the%20Same.m4a",
				track05.get(XmlNodeName.LOCATION));
		assertEquals("4", track05.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track05.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track05.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect06() {
		Map<XmlNodeName, String> track06 = stubRawTrackDataHandler
				.getTrack("1166");
		assertEquals("Sound of a Gun", track06.get(XmlNodeName.NAME));
		assertEquals("Audioslave", track06.get(XmlNodeName.ARTIST));
		assertEquals("Audioslave", track06.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Revelations", track06.get(XmlNodeName.ALBUM));
		assertEquals("Alternative", track06.get(XmlNodeName.GENRE));
		assertEquals("Purchased AAC audio file", track06.get(XmlNodeName.KIND));
		assertEquals("9029949", track06.get(XmlNodeName.SIZE));
		assertEquals("260200", track06.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2006", track06.get(XmlNodeName.YEAR));
		assertEquals("2", track06.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3341067844", track06.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-14T17:24:04Z",
				track06.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2006-09-01T07:00:00Z",
				track06.get(XmlNodeName.RELEASE_DATE));
		assertEquals("100", track06.get(XmlNodeName.RATING));
		assertEquals("80", track06.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track06.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", track06.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", track06.get(XmlNodeName.DISC_COUNT));
		assertEquals("3", track06.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("2009-04-04T20:59:44Z",
				track06.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:39:58Z",
				track06.get(XmlNodeName.DATE_ADDED));
		assertEquals("256", track06.get(XmlNodeName.BIT_RATE));
		assertEquals("44100", track06.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", track06.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("8534F313F31F3C99", track06.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("7921", track06.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", track06.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track06.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Audioslave/Revelations/03%20Sound%20of%20a%20Gun.m4a",
				track06.get(XmlNodeName.LOCATION));
		assertEquals("4", track06.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track06.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track06.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect07() {
		Map<XmlNodeName, String> track07 = stubRawTrackDataHandler
				.getTrack("1168");
		assertEquals("Until We Fall", track07.get(XmlNodeName.NAME));
		assertEquals("Audioslave", track07.get(XmlNodeName.ARTIST));
		assertEquals("Audioslave", track07.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Revelations", track07.get(XmlNodeName.ALBUM));
		assertEquals("Alternative", track07.get(XmlNodeName.GENRE));
		assertEquals("Purchased AAC audio file", track07.get(XmlNodeName.KIND));
		assertEquals("8113929", track07.get(XmlNodeName.SIZE));
		assertEquals("230786", track07.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2006", track07.get(XmlNodeName.YEAR));
		assertEquals("2", track07.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3341068070", track07.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-14T17:27:50Z",
				track07.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2006-09-01T07:00:00Z",
				track07.get(XmlNodeName.RELEASE_DATE));
		assertEquals("80", track07.get(XmlNodeName.RATING));
		assertEquals("80", track07.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track07.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", track07.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", track07.get(XmlNodeName.DISC_COUNT));
		assertEquals("4", track07.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("2009-04-04T21:00:26Z",
				track07.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:39:58Z",
				track07.get(XmlNodeName.DATE_ADDED));
		assertEquals("256", track07.get(XmlNodeName.BIT_RATE));
		assertEquals("44100", track07.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", track07.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("7DB559362585CC5C", track07.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("6119", track07.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", track07.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track07.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Audioslave/Revelations/04%20Until%20We%20Fall.m4a",
				track07.get(XmlNodeName.LOCATION));
		assertEquals("4", track07.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track07.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track07.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect08() {
		Map<XmlNodeName, String> track08 = stubRawTrackDataHandler
				.getTrack("1170");
		assertEquals("Original Fire", track08.get(XmlNodeName.NAME));
		assertEquals("Audioslave", track08.get(XmlNodeName.ARTIST));
		assertEquals("Audioslave", track08.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Revelations", track08.get(XmlNodeName.ALBUM));
		assertEquals("Alternative", track08.get(XmlNodeName.GENRE));
		assertEquals("Purchased AAC audio file", track08.get(XmlNodeName.KIND));
		assertEquals("7504955", track08.get(XmlNodeName.SIZE));
		assertEquals("218946", track08.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2006", track08.get(XmlNodeName.YEAR));
		assertEquals("2", track08.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3341068284", track08.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-14T17:31:24Z",
				track08.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2006-09-01T07:00:00Z",
				track08.get(XmlNodeName.RELEASE_DATE));
		assertEquals("100", track08.get(XmlNodeName.RATING));
		assertEquals("80", track08.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track08.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", track08.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", track08.get(XmlNodeName.DISC_COUNT));
		assertEquals("5", track08.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("2009-04-04T21:01:50Z",
				track08.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:39:59Z",
				track08.get(XmlNodeName.DATE_ADDED));
		assertEquals("256", track08.get(XmlNodeName.BIT_RATE));
		assertEquals("44100", track08.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", track08.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("7FA2FF2D849E0E99", track08.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("7791", track08.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", track08.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track08.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Audioslave/Revelations/05%20Original%20Fire.m4a",
				track08.get(XmlNodeName.LOCATION));
		assertEquals("4", track08.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track08.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track08.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect09() {
		Map<XmlNodeName, String> track09 = stubRawTrackDataHandler
				.getTrack("3100");
		assertEquals("4 Way Diablo", track09.get(XmlNodeName.NAME));
		assertEquals("Monster Magnet", track09.get(XmlNodeName.ARTIST));
		assertEquals("Monster Magnet", track09.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("4-Way Diablo", track09.get(XmlNodeName.ALBUM));
		assertEquals("Rock", track09.get(XmlNodeName.GENRE));
		assertEquals("Purchased AAC audio file", track09.get(XmlNodeName.KIND));
		assertEquals("6991746", track09.get(XmlNodeName.SIZE));
		assertEquals("199680", track09.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2007", track09.get(XmlNodeName.YEAR));
		assertEquals("1", track09.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3340566114", track09.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-08T22:01:54Z",
				track09.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2007-11-02T07:00:00Z",
				track09.get(XmlNodeName.RELEASE_DATE));
		assertEquals("80", track09.get(XmlNodeName.RATING));
		assertEquals("80", track09.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track09.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", track09.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", track09.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", track09.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("13", track09.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2009-06-06T19:01:20Z",
				track09.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:40:38Z",
				track09.get(XmlNodeName.DATE_ADDED));
		assertEquals("256", track09.get(XmlNodeName.BIT_RATE));
		assertEquals("44100", track09.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", track09.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("7E94358243C6D520", track09.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("14026", track09.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", track09.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track09.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Monster%20Magnet/4-Way%20Diablo/01%204%20Way%20Diablo.m4a",
				track09.get(XmlNodeName.LOCATION));
		assertEquals("4", track09.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track09.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track09.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testVariousCorrect10() {
		Map<XmlNodeName, String> track10 = stubRawTrackDataHandler
				.getTrack("19058");
		assertEquals("Plundered My Soul (Stones In Exile Version)",
				track10.get(XmlNodeName.NAME));
		assertEquals("The Rolling Stones", track10.get(XmlNodeName.ARTIST));
		assertEquals("The Rolling Stones",
				track10.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Exile On Main St", track10.get(XmlNodeName.ALBUM));
		assertEquals("Rock", track10.get(XmlNodeName.GENRE));
		assertEquals("Purchased MPEG-4 video file",
				track10.get(XmlNodeName.KIND));
		assertEquals("55605450", track10.get(XmlNodeName.SIZE));
		assertEquals("244080", track10.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2003", track10.get(XmlNodeName.YEAR));
		assertEquals("1", track10.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3382468751", track10.get(XmlNodeName.PLAY_DATE));
		assertEquals("2011-03-08T21:39:11Z",
				track10.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("2003-04-28T07:00:00Z",
				track10.get(XmlNodeName.RELEASE_DATE));
		assertEquals("80", track10.get(XmlNodeName.RATING));
		assertEquals("80", track10.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", track10.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("11", track10.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("Rolling Stones",
				track10.get(XmlNodeName.SORT_ALBUM_ARTIST));
		assertEquals("Rolling Stones", track10.get(XmlNodeName.SORT_ARTIST));
		assertEquals("2011-03-08T20:17:20Z",
				track10.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-03-08T21:23:36Z",
				track10.get(XmlNodeName.DATE_ADDED));
		assertEquals("241", track10.get(XmlNodeName.BIT_RATE));
		assertEquals("1", track10.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("5F8A2E8E75E60754", track10.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", track10.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", track10.get(XmlNodeName.PURCHASED));
		assertEquals("true", track10.get(XmlNodeName.HAS_VIDEO));
		assertEquals("true", track10.get(XmlNodeName.MUSIC_VIDEO));
		assertEquals("false", track10.get(XmlNodeName.HD));
		assertEquals("640", track10.get(XmlNodeName.VIDEO_WIDTH));
		assertEquals("352", track10.get(XmlNodeName.VIDEO_HEIGHT));
		assertEquals(
				"file://localhost/E:/iTunes/The%20Rolling%20Stones/Exile%20On%20Main%20St/11%20Plundered%20My%20Soul%20(Stones%20In%20Exil.m4v",
				track10.get(XmlNodeName.LOCATION));
		assertEquals("4", track10.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", track10.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(track10.get(XmlNodeName.COMMENTS));
	}

}
