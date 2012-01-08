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

import me.m1key.audiolicious.domain.to.SongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

import org.junit.Before;
import org.junit.Test;

public class AlbumTest {

	private Artist artist;

	@Before
	public void setup() {
		artist = new Artist("Dio");
	}

	@Test
	public void testEquals() {
		Album album1 = new Album("Holy Diver", artist, new Rating(100));
		Album album2 = new Album("Holy Diver", artist, new Rating(80));
		assertEquals(album1, album2);
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
		SongInfo songInfo = new SongInfoBuilder("Invisible").withTrackNumber(7)
				.withTrackNumber(1).withYear(1983).withGenre("Rock")
				.withHasVideo(false).withRating(100).build();

		album.addSong(songInfo,
				new StatInfoBuilder().withLibrary(new Library("Library UUID"))
						.withSkipCount(0).withRating(80).withPlayCount(19)
						.build());

		Song song = new Song(songInfo, album);
		assertTrue(album.getSongs().contains(song));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCannotModifySongsSet() {
		Album album = new Album("Holy Diver", artist, new Rating(100));
		Song song = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withTrackNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
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
