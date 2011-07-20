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

public class AudiobookToBuilder {

	private Date dateAdded;
	private Date dateModified;
	private String albumName;
	private String albumArtist;
	private String artist;
	private String genre;
	private String name;
	private RatingTo rating;
	private int playCount;
	private int year;
	private String comments;

	public AudiobookToBuilder(String name) {
		this.name = name;
	}

	public AudiobookToBuilder withDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public AudiobookToBuilder withDateModified(Date dateModified) {
		this.dateModified = dateModified;
		return this;
	}

	public AudiobookToBuilder withAlbumName(String albumName) {
		this.albumName = albumName;
		return this;
	}

	public AudiobookToBuilder withAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
		return this;
	}

	public AudiobookToBuilder withArtist(String artist) {
		this.artist = artist;
		return this;
	}

	public AudiobookToBuilder withGenre(String genre) {
		this.genre = genre;
		return this;
	}

	public AudiobookToBuilder withRating(int rating) {
		this.rating = new RatingTo(rating);
		return this;
	}

	public AudiobookToBuilder withPlayCount(int playCount) {
		this.playCount = playCount;
		return this;
	}

	public AudiobookToBuilder withYear(int year) {
		this.year = year;
		return this;
	}

	public AudiobookToBuilder withComments(String comments) {
		this.comments = comments;
		return this;
	}

	public AudiobookTo build() {
		return new AudiobookTo(name, albumName, artist, albumArtist, year,
				genre, dateAdded, dateModified, rating, playCount, comments);
	}

}
