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

import java.util.Date;
import java.util.Set;

import me.m1key.audiolicious.domain.to.AlbumInfoBuilder;
import me.m1key.audiolicious.domain.to.SongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

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
	private Date artist1Album1DateModified = new Date();
	private Date artist1Album1DateSkipped = new Date();
	private Date artist1Album2DateAdded = new Date();
	private Date artist1Album2DateModified = new Date();
	private Date artist1Album2DateSkipped = new Date();
	private Date artist2Album1DateAdded = new Date();
	private Date artist2Album1DateModified = new Date();
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
						ARTIST_2_ALBUM_1_SONG_5_NAME), ARTIST_2_NAME,
				song.getArtistName());
		assertEquals(String.format("Song [%s] genre should be correct.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getGenre(), "Alternative");
		assertEquals(String.format("Song [%s] name should be correct.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getName(),
				ARTIST_2_ALBUM_1_SONG_5_NAME);
		assertEquals(String.format("Song [%s] should have year equal 2009.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getYear(), 2009);
		assertNotNull(String.format("Song [%s] UUID should not be null.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song.getUuid());
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

		Library library = new Library("Library UUID");
		getEntityManager().persist(library);

		Artist artist1 = new Artist(ARTIST_1_NAME);
		AlbumInfo artist1Album1Info = new AlbumInfoBuilder()
				.withName(ARTIST_1_ALBUM_1_NAME).withRating(80).build();
		artist1.addAlbum(artist1Album1Info);
		AlbumInfo artist1Album2Info = new AlbumInfoBuilder()
				.withName(ARTIST_1_ALBUM_2_NAME).withRating(80).build();
		artist1.addAlbum(artist1Album2Info);
		for (Album album : artist1.getAlbums()) {
			if (album.getName().equals(ARTIST_1_ALBUM_1_NAME)) {
				addSongsToAlbum1(album, library);
			} else if (album.getName().equals(ARTIST_1_ALBUM_2_NAME)) {
				addSongsToAlbum2(album, library);
			}
		}
		getEntityManager().persist(artist1);

		Artist artist2 = new Artist(ARTIST_2_NAME);
		AlbumInfo artist2Album1Info = new AlbumInfoBuilder()
				.withName(ARTIST_2_ALBUM_1_NAME).withRating(80).build();
		artist2.addAlbum(artist2Album1Info);
		Album artist2Album1 = artist2.getAlbums().iterator().next();
		addSongsToAlbum3(artist2Album1, library);
		getEntityManager().persist(artist2);

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

	private void addSongsToAlbum1(Album artist1Album1, Library library) {
		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_1_NAME)
						.withTrackNumber(1).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(12)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_2_NAME)
						.withTrackNumber(2).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(true).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(3)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_3_NAME)
						.withTrackNumber(3).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(4)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_4_NAME)
						.withTrackNumber(4).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(8)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_5_NAME)
						.withTrackNumber(5).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(91)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_6_NAME)
						.withTrackNumber(6).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(11)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_7_NAME)
						.withTrackNumber(7).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(6)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_8_NAME)
						.withTrackNumber(8).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(2)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_9_NAME)
						.withTrackNumber(9).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(5)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_10_NAME)
						.withTrackNumber(10).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(6)
						.build());

		artist1Album1.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_1_SONG_11_NAME)
						.withTrackNumber(11).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album1DateAdded)
						.withDateModified(artist1Album1DateModified)
						.withDateSkipped(artist1Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(7)
						.build());
	}

	private void addSongsToAlbum2(Album artist1Album2, Library library) {

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_1_NAME)
						.withTrackNumber(1).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(19)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_2_NAME)
						.withTrackNumber(2).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(29)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_3_NAME)
						.withTrackNumber(3).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(39)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_4_NAME)
						.withTrackNumber(4).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(49)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_5_NAME)
						.withTrackNumber(5).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(59)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_6_NAME)
						.withTrackNumber(6).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(69)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_7_NAME)
						.withTrackNumber(7).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(79)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_8_NAME)
						.withTrackNumber(8).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(89)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_9_NAME)
						.withTrackNumber(9).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(99)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_10_NAME)
						.withTrackNumber(10).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(109)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_11_NAME)
						.withTrackNumber(11).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(119)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_12_NAME)
						.withTrackNumber(12).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(129)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_13_NAME)
						.withTrackNumber(13).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(139)
						.build());

	}

	private void addSongsToAlbum3(Album artist2Album1, Library library) {
		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_1_NAME)
						.withTrackNumber(1).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(19)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_2_NAME)
						.withTrackNumber(2).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(29)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_3_NAME)
						.withTrackNumber(3).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(39)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_4_NAME)
						.withTrackNumber(4).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(49)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_5_NAME)
						.withTrackNumber(5).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(59)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_6_NAME)
						.withTrackNumber(6).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(69)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_7_NAME)
						.withTrackNumber(7).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(79)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_8_NAME)
						.withTrackNumber(8).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(89)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_9_NAME)
						.withTrackNumber(1).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(99)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_10_NAME)
						.withTrackNumber(10).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(109)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_11_NAME)
						.withTrackNumber(11).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).withArtist(ARTIST_2_NAME).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(119)
						.build());
	}
}
