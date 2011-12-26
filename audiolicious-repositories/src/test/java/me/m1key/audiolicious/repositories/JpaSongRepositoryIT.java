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

package me.m1key.audiolicious.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NullAlbum;
import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.commons.qualifiers.NullLibrary;
import me.m1key.audiolicious.commons.qualifiers.NullSong;
import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;
import me.m1key.audiolicious.domain.entities.Rating;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.domain.entities.Stat;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.services.SongRepository;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JpaSongRepositoryIT {

	private static final String ARTIST_NAME = "Ozzy Osbourne";
	private static final String ALBUM_NAME = "No Rest for the Wicked";
	private static final String SONG_1_NAME = "Miracle Man";
	private static final String SONG_2_NAME = "Devil's Daughter (Holy War) [Album Version]";
	private static final String SONG_3_NAME = "Crazy Babies";
	private static final String SONG_4_NAME = "Breakin' All The Rules";
	private static final String SONG_5_NAME = "Bloodbath In Paradise [Album Version]";
	private static final String SONG_6_NAME = "Fire In the Sky";
	private static final String SONG_7_NAME = "Tattooed Dancer [Album Version]";
	private static final String SONG_8_NAME = "Demon Alcohol";
	private static final String SONG_9_NAME = "Hero [Album Version]";
	private static final String SONG_10_NAME = "The Liar";
	private static final String SONG_11_NAME = "Miracle Man [Live]";

	@Inject
	private SongRepository jpaSongRepository;
	@Inject
	private RepositoriesTestHelperBean testHelperBean;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						JpaSongRepositoryIT.class.getSimpleName() + ".war")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsResource("log4j.xml", "log4j.xml")
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addClasses(Album.class, Artist.class, JpaSongRepository.class,
						Library.class, NullAlbum.class, NullArtist.class,
						NullEntitiesFactory.class, NullLibrary.class,
						NullSong.class, Rating.class, RatingTo.class,
						Song.class, SongRepository.class, SongTo.class,
						Stat.class, RepositoriesTestHelperBean.class,
						TrackTo.class)
				.addAsLibraries(
						DependencyResolvers
								.use(MavenDependencyResolver.class)
								.artifacts("org.slf4j:slf4j-api:1.6.1",
										"org.slf4j:slf4j-log4j12:1.6.1",
										"commons-lang:commons-lang:2.6")
								.resolveAsFiles());
	}

	@Test
	public void shouldCreateAndRetrieveSong() {
		assertEquals("There should be no songs before any are created.",
				Long.valueOf(0), testHelperBean.totalSongs());

		Artist artist = testHelperBean.createArtist(ARTIST_NAME);
		Album album = testHelperBean.createAlbum(ALBUM_NAME, artist,
				new Rating(80));
		Song song = new Song(SONG_1_NAME, ALBUM_NAME, ARTIST_NAME, 1, 1, album,
				1988, "Zakk Wylde/Bob Daisley/Ozzy Osbourne", "Rock", false, 0,
				0, false, 100);
		jpaSongRepository.save(song);

		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song.getUuid()));
	}

	@Test
	public void shouldCreateAndRetrieveAllSongs() {
		assertEquals("There should be no songs before any are created.",
				Long.valueOf(0), testHelperBean.totalSongs());
		assertEquals("There should be no artists before any are created.",
				Long.valueOf(0), testHelperBean.totalArtists());

		Artist artist = testHelperBean.createArtist(ARTIST_NAME);
		Album album = testHelperBean.createAlbum(ALBUM_NAME, artist,
				new Rating(80));
		Song song01 = new Song(SONG_1_NAME, ALBUM_NAME, ARTIST_NAME, 1, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song02 = new Song(SONG_2_NAME, ALBUM_NAME, ARTIST_NAME, 2, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song03 = new Song(SONG_3_NAME, ALBUM_NAME, ARTIST_NAME, 3, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song04 = new Song(SONG_4_NAME, ALBUM_NAME, ARTIST_NAME, 4, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song05 = new Song(SONG_5_NAME, ALBUM_NAME, ARTIST_NAME, 5, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song06 = new Song(SONG_6_NAME, ALBUM_NAME, ARTIST_NAME, 6, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song07 = new Song(SONG_7_NAME, ALBUM_NAME, ARTIST_NAME, 7, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song08 = new Song(SONG_8_NAME, ALBUM_NAME, ARTIST_NAME, 8, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song09 = new Song(SONG_9_NAME, ALBUM_NAME, ARTIST_NAME, 9, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song10 = new Song(SONG_10_NAME, ALBUM_NAME, ARTIST_NAME, 10, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		Song song11 = new Song(SONG_11_NAME, ALBUM_NAME, ARTIST_NAME, 11, 1,
				album, 1991, "", "Rock", false, 0, 0, false, 100);
		jpaSongRepository.save(song01);
		jpaSongRepository.save(song02);
		jpaSongRepository.save(song03);
		jpaSongRepository.save(song04);
		jpaSongRepository.save(song05);
		jpaSongRepository.save(song06);
		jpaSongRepository.save(song07);
		jpaSongRepository.save(song08);
		jpaSongRepository.save(song09);
		jpaSongRepository.save(song10);
		jpaSongRepository.save(song11);

		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song01.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song02.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song03.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song04.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song05.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song06.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song07.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song08.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song09.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song10.getUuid()));
		assertNotNull("Saved song should not be null.",
				testHelperBean.getSongByUuid(song11.getUuid()));
	}

	@After
	public void clearTestData() {
		testHelperBean.deleteAllArtists();
	}

}
