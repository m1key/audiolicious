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

package me.m1key.audiolicious.domain.to;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SongToTest {

	@Test
	public void testEquals() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertEquals(song1, song2);
	}

	@Test
	public void testNotEquals1() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark2", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals2() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio2", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals3() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio2",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals4() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock2", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals5() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible2", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals6() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(99), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals7() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 99, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals8() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1984, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals9() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				true, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals13() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, false, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals14() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(99),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals17() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 1, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals18() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", true, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 1, true, new RatingTo(100),
				false, 7, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals19() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 8, 1, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals20() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 2, 100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals21() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date skipDate = new Date();
		SongTo song1 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 100);
		SongTo song2 = new SongTo("Invisible", "Rainbow in the Dark", "Dio",
				"Dio", 1983, "Dio", "Rock", false, dateAdded, dateModified,
				new RatingTo(100), 100, skipDate, 0, true, new RatingTo(100),
				false, 7, 1, 101);
		assertFalse(song1.equals(song2));
	}

}
