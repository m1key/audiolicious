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
import me.m1key.audiolicious.libraryparser.VtdItunesLibraryParserCdiAlternative;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.RawTrackDataHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.factories.TrackHandlersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.SongHandlerCdiAlternative;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProviderCdiAlternative;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractorCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.PodcastMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.SongMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.VideoMapperCdiAlternative;
import me.m1key.audiolicious.services.DefaultSongServiceCdiAlternative;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
	private DefaultObjectTrackDataHandlerCdiAlternative handler;
	@Inject
	private JpaSongRepositoryCdiAlternative songRepository;
	@Inject
	private JpaAlbumRepositoryCdiAlternative albumRepository;
	@Inject
	private JpaArtistRepositoryCdiAlternative artistRepository;

	private EntityManager entityManager;

	@Deployment
	public static JavaArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(JavaArchive.class,
						MacOsLibraryWithServiceIT.class.getSimpleName()
								+ ".jar")
				.addAsManifestResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addClasses(AudiobookMapperCdiAlternative.class,
						DefaultEnglishValuesProviderCdiAlternative.class,
						DefaultObjectTrackDataHandlerCdiAlternative.class,
						DefaultSongServiceCdiAlternative.class,
						I18nDataExtractorCdiAlternative.class,
						JpaAlbumRepositoryCdiAlternative.class, JpaArtistRepositoryCdiAlternative.class,
						JpaSongRepositoryCdiAlternative.class, NoopTrackHandlerCdiAlternative.class,
						NullEntitiesFactory.class,
						PodcastMapperCdiAlternative.class,
						RawTrackDataHandlerCdiAlternative.class, SongHandlerCdiAlternative.class,
						SongMapperCdiAlternative.class,
						TrackHandlersFactoryCdiAlternative.class, TrackMappersFactoryCdiAlternative.class,
						VideoMapperCdiAlternative.class,
						VtdItunesLibraryParserCdiAlternative.class);
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
