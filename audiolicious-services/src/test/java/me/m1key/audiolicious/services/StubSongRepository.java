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

package me.m1key.audiolicious.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NullSong;
import me.m1key.audiolicious.domain.entities.Song;

@Singleton
@Local({ SongRepository.class, StubSongRepository.class })
public class StubSongRepository implements SongRepository {

	@Inject
	@NullSong
	private Song nullSong;

	private Map<String, Integer> numberOfSongsPerAlbum;
	private Map<String, Integer> numberOfAlbumsPerArtist;
	private Map<String, Set<Song>> songsPerAlbum;

	public StubSongRepository() {
		numberOfSongsPerAlbum = new HashMap<String, Integer>();
		numberOfAlbumsPerArtist = new HashMap<String, Integer>();
		songsPerAlbum = new HashMap<String, Set<Song>>();
	}

	@Override
	public void save(Song song) {
		countSongsPerAlbum(song);
		countAlbumsPerArtist(song);
		addSongToAlbum(song);
	}

	public Integer getNumberOfSongs(String artist, String albumName) {
		return numberOfSongsPerAlbum
				.get(createArtistAlbumKey(artist, albumName));
	}

	public Integer getNumberOfAlbums() {
		return numberOfSongsPerAlbum.size();
	}

	public Integer getNumberOfArtists() {
		return numberOfAlbumsPerArtist.size();
	}

	private void countAlbumsPerArtist(Song song) {
		String albumArtist = song.getAlbum().getArtist().getName();
		if (numberOfAlbumsPerArtist.containsKey(albumArtist)) {
			Integer albumsPerThisArtist = numberOfAlbumsPerArtist
					.get(albumArtist);
			numberOfAlbumsPerArtist.put(albumArtist, ++albumsPerThisArtist);
		} else {
			numberOfAlbumsPerArtist.put(albumArtist, 1);
		}
	}

	private void countSongsPerAlbum(Song song) {
		String key = createArtistAlbumKey(
				song.getAlbum().getArtist().getName(), song.getAlbum()
						.getName());
		if (numberOfSongsPerAlbum.containsKey(key)) {
			Integer songsPerThisAlbum = numberOfSongsPerAlbum.get(key);
			numberOfSongsPerAlbum.put(key, ++songsPerThisAlbum);
		} else {
			numberOfSongsPerAlbum.put(key, 1);
		}
	}

	private String createArtistAlbumKey(String artist, String albumName) {
		return String.format("%s___%s", artist, albumName);
	}

	private void addSongToAlbum(Song song) {
		Set<Song> songsForThisAlbum = songsPerAlbum.get(song.getAlbum());
		if (songsForThisAlbum == null) {
			songsForThisAlbum = new HashSet<Song>();
			songsPerAlbum.put(toKey(song), songsForThisAlbum);
		}

		songsForThisAlbum.add(song);
	}

	@Override
	public Song getSong(String songName, String albumName, String albumArtist,
			int trackNumber, int discNumber) {
		Set<Song> songsForThisAlbum = songsPerAlbum.get(toKey(songName,
				albumName, albumArtist, trackNumber, discNumber));
		if (songsForThisAlbum != null) {
			for (Song song : songsForThisAlbum) {
				if (song.getName().equals(songName)) {
					return song;
				}
			}
		}
		return nullSong;
	}

	private String toKey(String songName, String albumName, String albumArtist,
			int trackNumber, int discNumber) {
		return String.format("%s:%s:%s:%d:%d", songName, albumName,
				albumArtist, trackNumber, discNumber);
	}

	private String toKey(Song song) {
		return toKey(song.getName(), song.getAlbumName(), song.getArtistName(),
				song.getTrackNumber(), song.getDiscNumber());
	}

}
