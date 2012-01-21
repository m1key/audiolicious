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

import org.junit.Test;

public class ArtistTest {

	@Test
	public void testEquals() {
		Artist artist1 = new Artist("Dio");
		Artist artist2 = new Artist("Dio");

		assertEquals(artist1, artist2);
	}

	@Test
	public void testNotEquals() {
		Artist artist1 = new Artist("Dio");
		Artist artist2 = new Artist("Black Sabbath");

		assertFalse(artist1.equals(artist2));
	}

	@Test
	public void testAddSongOneAlbum() {
		Artist artist = new Artist("Dio");
		assertEquals("Artist should have no albums when it has just"
				+ " been created.", 0, artist.getAlbums().size());

		artist.addSong(SampleData.album1Song1Info());

		assertEquals("Artist should have one album when one song added.", 1,
				artist.getAlbums().size());

		Album justAddedAlbum = artist.getAlbums().iterator().next();
		verifyAlbum1(artist, justAddedAlbum);
		Song justAddedSong = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum, "Stand Up and Shout");
		verifyAlbum1Song1(justAddedAlbum, justAddedSong);
	}

	@Test
	public void testAddSongOneAlbumTwice() {
		Artist artist = new Artist("Dio");
		assertEquals("Artist should have no albums when it has just"
				+ " been created.", 0, artist.getAlbums().size());

		artist.addSong(SampleData.album1Song1Info());
		artist.addSong(SampleData.album1Song1Info());

		assertEquals("Artist should have one album when one song added.", 1,
				artist.getAlbums().size());

		Album justAddedAlbum = artist.getAlbums().iterator().next();
		verifyAlbum1(artist, justAddedAlbum);
		Song justAddedSong = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum, "Stand Up and Shout");
		verifyAlbum1Song1(justAddedAlbum, justAddedSong);

		assertEquals("Album should only one song when one song "
				+ "was added twice.", 1, justAddedAlbum.getSongs().size());
	}

	@Test
	public void testAddTwoSongsOneAlbum() {
		Artist artist = new Artist("Dio");
		assertEquals("Artist should have no albums when it has just"
				+ " been created.", 0, artist.getAlbums().size());

		artist.addSong(SampleData.album1Song1Info());
		artist.addSong(SampleData.album1Song2Info());

		assertEquals(
				"Artist should have one album when two song from same album added.",
				1, artist.getAlbums().size());

		Album justAddedAlbum = artist.getAlbums().iterator().next();
		verifyAlbum1(artist, justAddedAlbum);
		Song justAddedSong1 = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum, "Stand Up and Shout");
		verifyAlbum1Song1(justAddedAlbum, justAddedSong1);
		Song justAddedSong2 = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum, "Holy Diver");
		verifyAlbum1Song2(justAddedAlbum, justAddedSong2);
	}

	@Test
	public void testAddFourSongsTwoAlbums() {
		Artist artist = new Artist("Dio");
		assertEquals("Artist should have no albums when it has just"
				+ " been created.", 0, artist.getAlbums().size());

		artist.addSong(SampleData.album1Song1Info());
		artist.addSong(SampleData.album1Song2Info());
		artist.addSong(SampleData.album2Song1Info());
		artist.addSong(SampleData.album2Song2Info());

		assertEquals(
				"Artist should have two album when four song from two albums added.",
				2, artist.getAlbums().size());

		Album justAddedAlbum1 = DomainObjectsTestHelper.getAlbumByName(artist,
				"Holy Diver");
		assertEquals("Album should have two songs when two songs added.", 2,
				justAddedAlbum1.getSongs().size());
		verifyAlbum1(artist, justAddedAlbum1);
		Song justAddedAlbum1Song1 = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum1, "Stand Up and Shout");
		verifyAlbum1Song1(justAddedAlbum1, justAddedAlbum1Song1);
		Song justAddedAlbum1Song2 = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum1, "Holy Diver");
		verifyAlbum1Song2(justAddedAlbum1, justAddedAlbum1Song2);

		Album justAddedAlbum2 = DomainObjectsTestHelper.getAlbumByName(artist,
				"Strange Highways");
		assertEquals("Album should have two songs when two songs added.", 2,
				justAddedAlbum2.getSongs().size());
		verifyAlbum2(artist, justAddedAlbum2);
		Song justAddedAlbum2Song1 = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum2, "Jesus, Mary & the Holy Ghost");
		verifyAlbum2Song1(justAddedAlbum2, justAddedAlbum2Song1);
		Song justAddedAlbum2Song2 = DomainObjectsTestHelper.getSongByName(
				justAddedAlbum2, "Firehead");
		verifyAlbum2Song2(justAddedAlbum2, justAddedAlbum2Song2);
	}

	private void verifyAlbum1(Artist artist, Album album1) {
		assertEquals("Album artist should equal artist it was added to.",
				artist, album1.getArtist());
		assertEquals("Album name should be set to correct value.",
				"Holy Diver", album1.getName());
		assertEquals("Album rating should be set to correct value.",
				new Rating(80), album1.getRating());
		assertNotNull("Album UUID should be set.", album1.getUuid());
	}

	private void verifyAlbum2(Artist artist, Album album2) {
		assertEquals("Album artist should equal artist it was added to.",
				artist, album2.getArtist());
		assertEquals("Album name should be set to correct value.",
				"Strange Highways", album2.getName());
		assertEquals("Album rating should be set to correct value.",
				new Rating(80), album2.getRating());
		assertNotNull("Album UUID should be set.", album2.getUuid());
	}

	private void verifyAlbum1Song1(Album album1, Song song1) {
		assertEquals("Song album should equal album it was added to.", album1,
				song1.getAlbum());
		assertEquals("Song name should be correct.", "Stand Up and Shout",
				song1.getName());
		assertEquals("Song artist name should be correct.", "Dio",
				song1.getArtistName());
		assertEquals("Song genre should be correct.", "Rock", song1.getGenre());
		assertNotNull("Song UUID should be set.", song1.getUuid());
		assertEquals("Song year should be correct.", 1983, song1.getYear());
	}

	private void verifyAlbum1Song2(Album album1, Song song2) {
		assertEquals("Song album should equal album it was added to.", album1,
				song2.getAlbum());
		assertEquals("Song name should be correct.", "Holy Diver",
				song2.getName());
		assertEquals("Song artist name should be correct.", "Dio",
				song2.getArtistName());
		assertEquals("Song genre should be correct.", "Rock", song2.getGenre());
		assertNotNull("Song UUID should be set.", song2.getUuid());
		assertEquals("Song year should be correct.", 1983, song2.getYear());
	}

	private void verifyAlbum2Song1(Album album2, Song song1) {
		assertEquals("Song album should equal album it was added to.", album2,
				song1.getAlbum());
		assertEquals("Song name should be correct.",
				"Jesus, Mary & the Holy Ghost", song1.getName());
		assertEquals("Song artist name should be correct.", "Dio",
				song1.getArtistName());
		assertEquals("Song genre should be correct.", "Rock", song1.getGenre());
		assertNotNull("Song UUID should be set.", song1.getUuid());
		assertEquals("Song year should be correct.", 1994, song1.getYear());
	}

	private void verifyAlbum2Song2(Album album2, Song song2) {
		assertEquals("Song album should equal album it was added to.", album2,
				song2.getAlbum());
		assertEquals("Song name should be correct.", "Firehead",
				song2.getName());
		assertEquals("Song artist name should be correct.", "Dio",
				song2.getArtistName());
		assertEquals("Song genre should be correct.", "Rock", song2.getGenre());
		assertNotNull("Song UUID should be set.", song2.getUuid());
		assertEquals("Song year should be correct.", 1994, song2.getYear());
	}
}
