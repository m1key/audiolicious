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

package me.m1key.audiolicious.domain.entities;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NullAlbum;
import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.commons.qualifiers.NullLibrary;

@ApplicationScoped
public class NullEntitiesFactory {

	@Inject
	@NullArtist
	private Artist nullArtist;

	@Produces
	@ApplicationScoped
	@NullArtist
	public Artist getNullArtist() {
		return new Artist("(null artist");
	}

	@Produces
	@ApplicationScoped
	@NullAlbum
	public Album getNullAlbum() {
		return new Album("(null album)", nullArtist, new Rating(0));
	}

	@Produces
	@ApplicationScoped
	@NullLibrary
	public Library getNullLibrary() {
		return new Library("(null library");
	}
}
