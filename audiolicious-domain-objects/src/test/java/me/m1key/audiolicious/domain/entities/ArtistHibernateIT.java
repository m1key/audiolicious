package me.m1key.audiolicious.domain.entities;

import static org.junit.Assert.assertEquals;

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
		assertEquals("There should be two artists after three are created.",
				new Integer(3), numberOfArtists());
	}

	@Test(expected = PersistenceException.class)
	public void shouldNotAllowArtistWithDuplicateName() {
		Artist artist1 = new Artist(ARTIST_1_NAME);
		Artist artist2 = new Artist(ARTIST_1_NAME);

		persistArtist(artist1);
		persistArtist(artist2);
	}

	@After
	public void clearTestData() {
		deleteAllArtists();
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

	private void createArtists() {
		Artist artist1 = new Artist(ARTIST_1_NAME);
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
