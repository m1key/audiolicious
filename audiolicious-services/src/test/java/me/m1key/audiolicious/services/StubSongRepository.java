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
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.services.SongRepository;

@Alternative
@ApplicationScoped
public class StubSongRepository implements SongRepository {
	private Map<String, Integer> songsPerAlbum;
	private Map<String, Integer> albumsPerArtist;

	public StubSongRepository() {
		songsPerAlbum = new HashMap<String, Integer>();
		albumsPerArtist = new HashMap<String, Integer>();
	}

	@Override
	public void save(Song song) {
		countSongsPerAlbum(song);
		countAlbumsPerArtist(song);
	}

	public Integer getNumberOfSongs(String artist, String albumName) {
		return songsPerAlbum.get(createArtistAlbumKey(artist, albumName));
	}

	public Integer getNumberOfAlbums() {
		return songsPerAlbum.size();
	}

	public Integer getNumberOfArtists() {
		return albumsPerArtist.size();
	}

	private void countAlbumsPerArtist(Song song) {
		String albumArtist = song.getAlbum().getArtist().getName();
		if (albumsPerArtist.containsKey(albumArtist)) {
			Integer albumsPerThisArtist = albumsPerArtist.get(albumArtist);
			albumsPerArtist.put(albumArtist, ++albumsPerThisArtist);
		} else {
			albumsPerArtist.put(albumArtist, 1);
		}
	}

	private void countSongsPerAlbum(Song song) {
		String key = createArtistAlbumKey(
				song.getAlbum().getArtist().getName(), song.getAlbum()
						.getName());
		if (songsPerAlbum.containsKey(key)) {
			Integer songsPerThisAlbum = songsPerAlbum.get(key);
			songsPerAlbum.put(key, ++songsPerThisAlbum);
		} else {
			songsPerAlbum.put(key, 1);
		}
	}

	private String createArtistAlbumKey(String artist, String albumName) {
		return String.format("%s___%s", artist, albumName);
	}

}
