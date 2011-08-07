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

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.commons.qualifiers.AggregateMapper;
import me.m1key.audiolicious.commons.qualifiers.NoopHandler;
import me.m1key.audiolicious.commons.qualifiers.NullAlbum;
import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.domain.entities.Album;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.NullEntitiesFactory;
import me.m1key.audiolicious.domain.entities.Rating;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.TrackToType;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.libraryparser.LibraryParser;
import me.m1key.audiolicious.libraryparser.RawTrackDataCallback;
import me.m1key.audiolicious.libraryparser.VtdItunesLibraryParserCdiAlternative;
import me.m1key.audiolicious.libraryparser.XmlParseException;
import me.m1key.audiolicious.objecthandler.DefaultObjectTrackDataHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.ObjectTrackDataHandler;
import me.m1key.audiolicious.objecthandler.RawTrackDataHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.factories.TrackHandlersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.factories.TrackMappersFactoryCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.NoopTrackHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.SongHandlerCdiAlternative;
import me.m1key.audiolicious.objecthandler.handlers.SongService;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.CannotMapTrackValuesException;
import me.m1key.audiolicious.objectmapper.ObjectMappingException;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.extractor.DataExtractor;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProviderCdiAlternative;
import me.m1key.audiolicious.objectmapper.extractor.EnglishValuesProvider;
import me.m1key.audiolicious.objectmapper.extractor.I18nDataExtractorCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.AudiobookMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.NonAggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.PodcastMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.SongMapperCdiAlternative;
import me.m1key.audiolicious.objectmapper.trackmappers.VideoMapperCdiAlternative;
import me.m1key.audiolicious.services.AlbumRepository;
import me.m1key.audiolicious.services.ArtistRepository;
import me.m1key.audiolicious.services.DefaultSongService;
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
	private TestHelperBean testHelperBean;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						MacOsLibraryWithServiceIT.class.getSimpleName()
								+ ".war")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsResource(
						new File(
								"../audiolicious-object-mappers/src/main/resources/englishValues.properties"),
						"englishValues.properties")
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("log4j.xml", "log4j.xml")
				.addClasses(AggregateMapper.class,
						AggregateTrackMapperCdiAlternative.class, Album.class,
						AlbumRepository.class, Artist.class,
						ArtistRepository.class, AudiobookTo.class,
						AudiobookMapperCdiAlternative.class,
						CannotMapTrackValuesException.class,
						DataExtractor.class,
						DefaultEnglishValuesProviderCdiAlternative.class,
						DefaultObjectTrackDataHandlerCdiAlternative.class,
						DefaultSongService.class, EnglishValuesProvider.class,
						I18nDataExtractorCdiAlternative.class,
						JpaAlbumRepository.class, JpaArtistRepository.class,
						JpaSongRepository.class, LibraryParser.class,
						NonAggregateTrackMapper.class, NoopHandler.class,
						NoopTrackHandlerCdiAlternative.class, NullAlbum.class,
						NullArtist.class, NullEntitiesFactory.class,
						ObjectMappingException.class,
						ObjectTrackDataHandler.class,
						PodcastMapperCdiAlternative.class, PodcastTo.class,
						Rating.class, RatingTo.class,
						RawTrackDataCallback.class,
						RawTrackDataHandlerCdiAlternative.class, Song.class,
						SongHandlerCdiAlternative.class,
						SongMapperCdiAlternative.class, SongRepository.class,
						SongService.class, SongTo.class, TestHelperBean.class,
						TrackHandler.class,
						TrackHandlersFactoryCdiAlternative.class,
						TrackMapper.class,
						TrackMappersFactoryCdiAlternative.class, TrackTo.class,
						TrackToType.class, VideoMapperCdiAlternative.class,
						VideoTo.class,
						VtdItunesLibraryParserCdiAlternative.class,
						XmlNodeName.class, XmlParseException.class)
				.addAsLibraries(
						DependencyResolvers
								.use(MavenDependencyResolver.class)
								.artifacts("com.ximpleware:vtd-xml:2.10",
										"joda-time:joda-time:1.6.2",
										"org.slf4j:slf4j-api:1.6.1",
										"org.slf4j:slf4j-log4j12:1.6.1",
										"commons-lang:commons-lang:2.6")
								.resolveAsFiles());
	}

	@Before
	public void setUp() throws Exception {
		assertEquals("There should be no artists before the test.",
				new Long(0), testHelperBean.totalArtists());

		File xmlLibraryFile = new File(pathToFile);
		handler.execute(xmlLibraryFile);
	}

	@Test
	public void testCorrectNumberOfEverything() {
		assertEquals("There should be the right number of artists in the DB.",
				TOTAL_ARTISTS, testHelperBean.totalArtists());
		assertEquals("There should be the right number of albums in the DB.",
				TOTAL_ALBUMS, testHelperBean.totalAlbums());
		assertEquals("There should be the right number of songs in the DB.",
				new Long(TOTAL_SONGS + TOTAL_VIDEOS),
				testHelperBean.totalSongs());
	}

	@After
	public void cleanup() {
		testHelperBean.deleteAllArtists();
	}
}
