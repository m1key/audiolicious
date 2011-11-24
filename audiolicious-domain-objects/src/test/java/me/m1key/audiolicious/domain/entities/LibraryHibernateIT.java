package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

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

		System.out.println("DELETE ALL ARTISTS!");
		deleteAllArtists();
		System.out.println("DELETE ALL ARTISTS COMPLETE!");
		assertEquals("There should be no stats after all artists removed.", 0,
				getAllStats().size());
		assertEquals("There should be songs after all artists removed.", 0,
				getAllSongs().size());
		assertEquals("There should be no artists.", 0, getAllArtists().size());
		assertEquals(
				"There should still be 1 library after all artists removed.",
				1, getAllLibraries().size());
	}

	private void createArtistsAlbumsSongsAndStats() {
		assertEquals("There should be no albums before any are created.", 0,
				getAllAlbums().size());

		getEntityManager().getTransaction().begin();

		getEntityManager().persist(library);

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
		Song song01 = new Song(ARTIST_1_ALBUM_1_SONG_1_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song02 = new Song(ARTIST_1_ALBUM_1_SONG_2_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", true, 0, 0, false);
		Song song03 = new Song(ARTIST_1_ALBUM_1_SONG_3_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song04 = new Song(ARTIST_1_ALBUM_1_SONG_4_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song05 = new Song(ARTIST_1_ALBUM_1_SONG_5_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song06 = new Song(ARTIST_1_ALBUM_1_SONG_6_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song07 = new Song(ARTIST_1_ALBUM_1_SONG_7_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song08 = new Song(ARTIST_1_ALBUM_1_SONG_8_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song09 = new Song(ARTIST_1_ALBUM_1_SONG_9_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song10 = new Song(ARTIST_1_ALBUM_1_SONG_10_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);
		Song song11 = new Song(ARTIST_1_ALBUM_1_SONG_11_NAME, ARTIST_1_NAME,
				artist1Album1, 1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne",
				"Rock", false, 0, 0, false);

		song01.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 12);
		song02.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 3);
		song03.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 4);
		song04.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 8);
		song05.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 91);
		song06.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 11);
		song07.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 1);
		song08.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 2);
		song09.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 5);
		song10.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 6);
		song11.addStat(library, artist1Album1DateAdded,
				artist1Album1DateModified, artist1Album1DateSkipped, 0,
				new Rating(80), 7);

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
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song02 = new Song(ARTIST_1_ALBUM_2_SONG_2_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song03 = new Song(ARTIST_1_ALBUM_2_SONG_3_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song04 = new Song(ARTIST_1_ALBUM_2_SONG_4_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song05 = new Song(ARTIST_1_ALBUM_2_SONG_5_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song06 = new Song(ARTIST_1_ALBUM_2_SONG_6_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song07 = new Song(ARTIST_1_ALBUM_2_SONG_7_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song08 = new Song(ARTIST_1_ALBUM_2_SONG_8_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song09 = new Song(ARTIST_1_ALBUM_2_SONG_9_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song10 = new Song(ARTIST_1_ALBUM_2_SONG_10_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song11 = new Song(ARTIST_1_ALBUM_2_SONG_11_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song12 = new Song(ARTIST_1_ALBUM_2_SONG_12_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);
		Song song13 = new Song(ARTIST_1_ALBUM_2_SONG_13_NAME, ARTIST_1_NAME,
				artist1Album2, 1991, "", "Rock", false, 0, 0, false);

		song01.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 19);
		song02.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 29);
		song03.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 39);
		song04.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 49);
		song05.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 59);
		song06.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 69);
		song07.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 79);
		song08.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 89);
		song09.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 99);
		song10.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 100);
		song11.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 101);
		song12.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 109);
		song13.addStat(library, artist1Album2DateAdded,
				artist1Album2DateModified, artist1Album2DateSkipped, 0,
				new Rating(80), 119);

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
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song02 = new Song(ARTIST_2_ALBUM_1_SONG_2_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song03 = new Song(ARTIST_2_ALBUM_1_SONG_3_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song04 = new Song(ARTIST_2_ALBUM_1_SONG_4_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song05 = new Song(ARTIST_2_ALBUM_1_SONG_5_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song06 = new Song(ARTIST_2_ALBUM_1_SONG_6_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song07 = new Song(ARTIST_2_ALBUM_1_SONG_7_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song08 = new Song(ARTIST_2_ALBUM_1_SONG_8_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song09 = new Song(ARTIST_2_ALBUM_1_SONG_9_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song10 = new Song(ARTIST_2_ALBUM_1_SONG_10_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);
		Song song11 = new Song(ARTIST_2_ALBUM_1_SONG_11_NAME, ARTIST_2_NAME,
				artist2Album1, 2009, "", "Alternative", false, 0, 0, false);

		song01.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 19);
		song02.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 29);
		song03.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 39);
		song04.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 49);
		song05.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 9);
		song06.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 59);
		song07.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 69);
		song08.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 79);
		song09.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 89);
		song10.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 99);
		song11.addStat(library, artist2Album1DateAdded,
				artist2Album1DateModified, artist2Album1DateSkipped, 0,
				new Rating(80), 109);

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
