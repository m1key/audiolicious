package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import me.m1key.audiolicious.domain.to.AlbumAndSongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

import org.junit.Test;

public class StatTest {

	@Test
	public void testEquals1() {
		Library library = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date dateSkipped = new Date();
		int skipCount = 1;
		Rating rating = new Rating(100);
		int playCount = 10;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);

		assertEquals("Stats should be identical because "
				+ "they are for the same song and are from the same library.",
				stat1, stat2);
	}

	@Test
	public void testEquals2() {
		Library library = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateAdded2 = new Date();
		Date dateModified = new Date();
		Date dateSkipped = new Date();
		int skipCount = 1;
		Rating rating = new Rating(100);
		int playCount = 10;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library, song, dateAdded2, dateModified,
				dateSkipped, skipCount, rating, playCount);

		assertEquals("Stats should be identical because "
				+ "they are for the same song and are from the same library.",
				stat1, stat2);
	}

	@Test
	public void testEquals3() {
		Library library = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date dateModified2 = new Date();
		Date dateSkipped = new Date();
		int skipCount = 1;
		Rating rating = new Rating(100);
		int playCount = 10;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library, song, dateAdded, dateModified2,
				dateSkipped, skipCount, rating, playCount);

		assertEquals("Stats should be identical because "
				+ "they are for the same song and are from the same library.",
				stat1, stat2);
	}

	@Test
	public void testEquals4() {
		Library library = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date dateSkipped = new Date();
		Date dateSkipped2 = new Date();
		int skipCount = 1;
		Rating rating = new Rating(100);
		int playCount = 10;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped2, skipCount, rating, playCount);

		assertEquals("Stats should be identical because "
				+ "they are for the same song and are from the same library.",
				stat1, stat2);
	}

	@Test
	public void testEquals5() {
		Library library = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date dateSkipped = new Date();
		int skipCount = 1;
		int skipCount2 = 1;
		Rating rating = new Rating(100);
		int playCount = 10;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount2, rating, playCount);

		assertEquals("Stats should be identical because "
				+ "they are for the same song and are from the same library.",
				stat1, stat2);
	}

	@Test
	public void testEquals6() {
		Library library = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date dateSkipped = new Date();
		int skipCount = 1;
		Rating rating = new Rating(100);
		Rating rating2 = new Rating(80);
		int playCount = 10;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating2, playCount);

		assertEquals("Stats should be identical because "
				+ "they are for the same song and are from the same library.",
				stat1, stat2);
	}

	@Test
	public void testEquals7() {
		Library library = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date dateSkipped = new Date();
		int skipCount = 1;
		Rating rating = new Rating(100);
		int playCount = 10;
		int playCount2 = 11;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount2);

		assertEquals("Stats should be identical because "
				+ "they are for the same song and are from the same library.",
				stat1, stat2);
	}

	@Test
	public void testNotEquals1() {
		Library library = new Library(new Date());
		Library library2 = new Library(new Date());
		Song song = createSong(library);
		Date dateAdded = new Date();
		Date dateModified = new Date();
		Date dateSkipped = new Date();
		int skipCount = 1;
		Rating rating = new Rating(100);
		int playCount = 10;

		Stat stat1 = new Stat(library, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);
		Stat stat2 = new Stat(library2, song, dateAdded, dateModified,
				dateSkipped, skipCount, rating, playCount);

		assertFalse("Stats should not be identical because "
				+ "they are for the same song but not from the same library.",
				stat1.equals(stat2));
	}

	private Song createSong(Library library) {

		Artist artist = new Artist("George Thorogood");
		Song song = artist.addSong(new AlbumAndSongInfoBuilder(
				"Bad to the Bone").withTrackNumber(1).withDiscNumber(1)
				.withYear(1988).withGenre("Rock").withHasVideo(false)
				.withRating(100).withAlbumName("Anthology").withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withLibrary(library)
				.withDateAdded(new Date()).withSkipCount(0).withRating(80)
				.withPlayCount(12).withSong(song).build());
		return artist.getAlbums().iterator().next().getSongs().iterator()
				.next();
	}
}
