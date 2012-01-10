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
import static org.junit.Assert.assertTrue;
import me.m1key.audiolicious.domain.to.AlbumInfoBuilder;
import me.m1key.audiolicious.domain.to.SongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

import org.junit.Before;
import org.junit.Test;

public class AlbumTest {

	private Artist artist;

	@Before
	public void setup() {
		artist = new Artist("Dio");
		assertEquals("Artist should have no albums before albums are added.",
				0, artist.getAlbums().size());
	}

	@Test
	public void testEquals() {
		artist.addAlbum(new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build());
		artist.addAlbum(new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build());

		assertEquals(
				"Artist should have one album after two identical are added.",
				1, artist.getAlbums().size());
	}

	@Test
	public void testNotEquals1() {
		artist.addAlbum(new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build());
		artist.addAlbum(new AlbumInfoBuilder().withName("Holy Diver 2")
				.withRating(100).build());

		assertEquals("Artist should have two albums after two are added.", 2,
				artist.getAlbums().size());
	}

	@Test
	public void testSongBelongsToAlbum() {
		artist.addAlbum(new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build());

		Album album = artist.getAlbums().iterator().next();

		SongInfo songInfo = new SongInfoBuilder("Invisible").withTrackNumber(7)
				.withTrackNumber(1).withYear(1983).withGenre("Rock")
				.withHasVideo(false).withRating(100).build();
		StatInfo statInfo = new StatInfoBuilder()
				.withLibrary(new Library("Library UUID")).withSkipCount(0)
				.withRating(80).withPlayCount(19).build();

		album.addSong(songInfo, statInfo);

		Song song = new Song(songInfo, album);
		assertTrue(album.getSongs().contains(song));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCannotModifySongsSet() {
		artist.addAlbum(new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build());
		Album album = artist.getAlbums().iterator().next();

		Song song = new Song(new SongInfoBuilder("Invisible")
				.withTrackNumber(7).withTrackNumber(1).withYear(1983)
				.withGenre("Rock").withHasVideo(false).withRating(100).build(),
				album);
		album.getSongs().add(song);
	}
}
