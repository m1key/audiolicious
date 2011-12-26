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

public final class VideoTo extends TrackTo {

	private int videoHeight;
	private int videoWidth;
	private boolean hd;

	public VideoTo(String name, String albumName, String albumArtist,
			String artist, int year, String genre, Date dateAdded,
			Date dateModified, RatingTo rating, int playCount,
			boolean hasVideo, int videoHeight, int videoWidth, boolean hd) {
		super(name, albumName, artist, albumArtist, year, genre, dateAdded,
				dateModified, rating, playCount, hasVideo);
		this.videoHeight = videoHeight;
		this.videoWidth = videoWidth;
		this.hd = hd;
	}

	@Override
	public TrackToType getType() {
		return TrackToType.VIDEO;
	}

	@Override
	public boolean isPodcast() {
		return false;
	}

	public int getVideoHeight() {
		return videoHeight;
	}

	public int getVideoWidth() {
		return videoWidth;
	}

	public boolean isHd() {
		return hd;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode())
				.append(getType()).append(isPodcast()).append(videoHeight)
				.append(videoWidth).append(hd).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof VideoTo))
			return false;
		VideoTo castOther = (VideoTo) other;
		return new EqualsBuilder().appendSuper(super.equals(other))
				.append(getType(), castOther.getType())
				.append(isPodcast(), castOther.isPodcast())
				.append(videoHeight, castOther.videoHeight)
				.append(videoWidth, castOther.videoWidth)
				.append(hd, castOther.hd).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
				.append("type", getType()).append("isPodcast", isPodcast())
				.append(videoHeight).append(videoWidth).append(hd).toString();
	}

}
