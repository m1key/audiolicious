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
import javax.persistence.PersistenceContext;

import me.m1key.audiolicious.commons.qualifiers.NullLibrary;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.services.LibraryRepository;

@Singleton
@Local(LibraryRepository.class)
public class JpaLibraryRepository implements LibraryRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	@NullLibrary
	private Library nullLibrary;

	@Override
	public Library getByUuid(String libraryUuid) {
		@SuppressWarnings("unchecked")
		List<Library> libraryObjects = entityManager
				.createQuery("FROM Library l WHERE l.uuid = :libraryUuid")
				.setParameter("libraryUuid", libraryUuid).getResultList();

		if (libraryNotFound(libraryObjects)) {
			return nullLibrary;
		} else {
			return libraryObjects.get(0);
		}
	}

	@Override
	public void save(Library library) {
		entityManager.flush();
		entityManager.clear();
		entityManager.persist(library);
	}

	private boolean libraryNotFound(List<Library> libraryObjects) {
		return libraryObjects.isEmpty();
	}

}
