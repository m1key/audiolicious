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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;
import me.m1key.audiolicious.domain.entities.Rating;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JpaAlbumRepositoryIT {

	private static final String ARTIST_1_NAME = "Morcheeba";
	private static final String ARTIST_1_ALBUM_1_NAME = "Charango";
	private static final String ARTIST_1_ALBUM_2_NAME = "Big Calm";
	private static final String ARTIST_2_NAME = "Natacha Atlas";
	private static final String ARTIST_2_ALBUM_1_NAME = "Halim";

	@Inject
	private JpaAlbumRepository jpaAlbumRepository;

	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						JpaAlbumRepositoryIT.class.getSimpleName() + ".war")
				.addAsManifestResource(
						new File("src/test/resources/META-INF/persistence.xml"),
						ArchivePaths.create("persistence.xml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addClasses(Album.class, Artist.class,
						JpaAlbumRepository.class, NullEntitiesFactory.class);
	}

	@Before
	public void setup() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("testPu");
		entityManager = emf.createEntityManager();
		jpaAlbumRepository.setEntityManager(entityManager);
	}

	@Test
	public void shouldNotReturnNullForNonExistentAlbum() {
		assertNotNull("Non existent album should not be null.",
				jpaAlbumRepository.getAlbum(createArtist(ARTIST_1_NAME),
						"nonexistentartist"));
	}

	@Test
	public void shouldSaveAndRetrieveAlbum() {
		entityManager.getTransaction().begin();
		Artist artist = new Artist(ARTIST_1_NAME);
		Album album = new Album(ARTIST_1_ALBUM_1_NAME, artist, new Rating(80));
		jpaAlbumRepository.createAlbum(album);

		entityManager.getTransaction().commit();

		Album retrievedAlbum = jpaAlbumRepository.getAlbum(artist,
				ARTIST_1_ALBUM_1_NAME);
		assertNotNull(retrievedAlbum);
		assertEquals("Created and retrieved by name album should be the same.",
				album, retrievedAlbum);
	}

	@Test
	public void shouldReturnCorrectAlbum() {
		entityManager.getTransaction().begin();

		Artist artist1 = new Artist(ARTIST_1_NAME);
		Album artist1Album1 = new Album(ARTIST_1_ALBUM_1_NAME, artist1,
				new Rating(80));
		jpaAlbumRepository.createAlbum(artist1Album1);
		Album artist1Album2 = new Album(ARTIST_1_ALBUM_2_NAME, artist1,
				new Rating(80));
		jpaAlbumRepository.createAlbum(artist1Album2);

		Artist artist2 = new Artist(ARTIST_2_NAME);
		Album artist2Album1 = new Album(ARTIST_2_ALBUM_1_NAME, artist2,
				new Rating(80));
		Album artist2Album2WithTheSameNameAsArist1Album1 = new Album(
				ARTIST_1_ALBUM_1_NAME, artist2, new Rating(80));
		jpaAlbumRepository.createAlbum(artist2Album1);

		entityManager.getTransaction().commit();

		assertEquals("Created and retrieved by name album should be the same.",
				artist1Album1,
				jpaAlbumRepository.getAlbum(artist1, ARTIST_1_ALBUM_1_NAME));
		assertEquals("Created and retrieved by name album should be the same.",
				artist1Album2,
				jpaAlbumRepository.getAlbum(artist1, ARTIST_1_ALBUM_2_NAME));
		assertEquals("Created and retrieved by name album should be the same.",
				artist2Album1,
				jpaAlbumRepository.getAlbum(artist2, ARTIST_2_ALBUM_1_NAME));
		assertEquals("Created and retrieved by name album should be the same.",
				artist2Album2WithTheSameNameAsArist1Album1,
				jpaAlbumRepository.getAlbum(artist2, ARTIST_1_ALBUM_1_NAME));
	}

	@After
	public void clearTestData() {
		deleteAllArtists();
	}

	private Artist createArtist(String artistName) {
		entityManager.getTransaction().begin();
		entityManager.persist(new Artist(artistName));
		entityManager.getTransaction().commit();

		@SuppressWarnings("unchecked")
		List<Artist> artistObjects = entityManager
				.createQuery("from Artist a where a.name = :name")
				.setParameter("name", artistName).getResultList();
		return artistObjects.get(0);
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
