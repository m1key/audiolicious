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

import java.util.Date;

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

	private Date artist1Album1DateAdded = new Date();
	private Date artist1Album1DateModifed = new Date();
	private Date artist1Album1DateSkipped = new Date();
	private Date artist1Album2DateAdded = new Date();
	private Date artist1Album2DateModifed = new Date();
	private Date artist1Album2DateSkipped = new Date();
	private Date artist2Album1DateAdded = new Date();
	private Date artist2Album1DateModifed = new Date();
	private Date artist2Album1DateSkipped = new Date();

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

	private void createArtistsAlbumsAndSongs() {
		assertEquals("There should be no albums before any are created.", 0,
				getAllAlbums().size());

		getEntityManager().getTransaction().begin();

		Artist artist1 = new Artist(ARTIST_1_NAME);
		Album artist1Album1 = new Album(ARTIST_1_ALBUM_1_NAME, artist1,
				new Rating(80));
		addSongsToAlbum1(artist1Album1);
		getEntityManager().persist(artist1Album1);
		Album artist1Album2 = new Album(ARTIST_1_ALBUM_2_NAME, artist1,
				new Rating(80));
		addSongsToAlbum2(artist1Album2);
		getEntityManager().persist(artist1Album2);

		Artist artist2 = new Artist(ARTIST_2_NAME);
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
		Song song01 = new Song(ARTIST_1_ALBUM_1_SONG_1_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(80), 9, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song02 = new Song(ARTIST_1_ALBUM_1_SONG_2_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(80), 1, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song03 = new Song(ARTIST_1_ALBUM_1_SONG_3_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist2Album1DateModifed,
				new Rating(100), 8, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song04 = new Song(ARTIST_1_ALBUM_1_SONG_4_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(80), 76, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song05 = new Song(ARTIST_1_ALBUM_1_SONG_5_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(100), 16, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song06 = new Song(ARTIST_1_ALBUM_1_SONG_6_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(100), 2, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song07 = new Song(ARTIST_1_ALBUM_1_SONG_7_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(60), 34, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song08 = new Song(ARTIST_1_ALBUM_1_SONG_8_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(80), 0, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song09 = new Song(ARTIST_1_ALBUM_1_SONG_9_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(80), 1, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song10 = new Song(ARTIST_1_ALBUM_1_SONG_10_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(60), 14, artist1Album1DateSkipped, 0, false, 0, 0,
				false);
		Song song11 = new Song(ARTIST_1_ALBUM_1_SONG_11_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", artist1Album1DateAdded, artist1Album1DateModifed,
				new Rating(80), 2, artist1Album1DateSkipped, 0, false, 0, 0,
				false);

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
		Song song01 = new Song(ARTIST_1_ALBUM_2_SONG_1_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(100), 9,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song02 = new Song(ARTIST_1_ALBUM_2_SONG_2_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(80), 1,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song03 = new Song(ARTIST_1_ALBUM_2_SONG_3_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(60), 8,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song04 = new Song(ARTIST_1_ALBUM_2_SONG_4_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(80), 76,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song05 = new Song(ARTIST_1_ALBUM_2_SONG_5_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(100), 11,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song06 = new Song(ARTIST_1_ALBUM_2_SONG_6_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(60), 2,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song07 = new Song(ARTIST_1_ALBUM_2_SONG_7_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(100), 21,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song08 = new Song(ARTIST_1_ALBUM_2_SONG_8_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(60), 0,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song09 = new Song(ARTIST_1_ALBUM_2_SONG_9_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(80), 1,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song10 = new Song(ARTIST_1_ALBUM_2_SONG_10_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(80), 14,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song11 = new Song(ARTIST_1_ALBUM_2_SONG_11_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(60), 2,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song12 = new Song(ARTIST_1_ALBUM_2_SONG_12_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(100), 2,
				artist1Album2DateSkipped, 0, false, 0, 0, false);
		Song song13 = new Song(ARTIST_1_ALBUM_2_SONG_13_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", artist1Album2DateAdded,
				artist1Album2DateModifed, new Rating(100), 2,
				artist1Album2DateSkipped, 0, false, 0, 0, false);

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
		Song song01 = new Song(ARTIST_2_ALBUM_1_SONG_1_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(100), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song02 = new Song(ARTIST_2_ALBUM_1_SONG_2_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song03 = new Song(ARTIST_2_ALBUM_1_SONG_3_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(100), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song04 = new Song(ARTIST_2_ALBUM_1_SONG_4_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(100), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song05 = new Song(ARTIST_2_ALBUM_1_SONG_5_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song06 = new Song(ARTIST_2_ALBUM_1_SONG_6_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song07 = new Song(ARTIST_2_ALBUM_1_SONG_7_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song08 = new Song(ARTIST_2_ALBUM_1_SONG_8_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song09 = new Song(ARTIST_2_ALBUM_1_SONG_9_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song10 = new Song(ARTIST_2_ALBUM_1_SONG_10_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);
		Song song11 = new Song(ARTIST_2_ALBUM_1_SONG_11_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", artist2Album1DateAdded,
				artist2Album1DateModifed, new Rating(80), 9,
				artist2Album1DateSkipped, 0, false, 0, 0, false);

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
