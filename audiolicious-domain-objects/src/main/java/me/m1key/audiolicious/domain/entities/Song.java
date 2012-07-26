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

import java.util.UUID;

import javax.persistence.Column;
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

@Entity(name = "Song")
@Table(name = "SONGS", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"NAME", "SONG_ID", "SONG_KEY" }) })
public class Song {

	@Id
	@GeneratedValue
	@Column(name = "SONG_ID")
	private Long id;

	@Column(name = "UUID", unique = true, length = 36)
	private String uuid;

	@Column(name = "NAME", length = 255)
	private String name;

	@Column(name = "ARTIST_NAME", length = 255)
	private String artistName;

	@Column(name = "GENRE", length = 128)
	private String genre;

	@Column(name = "SONG_KEY", length = 20, nullable = false)
	private String key;

	@Column(name = "YEAR")
	private int year;

	@Column(name = "HAS_VIDEO")
	private boolean hasVideo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ALBUM_ID")
	private Album album;

	// For proxying.
	protected Song() {
	}

	Song(SongInfo songInfo, Album album) {
		if (album == null) {
			throw new IllegalArgumentException(
					"Null Album passed to Song constructor.");
		}

		this.uuid = UUID.randomUUID().toString();
		this.name = songInfo.getName();
		this.artistName = songInfo.getArtist();
		this.genre = songInfo.getGenre();
		this.year = songInfo.getYear();
		this.hasVideo = songInfo.isHasVideo();
		this.album = album;

		this.key = toKey(songInfo.getTotalTime(), songInfo.getTrackNumber(),
				songInfo.getDiscNumber());
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public String getArtistName() {
		return artistName;
	}

	public Album getAlbum() {
		return album;
	}

	public String getGenre() {
		return genre;
	}

	public int getYear() {
		return year;
	}

	public boolean hasVideo() {
		return hasVideo;
	}

	public String getKey() {
		return key;
	}

	private String toKey(int totalTime, int trackNumber, int discNumber) {
		return String.format("%d:%d:%d", totalTime, trackNumber, discNumber);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).append(getKey())
				.toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Song))
			return false;
		Song castOther = (Song) other;
		return new EqualsBuilder().append(getName(), castOther.getName())
				.append(getKey(), castOther.getKey())
				.append(getAlbum(), castOther.getAlbum()).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("uuid", uuid)
				.append("name", name).append("artistName", artistName)
				.append("key", key).append("genre", genre).append("year", year)
				.append("hasVideo", hasVideo).toString();
	}

}
