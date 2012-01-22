/* 
 * Audiolicious - Your Music Library Statistics
 * Copyright (C) 2012, Michal Huniewicz
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

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;

@Singleton
@Local(ApplicationConversation.class)
public class DefaultApplicationConversation implements ApplicationConversation {

	private Map<String, Map<String, Artist>> artistCachePerLibrary = new HashMap<String, Map<String, Artist>>();

	@Override
	public void startConversation(Library library) {
		Map<String, Artist> artistCache = new HashMap<String, Artist>();
		artistCachePerLibrary.put(library.getUuid(), artistCache);
	}

	@Override
	public void endConversation(Library library) {
		artistCachePerLibrary.remove(library.getUuid());
	}

	@Override
	public Map<String, Artist> getArtistCache(Library library) {
		return artistCachePerLibrary.get(library.getUuid());
	}

}
