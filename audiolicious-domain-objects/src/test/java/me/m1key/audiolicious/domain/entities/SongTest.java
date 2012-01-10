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

import me.m1key.audiolicious.domain.to.AlbumInfoBuilder;
import me.m1key.audiolicious.domain.to.SongInfoBuilder;

import org.junit.Before;
import org.junit.Test;

public class SongTest {

	private Album album;

	@Before
	public void setup() {
		Artist artist = new Artist("Dio");
		artist.addAlbum(new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build());
		album = artist.getAlbums().iterator().next();
	}

	@Test
	public void testNotEquals1() {
		Song song1 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		Song song2 = new Song(new SongInfoBuilder("Invisible2")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		assertFalse(song1.equals(song2));
	}

	@Test
	public void testEquals4() {
		Song song1 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		Song song2 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(99).build(),
				album);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals1() {
		Song song1 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		Song song2 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1984)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals2() {
		Song song1 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		Song song2 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals3() {
		Song song1 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		Song song2 = new Song(
				new SongInfoBuilder("Invisible").withTrackNumber(7)
						.withDiscNumber(1).withYear(1983).withGenre("Rock2")
						.withHasVideo(false).withRating(100).build(), album);
		assertTrue(song1.equals(song2));
	}

	@Test
	public void testEquals7() {
		Song song1 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		Song song2 = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withDiscNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(true).withRating(100).build(),
				album);
		assertTrue(song1.equals(song2));
	}

}
