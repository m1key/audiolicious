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

package me.m1key.audiolicious.repositories;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.services.ArtistRepository;

@Stateless
public class IntegrationTestHelperBean {

	@PersistenceContext
	private EntityManager entityManager;
	@EJB
	private ArtistRepository artistRepository;

	public void deleteAllArtists() {
		Query select = entityManager.createQuery("FROM Artist");
		List<?> allArtists = select.getResultList();
		for (Object artist : allArtists) {
			entityManager.remove(artist);
		}
	}

	public void deleteAllLibraries() {
		Query select = entityManager.createQuery("FROM Library");
		List<?> allLibraries = select.getResultList();
		for (Object library : allLibraries) {
			entityManager.remove(library);
		}
	}

	public Long totalArtists() {
		return total("Artist");
	}

	public Long totalAlbums() {
		return total("Album");
	}

	public Long totalSongs() {
		return total("Song");
	}

	public Long totalLibraries() {
		return total("Library");
	}

	private Long total(String entityName) {
		Object howMany = entityManager.createQuery(
				String.format("SELECT COUNT(id) FROM %s", entityName))
				.getSingleResult();
		return (Long) howMany;
	}

	public int getArtistAlbumsSize(String artistName) {
		return artistRepository.getArtist(artistName).getAlbums().size();
	}

	public int getArtistSongsSize(String artistName) {
		int totalSongs = 0;
		for (Album album : artistRepository.getArtist(artistName).getAlbums()) {
			totalSongs += album.getSongs().size();
		}
		return totalSongs;
	}

	public int getSongStatsSize(String songName) {
		Query selectSongs = entityManager.createQuery(
				"FROM Song WHERE name = :name").setParameter("name", songName);
		@SuppressWarnings("unchecked")
		List<Song> allSongsByName = selectSongs.getResultList();
		if (allSongsByName.size() != 1) {
			throw new RuntimeException(String.format(
					"There should be one song by "
							+ "name [%s] but there are [%d].", songName,
					allSongsByName.size()));
		}
		Song song = allSongsByName.get(0);

		Query selectStats = entityManager.createQuery(
				"FROM Stat WHERE songUuid = :songUuid").setParameter(
				"songUuid", song.getUuid());
		List<?> allStatsForSong = selectStats.getResultList();
		return allStatsForSong.size();
	}
}
