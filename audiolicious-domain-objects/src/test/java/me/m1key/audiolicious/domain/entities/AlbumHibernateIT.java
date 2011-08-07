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

import org.junit.Test;

public class AlbumHibernateIT extends HibernateIT {

	private static final String ARTIST_1_NAME = "Morcheeba";
	private static final String ARTIST_1_ALBUM_1_NAME = "Charango";
	private static final String ARTIST_1_ALBUM_2_NAME = "Big Calm";
	private static final String ARTIST_2_NAME = "Natacha Atlas";
	private static final String ARTIST_2_ALBUM_1_NAME = "Halim";

	private Album artist1Album1;

	@Test
	public void shouldHaveCorrectNumberOfArtistsAndAlbums() {
		createAlbums();
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

		deleteAlbumByArtistNameAlbumName(ARTIST_1_NAME, ARTIST_1_ALBUM_1_NAME);

		assertEquals("There should be two artists.", 2, getAllArtists().size());
		assertEquals("There should be two albums after one has been deleted.",
				2, getAllAlbums().size());

		assertNull(
				String.format("Not deleted album [%s] should still exist.",
						ARTIST_1_ALBUM_1_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_1_NAME));
		assertNotNull(
				String.format("Not deleted album [%s] should still exist.",
						ARTIST_1_ALBUM_2_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_2_NAME));
		assertNotNull(
				String.format("Deleted album [%s] should not exist.",
						ARTIST_2_ALBUM_1_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_2_NAME,
						ARTIST_2_ALBUM_1_NAME));
	}

	@Test
	public void shouldDeleteOneArtistAndAllItsAlbums() {
		createAlbums();

		deleteArtistByName(ARTIST_2_NAME);

		assertEquals("There should be one artist.", 1, getAllArtists().size());
		assertEquals(
				"There should be two albums after the other artist has been deleted",
				2, getAllAlbums().size());

		assertNotNull(
				String.format("Deleted album [%s] should not exist.",
						ARTIST_1_ALBUM_1_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_1_NAME));
		assertNotNull(
				String.format("Not deleted album [%s] should still exist.",
						ARTIST_1_ALBUM_2_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_2_NAME));
		assertNull(
				String.format("Not deleted album [%s] should still exist.",
						ARTIST_2_ALBUM_1_NAME),
				getAlbumByArtistNameAlbumName(ARTIST_2_NAME,
						ARTIST_2_ALBUM_1_NAME));
	}

	@Test
	public void testGetAlbums() {
		createAlbums();

		getEntityManager().getTransaction().begin();

		Artist artist = (Artist) getEntityManager()
				.createQuery("FROM Artist WHERE name = :name")
				.setParameter("name", ARTIST_1_NAME).getResultList().get(0);
		assertNotNull(String.format("Artist's [%s] albums should not be null.",
				ARTIST_1_NAME), artist.getAlbums());
		assertEquals(String.format("Artist [%s] should have 2 albums.",
				ARTIST_1_NAME), 2, artist.getAlbums().size());
		for (Album album : artist.getAlbums()) {
			assertEquals(String.format("Album's artist should be [%s].",
					ARTIST_1_NAME), artist, album.getArtist());
			assertNotNull("Album name should not be null.", album.getName());
			assertNotNull("Album rating should not be null.", album.getRating());
			assertNotNull("Album uuid should not be null.", album.getUuid());
		}

		getEntityManager().getTransaction().commit();
	}

	@Test
	public void savingAlbumShouldSaveArtist() {
		getEntityManager().getTransaction().begin();
		Artist artist = new Artist(ARTIST_1_NAME);
		getEntityManager().persist(artist);
		Album album = new Album(ARTIST_1_ALBUM_2_NAME, artist, new Rating(80));
		getEntityManager().persist(album);
		getEntityManager().getTransaction().commit();

		assertNotNull("Artist should be persisted.",
				getArtistByName(ARTIST_1_NAME));
		assertNotNull(
				"Album should be persisted.",
				getAlbumByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_2_NAME));
	}

	private void createAlbums() {
		assertEquals("There should be no albums before any are created.", 0,
				getAllAlbums().size());

		getEntityManager().getTransaction().begin();

		Artist artist1 = new Artist(ARTIST_1_NAME);
		getEntityManager().persist(artist1);
		artist1Album1 = new Album(ARTIST_1_ALBUM_1_NAME, artist1,
				new Rating(80));
		getEntityManager().persist(artist1Album1);
		Album artist1Album2 = new Album(ARTIST_1_ALBUM_2_NAME, artist1,
				new Rating(80));
		getEntityManager().persist(artist1Album2);

		Artist artist2 = new Artist(ARTIST_2_NAME);
		getEntityManager().persist(artist2);
		Album artist2Album1 = new Album(ARTIST_2_ALBUM_1_NAME, artist2,
				new Rating(80));
		getEntityManager().persist(artist2Album1);

		getEntityManager().getTransaction().commit();

		assertEquals("There should be two artists.", 2, getAllArtists().size());
		assertEquals("There should be three albums.", 3, getAllAlbums().size());
	}

}
