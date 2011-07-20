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

public class PodcastToBuilder {

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
	private boolean hasVideo;
	private int videoHeight;
	private int videoWidth;
	private boolean hd;

	public PodcastToBuilder(String name) {
		this.name = name;
	}

	public PodcastToBuilder withDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public PodcastToBuilder withDateModified(Date dateModified) {
		this.dateModified = dateModified;
		return this;
	}

	public PodcastToBuilder withAlbumName(String albumName) {
		this.albumName = albumName;
		return this;
	}

	public PodcastToBuilder withAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
		return this;
	}

	public PodcastToBuilder withArtist(String artist) {
		this.artist = artist;
		return this;
	}

	public PodcastToBuilder withGenre(String genre) {
		this.genre = genre;
		return this;
	}

	public PodcastToBuilder withRating(int rating) {
		this.rating = new RatingTo(rating);
		return this;
	}

	public PodcastToBuilder withPlayCount(int playCount) {
		this.playCount = playCount;
		return this;
	}

	public PodcastToBuilder withYear(int year) {
		this.year = year;
		return this;
	}

	public PodcastToBuilder withHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
		return this;
	}

	public PodcastToBuilder withVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
		return this;
	}

	public PodcastToBuilder withVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
		return this;
	}

	public PodcastToBuilder withHd(boolean hd) {
		this.hd = hd;
		return this;
	}

	public PodcastTo build() {
		return new PodcastTo(name, albumName, artist, albumArtist, year, genre,
				dateAdded, dateModified, rating, playCount, hasVideo,
				videoHeight, videoWidth, hd);
	}

}
