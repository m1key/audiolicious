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
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import me.m1key.audiolicious.commons.XmlNodeName;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class VtdItunesLibraryParserAudiobooksIT {

	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/Audiobooks-2011-05-29.xml";

	@Inject
	private LibraryParser parser;
	@Inject
	private StubRawTrackDataHandler stubRawTrackDataHandler;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						VtdItunesLibraryParserAudiobooksIT.class
								.getSimpleName() + ".war")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addClasses(LibraryParser.class, RawTrackDataHandler.class,
						StubRawTrackDataHandler.class,
						StubRawTrackDataHandlerImpl.class,
						VtdItunesLibraryParser.class, XmlNodeName.class,
						XmlParseException.class)
				.addAsLibraries(
						DependencyResolvers
								.use(MavenDependencyResolver.class)
								.artifacts("com.ximpleware:vtd-xml:2.10",
										"org.slf4j:slf4j-api:1.6.1")
								.resolveAsFiles());
	}

	@Before
	public void setup() {
		File libraryFile = new File(pathToFile);
		parser.process(libraryFile);
	}

	@Test
	public void testAudiobooksCorrectNumberOfTracks() {
		assertEquals(30, stubRawTrackDataHandler.getRawTrackData().size());
	}

	@Test
	public void testAudiobooksCorrectAudiobook01() {
		Map<XmlNodeName, String> audiobook01 = stubRawTrackDataHandler
				.getTrack("18246");
		assertEquals("18246", audiobook01.get(XmlNodeName.TRACK_ID));
		assertEquals("All Quiet on the Western Front (Unabridged)",
				audiobook01.get(XmlNodeName.NAME));
		assertEquals("Erich Maria Remarque",
				audiobook01.get(XmlNodeName.ARTIST));
		assertEquals("Erich Maria Remarque",
				audiobook01.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("All Quiet on the Western Front (Unabridged)",
				audiobook01.get(XmlNodeName.ALBUM));
		assertEquals("Classics", audiobook01.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook01.get(XmlNodeName.KIND));
		assertEquals("206312863", audiobook01.get(XmlNodeName.SIZE));
		assertEquals("25771221", audiobook01.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook01.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook01.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook01.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook01.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-11-20T09:04:26Z",
				audiobook01.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-11-20T13:12:12Z",
				audiobook01.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook01.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook01.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook01.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("E29A456B06978C87",
				audiobook01.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook01.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook01.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook01.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Erich%20Maria%20Remarque/All%20Quiet%20on%20the%20Western%20Front%20(Unabridg/01%20All%20Quiet%20on%20the%20Western%20Front%20(U.m4b",
				audiobook01.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook01.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook01.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook02() {
		Map<XmlNodeName, String> audiobook02 = stubRawTrackDataHandler
				.getTrack("19264");
		assertEquals(
				"At Home: A Short History of Private Life (Unabridged) Part 1",
				audiobook02.get(XmlNodeName.NAME));
		assertEquals("Bill Bryson", audiobook02.get(XmlNodeName.ARTIST));
		assertEquals("Bill Bryson", audiobook02.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("At Home: A Short History of Private Life (Unabridged)",
				audiobook02.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook02.get(XmlNodeName.GENRE));
		assertEquals("Audible file", audiobook02.get(XmlNodeName.KIND));
		assertEquals("253635131", audiobook02.get(XmlNodeName.SIZE));
		assertEquals("31341226", audiobook02.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2011-03-16T20:05:12Z",
				audiobook02.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-03-16T20:50:44Z",
				audiobook02.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook02.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook02.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook02.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("9AAC061331BEB025",
				audiobook02.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook02.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook02.get(XmlNodeName.PROTECTED));
		assertEquals(
				"file://localhost/E:/iTunes/Bill%20Bryson/At%20Home_%20A%20Short%20History%20of%20Private%20Life/At%20Home_%20A%20Short%20History%20of%20Private.aax",
				audiobook02.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook02.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook02.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertEquals(
				"Bill Bryson was struck one day by the thought that we devote "
						+ "more time to studying the battles and wars of history than to "
						+ "considering what history really consists of....",
				audiobook02.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook03() {
		Map<XmlNodeName, String> audiobook03 = stubRawTrackDataHandler
				.getTrack("19266");
		assertEquals(
				"At Home: A Short History of Private Life (Unabridged) Part 2",
				audiobook03.get(XmlNodeName.NAME));
		assertEquals("Bill Bryson", audiobook03.get(XmlNodeName.ARTIST));
		assertEquals("Bill Bryson", audiobook03.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("At Home: A Short History of Private Life (Unabridged)",
				audiobook03.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook03.get(XmlNodeName.GENRE));
		assertEquals("Audible file", audiobook03.get(XmlNodeName.KIND));
		assertEquals("228916560", audiobook03.get(XmlNodeName.SIZE));
		assertEquals("28283623", audiobook03.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2011-03-16T20:11:44Z",
				audiobook03.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-03-16T20:50:58Z",
				audiobook03.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook03.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook03.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook03.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("90888100D07DF981",
				audiobook03.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook03.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook03.get(XmlNodeName.PROTECTED));
		assertEquals(
				"file://localhost/E:/iTunes/Bill%20Bryson/At%20Home_%20A%20Short%20History%20of%20Private%20Life/At%20Home_%20A%20Short%20History%20of%20Private%201.aax",
				audiobook03.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook03.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook03.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertEquals(
				"Bill Bryson was struck one day by the thought that we devote "
						+ "more time to studying the battles and wars of history than to "
						+ "considering what history really consists of....",
				audiobook03.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook04() {
		Map<XmlNodeName, String> audiobook04 = stubRawTrackDataHandler
				.getTrack("20052");
		assertEquals("Blade Runner (Unabridged), Part 1",
				audiobook04.get(XmlNodeName.NAME));
		assertEquals("Philip K. Dick", audiobook04.get(XmlNodeName.ARTIST));
		assertEquals("Philip K. Dick",
				audiobook04.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Blade Runner (Unabridged)",
				audiobook04.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook04.get(XmlNodeName.GENRE));
		assertEquals("Audible file", audiobook04.get(XmlNodeName.KIND));
		assertEquals("128194118", audiobook04.get(XmlNodeName.SIZE));
		assertEquals("15825235", audiobook04.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2007", audiobook04.get(XmlNodeName.YEAR));
		assertEquals("2011-04-16T16:27:34Z",
				audiobook04.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-04-17T12:47:29Z",
				audiobook04.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook04.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook04.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook04.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("1BE3CE501924BB06",
				audiobook04.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook04.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook04.get(XmlNodeName.PROTECTED));
		assertEquals(
				"file://localhost/E:/iTunes/Philip%20K.%20Dick/Blade%20Runner%20(Unabridged)/Blade%20Runner%20(Unabridged),%20Part%201.aax",
				audiobook04.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook04.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook04.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertEquals("Somewhere among the hordes of humans out there, lurked "
				+ "several rogue androids....",
				audiobook04.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook05() {
		Map<XmlNodeName, String> audiobook05 = stubRawTrackDataHandler
				.getTrack("20054");
		assertEquals("Blade Runner (Unabridged), Part 2",
				audiobook05.get(XmlNodeName.NAME));
		assertEquals("Philip K. Dick", audiobook05.get(XmlNodeName.ARTIST));
		assertEquals("Philip K. Dick",
				audiobook05.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Blade Runner (Unabridged)",
				audiobook05.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook05.get(XmlNodeName.GENRE));
		assertEquals("Audible file", audiobook05.get(XmlNodeName.KIND));
		assertEquals("140611539", audiobook05.get(XmlNodeName.SIZE));
		assertEquals("17361142", audiobook05.get(XmlNodeName.TOTAL_TIME));
		assertEquals("2007", audiobook05.get(XmlNodeName.YEAR));
		assertEquals("2011-04-16T16:34:52Z",
				audiobook05.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-04-17T12:47:29Z",
				audiobook05.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook05.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook05.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook05.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("2AB8C0AF7FFA8161",
				audiobook05.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook05.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook05.get(XmlNodeName.PROTECTED));
		assertEquals(
				"file://localhost/E:/iTunes/Philip%20K.%20Dick/Blade%20Runner%20(Unabridged)/Blade%20Runner%20(Unabridged),%20Part%202.aax",
				audiobook05.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook05.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook05.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertEquals("Somewhere among the hordes of humans out there, lurked "
				+ "several rogue androids....",
				audiobook05.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook06() {
		Map<XmlNodeName, String> audiobook06 = stubRawTrackDataHandler
				.getTrack("20852");
		assertEquals("Dune (Unabridged), Part 1",
				audiobook06.get(XmlNodeName.NAME));
		assertEquals("Frank Herbert", audiobook06.get(XmlNodeName.ARTIST));
		assertNull(audiobook06.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Dune (Unabridged)", audiobook06.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook06.get(XmlNodeName.GENRE));
		assertEquals("Audible file", audiobook06.get(XmlNodeName.KIND));
		assertEquals("104709017", audiobook06.get(XmlNodeName.SIZE));
		assertEquals("26238000", audiobook06.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook06.get(XmlNodeName.YEAR));
		assertEquals("2011-05-16T20:39:50Z",
				audiobook06.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-05-16T20:51:00Z",
				audiobook06.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook06.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook06.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook06.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("C171E6662FA7AEBA",
				audiobook06.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook06.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook06.get(XmlNodeName.PROTECTED));
		assertEquals(
				"file://localhost/E:/iTunes/Frank%20Herbert/Dune%20(Unabridged)/Dune%20(Unabridged),%20Part%201.aa",
				audiobook06.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook06.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook06.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook06.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook07() {
		Map<XmlNodeName, String> audiobook07 = stubRawTrackDataHandler
				.getTrack("20854");
		assertEquals("Dune (Unabridged), Part 2",
				audiobook07.get(XmlNodeName.NAME));
		assertEquals("Frank Herbert", audiobook07.get(XmlNodeName.ARTIST));
		assertNull(audiobook07.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Dune (Unabridged)", audiobook07.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook07.get(XmlNodeName.GENRE));
		assertEquals("Audible file", audiobook07.get(XmlNodeName.KIND));
		assertEquals("107573883", audiobook07.get(XmlNodeName.SIZE));
		assertEquals("26958000", audiobook07.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook07.get(XmlNodeName.YEAR));
		assertEquals("2011-05-16T20:39:42Z",
				audiobook07.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-05-16T20:51:00Z",
				audiobook07.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook07.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook07.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook07.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("5E259703E3F72D79",
				audiobook07.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook07.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook07.get(XmlNodeName.PROTECTED));
		assertEquals(
				"file://localhost/E:/iTunes/Frank%20Herbert/Dune%20(Unabridged)/Dune%20(Unabridged),%20Part%202.aa",
				audiobook07.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook07.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook07.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook07.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook08() {
		Map<XmlNodeName, String> audiobook08 = stubRawTrackDataHandler
				.getTrack("20856");
		assertEquals("Dune (Unabridged), Part 3",
				audiobook08.get(XmlNodeName.NAME));
		assertEquals("Frank Herbert", audiobook08.get(XmlNodeName.ARTIST));
		assertNull(audiobook08.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Dune (Unabridged)", audiobook08.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook08.get(XmlNodeName.GENRE));
		assertEquals("Audible file", audiobook08.get(XmlNodeName.KIND));
		assertEquals("90695492", audiobook08.get(XmlNodeName.SIZE));
		assertEquals("22728000", audiobook08.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook08.get(XmlNodeName.YEAR));
		assertEquals("2011-05-16T20:38:50Z",
				audiobook08.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-05-16T20:51:00Z",
				audiobook08.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook08.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook08.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook08.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("570D63FCB1DB9A75",
				audiobook08.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook08.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook08.get(XmlNodeName.PROTECTED));
		assertEquals(
				"file://localhost/E:/iTunes/Frank%20Herbert/Dune%20(Unabridged)/Dune%20(Unabridged),%20Part%203.aa",
				audiobook08.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook08.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook08.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook08.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook09() {
		Map<XmlNodeName, String> audiobook09 = stubRawTrackDataHandler
				.getTrack("20116");
		assertEquals("Farsi Persian Phase 1, Unit 01-05: Learn to Speak and "
				+ "Understand Farsi Persian with Pimsleur Language Programs",
				audiobook09.get(XmlNodeName.NAME));
		assertEquals("Pimsleur", audiobook09.get(XmlNodeName.ARTIST));
		assertEquals("Pimsleur", audiobook09.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Farsi Persian Phase 1, Unit 01-05: Learn to Speak and "
				+ "Understand Farsi Persian with Pimsleur Language Programs",
				audiobook09.get(XmlNodeName.ALBUM));
		assertEquals("Languages", audiobook09.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook09.get(XmlNodeName.KIND));
		assertEquals("77757717", audiobook09.get(XmlNodeName.SIZE));
		assertEquals("9710582", audiobook09.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook09.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook09.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook09.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook09.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook09.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2011-04-20T20:37:06Z",
				audiobook09.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2011-04-20T21:05:51Z",
				audiobook09.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook09.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook09.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook09.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("21E696430AA2CC62",
				audiobook09.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook09.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook09.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook09.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Pimsleur/Farsi%20Persian%20Phase%201,%20Unit%2001-05_%20Learn/01%20Farsi%20Persian%20Phase%201,%20Unit%2001-05.m4b",
				audiobook09.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook09.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook09.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook09.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook10() {
		Map<XmlNodeName, String> audiobook10 = stubRawTrackDataHandler
				.getTrack("13100");
		assertEquals(
				"The Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "Part 1 of 3", audiobook10.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook10.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook10.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals(
				"The Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "(Unabridged Nonfiction)",
				audiobook10.get(XmlNodeName.ALBUM));
		assertEquals("Wetenschap", audiobook10.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook10.get(XmlNodeName.KIND));
		assertEquals("38664077", audiobook10.get(XmlNodeName.SIZE));
		assertEquals("9751250", audiobook10.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook10.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook10.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook10.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook10.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("3", audiobook10.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-05-19T21:48:18Z",
				audiobook10.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:44:06Z",
				audiobook10.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook10.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook10.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("943", audiobook10.get(XmlNodeName.NORMALIZATION));
		assertEquals(
				"Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "(Unabridged Nonfiction)",
				audiobook10.get(XmlNodeName.SORT_ALBUM));
		assertEquals(
				"Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "Part 1 of 3", audiobook10.get(XmlNodeName.SORT_NAME));
		assertEquals("1", audiobook10.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("D6FAD3D98DFE59E0",
				audiobook10.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook10.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook10.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook10.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/The%20Feynman%20Lectures%20On%20Physics_%20Volume/01%20The%20Feynman%20Lectures%20On%20Physics_.m4b",
				audiobook10.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook10.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook10.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook10.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook11() {
		Map<XmlNodeName, String> audiobook11 = stubRawTrackDataHandler
				.getTrack("13102");
		assertEquals(
				"The Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "Part 2 of 3", audiobook11.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook11.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook11.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals(
				"The Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "(Unabridged Nonfiction)",
				audiobook11.get(XmlNodeName.ALBUM));
		assertEquals("Wetenschap", audiobook11.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook11.get(XmlNodeName.KIND));
		assertEquals("27268061", audiobook11.get(XmlNodeName.SIZE));
		assertEquals("6872957", audiobook11.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook11.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook11.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook11.get(XmlNodeName.DISC_COUNT));
		assertEquals("2", audiobook11.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("3", audiobook11.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-05-19T21:45:16Z",
				audiobook11.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:44:06Z",
				audiobook11.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook11.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook11.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("479", audiobook11.get(XmlNodeName.NORMALIZATION));
		assertEquals(
				"Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "(Unabridged Nonfiction)",
				audiobook11.get(XmlNodeName.SORT_ALBUM));
		assertEquals(
				"Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "Part 2 of 3", audiobook11.get(XmlNodeName.SORT_NAME));
		assertEquals("1", audiobook11.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("501F6AD39F3E775B",
				audiobook11.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook11.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook11.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook11.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/The%20Feynman%20Lectures%20On%20Physics_%20Volume/02%20The%20Feynman%20Lectures%20On%20Physics_.m4b",
				audiobook11.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook11.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook11.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook11.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook12() {
		Map<XmlNodeName, String> audiobook12 = stubRawTrackDataHandler
				.getTrack("13104");
		assertEquals(
				"The Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "Part 3 of 3", audiobook12.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook12.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook12.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals(
				"The Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "(Unabridged Nonfiction)",
				audiobook12.get(XmlNodeName.ALBUM));
		assertEquals("Wetenschap", audiobook12.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook12.get(XmlNodeName.KIND));
		assertEquals("12994109", audiobook12.get(XmlNodeName.SIZE));
		assertEquals("3269373", audiobook12.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook12.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook12.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook12.get(XmlNodeName.DISC_COUNT));
		assertEquals("3", audiobook12.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("3", audiobook12.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-05-19T21:41:56Z",
				audiobook12.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:44:06Z",
				audiobook12.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook12.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook12.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("547", audiobook12.get(XmlNodeName.NORMALIZATION));
		assertEquals(
				"Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "(Unabridged Nonfiction)",
				audiobook12.get(XmlNodeName.SORT_ALBUM));
		assertEquals(
				"Feynman Lectures On Physics: Volume 1, Quantum Mechanics "
						+ "Part 3 of 3", audiobook12.get(XmlNodeName.SORT_NAME));
		assertEquals("1", audiobook12.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("92E8D4607DAB58A4",
				audiobook12.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook12.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook12.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook12.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/The%20Feynman%20Lectures%20On%20Physics_%20Volume/03%20The%20Feynman%20Lectures%20On%20Physics_.m4b",
				audiobook12.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook12.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook12.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook12.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook13() {
		Map<XmlNodeName, String> audiobook13 = stubRawTrackDataHandler
				.getTrack("14526");
		assertEquals("Jabberwocky (Unabridged)",
				audiobook13.get(XmlNodeName.NAME));
		assertEquals("Lewis Carroll", audiobook13.get(XmlNodeName.ARTIST));
		assertEquals("Lewis Carroll", audiobook13.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Jabberwocky (Unabridged)",
				audiobook13.get(XmlNodeName.ALBUM));
		assertEquals("Kinderen en jongvolwassenen",
				audiobook13.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook13.get(XmlNodeName.KIND));
		assertEquals("1669386", audiobook13.get(XmlNodeName.SIZE));
		assertEquals("174750", audiobook13.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook13.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook13.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook13.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook13.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook13.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-05-17T20:30:30Z",
				audiobook13.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:44:33Z",
				audiobook13.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook13.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook13.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook13.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("840F7806BD4940F9",
				audiobook13.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook13.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook13.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook13.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Lewis%20Carroll/Jabberwocky%20(Unabridged)/01%20Jabberwocky%20(Unabridged).m4b",
				audiobook13.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook13.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook13.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook13.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook14() {
		Map<XmlNodeName, String> audiobook14 = stubRawTrackDataHandler
				.getTrack("17084");
		assertEquals("The Kite Runner (Unabridged), Part 1",
				audiobook14.get(XmlNodeName.NAME));
		assertEquals("Khaled Hosseini", audiobook14.get(XmlNodeName.ARTIST));
		assertEquals("Khaled Hosseini",
				audiobook14.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Kite Runner (Unabridged)",
				audiobook14.get(XmlNodeName.ALBUM));
		assertEquals("Fictie", audiobook14.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook14.get(XmlNodeName.KIND));
		assertEquals("178365763", audiobook14.get(XmlNodeName.SIZE));
		assertEquals("22280936", audiobook14.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook14.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook14.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook14.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook14.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("2", audiobook14.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-13T16:22:14Z",
				audiobook14.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-13T18:16:35Z",
				audiobook14.get(XmlNodeName.DATE_ADDED));
		assertEquals("Kite Runner (Unabridged)",
				audiobook14.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Kite Runner (Unabridged), Part 1",
				audiobook14.get(XmlNodeName.SORT_NAME));
		assertEquals("64", audiobook14.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook14.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook14.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("73CFBF1C09ECA59A",
				audiobook14.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook14.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook14.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook14.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Khaled%20Hosseini/The%20Kite%20Runner%20(Unabridged)/01%20The%20Kite%20Runner%20(Unabridged),%20Par.m4b",
				audiobook14.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook14.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook14.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook14.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook15() {
		Map<XmlNodeName, String> audiobook15 = stubRawTrackDataHandler
				.getTrack("17086");
		assertEquals("The Kite Runner (Unabridged), Part 2",
				audiobook15.get(XmlNodeName.NAME));
		assertEquals("Khaled Hosseini", audiobook15.get(XmlNodeName.ARTIST));
		assertEquals("Khaled Hosseini",
				audiobook15.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Kite Runner (Unabridged)",
				audiobook15.get(XmlNodeName.ALBUM));
		assertEquals("Fictie", audiobook15.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook15.get(XmlNodeName.KIND));
		assertEquals("168953779", audiobook15.get(XmlNodeName.SIZE));
		assertEquals("21104381", audiobook15.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook15.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook15.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook15.get(XmlNodeName.DISC_COUNT));
		assertEquals("2", audiobook15.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("2", audiobook15.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-13T16:39:52Z",
				audiobook15.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-13T18:16:35Z",
				audiobook15.get(XmlNodeName.DATE_ADDED));
		assertEquals("Kite Runner (Unabridged)",
				audiobook15.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Kite Runner (Unabridged), Part 2",
				audiobook15.get(XmlNodeName.SORT_NAME));
		assertEquals("64", audiobook15.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook15.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook15.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("2E70FCE756C235D3",
				audiobook15.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook15.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook15.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook15.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Khaled%20Hosseini/The%20Kite%20Runner%20(Unabridged)/02%20The%20Kite%20Runner%20(Unabridged),%20Par.m4b",
				audiobook15.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook15.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook15.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook15.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook16() {
		Map<XmlNodeName, String> audiobook16 = stubRawTrackDataHandler
				.getTrack("18044");
		assertEquals("Letters from the Earth (Unabridged)",
				audiobook16.get(XmlNodeName.NAME));
		assertEquals("Mark Twain", audiobook16.get(XmlNodeName.ARTIST));
		assertEquals("Mark Twain", audiobook16.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Letters from the Earth (Unabridged)",
				audiobook16.get(XmlNodeName.ALBUM));
		assertEquals("Klassiekers", audiobook16.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook16.get(XmlNodeName.KIND));
		assertEquals("188202349", audiobook16.get(XmlNodeName.SIZE));
		assertEquals("23510340", audiobook16.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook16.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook16.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook16.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook16.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook16.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-10-17T07:37:33Z",
				audiobook16.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-10-17T08:15:29Z",
				audiobook16.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook16.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook16.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook16.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("CD014D39CFC8BE14",
				audiobook16.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook16.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook16.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook16.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Mark%20Twain/Letters%20from%20the%20Earth%20(Unabridged)/01%20Letters%20from%20the%20Earth%20(Unabridge.m4b",
				audiobook16.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook16.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook16.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook16.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook17() {
		Map<XmlNodeName, String> audiobook17 = stubRawTrackDataHandler
				.getTrack("12994");
		assertEquals("The Raven (Unabridged)",
				audiobook17.get(XmlNodeName.NAME));
		assertEquals("Edgar Allan Poe", audiobook17.get(XmlNodeName.ARTIST));
		assertEquals("Edgar Allan Poe",
				audiobook17.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Raven (Unabridged)",
				audiobook17.get(XmlNodeName.ALBUM));
		assertEquals("Klassiekers", audiobook17.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook17.get(XmlNodeName.KIND));
		assertEquals("5401772", audiobook17.get(XmlNodeName.SIZE));
		assertEquals("669056", audiobook17.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook17.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook17.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook17.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook17.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook17.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-05-17T20:26:30Z",
				audiobook17.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:44:04Z",
				audiobook17.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook17.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook17.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook17.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("E823B5AE190C6BC2",
				audiobook17.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook17.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook17.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook17.get(XmlNodeName.PURCHASED));
		assertEquals("Raven (Unabridged)",
				audiobook17.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Raven (Unabridged)",
				audiobook17.get(XmlNodeName.SORT_NAME));
		assertEquals(
				"file://localhost/E:/iTunes/Edgar%20Allan%20Poe/The%20Raven%20(Unabridged)/01%20The%20Raven%20(Unabridged).m4b",
				audiobook17.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook17.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook17.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook17.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook18() {
		Map<XmlNodeName, String> audiobook18 = stubRawTrackDataHandler
				.getTrack("1160");
		assertEquals("The Rubaiyat (Unabridged)",
				audiobook18.get(XmlNodeName.NAME));
		assertEquals("Omar Khayyam", audiobook18.get(XmlNodeName.ARTIST));
		assertEquals("Omar Khayyam", audiobook18.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Rubaiyat (Unabridged)",
				audiobook18.get(XmlNodeName.ALBUM));
		assertEquals("Classics", audiobook18.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook18.get(XmlNodeName.KIND));
		assertEquals("6632521", audiobook18.get(XmlNodeName.SIZE));
		assertEquals("1670568", audiobook18.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook18.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook18.get(XmlNodeName.PLAY_COUNT));
		assertEquals("3340725993", audiobook18.get(XmlNodeName.PLAY_DATE));
		assertEquals("2009-11-10T18:26:33Z",
				audiobook18.get(XmlNodeName.PLAY_DATE_UTC));
		assertEquals("80", audiobook18.get(XmlNodeName.RATING));
		assertEquals("80", audiobook18.get(XmlNodeName.ALBUM_RATING));
		assertEquals("true", audiobook18.get(XmlNodeName.ALBUM_RATING_COMPUTED));
		assertEquals("1", audiobook18.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook18.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook18.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook18.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2009-03-17T17:42:40Z",
				audiobook18.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:39:58Z",
				audiobook18.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook18.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook18.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook18.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("BB894F4BB38E619E",
				audiobook18.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("1149", audiobook18.get(XmlNodeName.NORMALIZATION));
		assertEquals("File", audiobook18.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook18.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook18.get(XmlNodeName.PURCHASED));
		assertEquals("Rubaiyat (Unabridged)",
				audiobook18.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Rubaiyat (Unabridged)",
				audiobook18.get(XmlNodeName.SORT_NAME));
		assertEquals(
				"file://localhost/E:/iTunes/Omar%20Khayyam/The%20Rubaiyat%20(Unabridged)/01%20The%20Rubaiyat%20(Unabridged).m4b",
				audiobook18.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook18.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook18.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertNull(audiobook18.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook19() {
		Map<XmlNodeName, String> audiobook19 = stubRawTrackDataHandler
				.getTrack("16460");
		assertEquals("Sluga Bozy", audiobook19.get(XmlNodeName.NAME));
		assertEquals("Jacek Piekara", audiobook19.get(XmlNodeName.ARTIST));
		assertEquals("Sluga Bozy", audiobook19.get(XmlNodeName.ALBUM));
		assertEquals("Audiobook", audiobook19.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook19.get(XmlNodeName.KIND));
		assertEquals("235180143", audiobook19.get(XmlNodeName.SIZE));
		assertEquals("35297394", audiobook19.get(XmlNodeName.TOTAL_TIME));
		assertNull(audiobook19.get(XmlNodeName.YEAR));
		assertEquals("1", audiobook19.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook19.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-04T20:11:27Z",
				audiobook19.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-04T20:13:00Z",
				audiobook19.get(XmlNodeName.DATE_ADDED));
		assertEquals("51", audiobook19.get(XmlNodeName.BIT_RATE));
		assertEquals("44100", audiobook19.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook19.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("9980A509A40058CB",
				audiobook19.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook19.get(XmlNodeName.TRACK_TYPE));
		assertEquals(
				"file://localhost/E:/iTunes/Jacek%20Piekara/Sluga%20Bozy/01%20Sluga%20Bozy.m4b",
				audiobook19.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook19.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook19.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
		assertEquals("czyta Jaroslaw Rabenda",
				audiobook19.get(XmlNodeName.COMMENTS));
	}

	@Test
	public void testAudiobooksCorrectAudiobook20() {
		Map<XmlNodeName, String> audiobook20 = stubRawTrackDataHandler
				.getTrack("18122");
		assertEquals("The Sun Also Rises (Unabridged) Part 1 of 4",
				audiobook20.get(XmlNodeName.NAME));
		assertEquals("Ernest Hemingway", audiobook20.get(XmlNodeName.ARTIST));
		assertEquals("Ernest Hemingway",
				audiobook20.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Sun Also Rises (Unabridged)",
				audiobook20.get(XmlNodeName.ALBUM));
		assertEquals("Klassiekers", audiobook20.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook20.get(XmlNodeName.KIND));
		assertEquals("32588727", audiobook20.get(XmlNodeName.SIZE));
		assertEquals("8145362", audiobook20.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook20.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook20.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook20.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("4", audiobook20.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-10-21T20:39:23Z",
				audiobook20.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-10-21T21:26:47Z",
				audiobook20.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook20.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook20.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1333", audiobook20.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook20.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("8A5F4BFFCDAECB1E",
				audiobook20.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("Sun Also Rises (Unabridged)",
				audiobook20.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Sun Also Rises (Unabridged) Part 1 of 4",
				audiobook20.get(XmlNodeName.SORT_NAME));
		assertEquals("File", audiobook20.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook20.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook20.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Ernest%20Hemingway/The%20Sun%20Also%20Rises%20(Unabridged)/01%20The%20Sun%20Also%20Rises%20(Unabridged)%20P.m4b",
				audiobook20.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook20.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook20.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook21() {
		Map<XmlNodeName, String> audiobook21 = stubRawTrackDataHandler
				.getTrack("18124");
		assertEquals("The Sun Also Rises (Unabridged) Part 2 of 4",
				audiobook21.get(XmlNodeName.NAME));
		assertEquals("Ernest Hemingway", audiobook21.get(XmlNodeName.ARTIST));
		assertEquals("Ernest Hemingway",
				audiobook21.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Sun Also Rises (Unabridged)",
				audiobook21.get(XmlNodeName.ALBUM));
		assertEquals("Klassiekers", audiobook21.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook21.get(XmlNodeName.KIND));
		assertEquals("30733879", audiobook21.get(XmlNodeName.SIZE));
		assertEquals("7680893", audiobook21.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook21.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook21.get(XmlNodeName.DISC_COUNT));
		assertEquals("2", audiobook21.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("4", audiobook21.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-10-21T20:43:06Z",
				audiobook21.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-10-21T21:26:47Z",
				audiobook21.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook21.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook21.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1546", audiobook21.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook21.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("B150B9BD9735660D",
				audiobook21.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("Sun Also Rises (Unabridged)",
				audiobook21.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Sun Also Rises (Unabridged) Part 2 of 4",
				audiobook21.get(XmlNodeName.SORT_NAME));
		assertEquals("File", audiobook21.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook21.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook21.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Ernest%20Hemingway/The%20Sun%20Also%20Rises%20(Unabridged)/02%20The%20Sun%20Also%20Rises%20(Unabridged)%20P.m4b",
				audiobook21.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook21.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook21.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook22() {
		Map<XmlNodeName, String> audiobook22 = stubRawTrackDataHandler
				.getTrack("18126");
		assertEquals("The Sun Also Rises (Unabridged) Part 3 of 4",
				audiobook22.get(XmlNodeName.NAME));
		assertEquals("Ernest Hemingway", audiobook22.get(XmlNodeName.ARTIST));
		assertEquals("Ernest Hemingway",
				audiobook22.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Sun Also Rises (Unabridged)",
				audiobook22.get(XmlNodeName.ALBUM));
		assertEquals("Klassiekers", audiobook22.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook22.get(XmlNodeName.KIND));
		assertEquals("31942503", audiobook22.get(XmlNodeName.SIZE));
		assertEquals("7983570", audiobook22.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook22.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook22.get(XmlNodeName.DISC_COUNT));
		assertEquals("3", audiobook22.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("4", audiobook22.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-10-21T20:37:50Z",
				audiobook22.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-10-21T21:26:47Z",
				audiobook22.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook22.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook22.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1390", audiobook22.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook22.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("7943DCE5159AD171",
				audiobook22.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("Sun Also Rises (Unabridged)",
				audiobook22.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Sun Also Rises (Unabridged) Part 3 of 4",
				audiobook22.get(XmlNodeName.SORT_NAME));
		assertEquals("File", audiobook22.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook22.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook22.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Ernest%20Hemingway/The%20Sun%20Also%20Rises%20(Unabridged)/03%20The%20Sun%20Also%20Rises%20(Unabridged)%20P.m4b",
				audiobook22.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook22.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook22.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook23() {
		Map<XmlNodeName, String> audiobook23 = stubRawTrackDataHandler
				.getTrack("18128");
		assertEquals("The Sun Also Rises (Unabridged) Part 4 of 4",
				audiobook23.get(XmlNodeName.NAME));
		assertEquals("Ernest Hemingway", audiobook23.get(XmlNodeName.ARTIST));
		assertEquals("Ernest Hemingway",
				audiobook23.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("The Sun Also Rises (Unabridged)",
				audiobook23.get(XmlNodeName.ALBUM));
		assertEquals("Klassiekers", audiobook23.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook23.get(XmlNodeName.KIND));
		assertEquals("16788079", audiobook23.get(XmlNodeName.SIZE));
		assertEquals("4222589", audiobook23.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook23.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook23.get(XmlNodeName.DISC_COUNT));
		assertEquals("4", audiobook23.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("4", audiobook23.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-10-21T20:41:37Z",
				audiobook23.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-10-21T21:26:47Z",
				audiobook23.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook23.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook23.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1336", audiobook23.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook23.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("651C005C51859E9A",
				audiobook23.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("Sun Also Rises (Unabridged)",
				audiobook23.get(XmlNodeName.SORT_ALBUM));
		assertEquals("Sun Also Rises (Unabridged) Part 4 of 4",
				audiobook23.get(XmlNodeName.SORT_NAME));
		assertEquals("File", audiobook23.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook23.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook23.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Ernest%20Hemingway/The%20Sun%20Also%20Rises%20(Unabridged)/04%20The%20Sun%20Also%20Rises%20(Unabridged)%20P.m4b",
				audiobook23.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook23.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook23.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook24() {
		Map<XmlNodeName, String> audiobook24 = stubRawTrackDataHandler
				.getTrack("17010");
		assertEquals(
				"Surely You're Joking, Mr. Feynman! (Unabridged) Part 1 of 6",
				audiobook24.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook24.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook24.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Surely You're Joking, Mr. Feynman! (Unabridged)",
				audiobook24.get(XmlNodeName.ALBUM));
		assertEquals("Biografieën en memoires",
				audiobook24.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook24.get(XmlNodeName.KIND));
		assertEquals("32447024", audiobook24.get(XmlNodeName.SIZE));
		assertEquals("8188456", audiobook24.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook24.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook24.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook24.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("6", audiobook24.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-12T18:54:09Z",
				audiobook24.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-12T19:43:45Z",
				audiobook24.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook24.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook24.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("699", audiobook24.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook24.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("9578AC2A989BE4B9",
				audiobook24.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook24.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook24.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook24.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/Surely%20You're%20Joking,%20Mr.%20Feynman!%20(Unab/01%20Surely%20You're%20Joking,%20Mr.%20Feynman.m4b",
				audiobook24.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook24.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook24.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook25() {
		Map<XmlNodeName, String> audiobook25 = stubRawTrackDataHandler
				.getTrack("17012");
		assertEquals(
				"Surely You're Joking, Mr. Feynman! (Unabridged) Part 2 of 6",
				audiobook25.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook25.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook25.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Surely You're Joking, Mr. Feynman! (Unabridged)",
				audiobook25.get(XmlNodeName.ALBUM));
		assertEquals("Biografieën en memoires",
				audiobook25.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook25.get(XmlNodeName.KIND));
		assertEquals("33072944", audiobook25.get(XmlNodeName.SIZE));
		assertEquals("8346536", audiobook25.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook25.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook25.get(XmlNodeName.DISC_COUNT));
		assertEquals("2", audiobook25.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("6", audiobook25.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-12T18:58:59Z",
				audiobook25.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-12T19:43:45Z",
				audiobook25.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook25.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook25.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("786", audiobook25.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook25.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("4C701C67A865DBA4",
				audiobook25.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook25.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook25.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook25.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/Surely%20You're%20Joking,%20Mr.%20Feynman!%20(Unab/02%20Surely%20You're%20Joking,%20Mr.%20Feynman.m4b",
				audiobook25.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook25.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook25.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook26() {
		Map<XmlNodeName, String> audiobook26 = stubRawTrackDataHandler
				.getTrack("17014");
		assertEquals(
				"Surely You're Joking, Mr. Feynman! (Unabridged) Part 3 of 6",
				audiobook26.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook26.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook26.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Surely You're Joking, Mr. Feynman! (Unabridged)",
				audiobook26.get(XmlNodeName.ALBUM));
		assertEquals("Biografieën en memoires",
				audiobook26.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook26.get(XmlNodeName.KIND));
		assertEquals("32904896", audiobook26.get(XmlNodeName.SIZE));
		assertEquals("8302632", audiobook26.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook26.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook26.get(XmlNodeName.DISC_COUNT));
		assertEquals("3", audiobook26.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("6", audiobook26.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-12T18:54:10Z",
				audiobook26.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-12T19:43:45Z",
				audiobook26.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook26.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook26.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("728", audiobook26.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook26.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("1257AB9EB89D55C2",
				audiobook26.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook26.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook26.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook26.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/Surely%20You're%20Joking,%20Mr.%20Feynman!%20(Unab/03%20Surely%20You're%20Joking,%20Mr.%20Feynman.m4b",
				audiobook26.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook26.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook26.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook27() {
		Map<XmlNodeName, String> audiobook27 = stubRawTrackDataHandler
				.getTrack("17016");
		assertEquals(
				"Surely You're Joking, Mr. Feynman! (Unabridged) Part 4 of 6",
				audiobook27.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook27.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook27.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Surely You're Joking, Mr. Feynman! (Unabridged)",
				audiobook27.get(XmlNodeName.ALBUM));
		assertEquals("Biografieën en memoires",
				audiobook27.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook27.get(XmlNodeName.KIND));
		assertEquals("32715616", audiobook27.get(XmlNodeName.SIZE));
		assertEquals("8254973", audiobook27.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook27.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook27.get(XmlNodeName.DISC_COUNT));
		assertEquals("4", audiobook27.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("6", audiobook27.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-12T19:03:07Z",
				audiobook27.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-12T19:43:45Z",
				audiobook27.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook27.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook27.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("792", audiobook27.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook27.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("E358303567BDCC40",
				audiobook27.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook27.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook27.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook27.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/Surely%20You're%20Joking,%20Mr.%20Feynman!%20(Unab/04%20Surely%20You're%20Joking,%20Mr.%20Feynman.m4b",
				audiobook27.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook27.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook27.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook28() {
		Map<XmlNodeName, String> audiobook28 = stubRawTrackDataHandler
				.getTrack("17018");
		assertEquals(
				"Surely You're Joking, Mr. Feynman! (Unabridged) Part 5 of 6",
				audiobook28.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook28.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook28.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Surely You're Joking, Mr. Feynman! (Unabridged)",
				audiobook28.get(XmlNodeName.ALBUM));
		assertEquals("Biografieën en memoires",
				audiobook28.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook28.get(XmlNodeName.KIND));
		assertEquals("33162992", audiobook28.get(XmlNodeName.SIZE));
		assertEquals("8368680", audiobook28.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook28.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook28.get(XmlNodeName.DISC_COUNT));
		assertEquals("5", audiobook28.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("6", audiobook28.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-12T19:03:40Z",
				audiobook28.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-12T19:43:45Z",
				audiobook28.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook28.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook28.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("767", audiobook28.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook28.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("5034F2B30C3EC0D2",
				audiobook28.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook28.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook28.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook28.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/Surely%20You're%20Joking,%20Mr.%20Feynman!%20(Unab/05%20Surely%20You're%20Joking,%20Mr.%20Feynman.m4b",
				audiobook28.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook28.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook28.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook29() {
		Map<XmlNodeName, String> audiobook29 = stubRawTrackDataHandler
				.getTrack("17020");
		assertEquals(
				"Surely You're Joking, Mr. Feynman! (Unabridged) Part 6 of 6",
				audiobook29.get(XmlNodeName.NAME));
		assertEquals("Richard P. Feynman", audiobook29.get(XmlNodeName.ARTIST));
		assertEquals("Richard P. Feynman",
				audiobook29.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("Surely You're Joking, Mr. Feynman! (Unabridged)",
				audiobook29.get(XmlNodeName.ALBUM));
		assertEquals("Biografieën en memoires",
				audiobook29.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook29.get(XmlNodeName.KIND));
		assertEquals("8308971", audiobook29.get(XmlNodeName.SIZE));
		assertEquals("2093522", audiobook29.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook29.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook29.get(XmlNodeName.DISC_COUNT));
		assertEquals("6", audiobook29.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("6", audiobook29.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-08-12T19:01:38Z",
				audiobook29.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-08-12T19:43:45Z",
				audiobook29.get(XmlNodeName.DATE_ADDED));
		assertEquals("32", audiobook29.get(XmlNodeName.BIT_RATE));
		assertEquals("24000", audiobook29.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("700", audiobook29.get(XmlNodeName.NORMALIZATION));
		assertEquals("1", audiobook29.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("B699CB1F6E09AACF",
				audiobook29.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook29.get(XmlNodeName.TRACK_TYPE));
		assertEquals("true", audiobook29.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook29.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Richard%20P.%20Feynman/Surely%20You're%20Joking,%20Mr.%20Feynman!%20(Unab/06%20Surely%20You're%20Joking,%20Mr.%20Feynman.m4b",
				audiobook29.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook29.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook29.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

	@Test
	public void testAudiobooksCorrectAudiobook30() {
		Map<XmlNodeName, String> audiobook30 = stubRawTrackDataHandler
				.getTrack("12992");
		assertEquals("2001: a Space Odyssey (Unabridged)",
				audiobook30.get(XmlNodeName.NAME));
		assertEquals("Arthur C. Clarke", audiobook30.get(XmlNodeName.ARTIST));
		assertEquals("Arthur C. Clarke",
				audiobook30.get(XmlNodeName.ALBUM_ARTIST));
		assertEquals("2001: a Space Odyssey (Unabridged)",
				audiobook30.get(XmlNodeName.ALBUM));
		assertEquals("Science Fiction & Fantasy",
				audiobook30.get(XmlNodeName.GENRE));
		assertEquals("Protected AAC audio file",
				audiobook30.get(XmlNodeName.KIND));
		assertEquals("193514731", audiobook30.get(XmlNodeName.SIZE));
		assertEquals("24180700", audiobook30.get(XmlNodeName.TOTAL_TIME));
		assertEquals("1", audiobook30.get(XmlNodeName.DISC_NUMBER));
		assertEquals("1", audiobook30.get(XmlNodeName.DISC_COUNT));
		assertEquals("1", audiobook30.get(XmlNodeName.TRACK_NUMBER));
		assertEquals("1", audiobook30.get(XmlNodeName.TRACK_COUNT));
		assertEquals("2010-05-16T11:52:20Z",
				audiobook30.get(XmlNodeName.DATE_MODIFIED));
		assertEquals("2010-07-17T15:44:04Z",
				audiobook30.get(XmlNodeName.DATE_ADDED));
		assertEquals("64", audiobook30.get(XmlNodeName.BIT_RATE));
		assertEquals("22050", audiobook30.get(XmlNodeName.SAMPLE_RATE));
		assertEquals("1", audiobook30.get(XmlNodeName.ARTWORK_COUNT));
		assertEquals("11B1BE9A96B711AD",
				audiobook30.get(XmlNodeName.PERSISTENT_ID));
		assertEquals("File", audiobook30.get(XmlNodeName.TRACK_TYPE));
		assertEquals("false", audiobook30.get(XmlNodeName.PROTECTED));
		assertEquals("true", audiobook30.get(XmlNodeName.PURCHASED));
		assertEquals(
				"file://localhost/E:/iTunes/Arthur%20C.%20Clarke/2001_%20a%20Space%20Odyssey%20(Unabridged)/01%202001_%20a%20Space%20Odyssey%20(Unabridged.m4b",
				audiobook30.get(XmlNodeName.LOCATION));
		assertEquals("4", audiobook30.get(XmlNodeName.FILE_FOLDER_COUNT));
		assertEquals("1", audiobook30.get(XmlNodeName.LIBRARY_FOLDER_COUNT));
	}

}
