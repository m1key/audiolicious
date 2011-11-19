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
