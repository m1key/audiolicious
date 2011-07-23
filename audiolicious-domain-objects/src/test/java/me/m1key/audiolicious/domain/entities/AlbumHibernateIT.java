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

package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AlbumHibernateIT {

	private static final String ARTIST_1_NAME = "Morcheeba";
	private static final String ARTIST_1_ALBUM_1_NAME = "Charango";
	private static final String ARTIST_1_ALBUM_2_NAME = "Big Calm";
	private static final String ARTIST_2_NAME = "Natacha Atlas";
	private static final String ARTIST_2_ALBUM_1_NAME = "Halim";

	private EntityManager entityManager;
	private Album artist1Album1;

	@Deployment
	public static Archive<?> createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(AlbumHibernateIT.class.getSimpleName() + ".jar",
						JavaArchive.class)
				.addManifestResource(
						new File("src/test/resources/META-INF/persistence.xml"),
						ArchivePaths.create("persistence.xml"))
				.addClasses(Album.class, Artist.class);
	}

	@Before
	public void setup() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("testPu");
		entityManager = emf.createEntityManager();
	}

	@Test
	public void shouldHaveCorrectNumberOfArtistsAndAlbums() {
		createAlbums();

		Query selectArtists = entityManager.createQuery("FROM Artist");
		List<?> allArtists = selectArtists.getResultList();
		assertEquals("There should be two artists.", 2, allArtists.size());

		assertEquals("There should be three albums.", 3, getAllAlbums().size());
	}

	@Test
	public void whenAllArtistsRemovedThereShouldBeNoAlbums() {
		createAlbums();
		deleteAllArtists();
		assertEquals("There should be no albums after all artists removed.", 0,
				getAllAlbums().size());
	}

	@Test
	public void shouldHaveCorrectNumberOfAlbumsPerArtist() {
		createAlbums();

		Query selectArtists = entityManager.createQuery("FROM Artist");
		List<?> allArtists = selectArtists.getResultList();
		assertEquals("There should be two artists.", 2, allArtists.size());

		Query select = entityManager.createQuery("FROM Album");
		List<?> allAlbums = select.getResultList();
		assertEquals("There should be three albums.", 3, allAlbums.size());
	}

	@Test
	public void shouldReturnAlbumByUuid() {
		createAlbums();

		Album retrievedAlbum = getAlbumByUuid(artist1Album1.getUuid());
		assertNotNull(String.format(
				"There should be a non-null album with UUID [%s].",
				artist1Album1.getUuid()), retrievedAlbum);
		assertEquals("Retrieved album should have correct name.",
				ARTIST_1_ALBUM_1_NAME, retrievedAlbum.getName());
	}

	@After
	public void clearTestData() {
		deleteAllArtists();
	}

	private Album getAlbumByUuid(String uuid) {
		entityManager.getTransaction().begin();
		Album album = (Album) entityManager
				.createQuery("FROM Album WHERE uuid = :uuid")
				.setParameter("uuid", uuid).getResultList().get(0);
		entityManager.getTransaction().commit();
		return album;
	}

	@SuppressWarnings("unchecked")
	private List<Album> getAllAlbums() {
		Query select = entityManager.createQuery("FROM Album");
		return select.getResultList();
	}

	private void createAlbums() {
		assertEquals("There should be no albums before any are created.", 0,
				getAllAlbums().size());

		entityManager.getTransaction().begin();

		Artist artist1 = new Artist(ARTIST_1_NAME);
		artist1Album1 = new Album(ARTIST_1_ALBUM_1_NAME, artist1,
				new Rating(80));
		entityManager.persist(artist1Album1);
		Album artist1Album2 = new Album(ARTIST_1_ALBUM_2_NAME, artist1,
				new Rating(80));
		entityManager.persist(artist1Album2);

		Artist artist2 = new Artist(ARTIST_2_NAME);
		Album artist2Album1 = new Album(ARTIST_2_ALBUM_1_NAME, artist2,
				new Rating(80));
		entityManager.persist(artist2Album1);

		entityManager.getTransaction().commit();
	}

	private void deleteAllArtists() {
		entityManager.getTransaction().begin();
		Query select = entityManager.createQuery("FROM Artist");
		List<?> allArtists = select.getResultList();
		for (Object artist : allArtists) {
			entityManager.remove(artist);
		}
		entityManager.getTransaction().commit();
	}

}
