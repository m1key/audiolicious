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

import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.RatingTo;

import org.junit.Test;

public class AudiobookToTest {

	@Test
	public void testEquals() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		assertEquals(audiobook1, audiobook2);
	}

	@Test
	public void testNotEquals1() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark2", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals2() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio2", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals3() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio2", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals4() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock2", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals5() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible2",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals6() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(80), 100, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals7() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 80, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals8() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1984, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		assertFalse(audiobook1.equals(audiobook2));
	}

	@Test
	public void testNotEquals9() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		AudiobookTo audiobook1 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment");
		AudiobookTo audiobook2 = new AudiobookTo("Invisible",
				"Rainbow in the Dark", "Dio", "Dio", 1983, "Rock", dateAdded,
				dateModified, new RatingTo(100), 100, "comment2");
		assertFalse(audiobook1.equals(audiobook2));
	}

}
