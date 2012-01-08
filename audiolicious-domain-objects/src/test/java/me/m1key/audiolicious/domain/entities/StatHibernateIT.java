package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import me.m1key.audiolicious.domain.to.SongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

import org.junit.Test;

public class StatHibernateIT extends HibernateIT {

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

		deleteArtistByName(ARTIST_1_NAME);

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

		assertTrue("There should be at least one stat for this song.", song
				.getStats().iterator().hasNext());

		Stat stat = song.getStats().iterator().next();

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
		assertEquals(String.format("Song [%s] should have play count equal 9.",
				ARTIST_2_ALBUM_1_SONG_5_NAME), stat.getPlayCount(), 9);
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
						.withGenre("Rock").withHasVideo(false).withRating(100)
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
						.withSkipCount(0).withRating(80).withPlayCount(1)
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

	private void addSongsToAlbum2(Album artist1Album2) {
		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_1_NAME)
						.withTrackNumber(1).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(12)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_2_NAME)
						.withTrackNumber(2).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(3)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_3_NAME)
						.withTrackNumber(3).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(4)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_4_NAME)
						.withTrackNumber(4).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(8)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_5_NAME)
						.withTrackNumber(5).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(91)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_6_NAME)
						.withTrackNumber(6).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(11)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_7_NAME)
						.withTrackNumber(7).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(1)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_8_NAME)
						.withTrackNumber(8).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(2)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_9_NAME)
						.withTrackNumber(9).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(5)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_10_NAME)
						.withTrackNumber(10).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(100)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_11_NAME)
						.withTrackNumber(11).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(101)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_12_NAME)
						.withTrackNumber(12).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(109)
						.build());

		artist1Album2.addSong(
				new SongInfoBuilder(ARTIST_1_ALBUM_2_SONG_13_NAME)
						.withTrackNumber(13).withDiscNumber(1).withYear(1991)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.build(), new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist1Album2DateAdded)
						.withDateModified(artist1Album2DateModified)
						.withDateSkipped(artist1Album2DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(119)
						.build());

	}

	private void addSongsToAlbum3(Album artist2Album1) {
		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_1_NAME)
						.withTrackNumber(1).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).build(),
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
						.withRating(100).build(),
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
						.withRating(100).build(),
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
						.withRating(100).build(),
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
						.withRating(100).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(9)
						.build());

		artist2Album1.addSong(
				new SongInfoBuilder(ARTIST_2_ALBUM_1_SONG_6_NAME)
						.withTrackNumber(6).withDiscNumber(1).withYear(2009)
						.withGenre("Alternative").withHasVideo(false)
						.withRating(100).build(),
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
						.withRating(100).build(),
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
						.withRating(100).build(),
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
						.withRating(100).build(),
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
						.withRating(100).build(),
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
						.withRating(100).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(artist2Album1DateAdded)
						.withDateModified(artist2Album1DateModified)
						.withDateSkipped(artist2Album1DateSkipped)
						.withSkipCount(0).withRating(80).withPlayCount(119)
						.build());
	}

}
