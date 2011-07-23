package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArtistHibernateIT {

	private static final String ARTIST_1_NAME = "Blue Öyster Cult";
	private static final String ARTIST_2_NAME = "Foghat";
	private static final String ARTIST_3_NAME = "The Edgar Winter Group";

	private Artist artist1;
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(AlbumHibernateIT.class.getSimpleName() + ".jar",
						JavaArchive.class)
				.addManifestResource(
						new File("src/test/resources/META-INF/persistence.xml"),
						ArchivePaths.create("persistence.xml"))
				.addClasses(Album.class, Artist.class);
	}

	@Before
	public void setup() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("testPu");
		entityManager = emf.createEntityManager();
	}

	@Test
	public void shouldHaveCorrectNumberOfArtists() {
		assertEquals("There should be no artists before none are created.",
				new Integer(0), numberOfArtists());
		createArtists();
		assertEquals("There should be three artists after three are created.",
				new Integer(3), numberOfArtists());
	}

	@Test(expected = PersistenceException.class)
	public void shouldNotAllowArtistWithDuplicateName() {
		Artist artist1 = new Artist(ARTIST_1_NAME);
		Artist artist2 = new Artist(ARTIST_1_NAME);

		persistArtist(artist1);
		persistArtist(artist2);
	}

	@Test
	public void shouldReturnArtistByName() {
		assertEquals("There should be no artists before none are created.",
				new Integer(0), numberOfArtists());
		createArtists();
		assertTrue(String.format("Should return artist [%s].", ARTIST_1_NAME),
				artistsContainByName(ARTIST_1_NAME));
	}

	@Test
	public void shouldDeleteOnlyOneArtistByName() {
		assertEquals("There should be no artists before none are created.",
				new Integer(0), numberOfArtists());
		createArtists();
		assertEquals("There should be three artists after three are created.",
				new Integer(3), numberOfArtists());
		assertTrue(String.format("Should return artist [%s].", ARTIST_1_NAME),
				artistsContainByName(ARTIST_1_NAME));
		deleteArtistByName(ARTIST_1_NAME);
		assertEquals("There should be two artists after one has been deleted.",
				new Integer(2), numberOfArtists());
		assertFalse(String.format(
				"Should not return artist [%s] after it was deleted.",
				ARTIST_1_NAME), artistsContainByName(ARTIST_1_NAME));
	}

	@Test
	public void shouldDeleteOnlyOneArtistByUuid() {
		assertEquals("There should be no artists before none are created.",
				new Integer(0), numberOfArtists());
		createArtists();
		assertEquals("There should be three artists after three are created.",
				new Integer(3), numberOfArtists());
		assertTrue(String.format("Should return artist [%s].", ARTIST_1_NAME),
				artistsContainByName(ARTIST_1_NAME));
		deleteArtistByUuid(artist1.getUuid());
		assertEquals("There should be two artists after one has been deleted.",
				new Integer(2), numberOfArtists());
		assertFalse(String.format(
				"Should not return artist [%s] after it was deleted.",
				ARTIST_1_NAME), artistsContainByName(ARTIST_1_NAME));
		assertFalse(String.format(
				"Should not return artist [%s] after it was deleted.",
				ARTIST_1_NAME), artistsContainByUuid(artist1.getUuid()));
	}

	@After
	public void clearTestData() {
		deleteAllArtists();
	}

	private boolean artistsContainByName(String artistName) {
		entityManager.getTransaction().begin();
		Query select = entityManager.createQuery(
				"FROM Artist WHERE name = :name").setParameter("name",
				artistName);
		boolean artistExists = select.getResultList().size() > 0;
		entityManager.getTransaction().commit();
		return artistExists;
	}

	private boolean artistsContainByUuid(String artistName) {
		entityManager.getTransaction().begin();
		Query select = entityManager.createQuery(
				"FROM Artist WHERE uuid = :uuid").setParameter("uuid",
				artistName);
		boolean artistExists = select.getResultList().size() > 0;
		entityManager.getTransaction().commit();
		return artistExists;
	}

	private void deleteAllArtists() {
		entityManager.getTransaction().begin();

		Query select = entityManager.createQuery("FROM Artist");
		List<?> allArtists = select.getResultList();
		for (Object artist : allArtists) {
			entityManager.remove(artist);
		}

		entityManager.getTransaction().commit();
	}

	private void deleteArtistByName(String artistName) {
		entityManager.getTransaction().begin();

		Query select = entityManager.createQuery(
				"FROM Artist WHERE name = :name").setParameter("name",
				artistName);
		Artist artist = (Artist) select.getResultList().get(0);
		entityManager.remove(artist);

		entityManager.getTransaction().commit();
	}

	private void deleteArtistByUuid(String artistUuid) {
		entityManager.getTransaction().begin();

		Query select = entityManager.createQuery(
				"FROM Artist WHERE uuid = :uuid").setParameter("uuid",
				artistUuid);
		Artist artist = (Artist) select.getResultList().get(0);
		entityManager.remove(artist);

		entityManager.getTransaction().commit();
	}

	private void createArtists() {
		artist1 = new Artist(ARTIST_1_NAME);
		Artist artist2 = new Artist(ARTIST_2_NAME);
		Artist artist3 = new Artist(ARTIST_3_NAME);

		persistArtist(artist1);
		persistArtist(artist2);
		persistArtist(artist3);
	}

	private void persistArtist(Artist artist1) {
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(artist1);
		} catch (PersistenceException pe) {
			entityManager.getTransaction().rollback();
			throw pe;
		}
		entityManager.getTransaction().commit();
	}

	private Integer numberOfArtists() {
		List<?> allArtists = entityManager.createQuery("FROM Artist")
				.getResultList();
		return allArtists.size();
	}
}
