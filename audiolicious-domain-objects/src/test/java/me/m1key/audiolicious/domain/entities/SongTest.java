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
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible2", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals2() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio2", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals3() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1984,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals4() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio2", "Rock", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals5() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock2", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals6() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals7() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals8() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals9() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", true, 0, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals10() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 1, 0, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals11() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 1, false);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testNotEquals12() {
		Song song1 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, false);
		Song song2 = new Song("Invisible", "Dio", album, 1983,
				"Ronnie James Dio", "Rock", false, 0, 0, true);
		assertFalse(song1.equals(song2));
	}

}
