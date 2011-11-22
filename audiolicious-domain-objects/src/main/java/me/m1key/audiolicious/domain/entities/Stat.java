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

package me.m1key.audiolicious.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import me.m1key.audiolicious.domain.to.SongTo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity(name = "Stat")
@Table(name = "STATS")
public class Stat {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	@Column(name = "ARTIST_ID")
	private Long id;

	@Column(name = "DATE_ADDED")
	private Date dateAdded;

	@Column(name = "DATE_MODIFIED")
	private Date dateModified;

	@Column(name = "DATE_SKIPPED")
	private Date dateSkipped;

	@Column(name = "SKIP_COUNT")
	private int skipCount;

	@Column(name = "PLAY_COUNT")
	private int playCount;

	@Embedded
	private Rating rating;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Song song;

	// For proxying.
	protected Stat() {
	}

	protected Stat(Song song, Date dateAdded, Date dateModified,
			Date dateSkipped, int skipCount, Rating rating, int playCount) {
		this.song = song;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.dateSkipped = dateSkipped;
		this.skipCount = skipCount;
		this.rating = rating;
		this.playCount = playCount;
	}

	protected Stat(Song song, SongTo songTo) {
		this.song = song;
		this.dateAdded = songTo.getDateAdded();
		this.dateModified = songTo.getDateModified();
		this.playCount = songTo.getPlayCount();
		this.rating = new Rating(songTo.getRating());
		this.dateSkipped = songTo.getSkipDate();
		this.skipCount = songTo.getSkipCount();
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public int getPlayCount() {
		return playCount;
	}

	public Rating getRating() {
		return rating;
	}

	public int getSkipCount() {
		return skipCount;
	}

	public Date getDateSkipped() {
		return dateSkipped;
	}

	public Song getSong() {
		return song;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(dateAdded).append(dateModified)
				.append(playCount).append(rating).append(dateSkipped)
				.append(skipCount).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Stat))
			return false;
		Stat castOther = (Stat) other;
		return new EqualsBuilder().append(dateAdded, castOther.dateAdded)
				.append(dateModified, castOther.dateModified)
				.append(dateSkipped, castOther.dateSkipped)
				.append(playCount, castOther.playCount)
				.append(skipCount, castOther.skipCount)
				.append(rating, castOther.rating).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("dateAdded", dateAdded)
				.append("dateModified", dateModified)
				.append("playCount", playCount)
				.append("dateSkipped", dateSkipped)
				.append("skipCount", skipCount).append("rating", rating)
				.toString();
	}
}
