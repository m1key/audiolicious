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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity(name = "Album")
@Table(name = "ALBUMS", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"ARTIST_ID", "NAME" }) })
public class Album {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	@Column(name = "ALBUM_ID")
	private Long id;

	@Column(name = "UUID", unique = true, length = 36)
	private String uuid;

	@Column(name = "NAME", length = 255)
	private String name;

	@Embedded
	private Rating rating;

	@ManyToOne(optional = false, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "ARTIST_ID")
	private Artist artist;

	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Song> songs;

	// For proxying.
	protected Album() {
	}

	public Album(String albumName, Artist albumArtist, Rating albumRating) {
		this.uuid = UUID.randomUUID().toString();
		this.name = albumName;
		this.rating = albumRating;

		songs = new HashSet<Song>();
		setArtist(albumArtist);
	}

	public String getUuid() {
		return uuid;
	}

	public Artist getArtist() {
		return artist;
	}

	public String getName() {
		return name;
	}

	public Rating getRating() {
		return rating;
	}

	public Set<Song> getSongs() {
		return Collections.unmodifiableSet(songs);
	}

	public void addSong(Song song) {
		if (song.getAlbum() != this) {
			song.setAlbum(this);
		}
		if (!songs.contains(song)) {
			songs.add(song);
		}
	}

	public void removeSong(Song song) {
		songs.remove(song);
	}

	public void setArtist(Artist artist) {
		removeFromCurrentArtist();
		this.artist = artist;
		artist.addAlbum(this);
	}

	private void removeFromCurrentArtist() {
		if (artist != null) {
			artist.removeAlbum(this);
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Album))
			return false;
		Album castOther = (Album) other;
		return new EqualsBuilder().append(name, castOther.name)
				.append(artist, castOther.artist).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("uuid", uuid)
				.append("name", name).toString();
	}

}
