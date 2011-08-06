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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Rating;
import me.m1key.audiolicious.domain.entities.Song;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JpaSongRepositoryIT {

	private static final String ARTIST_NAME = "Ozzy Osbourne";
	private static final String ALBUM_NAME = "No Rest for the Wicked";
	private static final String SONG_1_NAME = "Miracle Man";
	private static final String SONG_2_NAME = "Devil's Daughter (Holy War) [Album Version]";
	private static final String SONG_3_NAME = "Crazy Babies";
	private static final String SONG_4_NAME = "Breakin' All The Rules";
	private static final String SONG_5_NAME = "Bloodbath In Paradise [Album Version]";
	private static final String SONG_6_NAME = "Fire In the Sky";
	private static final String SONG_7_NAME = "Tattooed Dancer [Album Version]";
	private static final String SONG_8_NAME = "Demon Alcohol";
	private static final String SONG_9_NAME = "Hero [Album Version]";
	private static final String SONG_10_NAME = "The Liar";
	private static final String SONG_11_NAME = "Miracle Man [Live]";

	private Date albumDateAdded = new Date();
	private Date albumDateModified = new Date();
	private Date albumDateSkipped = new Date();

	private JpaSongRepository jpaSongRepository;
	private EntityManager entityManager;

	@Before
	public void setup() {
		jpaSongRepository = new JpaSongRepository();
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("testPu");
		entityManager = emf.createEntityManager();
		jpaSongRepository.setEntityManager(entityManager);
	}

	@Test
	public void shouldCreateAndRetrieveSong() {
		assertFalse(
				"There should be no songs before any are created.",
				entityManager.createQuery("FROM Song").getResultList().size() > 0);

		entityManager.getTransaction().begin();
		Artist artist = new Artist(ARTIST_NAME);
		Album album = new Album(ALBUM_NAME, artist, new Rating(80));
		Song song = new Song(SONG_1_NAME, ARTIST_NAME, album, 1988,
				"Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", albumDateAdded,
				albumDateModified, new Rating(80), 9, albumDateSkipped, 0,
				false, 0, 0, false);
		jpaSongRepository.save(song);
		entityManager.getTransaction().commit();

		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song.getUuid()).getResultList()
						.size() > 0);
	}

	@Test
	public void shouldCreateAndRetrieveAllSongs() {
		assertFalse(
				"There should be no songs before any are created.",
				entityManager.createQuery("FROM Song").getResultList().size() > 0);

		entityManager.getTransaction().begin();
		Artist artist = new Artist(ARTIST_NAME);
		Album album = new Album(ALBUM_NAME, artist, new Rating(80));
		Song song01 = new Song(SONG_1_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(100), 9,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song02 = new Song(SONG_2_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(80), 1,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song03 = new Song(SONG_3_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(60), 8,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song04 = new Song(SONG_4_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(80), 76,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song05 = new Song(SONG_5_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(100), 11,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song06 = new Song(SONG_6_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(60), 2,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song07 = new Song(SONG_7_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(100), 21,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song08 = new Song(SONG_8_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(60), 0,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song09 = new Song(SONG_9_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(80), 1,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song10 = new Song(SONG_10_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(80), 14,
				albumDateSkipped, 0, false, 0, 0, false);
		Song song11 = new Song(SONG_11_NAME, ARTIST_NAME, album, 1991, "",
				"Rock", albumDateAdded, albumDateModified, new Rating(60), 2,
				albumDateSkipped, 0, false, 0, 0, false);
		jpaSongRepository.save(song01);
		jpaSongRepository.save(song02);
		jpaSongRepository.save(song03);
		jpaSongRepository.save(song04);
		jpaSongRepository.save(song05);
		jpaSongRepository.save(song06);
		jpaSongRepository.save(song07);
		jpaSongRepository.save(song08);
		jpaSongRepository.save(song09);
		jpaSongRepository.save(song10);
		jpaSongRepository.save(song11);
		entityManager.getTransaction().commit();

		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song01.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song02.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song03.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song04.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song05.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song06.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song07.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song08.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song09.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song10.getUuid()).getResultList()
						.size() > 0);
		assertTrue("Saved song should not be null.",
				entityManager.createQuery("FROM Song WHERE uuid = :uuid")
						.setParameter("uuid", song11.getUuid()).getResultList()
						.size() > 0);
	}

	@After
	public void clearTestData() {
		entityManager.getTransaction().begin();
		Query select = entityManager.createQuery("FROM Artist");
		List<?> allArtists = select.getResultList();
		for (Object artist : allArtists) {
			entityManager.remove(artist);
		}
		entityManager.getTransaction().commit();
	}

}
