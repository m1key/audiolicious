package me.m1key.audiolicious.web.beans;

import java.io.File;

import javax.ejb.Local;
import javax.ejb.Stateful;

import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.libraryparser.LibraryParser;

@Stateful
@Local(LibraryParser.class)
public class StubLibraryParser implements LibraryParser {

	@Override
	public void process(File libraryFile, Library library) {
	}

}
