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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class TrackTo {

	private final Date dateAdded;
	private final Date dateModified;
	private final String albumName;
	private final String albumArtist;
	private final String artist;
	private final String genre;
	private final String name;
	private final RatingTo rating;
	private final int playCount;
	private final int year;
	private final boolean hasVideo;

	public TrackTo(String name, String albumName, String artist,
			String albumArtist, int year, String genre, Date dateAdded,
			Date dateModified, RatingTo rating, int playCount, boolean hasVideo) {
		super();
		this.dateAdded = dateAdded == null ? null : new Date(
				dateAdded.getTime());
		this.dateModified = dateModified == null ? null : new Date(
				dateModified.getTime());
		this.albumName = albumName;
		this.albumArtist = albumArtist;
		this.artist = artist;
		this.genre = genre;
		this.name = name;
		this.rating = rating;
		this.playCount = playCount;
		this.year = year;
		this.hasVideo = hasVideo;
	}

	public Date getDateAdded() {
		return dateAdded == null ? null : new Date(dateAdded.getTime());
	}

	public Date getDateModified() {
		return dateModified == null ? null : new Date(dateModified.getTime());
	}

	public String getAlbumName() {
		return albumName;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public String getArtist() {
		return artist;
	}

	public String getGenre() {
		return genre;
	}

	public String getName() {
		return name;
	}

	public RatingTo getRating() {
		return rating;
	}

	public int getPlayCount() {
		return playCount;
	}

	public int getYear() {
		return year;
	}

	public boolean isHasVideo() {
		return hasVideo;
	}

	public abstract TrackToType getType();

	public abstract boolean isPodcast();

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(dateAdded).append(dateModified)
				.append(albumName).append(albumArtist).append(artist)
				.append(genre).append(name).append(rating).append(playCount)
				.append(year).append(hasVideo).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof TrackTo))
			return false;
		TrackTo castOther = (TrackTo) other;
		return new EqualsBuilder().append(dateAdded, castOther.dateAdded)
				.append(dateModified, castOther.dateModified)
				.append(albumName, castOther.albumName)
				.append(albumArtist, castOther.albumArtist)
				.append(artist, castOther.artist)
				.append(genre, castOther.genre).append(name, castOther.name)
				.append(rating, castOther.rating)
				.append(playCount, castOther.playCount)
				.append(year, castOther.year)
				.append(hasVideo, castOther.hasVideo).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("dateAdded", dateAdded)
				.append("dateModified", dateModified)
				.append("albumName", albumName)
				.append("albumArtist", albumArtist).append("artist", artist)
				.append("genre", genre).append("name", name)
				.append("rating", rating).append("playCount", playCount)
				.append("year", year).append("hasVideo", hasVideo).toString();
	}

}
