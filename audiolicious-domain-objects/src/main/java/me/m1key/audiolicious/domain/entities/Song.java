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

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import me.m1key.audiolicious.domain.to.SongTo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity(name = "Song")
@Table(name = "SONGS", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"NAME", "ALBUM_ID", "TRACK_NUMBER", "DISC_NUMBER", "TOTAL_TIME" }) })
public class Song {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	@Column(name = "ARTIST_ID")
	private Long id;

	@Column(name = "UUID", unique = true, length = 36)
	private String uuid;

	@Column(name = "NAME", length = 255)
	private String name;

	@Column(name = "ARTIST_NAME", length = 255)
	private String artistName;

	@Column(name = "GENRE", length = 128)
	private String genre;

	@Column(name = "TRACK_NUMBER")
	private int trackNumber;

	@Column(name = "DISC_NUMBER")
	private int discNumber;

	@Column(name = "YEAR")
	private int year;

	@Column(name = "HAS_VIDEO")
	private boolean hasVideo;

	@Column(name = "TOTAL_TIME")
	private int totalTime;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ALBUM_ID")
	private Album album;

	@OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Stat> stats;

	// For proxying.
	protected Song() {
	}

	public Song(String name, int trackNumber, int discNumber, Album album,
			int year, String genre, boolean hasVideo, int totalTime) {
		this.uuid = UUID.randomUUID().toString();
		this.name = name;
		this.artistName = album.getArtist().getName();
		this.discNumber = discNumber;
		this.trackNumber = trackNumber;
		this.genre = genre;
		this.year = year;
		this.hasVideo = hasVideo;
		this.totalTime = totalTime;
		setAlbum(album);

		stats = new HashSet<Stat>();
	}

	public Song(SongTo songTo) {
		this.uuid = UUID.randomUUID().toString();
		this.name = songTo.getName();
		this.artistName = songTo.getArtist();
		this.genre = songTo.getGenre();
		this.year = songTo.getYear();
		this.hasVideo = songTo.isHasVideo();
		this.trackNumber = songTo.getTrackNumber();
		this.discNumber = songTo.getDiscNumber();
		this.totalTime = songTo.getTotalTime();

		stats = new HashSet<Stat>();
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

	public void setAlbum(Album album) {
		removeFromCurrentAlbum();
		this.album = album;
		album.addSong(this);
	}

	public void addStat(SongTo songTo, Library library) {
		stats.add(new Stat(library, this, songTo));
	}

	public void addStat(Library library, Date dateAdded, Date dateModified,
			Date dateSkipped, int skipCount, Rating rating, int played) {
		stats.add(new Stat(library, this, dateAdded, dateModified, dateSkipped,
				skipCount, rating, played));
	}

	public Set<Stat> getStats() {
		return Collections.unmodifiableSet(stats);
	}

	public int getDiscNumber() {
		return discNumber;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public int getTotalTime() {
		return totalTime;
	}

	private void removeFromCurrentAlbum() {
		if (album != null) {
			album.removeSong(this);
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(trackNumber)
				.append(discNumber).append(totalTime).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Song))
			return false;
		Song castOther = (Song) other;
		return new EqualsBuilder().append(name, castOther.name)
				.append(trackNumber, castOther.trackNumber)
				.append(discNumber, castOther.discNumber)
				.append(totalTime, castOther.totalTime)
				.append(album, castOther.album).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("uuid", uuid)
				.append("name", name).append("artistName", artistName)
				.append("trackNumber", trackNumber)
				.append("discNumber", discNumber)
				.append("totalTime", totalTime).append("genre", genre)
				.append("year", year).append("hasVideo", hasVideo).toString();
	}

	public void clearStats() {
		stats.clear();
	}

}
