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

import me.m1key.audiolicious.commons.qualifiers.NullSong;
import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.services.SongRepository;

@Singleton
@Local(SongRepository.class)
public class JpaSongRepository implements SongRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	@NullSong
	private Song nullSong;

	@Override
	public void save(Song song) {
		entityManager.persist(song);
	}

	@Override
	public Song getSong(String songName, Album album, int trackNumber,
			int discNumber, int totalTime) {
		@SuppressWarnings("unchecked")
		List<Song> songObjects = entityManager
				.createQuery(
						"FROM Song s WHERE s.name = :songName AND s.album = :album"
								+ " AND s.key = :songKey")
				.setParameter("songName", songName)
				.setParameter("album", album)
				.setParameter("songKey",
						Song.toKey(totalTime, trackNumber, discNumber))
				.getResultList();

		if (songNotFound(songObjects)) {
			return nullSong;
		} else {
			return songObjects.get(0);
		}
	}

	private boolean songNotFound(List<Song> songObjects) {
		return songObjects.isEmpty();
	}
}
