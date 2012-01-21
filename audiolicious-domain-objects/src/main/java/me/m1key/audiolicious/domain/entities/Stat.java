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
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity(name = "Stat")
@Table(name = "STATS", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"SONG_UUID", "LIBRARY_ID" }) })
public class Stat {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	@Column(name = "STAT_ID")
	private Long id;

	@Column(name = "UUID", unique = true, length = 36)
	private String uuid;

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

	@Column(name = "SONG_UUID")
	private String songUuid;

	@Embedded
	private Rating rating;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "LIBRARY_ID")
	private Library library;

	// For proxying.
	protected Stat() {
	}

	protected Stat(Library library, String songUuid, Date dateAdded,
			Date dateModified, Date dateSkipped, int skipCount, Rating rating,
			int playCount) {
		if (library == null) {
			throw new IllegalArgumentException(
					"Null library passed to Stat constructor.");
		}
		if (songUuid == null) {
			throw new IllegalArgumentException(
					"Null songUuid passed to Stat constructor.");
		}

		this.uuid = UUID.randomUUID().toString();
		this.library = library;
		this.songUuid = songUuid;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.dateSkipped = dateSkipped;
		this.skipCount = skipCount;
		this.rating = rating;
		this.playCount = playCount;
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

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public String getSongUuid() {
		return songUuid;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getSongUuid()).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Stat))
			return false;
		Stat castOther = (Stat) other;
		return new EqualsBuilder()
				.append(getSongUuid(), castOther.getSongUuid())
				.append(getLibrary(), castOther.getLibrary()).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("uuid", uuid)
				.append("dateAdded", dateAdded)
				.append("dateModified", dateModified)
				.append("playCount", playCount)
				.append("dateSkipped", dateSkipped)
				.append("skipCount", skipCount).append("rating", rating)
				.toString();
	}

	public void removeFromLibrary() {
		library.remove(this);
	}
}
