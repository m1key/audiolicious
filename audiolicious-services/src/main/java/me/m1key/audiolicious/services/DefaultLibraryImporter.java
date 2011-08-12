package me.m1key.audiolicious.services;

import java.io.File;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import me.m1key.audiolicious.libraryparser.LibraryParser;

@Stateless
@Local(LibraryImporter.class)
public class DefaultLibraryImporter implements LibraryImporter {

	@EJB
	private LibraryParser libraryParser;

	@Override
	public void importLibrary(File libraryFile) {
		libraryParser.process(libraryFile);
	}

}
