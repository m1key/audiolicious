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

public class SongToBuilder {

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
	private int videoHeight;
	private int videoWidth;
	private boolean hd;
	private boolean musicVideo;
	private int trackNumber;
	private int discNumber;
	private int totalTime;

	public SongToBuilder(String name) {
		this.name = name;
	}

	public SongToBuilder withDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public SongToBuilder withDateModified(Date dateModified) {
		this.dateModified = dateModified;
		return this;
	}

	public SongToBuilder withAlbumName(String albumName) {
		this.albumName = albumName;
		return this;
	}

	public SongToBuilder withAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
		return this;
	}

	public SongToBuilder withArtist(String artist) {
		this.artist = artist;
		return this;
	}

	public SongToBuilder withGenre(String genre) {
		this.genre = genre;
		return this;
	}

	public SongToBuilder withRating(int rating) {
		this.rating = new RatingTo(rating);
		return this;
	}

	public SongToBuilder withPlayCount(int playCount) {
		this.playCount = playCount;
		return this;
	}

	public SongToBuilder withYear(int year) {
		this.year = year;
		return this;
	}

	public SongToBuilder withHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
		return this;
	}

	public SongToBuilder withVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
		return this;
	}

	public SongToBuilder withVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
		return this;
	}

	public SongToBuilder withHd(boolean hd) {
		this.hd = hd;
		return this;
	}

	public SongToBuilder withMusicVideo(boolean musicVideo) {
		this.musicVideo = musicVideo;
		return this;
	}

	public SongToBuilder withComposer(String composer) {
		this.composer = composer;
		return this;
	}

	public SongToBuilder withCompilation(boolean compilation) {
		this.compilation = compilation;
		return this;
	}

	public SongToBuilder withSkipCount(int skipCount) {
		this.skipCount = skipCount;
		return this;
	}

	public SongToBuilder withSkipDate(Date skipDate) {
		this.skipDate = skipDate;
		return this;
	}

	public SongToBuilder withAlbumRatingComputed(boolean albumRatingComputed) {
		this.albumRatingComputed = albumRatingComputed;
		return this;
	}

	public SongToBuilder withAlbumRating(int albumRating) {
		this.albumRating = new RatingTo(albumRating);
		return this;
	}

	public SongToBuilder withTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
		return this;
	}

	public SongToBuilder withDiscNumber(int discNumber) {
		this.discNumber = discNumber;
		return this;
	}

	public SongToBuilder withTotalTime(int totalTime) {
		this.totalTime = totalTime;
		return this;
	}

	public SongTo build() {
		return new SongTo(name, albumName, artist, albumArtist, year, composer,
				genre, compilation, dateAdded, dateModified, rating, playCount,
				skipDate, skipCount, albumRatingComputed, albumRating,
				hasVideo, videoHeight, videoWidth, hd, musicVideo, trackNumber,
				discNumber, totalTime);
	}

}
