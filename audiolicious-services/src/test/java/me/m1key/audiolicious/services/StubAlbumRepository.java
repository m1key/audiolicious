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
import me.m1key.audiolicious.domain.entities.Rating;

@Singleton
@Local({ AlbumRepository.class, StubAlbumRepository.class })
public class StubAlbumRepository implements AlbumRepository {

	private Album monsterMagnetSpineOfGod;
	private Album toolAenima;
	private Album fleetFoxesFleetFoxes;
	private Album variousArtistsAnimatrix;

	public StubAlbumRepository() {
		monsterMagnetSpineOfGod = new Album("Spine Of God", new Artist(
				"Monster Magnet"), new Rating(100));
		toolAenima = new Album("Ænima", new Artist("Tool"), new Rating(80));
		fleetFoxesFleetFoxes = new Album("Fleet Foxes (Deluxe Edition)",
				new Artist("Fleet Foxes"), new Rating(80));
		variousArtistsAnimatrix = new Album("Animatrix: The Album", new Artist(
				"Various Artists"), new Rating(100));
	}

	@Override
	public Album getAlbum(Artist artist, String album) {
		if (artist.getName().equals("Monster Magnet")
				&& album.equals("Spine Of God")) {
			return monsterMagnetSpineOfGod;
		}
		if (artist.getName().equals("Tool") && album.equals("Ænima")) {
			return toolAenima;
		}
		if (artist.getName().equals("Fleet Foxes")
				&& album.equals("Fleet Foxes (Deluxe Edition)")) {
			return fleetFoxesFleetFoxes;
		}
		if (artist.getName().equals("Various Artists")
				&& album.equals("Animatrix: The Album")) {
			return variousArtistsAnimatrix;
		}
		return new Album(album, artist, new Rating(100));
	}

	public Album getMonsterMagnetSpineOfGod() {
		return monsterMagnetSpineOfGod;
	}

	public Album getToolAenima() {
		return toolAenima;
	}

	public Album getFleetFoxesFleetFoxes() {
		return fleetFoxesFleetFoxes;
	}

	public Album getVariousArtistsAnimatrix() {
		return variousArtistsAnimatrix;
	}

	@Override
	public void createAlbum(Album album) {
	}

}
