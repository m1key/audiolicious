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
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.SongToBuilder;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SongToBuilderTest {

	private SongTo songViaConstructor;
	private SongTo songViaBuilder;

	@Before
	public void setup() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		String name = "Invisible";
		String albumName = "Rainbow in the Dark";
		String artist = "Dio.";
		String albumArtist = "Dio";
		int year = 1983;
		String composer = "Ronnie James Dio";
		String genre = "Rock";
		int rating = 100;
		int playCount = 100;
		boolean hasVideo = true;
		boolean compilation = true;
		int skipCount = 111;
		Date skipDate = new Date();
		int albumRating = 99;
		boolean albumRatingComputed = true;
		int trackNumber = 2;
		int discNumber = 1;
		int totalTime = 100;

		songViaConstructor = new SongTo(name, albumName, artist, albumArtist,
				year, composer, genre, compilation, dateAdded, dateModified,
				new RatingTo(rating), playCount, skipDate, skipCount,
				albumRatingComputed, new RatingTo(albumRating), hasVideo,
				trackNumber, discNumber, totalTime);

		songViaBuilder = new SongToBuilder(name).withAlbumName(albumName)
				.withArtist(artist).withAlbumArtist(albumArtist).withYear(year)
				.withGenre(genre).withDateAdded(dateAdded)
				.withDateModified(dateModified).withRating(rating)
				.withPlayCount(playCount).withHasVideo(hasVideo)
				.withCompilation(compilation).withSkipCount(skipCount)
				.withSkipDate(skipDate).withAlbumRating(albumRating)
				.withAlbumRatingComputed(albumRatingComputed)
				.withTrackNumber(trackNumber).withDiscNumber(discNumber)
				.build();
	}

	@Test
	public void shouldHaveEqualDateAdded() {
		assertEquals(songViaConstructor.getDateAdded(),
				songViaBuilder.getDateAdded());
	}

	@Test
	public void shouldHaveEqualDateModified() {
		assertEquals(songViaConstructor.getDateModified(),
				songViaBuilder.getDateModified());
	}

	@Test
	public void shouldHaveEqualName() {
		assertEquals(songViaConstructor.getName(), songViaBuilder.getName());
	}

	@Test
	public void shouldHaveEqualAlbumName() {
		assertEquals(songViaConstructor.getAlbumName(),
				songViaBuilder.getAlbumName());
	}

	@Test
	public void shouldHaveEqualArtist() {
		assertEquals(songViaConstructor.getArtist(), songViaBuilder.getArtist());
	}

	@Test
	public void shouldHaveEqualAlbumArtist() {
		assertEquals(songViaConstructor.getAlbumArtist(),
				songViaBuilder.getAlbumArtist());
	}

	@Test
	public void shouldHaveEqualYear() {
		assertEquals(songViaConstructor.getYear(), songViaBuilder.getYear());
	}

	@Test
	public void shouldHaveEqualGenre() {
		assertEquals(songViaConstructor.getGenre(), songViaBuilder.getGenre());
	}

	@Test
	public void shouldHaveEqualRating() {
		assertEquals(songViaConstructor.getRating(), songViaBuilder.getRating());
	}

	@Test
	public void shouldHaveEqualPlayCount() {
		assertEquals(songViaConstructor.getPlayCount(),
				songViaBuilder.getPlayCount());
	}

	@Test
	public void shouldHaveEqualHasVideo() {
		assertEquals(songViaConstructor.isHasVideo(),
				songViaBuilder.isHasVideo());
	}

	@Test
	public void shouldHaveEqualAlbumRatingComputed() {
		assertEquals(songViaConstructor.isAlbumRatingComputed(),
				songViaBuilder.isAlbumRatingComputed());
	}

	@Test
	public void shouldHaveEqualAlbumRating() {
		assertEquals(songViaConstructor.getAlbumRating(),
				songViaBuilder.getAlbumRating());
	}

	@Test
	public void shouldHaveEqualSkipDate() {
		assertEquals(songViaConstructor.getSkipDate(),
				songViaBuilder.getSkipDate());
	}

	@Test
	public void shouldHaveEqualSkipCount() {
		assertEquals(songViaConstructor.getSkipCount(),
				songViaBuilder.getSkipCount());
	}

	@Test
	public void shouldHaveEqualSkipCompilation() {
		assertEquals(songViaConstructor.isCompilation(),
				songViaBuilder.isCompilation());
	}

	@Test
	public void shouldHaveEqualTrackNumber() {
		assertEquals(songViaConstructor.getTrackNumber(),
				songViaBuilder.getTrackNumber());
	}

	@Test
	public void shouldHaveEqualDiscNumber() {
		assertEquals(songViaConstructor.getDiscNumber(),
				songViaBuilder.getDiscNumber());
	}

}
