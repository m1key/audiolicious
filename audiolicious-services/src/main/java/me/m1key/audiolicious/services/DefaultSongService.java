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
import javax.ejb.Stateful;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.objecthandler.handlers.SongService;

@Stateful
@Local(SongService.class)
public class DefaultSongService implements SongService {

	@EJB
	private ArtistRepository artistRepository;
	@Inject
	@NullArtist
	private Artist nullArtist;

	@Override
	public void addSong(SongTo songTo, Library library) {
		Artist artist = getOrCreateArtistByName(getAlbumArtistName(songTo));

		if (artist.addSong(songTo, new FullStatInfo(songTo, library))) {
			artistRepository.createArtist(artist);
		}
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
}
