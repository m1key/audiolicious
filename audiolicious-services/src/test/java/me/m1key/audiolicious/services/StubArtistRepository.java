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

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;

@Singleton
@Local({ ArtistRepository.class, StubArtistRepository.class })
public class StubArtistRepository implements ArtistRepository {

	private Artist monsterMagnet = new Artist("Monster Magnet");
	private Artist tool = new Artist("Tool");
	private Artist korn = new Artist("Korn");
	private Artist fleetFoxes = new Artist("Fleet Foxes");
	private Artist variousArtists = new Artist("Various Artists");

	@Override
	public Artist getArtist(String artistName) {
		if (artistName.equals("Monster Magnet")) {
			return monsterMagnet;
		} else if (artistName.equals("Tool")) {
			return tool;
		} else if (artistName.equals("Korn")) {
			return korn;
		} else if (artistName.equals("Fleet Foxes")) {
			return fleetFoxes;
		} else if (artistName.equals("Various Artists")) {
			return variousArtists;
		} else {
			return new Artist(artistName);
		}
	}

	@Override
	public void createArtist(Artist artist) {
	}

	public Album getMonsterMagnetSpineOfGod() {
		return getAlbum(monsterMagnet, "Spine Of God");
	}

	public Album getToolAenima() {
		return getAlbum(tool, "Ænima");
	}

	public Album getKornIssues() {
		return getAlbum(korn, "Issues");
	}

	public Album getFleetFoxesFleetFoxes() {
		return getAlbum(fleetFoxes, "Fleet Foxes (Deluxe Edition)");
	}

	public Album getVariousArtistsAnimatrix() {
		return getAlbum(variousArtists, "Animatrix: The Album");
	}

	private Album getAlbum(Artist artist, String albumName) {
		for (Album album : artist.getAlbums()) {
			if (album.getName().equals(albumName)) {
				return album;
			}
		}
		return null;
	}
}
