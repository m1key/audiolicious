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

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;
import me.m1key.audiolicious.libraryparser.VtdItunesLibraryParser;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.RawTrackDataHandler;
import me.m1key.audiolicious.objecthandler.factories.TrackHandlersFactory;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactory;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.SongHandler;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractor;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.PodcastMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.SongMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.VideoMapper;
import me.m1key.audiolicious.services.DefaultSongService;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MacOsLibraryWithServiceIT {

	private static final Long TOTAL_ARTISTS = new Long(449);
	private static final Long TOTAL_ALBUMS = new Long(704);
	private static final Long TOTAL_SONGS = new Long(10732);
	private static final Long TOTAL_VIDEOS = new Long(6);
	private static final String pathToFile = "../audiolicious-test-data/src/test/resources/libraries/MacOsExportedLibrary-2011-07-28.xml";

	@Inject
	private DefaultObjectTrackDataHandler handler;
	@Inject
	private JpaSongRepository songRepository;
	@Inject
	private JpaAlbumRepository albumRepository;
	@Inject
	private JpaArtistRepository artistRepository;

	private EntityManager entityManager;

	@Deployment
	public static JavaArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(MacOsLibraryWithServiceIT.class.getSimpleName()
						+ ".jar", JavaArchive.class)
				.addManifestResource(new ByteArrayAsset(new byte[0]),
						ArchivePaths.create("beans.xml"))
				.addClasses(AudiobookMapper.class,
						DefaultEnglishValuesProvider.class,
						DefaultObjectTrackDataHandler.class,
						DefaultSongService.class, I18nDataExtractor.class,
						JpaAlbumRepository.class, JpaArtistRepository.class,
						JpaSongRepository.class, NoopTrackHandler.class,
						NullEntitiesFactory.class, PodcastMapper.class,
						RawTrackDataHandler.class, SongHandler.class,
						SongMapper.class, TrackHandlersFactory.class,
						TrackMappersFactory.class, VideoMapper.class,
						VtdItunesLibraryParser.class);
	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("testPu");
		entityManager = emf.createEntityManager();

		songRepository.setEntityManager(entityManager);
		albumRepository.setEntityManager(entityManager);
		artistRepository.setEntityManager(entityManager);

		entityManager.getTransaction().begin();
		File xmlLibraryFile = new File(pathToFile);
		handler.execute(xmlLibraryFile);
		entityManager.getTransaction().commit();
	}

	@Test
	public void testCorrectNumberOfEverything() {
		assertEquals("There should be the right number of artists in the DB.",
				TOTAL_ARTISTS, totalArtists());
		assertEquals("There should be the right number of albums in the DB.",
				TOTAL_ALBUMS, totalAlbums());
		assertEquals("There should be the right number of songs in the DB.",
				new Long(TOTAL_SONGS + TOTAL_VIDEOS), totalSongs());
	}

	private Long totalArtists() {
		return total("Artist");
	}

	private Long total(String entityName) {
		entityManager.getTransaction().begin();
		Object howMany = entityManager.createQuery(
				String.format("SELECT COUNT(id) FROM %s", entityName))
				.getSingleResult();
		entityManager.getTransaction().commit();
		return (Long) howMany;
	}

	private Long totalAlbums() {
		return total("Album");
	}

	private Long totalSongs() {
		return total("Song");
	}
}
