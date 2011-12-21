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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AlbumTest {

	private Artist artist;

	@Before
	public void setup() {
		artist = new Artist("Dio");
	}

	@Test
	public void testNotEquals1() {
		Album album1 = new Album("Holy Diver", artist, new Rating(100));
		Album album2 = new Album("Holy Diver3", artist, new Rating(100));
		assertFalse(album1.equals(album2));
	}

	@Test
	public void testSongBelongsToAlbum() {
		Album album = new Album("Holy Diver", artist, new Rating(100));
		Song song = new Song("Invisible", "Holy Diver", "Dio", 7, 1, album,
				1983, "Ronnie James Dio", "Rock", false, 0, 0, false);
		album.addSong(song);
		assertTrue(album.getSongs().contains(song));
	}

	@Test
	public void testSongBelongsToOnlyOneAlbum() {
		Album album1 = new Album("Holy Diver", artist, new Rating(100));
		Album album2 = new Album("Strange Highways", artist, new Rating(100));
		Album album = new Album("Holy Diver", artist, new Rating(100));
		Song song = new Song("Invisible", "Holy Diver", "Dio", 7, 1, album,
				1983, "Ronnie James Dio", "Rock", false, 0, 0, false);
		album1.addSong(song);
		album2.addSong(song);
		assertFalse(album1.getSongs().contains(song));
		assertTrue(album2.getSongs().contains(song));
		assertEquals(album2, song.getAlbum());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCannotModifySongsSet() {
		Album album = new Album("Holy Diver", artist, new Rating(100));
		Song song = new Song("Invisible", "Holy Diver", "Dio", 7, 1, album,
				1983, "Ronnie James Dio", "Rock", false, 0, 0, false);
		album.getSongs().add(song);
	}

	@Test
	public void testAlbumBelonsToOneArtist() {
		Artist artist2 = new Artist("Black Sabbath");
		Album album = new Album("Mob Rules", artist, new Rating(100));
		artist2.addAlbum(album);
		assertFalse(artist.getAlbums().contains(album));
		assertTrue(artist2.getAlbums().contains(album));
	}

	@Test
	public void testAlbumDoesntBelongAfterRemoved() {
		Album album = new Album("Mob Rules", artist, new Rating(100));
		artist.removeAlbum(album);
		assertFalse(artist.getAlbums().contains(album));
	}
}
