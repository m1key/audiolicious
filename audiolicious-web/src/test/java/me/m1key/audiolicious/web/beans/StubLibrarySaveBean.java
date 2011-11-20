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

import java.io.File;
import java.io.InputStream;

import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

@Alternative
@Singleton
public class StubLibrarySaveBean extends LibrarySaveBean {

	private boolean hasSaveBeenCalled;

	@Override
	public File save(InputStream inputStream, String targetFileName) {
		hasSaveBeenCalled = true;
		return null;
	}

	boolean hasSaveBeenCalled() {
		return hasSaveBeenCalled;
	}

}
