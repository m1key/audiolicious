package me.m1key.audiolicious.services;

import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.entities.Library;

@Singleton
@Local(LibraryService.class)
public class StubLibraryService implements LibraryService {

	private Library library = new Library(new Date());

	@Override
	public Library createLibrary() {
		return library;
	}

	@Override
	public Library getByUuid(String libraryUuid) {
		return library;
	}

}
