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

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.services.ArtistRepository;

@Singleton
@Local(ArtistRepository.class)
public class JpaArtistRepository implements ArtistRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	@NullArtist
	private Artist nullArtist;

	@Override
	public Artist getArtist(String artistName) {
		@SuppressWarnings("unchecked")
		List<Artist> artistObjects = entityManager
				.createQuery("from Artist a where a.name = :name")
				.setParameter("name", artistName)
				.setFlushMode(FlushModeType.COMMIT).getResultList();

		if (artistNotFound(artistObjects)) {
			return nullArtist;
		} else {
			return artistObjects.get(0);
		}
	}

	@Override
	public void createArtist(Artist artist) {
		entityManager.persist(artist);
	}

	private boolean artistNotFound(List<Artist> artistObjects) {
		return artistObjects.isEmpty();
	}

}
