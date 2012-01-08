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

package me.m1key.audiolicious.services;

import java.io.File;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.to.LibraryTo;
import me.m1key.audiolicious.libraryparser.LibraryParser;

@Singleton
@Local(LibraryImporter.class)
public class DefaultLibraryImporter implements LibraryImporter {

	@EJB
	private LibraryParser libraryParser;
	@EJB
	private LibraryService libraryService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public LibraryTo importLibrary(File libraryFile) {
		Library library = libraryService.createLibrary();
		libraryParser.process(libraryFile, library);
		return new LibraryTo(library);
	}

}
