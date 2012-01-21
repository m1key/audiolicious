/* 
 * Audiolicious - Your Music Library Statistics
 * Copyright (C) 2012, Michal Huniewicz
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
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import me.m1key.audiolicious.domain.to.AlbumAndSongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

import org.junit.Test;

public class LibraryHibernateIT extends HibernateIT {

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

	private Library library = new Library(new Date());

	@Test
	public void shouldHaveCorrectNumberOfArtistsAlbumsSongsAndStats() {
		createArtistsAlbumsSongsAndStats();
		assertEquals("There should be 1 library.", 1, getAllLibraries().size());
	}

	@Test
	public void whenAllLibrariesRemovedThereShouldBeNoStats() {
		createArtistsAlbumsSongsAndStats();

		deleteAllLibraries();
		assertEquals("There should be no stats after all libraries removed.",
				0, getAllStats().size());
		assertEquals(
				"There should be no libraries after all libraries removed.", 0,
				getAllLibraries().size());
		assertTrue("There should be songs after all libraries removed.",
				getAllSongs().size() > 0);
		assertEquals("There should be two artists.", 2, getAllArtists().size());
	}

	@Test
	public void whenAllArtistsRemovedThereShouldBeNoStatsButThereShouldBeLibrary() {
		createArtistsAlbumsSongsAndStats();

		deleteAllArtists();
		assertEquals("There should be no stats after all artists removed.", 0,
				getAllStats().size());
		assertEquals("There should be songs after all artists removed.", 0,
				getAllSongs().size());
		assertEquals("There should be no artists.", 0, getAllArtists().size());
		assertEquals(
				"There should still be 1 library after all artists removed.",
				1, getAllLibraries().size());
	}

	@Test
	public void whenAllArtistsRemovedThereShouldBeNoStats() {
		createArtistsAlbumsSongsAndStats();

		deleteAllArtists();
		assertEquals("There should be no stats after all artists removed.", 0,
				getAllStats().size());
	}

	@Test
	public void shouldDeleteOnlyOneArtistAndAllOfItsStatsSongsAndAlbums() {
		createArtistsAlbumsSongsAndStats();

		deleteArtistByName(ARTIST_1_NAME, library);

		assertEquals("There should be one artist after one has been deleted.",
				1, getAllArtists().size());
		assertEquals(
				"There should be one albums after one artist has been deleted..",
				1, getAllAlbums().size());
		assertEquals(
				"There should be 11 songs after one artist has been deleted.",
				11, getAllSongs().size());
		assertEquals(
				"There should be 11 stats after one artist has been deleted.",
				11, getAllStats().size());
	}

	@Test
	public void shouldReturnValidSong05StatFromAlbum1Artist2() {
		createArtistsAlbumsSongsAndStats();

		Song song = getSongByArtistNameAlbumNameSongName(ARTIST_2_NAME,
				ARTIST_2_ALBUM_1_NAME, ARTIST_2_ALBUM_1_SONG_5_NAME);

		assertNotNull(String.format("Song [%s] should not be null.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), song);

		Set<Stat> stats = getStats(song, library);
		assertTrue("There should be at least one stat for this song.", stats
				.iterator().hasNext());

		Stat stat = stats.iterator().next();

		assertEquals(String.format("Song [%s] date added should be correct.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), stat.getDateAdded(),
				artist2Album1DateAdded);
		assertEquals(String.format(
				"Song [%s] date modified should be correct.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), stat.getDateModified(),
				artist2Album1DateModified);
		assertEquals(String.format("Song [%s] date skipped should be correct.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), stat.getDateSkipped(),
				artist2Album1DateSkipped);
		assertEquals(String.format(
				"Song [%s] should have play count equal 59.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), stat.getPlayCount(), 59);
		assertEquals(String.format("Song [%s] should have rating equal 80.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), stat.getRating(), new Rating(80));
		assertEquals(String.format("Song [%s] should have skip count equal 0.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), stat.getSkipCount(), 0);
	}

	private void createArtistsAlbumsSongsAndStats() {
		assertEquals("There should be no albums before any are created.", 0,
				getAllAlbums().size());

		getEntityManager().getTransaction().begin();

		getEntityManager().persist(library);

		Artist artist1 = new Artist(ARTIST_1_NAME);
		addSongsToAlbum1(artist1);
		addSongsToAlbum2(artist1);
		getEntityManager().persist(artist1);

		Artist artist2 = new Artist(ARTIST_2_NAME);
		addSongsToAlbum3(artist2);
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

	private void addSongsToAlbum1(Artist artist1) {

		Song song1 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_1_NAME).withTrackNumber(1)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(12).withSong(song1).build());

		Song song2 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_2_NAME).withTrackNumber(2)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(3).withSong(song2).build());

		Song song3 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_3_NAME).withTrackNumber(3)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(4).withSong(song3).build());

		Song song4 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_4_NAME).withTrackNumber(4)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(8).withSong(song4).build());

		Song song5 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_5_NAME).withTrackNumber(5)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(91).withSong(song5).build());

		Song song6 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_6_NAME).withTrackNumber(6)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(11).withSong(song6).build());

		Song song7 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_7_NAME).withTrackNumber(7)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(6).withSong(song7).build());

		Song song8 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_8_NAME).withTrackNumber(8)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(2).withSong(song8).build());

		Song song9 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_9_NAME).withTrackNumber(9)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(5).withSong(song9).build());

		Song song10 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_10_NAME).withTrackNumber(10)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(6).withSong(song10).build());

		Song song11 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_1_SONG_11_NAME).withTrackNumber(11)
				.withDiscNumber(1).withYear(1988).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_1_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album1DateAdded)
				.withDateModified(artist1Album1DateModified)
				.withDateSkipped(artist1Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(7).withSong(song11).build());
	}

	private void addSongsToAlbum2(Artist artist1) {
		Song song1 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_1_NAME).withTrackNumber(1)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(19).withSong(song1).build());

		Song song2 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_2_NAME).withTrackNumber(2)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(29).withSong(song2).build());

		Song song3 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_3_NAME).withTrackNumber(3)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(39).withSong(song3).build());

		Song song4 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_4_NAME).withTrackNumber(4)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(49).withSong(song4).build());

		Song song5 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_5_NAME).withTrackNumber(5)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(59).withSong(song5).build());

		Song song6 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_6_NAME).withTrackNumber(6)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(69).withSong(song6).build());

		Song song7 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_7_NAME).withTrackNumber(7)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(79).withSong(song7).build());

		Song song8 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_8_NAME).withTrackNumber(8)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(89).withSong(song8).build());

		Song song9 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_9_NAME).withTrackNumber(9)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(99).withSong(song9).build());

		Song song10 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_10_NAME).withTrackNumber(10)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(109).withSong(song10).build());

		Song song11 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_11_NAME).withTrackNumber(11)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(119).withSong(song11).build());

		Song song12 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_12_NAME).withTrackNumber(12)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(129).withSong(song12).build());

		Song song13 = artist1.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_1_ALBUM_2_SONG_13_NAME).withTrackNumber(13)
				.withDiscNumber(1).withYear(1991).withGenre("Rock")
				.withHasVideo(false).withRating(100)
				.withAlbumName(ARTIST_1_ALBUM_2_NAME).withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist1Album2DateAdded)
				.withDateModified(artist1Album2DateModified)
				.withDateSkipped(artist1Album2DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(139).withSong(song13).build());
	}

	private void addSongsToAlbum3(Artist artist2) {

		Song song1 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_1_NAME).withTrackNumber(1)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withAlbumRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(19).withSong(song1).build());

		Song song2 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_2_NAME).withTrackNumber(2)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withAlbumRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(29).withSong(song2).build());

		Song song3 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_3_NAME).withTrackNumber(3)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(39).withSong(song3).build());

		Song song4 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_4_NAME).withTrackNumber(4)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(49).withSong(song4).build());

		Song song5 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_5_NAME).withTrackNumber(5)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(59).withSong(song5).build());

		Song song6 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_6_NAME).withTrackNumber(6)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(69).withSong(song6).build());

		Song song7 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_7_NAME).withTrackNumber(7)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(79).withSong(song7).build());

		Song song8 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_8_NAME).withTrackNumber(8)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(89).withSong(song8).build());

		Song song9 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_9_NAME).withTrackNumber(1)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(99).withSong(song9).build());

		Song song10 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_10_NAME).withTrackNumber(10)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(109).withSong(song10).build());

		Song song11 = artist2.addSong(new AlbumAndSongInfoBuilder(
				ARTIST_2_ALBUM_1_SONG_11_NAME).withTrackNumber(11)
				.withDiscNumber(1).withYear(2009).withGenre("Alternative")
				.withHasVideo(false).withAlbumName(ARTIST_2_ALBUM_1_NAME)
				.withAlbumRating(80).withRating(100).build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(artist2Album1DateAdded)
				.withDateModified(artist2Album1DateModified)
				.withDateSkipped(artist2Album1DateSkipped).withSkipCount(0)
				.withRating(80).withPlayCount(119).withSong(song11).build());
	}
}
