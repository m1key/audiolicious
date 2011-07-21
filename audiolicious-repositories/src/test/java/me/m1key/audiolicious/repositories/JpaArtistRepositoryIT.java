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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;

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
public class JpaArtistRepositoryIT {

	private static final String ARTIST_1_NAME = "Foreigner";
	private static final String ARTIST_2_NAME = "Natacha Atlas";

	@Inject
	private JpaArtistRepository jpaArtistRepository;

	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(JpaArtistRepositoryIT.class.getSimpleName() + ".jar",
						JavaArchive.class)
				.addManifestResource(
						new File("src/test/resources/META-INF/persistence.xml"),
						ArchivePaths.create("persistence.xml"))
				.addClasses(Artist.class, JpaArtistRepository.class,
						NullEntitiesFactory.class);
	}

	@Before
	public void setup() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("testPu");
		entityManager = emf.createEntityManager();
		jpaArtistRepository.setEntityManager(entityManager);
	}

	@Test
	public void shouldNotReturnNullForNonExistentArtist() {
		assertNotNull("Non existent artist should not be null.",
				jpaArtistRepository.getArtist("nonexistentartist"));
	}

	@Test
	public void shouldSaveAndRetrieveArtist() {
		entityManager.getTransaction().begin();
		Artist artist = new Artist(ARTIST_1_NAME);
		jpaArtistRepository.createArtist(artist);

		entityManager.getTransaction().commit();

		Artist retrievedArtist = jpaArtistRepository.getArtist(ARTIST_1_NAME);
		assertNotNull(retrievedArtist);
		assertEquals(
				"Created and retrieved by name artist should be the same.",
				artist, retrievedArtist);
	}

	@Test
	public void shouldSaveTwoArtistsAndRetrieveCorrectArtist() {
		entityManager.getTransaction().begin();
		Artist artist1 = new Artist(ARTIST_1_NAME);
		Artist artist2 = new Artist(ARTIST_2_NAME);
		jpaArtistRepository.createArtist(artist1);
		jpaArtistRepository.createArtist(artist2);

		entityManager.getTransaction().commit();

		Artist retrievedArtist1 = jpaArtistRepository.getArtist(ARTIST_1_NAME);
		Artist retrievedArtist2 = jpaArtistRepository.getArtist(ARTIST_2_NAME);
		assertNotNull(retrievedArtist1);
		assertNotNull(retrievedArtist2);
		assertEquals(
				"Created and retrieved by name artist should be the same.",
				artist1, retrievedArtist1);
		assertEquals(
				"Created and retrieved by name artist should be the same.",
				artist2, retrievedArtist2);
	}

	@After
	public void clearTestData() {
		entityManager.getTransaction().begin();
		Query deleteAllQuery = entityManager.createQuery("DELETE FROM Artist");
		deleteAllQuery.executeUpdate();
		entityManager.getTransaction().commit();
	}

}
