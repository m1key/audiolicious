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

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.entities.Library;

@Singleton
@Local(LibraryService.class)
public class DefaultLibraryService implements LibraryService {

	@EJB
	private LibraryRepository libraryRepository;

	@Override
	public Library createLibrary() {
		Library library = new Library(new Date());
		libraryRepository.save(library);
		return library;
	}

	@Override
	public Library getByUuid(String libraryUuid) {
		return libraryRepository.getByUuid(libraryUuid);
	}

}
