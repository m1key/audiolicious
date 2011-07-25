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

import javax.persistence.CascadeType;
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

	@Column(name = "NAME", length = 512)
	private String name;

	@Column(name = "ARTIST_NAME", length = 512)
	private String artistName;

	@Column(name = "COMPOSER", length = 512)
	private String composer;

	@Column(name = "DATE_ADDED")
	private Date dateAdded;

	@Column(name = "DATE_MODIFIED")
	private Date dateModified;

	@Column(name = "DATE_SKIPPED")
	private Date dateSkipped;

	@Column(name = "SKIP_COUNT")
	private int skipCount;

	@Column(name = "GENRE", length = 128)
	private String genre;

	@Column(name = "PLAY_COUNT")
	private int playCount;

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

	@Embedded
	private Rating rating;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	private Album album;

	public Song(String name, String artistName, Album album, int year,
			String composer, String genre, Date dateAdded, Date dateModified,
			Rating rating, int playCount, Date dateSkipped, int skipCount,
			boolean hasVideo, int videoHeight, int videoWidth, boolean hd) {
		this.uuid = UUID.randomUUID().toString();
		this.name = name;
		this.album = album;
		this.artistName = artistName;
		this.composer = composer;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.genre = genre;
		this.playCount = playCount;
		this.year = year;
		this.hasVideo = hasVideo;
		this.videoHeight = videoHeight;
		this.videoWidth = videoWidth;
		this.hd = hd;
		this.rating = rating;
		this.dateSkipped = dateSkipped;
		this.skipCount = skipCount;
	}

	public Song(SongTo songTo) {
		this.uuid = UUID.randomUUID().toString();
		this.name = songTo.getName();
		this.artistName = songTo.getArtist();
		this.composer = songTo.getComposer();
		this.dateAdded = songTo.getDateAdded();
		this.dateModified = songTo.getDateModified();
		this.genre = songTo.getGenre();
		this.playCount = songTo.getPlayCount();
		this.year = songTo.getYear();
		this.hasVideo = songTo.isHasVideo();
		this.videoHeight = songTo.getVideoHeight();
		this.videoWidth = songTo.getVideoWidth();
		this.hd = songTo.isHd();
		this.rating = new Rating(songTo.getRating());
		this.dateSkipped = songTo.getSkipDate();
		this.skipCount = songTo.getSkipCount();
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

	public Date getDateAdded() {
		return dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public String getGenre() {
		return genre;
	}

	public int getPlayCount() {
		return playCount;
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

	public Rating getRating() {
		return rating;
	}

	public int getSkipCount() {
		return skipCount;
	}

	public Date getDateSkipped() {
		return dateSkipped;
	}

	public void setAlbum(Album album) {
		removeFromCurrentAlbum();
		this.album = album;
		album.addSong(this);
	}

	private void removeFromCurrentAlbum() {
		if (album != null) {
			album.removeSong(this);
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(uuid).append(name)
				.append(artistName).append(composer).append(dateAdded)
				.append(dateModified).append(genre).append(playCount)
				.append(year).append(hasVideo).append(videoHeight)
				.append(videoWidth).append(hd).append(rating)
				.append(dateSkipped).append(skipCount).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Song))
			return false;
		Song castOther = (Song) other;
		return new EqualsBuilder().append(uuid, castOther.uuid)
				.append(name, castOther.name)
				.append(artistName, castOther.artistName)
				.append(composer, castOther.composer)
				.append(dateAdded, castOther.dateAdded)
				.append(dateModified, castOther.dateModified)
				.append(genre, castOther.genre)
				.append(playCount, castOther.playCount)
				.append(year, castOther.year)
				.append(hasVideo, castOther.hasVideo)
				.append(videoHeight, castOther.videoHeight)
				.append(videoWidth, castOther.videoWidth)
				.append(skipCount, castOther.skipCount)
				.append(hd, castOther.hd).append(rating, castOther.rating)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("uuid", uuid)
				.append("name", name).append("artistName", artistName)
				.append("composer", composer).append("dateAdded", dateAdded)
				.append("dateModified", dateModified).append("genre", genre)
				.append("playCount", playCount).append("year", year)
				.append("hasVideo", hasVideo)
				.append("videoHeight", videoHeight)
				.append("videoWidth", videoWidth).append("hd", hd)
				.append("dateSkipped", dateSkipped)
				.append("skipCount", skipCount).append("rating", rating)
				.toString();
	}

}
