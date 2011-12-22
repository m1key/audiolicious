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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class SongHibernateIT extends HibernateIT {

	private static final String ARTIST_1_NAME = "Ozzy Osbourne";
	private static final String ARTIST_1_ALBUM_1_NAME = "No Rest for the Wicked";
	private static final String ARTIST_1_ALBUM_1_SONG_1_NAME = "Miracle Man";
	private static final String ARTIST_1_ALBUM_1_SONG_2_NAME = "Devil's Daughter (Holy War) [Album Version]";
	private static final String ARTIST_1_ALBUM_1_SONG_3_NAME = "Crazy Babies";
	private static final String ARTIST_1_ALBUM_1_SONG_4_NAME = "Breakin' All The Rules";
	private static final String ARTIST_1_ALBUM_1_SONG_5_NAME = "Bloodbath In Paradise [Album Version]";
	private static final String ARTIST_1_ALBUM_1_SONG_6_NAME = "Fire In the Sky";
	private static final String ARTIST_1_ALBUM_1_SONG_7_NAME = "Tattooed Dancer [Album Version]";
	private static final String ARTIST_1_ALBUM_1_SONG_8_NAME = "Demon Alcohol";
	private static final String ARTIST_1_ALBUM_1_SONG_9_NAME = "Hero [Album Version]";
	private static final String ARTIST_1_ALBUM_1_SONG_10_NAME = "The Liar";
	private static final String ARTIST_1_ALBUM_1_SONG_11_NAME = "Miracle Man [Live]";
	private static final String ARTIST_1_ALBUM_2_NAME = "No More Tears";
	private static final String ARTIST_1_ALBUM_2_SONG_1_NAME = "Mr. Tinkertrain";
	private static final String ARTIST_1_ALBUM_2_SONG_2_NAME = "I Don't Want to Change the World";
	private static final String ARTIST_1_ALBUM_2_SONG_3_NAME = "Mama, I'm Coming Home";
	private static final String ARTIST_1_ALBUM_2_SONG_4_NAME = "Desire";
	private static final String ARTIST_1_ALBUM_2_SONG_5_NAME = "No More Tears";
	private static final String ARTIST_1_ALBUM_2_SONG_6_NAME = "Won't Be Coming Home (S.I.N.)";
	private static final String ARTIST_1_ALBUM_2_SONG_7_NAME = "Hellraiser";
	private static final String ARTIST_1_ALBUM_2_SONG_8_NAME = "Time After Time";
	private static final String ARTIST_1_ALBUM_2_SONG_9_NAME = "Zombie Stomp";
	private static final String ARTIST_1_ALBUM_2_SONG_10_NAME = "A.V.H.";
	private static final String ARTIST_1_ALBUM_2_SONG_11_NAME = "Road to Nowhere";
	private static final String ARTIST_1_ALBUM_2_SONG_12_NAME = "Don't Blame Me";
	private static final String ARTIST_1_ALBUM_2_SONG_13_NAME = "Party With the Animals";
	private static final String ARTIST_2_NAME = "The Dead Weather";
	private static final String ARTIST_2_ALBUM_1_NAME = "Horehound";
	private static final String ARTIST_2_ALBUM_1_SONG_1_NAME = "60 Feet Tall";
	private static final String ARTIST_2_ALBUM_1_SONG_2_NAME = "Hang You from the Heavens";
	private static final String ARTIST_2_ALBUM_1_SONG_3_NAME = "I Cut Like a Buffalo";
	private static final String ARTIST_2_ALBUM_1_SONG_4_NAME = "So Far From Your Weapon";
	private static final String ARTIST_2_ALBUM_1_SONG_5_NAME = "Treat Me Like Your Mother";
	private static final String ARTIST_2_ALBUM_1_SONG_6_NAME = "Rocking Horse";
	private static final String ARTIST_2_ALBUM_1_SONG_7_NAME = "New Pony";
	private static final String ARTIST_2_ALBUM_1_SONG_8_NAME = "Bone House";
	private static final String ARTIST_2_ALBUM_1_SONG_9_NAME = "3 Birds";
	private static final String ARTIST_2_ALBUM_1_SONG_10_NAME = "No Hassle Night";
	private static final String ARTIST_2_ALBUM_1_SONG_11_NAME = "Will There Be Enough Water?";

	@Test
	public void shouldHaveCorrectNumberOfArtistsAlbumsAndSongs() {
		createArtistsAlbumsAndSongs();
	}

	@Test
	public void whenAllArtistsRemovedThereShouldBeNoSongs() {
		createArtistsAlbumsAndSongs();

		deleteAllArtists();
		assertEquals("There should be no songs after all artists removed.", 0,
				getAllSongs().size());
	}

	@Test
	public void shouldDeleteOnlyOneArtistAndAllOfItsSongsAndAlbums() {
		createArtistsAlbumsAndSongs();

		deleteArtistByName(ARTIST_1_NAME);

		assertEquals("There should be one artist after one has been deleted.",
				1, getAllArtists().size());
		assertEquals(
				"There should be one albums after one artist has been deleted..",
				1, getAllAlbums().size());
		assertEquals(
				"There should be 11 songs after one artist has been deleted.",
				11, getAllSongs().size());
	}

	@Test
	public void shouldReturnValidSong05FromAlbum1Artist2() {
		createArtistsAlbumsAndSongs();

		Song song = getSongByArtistNameAlbumNameSongName(ARTIST_2_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_ALBUM_1_SONG_5_NAME);

		assertNotNull(String.format("Song [%s] should not be null.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song);
		assertEquals(
				String.format(
						"Song [%s] album should equal album it was requested with.",
						ARTIST_2_ALBUM_1_SONG_5_NAME),
				song.getAlbum(),
				getAlbumByArtistNameAlbumName(ARTIST_2_NAME,
						ARTIST_2_ALBUM_1_NAME));
		assertEquals(
				String.format(
						"Song [%s] album artist should equal artist it was requested with.",
						ARTIST_2_ALBUM_1_SONG_5_NAME), song.getArtistName(),
				ARTIST_2_NAME);
		assertEquals(String.format(
				"Song [%s] composer should be an empty string.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getComposer(), "");
		assertEquals(String.format("Song [%s] genre should be correct.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getGenre(), "Alternative");
		assertEquals(String.format("Song [%s] name should be correct.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getName(),
				ARTIST_2_ALBUM_1_SONG_5_NAME);
		assertEquals(String.format(
				"Song [%s] should have video height count equal 0.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getVideoHeight(), 0);
		assertEquals(String.format(
				"Song [%s] should have video width count equal 0.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getVideoWidth(), 0);
		assertEquals(String.format("Song [%s] should have year equal 2009.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getYear(), 2009);
		assertNotNull(String.format("Song [%s] UUID should not be null.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getUuid());
		assertFalse(String.format("Song [%s] HD should be false.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.isHd());
		assertFalse(String.format("Song [%s] should have no video.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.hasVideo());
	}

	@Test
	public void testGetSongs() {
		createArtistsAlbumsAndSongs();

		getEntityManager().getTransaction().begin();

		Album album = getAlbumByArtistNameAlbumNameInsideExistingTransaction(
				ARTIST_1_NAME, ARTIST_1_ALBUM_1_NAME);

		Set<Song> songs = getAndVerifyAlbumSongs(album, 11);

		verifySong1Correct(album, songs);
		verifySong2Correct(album, songs);

		getEntityManager().getTransaction().commit();
	}

	@Test
	public void savingAlbumShouldSaveArtist() {
		getEntityManager().getTransaction().begin();
		Artist artist = new Artist(ARTIST_1_NAME);
		getEntityManager().persist(artist);
		Album album = new Album(ARTIST_1_ALBUM_1_NAME, artist, new Rating(80));
		getEntityManager().persist(album);
		Song song = new Song(ARTIST_1_ALBUM_1_SONG_1_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 1, 1, album, 1988,
				"Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0, 0,
				false, 100);
		getEntityManager().persist(song);
		getEntityManager().getTransaction().commit();

		assertNotNull("Artist should be persisted.",
				getArtistByName(ARTIST_1_NAME));
		assertNotNull(
				"Album should be persisted.",
				getAlbumByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_1_NAME));
		assertNotNull(
				"Song should be persisted.",
				getSongByArtistNameAlbumNameSongName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_1_NAME, ARTIST_1_ALBUM_1_SONG_1_NAME));
	}

	private Album getAlbumByArtistNameAlbumNameInsideExistingTransaction(
			String artistName, String albumName) {
		Artist artist = (Artist) getEntityManager()
				.createQuery("FROM Artist WHERE name = :name")
				.setParameter("name", artistName).getResultList().get(0);
		Album album = (Album) getEntityManager()
				.createQuery(
						"FROM Album WHERE artist = :artist AND name = :name")
				.setParameter("artist", artist).setParameter("name", albumName)
				.getResultList().get(0);
		return album;
	}

	private Set<Song> getAndVerifyAlbumSongs(Album album,
			int expectedNumberOfSongs) {
		Set<Song> songs = album.getSongs();
		assertNotNull("Songs obtained via getter should not be null.", songs);
		assertEquals(String.format(
				"There should be [%s] songs obtained via getter.",
				expectedNumberOfSongs), expectedNumberOfSongs, songs.size());
		return songs;
	}

	private void verifySong1Correct(Album album, Set<Song> songs) {
		Song song1 = getSong(songs, ARTIST_1_ALBUM_1_SONG_1_NAME);
		assertNotNull(String.format("Song [%s] should not be null.",
				ARTIST_1_ALBUM_1_SONG_1_NAME), song1);
		assertFalse(String.format("Song [%s] should not have video.",
				ARTIST_1_ALBUM_1_SONG_1_NAME), song1.hasVideo());
		assertEquals("song1.getAlbum() should equal album.", song1.getAlbum(),
				album);
	}

	private void verifySong2Correct(Album album, Set<Song> songs) {
		Song song2 = getSong(songs, ARTIST_1_ALBUM_1_SONG_2_NAME);
		assertNotNull(String.format("Song [%s] should not be null.",
				ARTIST_1_ALBUM_1_SONG_2_NAME), song2);
		assertTrue(String.format("Song [%s] should have video.",
				ARTIST_1_ALBUM_1_SONG_2_NAME), song2.hasVideo());
		assertEquals("song2.getAlbum() should equal album.", song2.getAlbum(),
				album);
	}

	private Song getSong(Set<Song> songs, String songName) {
		for (Song song : songs) {
			if (song.getName().equals(songName)) {
				return song;
			}
		}
		return null;
	}

	private void createArtistsAlbumsAndSongs() {
		assertEquals("There should be no albums before any are created.", 0,
				getAllAlbums().size());

		getEntityManager().getTransaction().begin();

		Artist artist1 = new Artist(ARTIST_1_NAME);
		getEntityManager().persist(artist1);
		Album artist1Album1 = new Album(ARTIST_1_ALBUM_1_NAME, artist1,
				new Rating(80));
		addSongsToAlbum1(artist1Album1);
		getEntityManager().persist(artist1Album1);
		Album artist1Album2 = new Album(ARTIST_1_ALBUM_2_NAME, artist1,
				new Rating(80));
		addSongsToAlbum2(artist1Album2);
		getEntityManager().persist(artist1Album2);

		Artist artist2 = new Artist(ARTIST_2_NAME);
		getEntityManager().persist(artist2);
		Album artist2Album1 = new Album(ARTIST_2_ALBUM_1_NAME, artist2,
				new Rating(80));
		addSongsToAlbum3(artist2Album1);
		getEntityManager().persist(artist2Album1);

		getEntityManager().getTransaction().commit();

		assertEquals("There should be two artists.", 2, getAllArtists().size());
		assertEquals("There should be three albums.", 3, getAllAlbums().size());
		assertEquals(
				String.format("Artist [%s] album [%s] should have 11 songs.",
						ARTIST_1_NAME, ARTIST_1_ALBUM_1_NAME),
				11,
				getAllSongsByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_1_NAME).size());
		assertEquals(
				String.format("Artist [%s] album [%s] should have 13 songs.",
						ARTIST_1_NAME, ARTIST_1_ALBUM_2_NAME),
				13,
				getAllSongsByArtistNameAlbumName(ARTIST_1_NAME,
						ARTIST_1_ALBUM_2_NAME).size());
		assertEquals(
				String.format("Artist [%s] album [%s] should have 11 songs.",
						ARTIST_2_NAME, ARTIST_2_ALBUM_1_NAME),
				11,
				getAllSongsByArtistNameAlbumName(ARTIST_2_NAME,
						ARTIST_2_ALBUM_1_NAME).size());
	}

	private void addSongsToAlbum1(Album artist1Album1) {
		Song song01 = new Song(ARTIST_1_ALBUM_1_SONG_1_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 1, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song02 = new Song(ARTIST_1_ALBUM_1_SONG_2_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 2, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", true, 0,
				0, false, 100);
		Song song03 = new Song(ARTIST_1_ALBUM_1_SONG_3_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 3, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song04 = new Song(ARTIST_1_ALBUM_1_SONG_4_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 4, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song05 = new Song(ARTIST_1_ALBUM_1_SONG_5_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 5, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song06 = new Song(ARTIST_1_ALBUM_1_SONG_6_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 6, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song07 = new Song(ARTIST_1_ALBUM_1_SONG_7_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 7, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song08 = new Song(ARTIST_1_ALBUM_1_SONG_8_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 8, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song09 = new Song(ARTIST_1_ALBUM_1_SONG_9_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 9, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song10 = new Song(ARTIST_1_ALBUM_1_SONG_10_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 10, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		Song song11 = new Song(ARTIST_1_ALBUM_1_SONG_11_NAME,
				ARTIST_1_ALBUM_1_NAME, ARTIST_1_NAME, 11, 1, artist1Album1,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);

		artist1Album1.addSong(song01);
		artist1Album1.addSong(song02);
		artist1Album1.addSong(song03);
		artist1Album1.addSong(song04);
		artist1Album1.addSong(song05);
		artist1Album1.addSong(song06);
		artist1Album1.addSong(song07);
		artist1Album1.addSong(song08);
		artist1Album1.addSong(song09);
		artist1Album1.addSong(song10);
		artist1Album1.addSong(song11);

	}

	private void addSongsToAlbum2(Album artist1Album2) {
		Song song01 = new Song(ARTIST_1_ALBUM_2_SONG_1_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 1, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song02 = new Song(ARTIST_1_ALBUM_2_SONG_2_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 2, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song03 = new Song(ARTIST_1_ALBUM_2_SONG_3_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 3, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song04 = new Song(ARTIST_1_ALBUM_2_SONG_4_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 4, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song05 = new Song(ARTIST_1_ALBUM_2_SONG_5_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 5, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song06 = new Song(ARTIST_1_ALBUM_2_SONG_6_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 6, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song07 = new Song(ARTIST_1_ALBUM_2_SONG_7_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 7, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song08 = new Song(ARTIST_1_ALBUM_2_SONG_8_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 8, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song09 = new Song(ARTIST_1_ALBUM_2_SONG_9_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 9, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song10 = new Song(ARTIST_1_ALBUM_2_SONG_10_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 10, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song11 = new Song(ARTIST_1_ALBUM_2_SONG_11_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 11, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song12 = new Song(ARTIST_1_ALBUM_2_SONG_12_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 12, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);
		Song song13 = new Song(ARTIST_1_ALBUM_2_SONG_13_NAME,
				ARTIST_1_ALBUM_2_NAME, ARTIST_1_NAME, 13, 1, artist1Album2,
				1991, "", "Rock", false, 0, 0, false, 100);

		artist1Album2.addSong(song01);
		artist1Album2.addSong(song02);
		artist1Album2.addSong(song03);
		artist1Album2.addSong(song04);
		artist1Album2.addSong(song05);
		artist1Album2.addSong(song06);
		artist1Album2.addSong(song07);
		artist1Album2.addSong(song08);
		artist1Album2.addSong(song09);
		artist1Album2.addSong(song10);
		artist1Album2.addSong(song11);
		artist1Album2.addSong(song12);
		artist1Album2.addSong(song13);

	}

	private void addSongsToAlbum3(Album artist2Album1) {
		Song song01 = new Song(ARTIST_2_ALBUM_1_SONG_1_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 1, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song02 = new Song(ARTIST_2_ALBUM_1_SONG_2_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 2, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song03 = new Song(ARTIST_2_ALBUM_1_SONG_3_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 3, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song04 = new Song(ARTIST_2_ALBUM_1_SONG_4_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 4, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song05 = new Song(ARTIST_2_ALBUM_1_SONG_5_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 5, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song06 = new Song(ARTIST_2_ALBUM_1_SONG_6_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 6, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song07 = new Song(ARTIST_2_ALBUM_1_SONG_7_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 7, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song08 = new Song(ARTIST_2_ALBUM_1_SONG_8_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 8, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song09 = new Song(ARTIST_2_ALBUM_1_SONG_9_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 9, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song10 = new Song(ARTIST_2_ALBUM_1_SONG_10_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 10, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);
		Song song11 = new Song(ARTIST_2_ALBUM_1_SONG_11_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_NAME, 11, 1, artist2Album1,
				2009, "", "Alternative", false, 0, 0, false, 100);

		artist2Album1.addSong(song01);
		artist2Album1.addSong(song02);
		artist2Album1.addSong(song03);
		artist2Album1.addSong(song04);
		artist2Album1.addSong(song05);
		artist2Album1.addSong(song06);
		artist2Album1.addSong(song07);
		artist2Album1.addSong(song08);
		artist2Album1.addSong(song09);
		artist2Album1.addSong(song10);
		artist2Album1.addSong(song11);
	}
}
