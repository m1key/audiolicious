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

import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.PodcastToBuilder;
import me.m1key.audiolicious.domain.to.RatingTo;

import org.junit.Before;
import org.junit.Test;

public class PodcastToBuilderTest {

	private PodcastTo podcastViaConstructor;
	private PodcastTo podcastViaBuilder;

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

		podcastViaConstructor = new PodcastTo(name, albumName, artist,
				albumArtist, 1983, genre, dateAdded, dateModified,
				new RatingTo(rating), playCount, hasVideo, videoHeight,
				videoWidth, hd);

		podcastViaBuilder = new PodcastToBuilder(name).withAlbumName(albumName)
				.withArtist(artist).withAlbumArtist(albumArtist).withYear(year)
				.withGenre(genre).withDateAdded(dateAdded)
				.withDateModified(dateModified).withRating(rating)
				.withPlayCount(playCount).withHasVideo(hasVideo)
				.withVideoHeight(videoHeight).withVideoWidth(videoWidth)
				.withHd(hd).build();
	}

	@Test
	public void shouldHaveEqualDateAdded() {
		assertEquals(podcastViaConstructor.getDateAdded(),
				podcastViaBuilder.getDateAdded());
	}

	@Test
	public void shouldHaveEqualDateModified() {
		assertEquals(podcastViaConstructor.getDateModified(),
				podcastViaBuilder.getDateModified());
	}

	@Test
	public void shouldHaveEqualName() {
		assertEquals(podcastViaConstructor.getName(),
				podcastViaBuilder.getName());
	}

	@Test
	public void shouldHaveEqualAlbumName() {
		assertEquals(podcastViaConstructor.getAlbumName(),
				podcastViaBuilder.getAlbumName());
	}

	@Test
	public void shouldHaveEqualArtist() {
		assertEquals(podcastViaConstructor.getArtist(),
				podcastViaBuilder.getArtist());
	}

	@Test
	public void shouldHaveEqualAlbumArtist() {
		assertEquals(podcastViaConstructor.getAlbumArtist(),
				podcastViaBuilder.getAlbumArtist());
	}

	@Test
	public void shouldHaveEqualYear() {
		assertEquals(podcastViaConstructor.getYear(),
				podcastViaBuilder.getYear());
	}

	@Test
	public void shouldHaveEqualGenre() {
		assertEquals(podcastViaConstructor.getGenre(),
				podcastViaBuilder.getGenre());
	}

	@Test
	public void shouldHaveEqualRating() {
		assertEquals(podcastViaConstructor.getRating(),
				podcastViaBuilder.getRating());
	}

	@Test
	public void shouldHaveEqualPlayCount() {
		assertEquals(podcastViaConstructor.getPlayCount(),
				podcastViaBuilder.getPlayCount());
	}

	@Test
	public void shouldHaveEqualHasVideo() {
		assertEquals(podcastViaConstructor.isHasVideo(),
				podcastViaBuilder.isHasVideo());
	}

	@Test
	public void shouldHaveEqualVideoHeight() {
		assertEquals(podcastViaConstructor.getVideoHeight(),
				podcastViaBuilder.getVideoHeight());
	}

	@Test
	public void shouldHaveEqualVideoWidth() {
		assertEquals(podcastViaConstructor.getVideoWidth(),
				podcastViaBuilder.getVideoWidth());
	}

	@Test
	public void shouldHaveEqualHd() {
		assertEquals(podcastViaConstructor.isHd(), podcastViaBuilder.isHd());
	}

}