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
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

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

		assertEquals("There should be two artists.", 2, getAllArtists().size());
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

		assertEquals("There should be two artists.", 2, getAllArtists().size());
		assertEquals("There should be three albums.", 3, getAllAlbums().size());

		assertEquals(String.format("Artist [%s] should have 2 albums",
				ARTIST_1_NAME), 2, getAlbumsByArtistName(ARTIST_1_NAME).size());
		assertEquals(String.format("Artist [%s] should have 2 albums",
				ARTIST_2_NAME), 1, getAlbumsByArtistName(ARTIST_2_NAME).size());
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

	@Test
	public void shouldDeleteOnlyOneAlbum() {
		createAlbums();

		assertEquals("There should be two artists.", 2, getAllArtists().size());
		assertEquals("There should be three albums.", 3, getAllAlbums().size());

		deleteAlbumByArtistNameAlbumName(ARTIST_1_NAME, ARTIST_1_ALBUM_1_NAME);

		assertEquals("There should be two artists.", 2, getAllArtists().size());
		assertEquals("There should be two albums after one has been deleted.",
				2, getAllAlbums().size());

		assertNotNull(
				String.format("Not deleted album [%s] should still exist.",
						ARTIST_1_ALBUM_2_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_2_NAME));
		assertNotNull(
				String.format("Not deleted album [%s] should still exist.",
						ARTIST_2_ALBUM_1_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_2_NAME,
						ARTIST_2_ALBUM_1_NAME));
		assertNull(
				String.format("Deleted album [%s] should not exist.",
						ARTIST_1_ALBUM_1_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_2_NAME,
						ARTIST_1_ALBUM_1_NAME));
	}

	@After
	public void clearTestData() {
		deleteAllArtists();
	}

	private void deleteAlbumByArtistNameAlbumName(String artistName,
			String albumName) {
		Album album = getAlbumByArtistNameAlbumName(artistName, albumName);
		Artist artist = getArtistByName(artistName);

		entityManager.getTransaction().begin();
		removeAlbumFromArtist(artist, album);
		entityManager.remove(album);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	private void removeAlbumFromArtist(Artist artist, Album album) {
		try {
			Field albumsField = artist.getClass().getDeclaredField("albums");
			albumsField.setAccessible(true);
			Set<Album> albums = (Set<Album>) albumsField.get(artist);
			albums.remove(album);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Album getAlbumByArtistNameAlbumName(String artistName,
			String albumName) {
		Artist artist = getArtistByName(artistName);
		entityManager.getTransaction().begin();
		List<?> albums = entityManager
				.createQuery(
						"FROM Album WHERE artist = :artist AND name = :name")
				.setParameter("artist", artist).setParameter("name", albumName)
				.getResultList();
		Album album = null;
		if (albums.size() > 0) {
			album = (Album) albums.get(0);
		}
		entityManager.getTransaction().commit();
		return album;
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
	private List<Album> getAlbumsByArtistName(String artistName) {
		Artist artist = getArtistByName(artistName);
		Query select = entityManager.createQuery(
				"FROM Album WHERE artist = :artist").setParameter("artist",
				artist);
		return (List<Album>) select.getResultList();
	}

	private Artist getArtistByName(String artistName) {
		return (Artist) entityManager
				.createQuery("FROM Artist WHERE name = :name")
				.setParameter("name", artistName).getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	private List<Album> getAllAlbums() {
		Query select = entityManager.createQuery("FROM Album");
		return select.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Artist> getAllArtists() {
		Query select = entityManager.createQuery("FROM Artist");
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
		List<Artist> allArtists = getAllArtists();

		entityManager.getTransaction().begin();
		for (Artist artist : allArtists) {
			entityManager.remove(artist);
		}
		entityManager.getTransaction().commit();
	}

}
