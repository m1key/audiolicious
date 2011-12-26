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

package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SongTest {

	private Album album;

	@Before
	public void setup() {
		Artist artist = new Artist("Dio");
		album = new Album("Holy Diver", artist, new Rating(100));
	}

	@Test
	public void testNotEquals1() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible2", 7, 1, album, 1983, "Rock", false,
				100);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals4() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				101);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testEquals1() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1984, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals2() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals3() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock2", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals4() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals5() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals6() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals7() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", true, 100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals8() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals9() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals10() {
		Song song1 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		Song song2 = new Song("Invisible", 7, 1, album, 1983, "Rock", false,
				100);
		assertTrue(song1.equals(song2));
	}

}
