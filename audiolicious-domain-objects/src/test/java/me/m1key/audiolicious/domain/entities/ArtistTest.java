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

import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Rating;
import me.m1key.audiolicious.domain.to.AlbumInfoBuilder;

import org.junit.Test;

public class ArtistTest {

	@Test
	public void testEquals() {
		Artist artist1 = new Artist("Dio");
		Artist artist2 = new Artist("Dio");

		assertEquals(artist1, artist2);
	}

	@Test
	public void testNotEquals() {
		Artist artist1 = new Artist("Dio");
		Artist artist2 = new Artist("Black Sabbath");

		assertFalse(artist1.equals(artist2));
	}

	@Test
	public void testContains1Album() {
		Artist artist = new Artist("Dio");
		Album album = new Album("Holy Diver", artist, new Rating(100));
		AlbumInfo albumInfo = new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build();
		assertFalse(artist.getAlbums().contains(album));
		artist.addAlbum(albumInfo);
		assertTrue(artist.getAlbums().contains(album));
	}

	@Test
	public void testContains2Albums() {
		Artist artist = new Artist("Dio");
		Album album1 = new Album("Holy Diver", artist, new Rating(100));
		Album album2 = new Album("Strange Highways", artist, new Rating(100));
		assertFalse(artist.getAlbums().contains(album1));
		assertFalse(artist.getAlbums().contains(album2));

		AlbumInfo albumInfo1 = new AlbumInfoBuilder().withName("Holy Diver")
				.withRating(100).build();
		AlbumInfo albumInfo2 = new AlbumInfoBuilder()
				.withName("Strange Highways").withRating(100).build();
		artist.addAlbum(albumInfo1);
		artist.addAlbum(albumInfo2);

		assertTrue(artist.getAlbums().contains(album1));
		assertTrue(artist.getAlbums().contains(album2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCannotModifySongsSet() {
		Artist artist = new Artist("Dio");
		Album album1 = new Album("Holy Diver", artist, new Rating(100));
		artist.getAlbums().add(album1);
	}

}
