package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;

public class StatTest {

	@Test
	public void testNotEquals() {
		Library library = new Library();
		Song song = new Song();
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

		assertFalse("Stats should be different because "
				+ "they have different UUIDs.", stat1.equals(stat2));
	}
}
