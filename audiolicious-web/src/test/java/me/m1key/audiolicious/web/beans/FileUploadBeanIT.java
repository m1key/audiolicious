package me.m1key.audiolicious.web.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@RunWith(Arquillian.class)
public class FileUploadBeanIT {

	@Inject
	private FileUploadBean uploadBean;
	@Inject
	private StubConsumptionBean stubConsumptionBean;
	private FileUploadEvent fileUploadEvent;

	@Deployment
	public static WebArchive createTestArchive()
			throws IllegalArgumentException, IOException {
		return ShrinkWrap
				.create(WebArchive.class,
						FileUploadBeanIT.class.getSimpleName() + ".war")
				.addAsWebInfResource(
						new File("src/test/resources/WEB-INF/beans.xml"),
						ArchivePaths.create("beans.xml"))
				.addClasses(ConsumptionBean.class, FileUploadBean.class,
						FileUploadEvent.class, StubConsumptionBean.class,
						UploadedFile.class)
				.addAsLibraries(
						DependencyResolvers.use(MavenDependencyResolver.class)
								.artifacts("org.slf4j:slf4j-api:1.6.1")
								.resolveAsFiles());
	}

	@Before
	public void setup() {
		UIComponent component = new UIPanel();
		fileUploadEvent = new FileUploadEvent(component, null);
	}

	@Test
	public void shouldCallConsumptionBean() throws Exception {
		assertFalse(stubConsumptionBean.hasConsumeBeenCalled());
		uploadBean.listener(fileUploadEvent);
		assertTrue(stubConsumptionBean.hasConsumeBeenCalled());
	}

}
