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

import java.util.Date;

import me.m1key.audiolicious.domain.entities.StatInfo;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;

public class ToBasedStatInfo implements StatInfo {

	private SongTo songTo;
	private String songUuid;

	public ToBasedStatInfo(SongTo songTo, String songUuid) {
		if (songTo == null) {
			throw new IllegalArgumentException(
					"Null SongTo passed to DefaultStatInfo constructor.");
		}
		if (songUuid == null) {
			throw new IllegalArgumentException(
					"Null uuid passed to DefaultStatInfo constructor.");
		}

		this.songTo = songTo;
		this.songUuid = songUuid;
	}

	@Override
	public Date getDateAdded() {
		return songTo.getDateAdded();
	}

	@Override
	public Date getDateModified() {
		return songTo.getDateModified();
	}

	@Override
	public int getPlayCount() {
		return songTo.getPlayCount();
	}

	@Override
	public RatingTo getRating() {
		return songTo.getRating();
	}

	@Override
	public Date getDateSkipped() {
		return songTo.getSkipDate();
	}

	@Override
	public int getSkipCount() {
		return songTo.getSkipCount();
	}

	@Override
	public String getSongUuid() {
		return songUuid;
	}

}
