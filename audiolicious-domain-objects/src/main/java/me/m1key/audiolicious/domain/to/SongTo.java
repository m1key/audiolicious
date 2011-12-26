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

public final class SongTo extends TrackTo {

	private final boolean albumRatingComputed;
	private final RatingTo albumRating;
	private final Date skipDate;
	private final int skipCount;
	private final boolean compilation;
	private final int trackNumber;
	private final int discNumber;
	private final int totalTime;

	public SongTo(String name, String albumName, String artist,
			String albumArtist, int year, String composer, String genre,
			boolean compilation, Date dateAdded, Date dateModified,
			RatingTo rating, int playCount, Date skipDate, int skipCount,
			boolean albumRatingComputed, RatingTo albumRating,
			boolean hasVideo, int trackNumber, int discNumber, int totalTime) {
		super(name, albumName, artist, albumArtist, year, genre, dateAdded,
				dateModified, rating, playCount, hasVideo);
		this.albumRatingComputed = albumRatingComputed;
		this.albumRating = albumRating;
		this.skipDate = skipDate == null ? null : new Date(skipDate.getTime());
		this.skipCount = skipCount;
		this.compilation = compilation;
		this.trackNumber = trackNumber;
		this.discNumber = discNumber;
		this.totalTime = totalTime;
	}

	public RatingTo getAlbumRating() {
		return albumRating;
	}

	public boolean isAlbumRatingComputed() {
		return albumRatingComputed;
	}

	public Date getSkipDate() {
		return skipDate == null ? null : new Date(skipDate.getTime());
	}

	public int getSkipCount() {
		return skipCount;
	}

	public boolean isCompilation() {
		return compilation;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public int getDiscNumber() {
		return discNumber;
	}

	public int getTotalTime() {
		return totalTime;
	}

	@Override
	public TrackToType getType() {
		return TrackToType.SONG;
	}

	@Override
	public boolean isPodcast() {
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode())
				.append(getType()).append(isPodcast())
				.append(albumRatingComputed).append(albumRating)
				.append(skipDate).append(skipCount).append(compilation)
				.toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SongTo))
			return false;
		SongTo castOther = (SongTo) other;
		return new EqualsBuilder().appendSuper(super.equals(other))
				.append(getType(), castOther.getType())
				.append(isPodcast(), castOther.isPodcast())
				.append(albumRatingComputed, castOther.albumRatingComputed)
				.append(albumRating, castOther.albumRating)
				.append(skipDate, castOther.skipDate)
				.append(skipCount, castOther.skipCount)
				.append(compilation, castOther.compilation)
				.append(trackNumber, castOther.trackNumber)
				.append(discNumber, castOther.discNumber)
				.append(totalTime, castOther.totalTime).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
				.append("type", getType()).append("isPodcast", isPodcast())
				.append("albumRatingComputed", albumRatingComputed)
				.append("albumRating", albumRating)
				.append("skipDate", skipDate).append("skipCount", skipCount)
				.append("compilation", compilation)
				.append("trackNumber", trackNumber)
				.append("discNumber", discNumber)
				.append("totalTime", totalTime).toString();
	}
}
