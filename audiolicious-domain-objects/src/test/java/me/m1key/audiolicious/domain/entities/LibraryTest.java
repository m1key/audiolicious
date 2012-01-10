package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import me.m1key.audiolicious.domain.to.AlbumAndSongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

import org.junit.Test;

public class LibraryTest {

	@Test
	public void testEquals() {
		Date dateAdded = new Date();
		Library library1 = new Library(dateAdded);
		Library library2 = new Library(dateAdded);

		assertFalse("Two libraries created in the same moment "
				+ "should not be equal.", library1.equals(library2));
	}

	@Test
	public void testAddStat() {
		Library library = new Library(new Date());

		Artist artist = new Artist("George Thorogood");
		artist.addSong(
				new AlbumAndSongInfoBuilder("Bad to the Bone")
						.withTrackNumber(1).withDiscNumber(1).withYear(1988)
						.withGenre("Rock").withHasVideo(false).withRating(100)
						.withAlbumName("Anthology").withAlbumRating(80).build(),
				new StatInfoBuilder().withLibrary(library)
						.withDateAdded(new Date()).withSkipCount(0)
						.withRating(80).withPlayCount(12).build());
		Song song = artist.getAlbums().iterator().next().getSongs().iterator()
				.next();

		Stat stat = new Stat(library, song, new Date(), new Date(), new Date(),
				0, new Rating(100), 10);
		library.addStat(stat);

		assertTrue("Library should contain stat after stat added.", library
				.getStats().contains(stat));
	}

}
