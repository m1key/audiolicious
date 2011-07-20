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

import java.util.Date;

import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.AudiobookToBuilder;
import me.m1key.audiolicious.domain.to.RatingTo;

import org.junit.Before;
import org.junit.Test;

public class AudiobookToBuilderTest {

	private AudiobookTo audiobookViaConstructor;
	private AudiobookTo audiobookViaBuilder;

	@Before
	public void setup() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		String name = "Invisible";
		String albumName = "Rainbow in the Dark";
		String artist = "Dio.";
		String albumArtist = "Dio";
		int year = 1983;
		String genre = "Rock";
		int rating = 100;
		int playCount = 100;
		String comments = "comment";

		audiobookViaConstructor = new AudiobookTo(name, albumName, artist,
				albumArtist, year, genre, dateAdded, dateModified,
				new RatingTo(rating), playCount, comments);

		audiobookViaBuilder = new AudiobookToBuilder(name)
				.withAlbumName(albumName).withArtist(artist)
				.withAlbumArtist(albumArtist).withYear(year).withGenre(genre)
				.withDateAdded(dateAdded).withDateModified(dateModified)
				.withRating(rating).withPlayCount(playCount)
				.withComments(comments).build();
	}

	@Test
	public void shouldHaveEqualDateAdded() {
		assertEquals(audiobookViaConstructor.getDateAdded(),
				audiobookViaBuilder.getDateAdded());
	}

	@Test
	public void shouldHaveEqualDateModified() {
		assertEquals(audiobookViaConstructor.getDateModified(),
				audiobookViaBuilder.getDateModified());
	}

	@Test
	public void shouldHaveEqualName() {
		assertEquals(audiobookViaConstructor.getName(),
				audiobookViaBuilder.getName());
	}

	@Test
	public void shouldHaveEqualAlbumName() {
		assertEquals(audiobookViaConstructor.getAlbumName(),
				audiobookViaBuilder.getAlbumName());
	}

	@Test
	public void shouldHaveEqualArtist() {
		assertEquals(audiobookViaConstructor.getArtist(),
				audiobookViaBuilder.getArtist());
	}

	@Test
	public void shouldHaveEqualAlbumArtist() {
		assertEquals(audiobookViaConstructor.getAlbumArtist(),
				audiobookViaBuilder.getAlbumArtist());
	}

	@Test
	public void shouldHaveEqualYear() {
		assertEquals(audiobookViaConstructor.getYear(),
				audiobookViaBuilder.getYear());
	}

	@Test
	public void shouldHaveEqualGenre() {
		assertEquals(audiobookViaConstructor.getGenre(),
				audiobookViaBuilder.getGenre());
	}

	@Test
	public void shouldHaveEqualRating() {
		assertEquals(audiobookViaConstructor.getRating(),
				audiobookViaBuilder.getRating());
	}

	@Test
	public void shouldHaveEqualPlayCount() {
		assertEquals(audiobookViaConstructor.getPlayCount(),
				audiobookViaBuilder.getPlayCount());
	}

	@Test
	public void shouldHaveEqualComments() {
		assertEquals(audiobookViaConstructor.getComments(),
				audiobookViaBuilder.getComments());
	}
}
