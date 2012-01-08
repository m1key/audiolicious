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

import java.util.Date;

import me.m1key.audiolicious.domain.entities.SongInfo;

public class SongInfoBuilder {

	private Date dateAdded;
	private Date dateModified;
	private String albumName;
	private String albumArtist;
	private String artist;
	private String genre;
	private boolean compilation;
	private String name;
	private RatingTo rating;
	private int playCount;
	private Date skipDate;
	private int skipCount;
	private int year;
	private String composer;
	private boolean albumRatingComputed;
	private RatingTo albumRating;
	private boolean hasVideo;
	private int trackNumber;
	private int discNumber;
	private int totalTime;

	public SongInfoBuilder(String name) {
		this.name = name;
	}

	public SongInfoBuilder withDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public SongInfoBuilder withDateModified(Date dateModified) {
		this.dateModified = dateModified;
		return this;
	}

	public SongInfoBuilder withAlbumName(String albumName) {
		this.albumName = albumName;
		return this;
	}

	public SongInfoBuilder withAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
		return this;
	}

	public SongInfoBuilder withArtist(String artist) {
		this.artist = artist;
		return this;
	}

	public SongInfoBuilder withGenre(String genre) {
		this.genre = genre;
		return this;
	}

	public SongInfoBuilder withRating(int rating) {
		this.rating = new RatingTo(rating);
		return this;
	}

	public SongInfoBuilder withPlayCount(int playCount) {
		this.playCount = playCount;
		return this;
	}

	public SongInfoBuilder withYear(int year) {
		this.year = year;
		return this;
	}

	public SongInfoBuilder withHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
		return this;
	}

	public SongInfoBuilder withComposer(String composer) {
		this.composer = composer;
		return this;
	}

	public SongInfoBuilder withCompilation(boolean compilation) {
		this.compilation = compilation;
		return this;
	}

	public SongInfoBuilder withSkipCount(int skipCount) {
		this.skipCount = skipCount;
		return this;
	}

	public SongInfoBuilder withSkipDate(Date skipDate) {
		this.skipDate = skipDate;
		return this;
	}

	public SongInfoBuilder withAlbumRatingComputed(boolean albumRatingComputed) {
		this.albumRatingComputed = albumRatingComputed;
		return this;
	}

	public SongInfoBuilder withAlbumRating(int albumRating) {
		this.albumRating = new RatingTo(albumRating);
		return this;
	}

	public SongInfoBuilder withTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
		return this;
	}

	public SongInfoBuilder withDiscNumber(int discNumber) {
		this.discNumber = discNumber;
		return this;
	}

	public SongInfoBuilder withTotalTime(int totalTime) {
		this.totalTime = totalTime;
		return this;
	}

	public SongInfo build() {
		return new SongTo(name, albumName, artist, albumArtist, year, composer,
				genre, compilation, dateAdded, dateModified, rating, playCount,
				skipDate, skipCount, albumRatingComputed, albumRating,
				hasVideo, trackNumber, discNumber, totalTime);
	}

}
