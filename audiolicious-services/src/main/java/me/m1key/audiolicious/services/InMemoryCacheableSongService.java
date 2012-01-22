/* 
 * Audiolicious - Your Music Library Statistics
 * Copyright (C) 2012, Michal Huniewicz
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
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.objecthandler.handlers.StatefulSongService;

@Singleton
@Local({ CacheableSongService.class, StatefulSongService.class })
public class InMemoryCacheableSongService implements CacheableSongService {

	@EJB
	private ArtistRepository artistRepository;
	@Inject
	@NullArtist
	private Artist nullArtist;

	private Map<String, Artist> artistCache = new HashMap<String, Artist>();

	private Library library;

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void addSong(SongTo songTo, Library libraryNotUsed) {
		Artist artist = getOrCreateArtistByName(getAlbumArtistName(songTo));
		String songUuid = artist.addSong(songTo);

		libraryNotUsed.addStat(new ToBasedStatInfo(songTo, songUuid));
	}

	private String getAlbumArtistName(SongTo song) {
		if (song.getAlbumArtist() == null) {
			return song.getArtist();
		} else {
			return song.getAlbumArtist();
		}
	}

	private Artist getOrCreateArtistByName(String albumArtistName) {
		Artist artistFromCache = getArtistCache().get(albumArtistName);

		if (artistFromCache == null) {
			Artist artist = artistRepository.getArtist(albumArtistName);
			if (artist.equals(nullArtist)) {
				artist = new Artist(albumArtistName);
			}
			getArtistCache().put(albumArtistName, artist);
			return artist;
		} else {
			return artistFromCache;
		}
	}

	@Override
	public void initialise() {
		getArtistCache().clear();
	}

	@Override
	public void finalise() {
		for (Entry<String, Artist> entry : getArtistCache().entrySet()) {
			artistRepository.createArtist(entry.getValue());
		}
		getArtistCache().clear();
	}

	protected Map<String, Artist> getArtistCache() {
		return artistCache;
	}

	@Override
	public void setLibrary(Library library) {
		this.library = library;
	}

}
