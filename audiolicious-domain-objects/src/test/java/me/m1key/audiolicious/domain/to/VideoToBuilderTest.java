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

import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.domain.to.VideoToBuilder;

import org.junit.Before;
import org.junit.Test;

public class VideoToBuilderTest {

	private VideoTo videoViaConstructor;
	private VideoTo videoViaBuilder;

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
		boolean hasVideo = true;
		int videoHeight = 123;
		int videoWidth = 321;
		boolean hd = true;

		videoViaConstructor = new VideoTo(name, albumName, artist, albumArtist,
				1983, genre, dateAdded, dateModified, new RatingTo(rating),
				playCount, hasVideo, videoHeight, videoWidth, hd);

		videoViaBuilder = new VideoToBuilder(name).withAlbumName(albumName)
				.withArtist(artist).withAlbumArtist(albumArtist).withYear(year)
				.withGenre(genre).withDateAdded(dateAdded)
				.withDateModified(dateModified).withRating(rating)
				.withPlayCount(playCount).withHasVideo(hasVideo)
				.withVideoHeight(videoHeight).withVideoWidth(videoWidth)
				.withHd(hd).build();
	}

	@Test
	public void shouldHaveEqualDateAdded() {
		assertEquals(videoViaConstructor.getDateAdded(),
				videoViaBuilder.getDateAdded());
	}

	@Test
	public void shouldHaveEqualDateModified() {
		assertEquals(videoViaConstructor.getDateModified(),
				videoViaBuilder.getDateModified());
	}

	@Test
	public void shouldHaveEqualName() {
		assertEquals(videoViaConstructor.getName(), videoViaBuilder.getName());
	}

	@Test
	public void shouldHaveEqualAlbumName() {
		assertEquals(videoViaConstructor.getAlbumName(),
				videoViaBuilder.getAlbumName());
	}

	@Test
	public void shouldHaveEqualArtist() {
		assertEquals(videoViaConstructor.getArtist(),
				videoViaBuilder.getArtist());
	}

	@Test
	public void shouldHaveEqualAlbumArtist() {
		assertEquals(videoViaConstructor.getAlbumArtist(),
				videoViaBuilder.getAlbumArtist());
	}

	@Test
	public void shouldHaveEqualYear() {
		assertEquals(videoViaConstructor.getYear(), videoViaBuilder.getYear());
	}

	@Test
	public void shouldHaveEqualGenre() {
		assertEquals(videoViaConstructor.getGenre(), videoViaBuilder.getGenre());
	}

	@Test
	public void shouldHaveEqualRating() {
		assertEquals(videoViaConstructor.getRating(),
				videoViaBuilder.getRating());
	}

	@Test
	public void shouldHaveEqualPlayCount() {
		assertEquals(videoViaConstructor.getPlayCount(),
				videoViaBuilder.getPlayCount());
	}

	@Test
	public void shouldHaveEqualHasVideo() {
		assertEquals(videoViaConstructor.isHasVideo(),
				videoViaBuilder.isHasVideo());
	}

}
