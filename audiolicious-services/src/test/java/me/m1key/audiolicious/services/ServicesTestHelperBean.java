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

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;

import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.domain.entities.Stat;

@Stateless
public class ServicesTestHelperBean {

	public Set<Stat> getStats(Song song, Library library) {
		Set<Stat> stats = new HashSet<Stat>();
		System.out.println("!!! " + library);
		for (Stat stat : library.getStats()) {
			if (song.getUuid().equals(stat.getSongUuid())) {
				stats.add(stat);
			}
		}
		return stats;
	}

}
