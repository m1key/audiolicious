package me.m1key.audiolicious.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import me.m1key.audiolicious.web.to.LibraryTo;

@ApplicationScoped
@ManagedBean
public class LibrariesBean {

	private static final int LATEST_LIBRARIES_COUNT = 5;

	public LibraryTo getLatestLibrary() {
		return createDummyLibrary();
	}

	public List<LibraryTo> getLatestLibraries() {
		List<LibraryTo> dummyLibraries = new ArrayList<LibraryTo>();
		for (int i = 0; i < LATEST_LIBRARIES_COUNT; i++) {
			dummyLibraries.add(createDummyLibrary());
		}
		return dummyLibraries;
	}

	private LibraryTo createDummyLibrary() {
		LibraryTo dummyLibrary = new LibraryTo();
		dummyLibrary.setUuid(UUID.randomUUID().toString());
		dummyLibrary.setUploadTime(new Date());
		return dummyLibrary;
	}

}
