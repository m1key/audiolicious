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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import me.m1key.audiolicious.domain.to.SongTo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity(name = "Song")
@Table(name = "SONGS")
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

	@Column(name = "ALBUM_NAME", length = 255)
	private String albumName;

	@Column(name = "COMPOSER", length = 255)
	private String composer;

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

	@Column(name = "VIDEO_HEIGHT")
	private int videoHeight;

	@Column(name = "VIDEO_WIDTH")
	private int videoWidth;

	@Column(name = "HD")
	private boolean hd;

	@Column(name = "TOTAL_TIME")
	private int totalTime;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Album album;

	@OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Stat> stats;

	// For proxying.
	protected Song() {
	}

	public Song(String name, String albumName, String artistName,
			int trackNumber, int discNumber, Album album, int year,
			String composer, String genre, boolean hasVideo, int videoHeight,
			int videoWidth, boolean hd, int totalTime) {
		this.uuid = UUID.randomUUID().toString();
		this.name = name;
		this.albumName = albumName;
		this.artistName = artistName;
		this.discNumber = discNumber;
		this.trackNumber = trackNumber;
		this.composer = composer;
		this.genre = genre;
		this.year = year;
		this.hasVideo = hasVideo;
		this.videoHeight = videoHeight;
		this.videoWidth = videoWidth;
		this.hd = hd;
		this.totalTime = totalTime;
		setAlbum(album);

		stats = new HashSet<Stat>();
	}

	public Song(SongTo songTo) {
		this.uuid = UUID.randomUUID().toString();
		this.name = songTo.getName();
		this.artistName = songTo.getArtist();
		this.albumName = songTo.getAlbumName();
		this.composer = songTo.getComposer();
		this.genre = songTo.getGenre();
		this.year = songTo.getYear();
		this.hasVideo = songTo.isHasVideo();
		this.videoHeight = songTo.getVideoHeight();
		this.videoWidth = songTo.getVideoWidth();
		this.hd = songTo.isHd();
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

	public String getComposer() {
		return composer;
	}

	public Album getAlbum() {
		return album;
	}

	public String getGenre() {
		return genre;
	}

	public int getVideoHeight() {
		return videoHeight;
	}

	public int getVideoWidth() {
		return videoWidth;
	}

	public int getYear() {
		return year;
	}

	public boolean hasVideo() {
		return hasVideo;
	}

	public boolean isHd() {
		return hd;
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

	public String getAlbumName() {
		return albumName;
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
		return new HashCodeBuilder().append(name).append(albumName)
				.append(artistName).append(trackNumber).append(discNumber)
				.append(totalTime).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Song))
			return false;
		Song castOther = (Song) other;
		return new EqualsBuilder().append(name, castOther.name)
				.append(albumName, castOther.albumName)
				.append(artistName, castOther.artistName)
				.append(trackNumber, castOther.trackNumber)
				.append(discNumber, castOther.discNumber)
				.append(totalTime, castOther.totalTime).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("uuid", uuid)
				.append("name", name).append("albumName", albumName)
				.append("artistName", artistName)
				.append("trackNumber", trackNumber)
				.append("discNumber", discNumber)
				.append("totalTime", totalTime).append("composer", composer)
				.append("genre", genre).append("year", year)
				.append("hasVideo", hasVideo)
				.append("videoHeight", videoHeight)
				.append("videoWidth", videoWidth).append("hd", hd).toString();
	}

	public void clearStats() {
		stats.clear();
	}

}
