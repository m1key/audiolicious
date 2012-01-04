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

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NullAlbum;
import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.commons.qualifiers.NullSong;
import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.Rating;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.objecthandler.handlers.SongService;

@Singleton
@Local(SongService.class)
public class DefaultSongService implements SongService {

	@EJB
	private SongRepository songRepository;
	@EJB
	private AlbumRepository albumRepository;
	@EJB
	private ArtistRepository artistRepository;
	@EJB
	private LibraryService libraryService;

	@Inject
	@NullAlbum
	private Album nullAlbum;
	@Inject
	@NullArtist
	private Artist nullArtist;
	@Inject
	@NullSong
	private Song nullSong;

	@Override
	public void addSong(SongTo songTo, String libraryUuid) {
		Artist artist = getOrCreateArtistByName(getAlbumArtistName(songTo));
		Album album = getOrCreateAlbum(songTo, artist);
		Song song = getOrCreateSong(songTo, album);

		song.setAlbum(album);

		Library library = libraryService.getByUuid(libraryUuid);
		song.addStat(songTo, library);

		songRepository.save(song);
	}

	private String getAlbumArtistName(SongTo song) {
		if (song.getAlbumArtist() == null) {
			return song.getArtist();
		} else {
			return song.getAlbumArtist();
		}
	}

	private Artist getOrCreateArtistByName(String albumArtistName) {
		Artist artist = artistRepository.getArtist(albumArtistName);
		if (artist.equals(nullArtist)) {
			artist = new Artist(albumArtistName);
			artistRepository.createArtist(artist);
		}
		return artist;
	}

	private Album getOrCreateAlbum(SongTo songTo, Artist artist) {
		String albumName = songTo.getAlbumName();
		Album album = getAlbumByName(artist, albumName);
		if (album.equals(nullAlbum)) {
			album = new Album(albumName, artist, new Rating(
					songTo.getAlbumRating()));
			albumRepository.createAlbum(album);
		}
		return album;
	}

	private Album getAlbumByName(Artist artist, String albumName) {
		return albumRepository.getAlbum(artist, albumName);
	}

	private Song getOrCreateSong(SongTo songTo, Album album) {
		Song song = songRepository.getSong(songTo.getName(), album,
				songTo.getTrackNumber(), songTo.getDiscNumber(),
				songTo.getTotalTime());
		if (song.equals(nullSong)) {
			song = new Song(songTo);
		}
		return song;
	}
}
