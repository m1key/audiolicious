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
import static org.junit.Assert.assertFalse;

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
		String songUuid = artist.addSong(new AlbumAndSongInfoBuilder(
				"Bad to the Bone").withTrackNumber(1).withDiscNumber(1)
				.withYear(1988).withGenre("Rock").withHasVideo(false)
				.withRating(100).withAlbumName("Anthology").withAlbumRating(80)
				.build());
		library.addStat(new StatInfoBuilder().withDateAdded(new Date())
				.withSkipCount(0).withRating(80).withPlayCount(12)
				.withSongUuid(songUuid).build());
		Song song = artist.getAlbums().iterator().next().getSongs().iterator()
				.next();

		assertEquals("Library should contain stat after stat added.", 1,
				library.getStats().size());
		library.addStat(new StatInfoBuilder().withDateAdded(new Date())
				.withDateModified(new Date()).withDateSkipped(new Date())
				.withSkipCount(0).withRating(100).withPlayCount(10)
				.withSongUuid(song.getUuid()).build());

		assertEquals(
				"Library should contain stat after stat added for the same song.",
				1, library.getStats().size());
	}

}
