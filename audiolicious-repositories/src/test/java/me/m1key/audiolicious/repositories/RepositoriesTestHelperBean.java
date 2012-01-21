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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.Song;

@Stateless
public class RepositoriesTestHelperBean {

	@PersistenceContext
	private EntityManager entityManager;

	public Artist createArtist(String artistName) {
		entityManager.persist(new Artist(artistName));

		@SuppressWarnings("unchecked")
		List<Artist> artistObjects = entityManager
				.createQuery("from Artist a where a.name = :name")
				.setParameter("name", artistName).getResultList();
		return artistObjects.get(0);
	}

	public void deleteAllArtists() {
		Query select = entityManager.createQuery("FROM Artist");
		List<?> allArtists = select.getResultList();
		for (Object artist : allArtists) {
			entityManager.remove(artist);
		}
	}

	public Song getSongByUuid(String uuid) {
		@SuppressWarnings("unchecked")
		List<Song> songs = entityManager
				.createQuery("FROM Song WHERE uuid = :uuid")
				.setParameter("uuid", uuid).getResultList();
		if (songs.isEmpty()) {
			return null;
		} else {
			return (Song) entityManager
					.createQuery("FROM Song WHERE uuid = :uuid")
					.setParameter("uuid", uuid).getResultList().get(0);
		}
	}

	public Long totalSongs() {
		return total("Song");
	}

	private Long total(String entityName) {
		Object howMany = entityManager.createQuery(
				String.format("SELECT COUNT(id) FROM %s", entityName))
				.getSingleResult();
		return (Long) howMany;
	}

	public void deleteAllLibraries() {
		List<Library> allLibraries = getAllLibraries();

		for (Library library : allLibraries) {
			entityManager.remove(library);
		}
	}

	@SuppressWarnings("unchecked")
	private List<Library> getAllLibraries() {
		Query select = entityManager.createQuery("FROM Library");
		return select.getResultList();
	}

	public Long totalArtists() {
		return total("Artist");
	}
}
