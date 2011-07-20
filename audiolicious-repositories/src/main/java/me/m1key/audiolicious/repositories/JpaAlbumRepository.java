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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.services.AlbumRepository;

public class JpaAlbumRepository implements AlbumRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	@NullArtist
	private Album nullAlbum;

	@Override
	public Album getAlbum(Artist artist, String albumName) {
		@SuppressWarnings("unchecked")
		List<Album> albumObjects = entityManager
				.createQuery("from Album a where a.name = :name")
				.setParameter("name", albumName).getResultList();

		if (albumNotFound(albumObjects)) {
			return nullAlbum;
		} else {
			return albumObjects.get(0);
		}
	}

	private boolean albumNotFound(List<Album> artistObjects) {
		return artistObjects.isEmpty();
	}

	void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
