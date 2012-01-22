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

package me.m1key.audiolicious.domain.to;

import java.util.Date;

import me.m1key.audiolicious.domain.entities.StatInfo;

public class ValuesBasedStatInfo implements StatInfo {

	private Date dateAdded;
	private Date dateModified;
	private int playCount;
	private int rating;
	private Date dateSkipped;
	private int skipCount;
	private String songUuid;

	public ValuesBasedStatInfo(String songUuid, Date dateAdded,
			Date dateModified, int playCount, int rating, Date dateSkipped,
			int skipCount) {
		super();
		this.songUuid = songUuid;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.playCount = playCount;
		this.rating = rating;
		this.dateSkipped = dateSkipped;
		this.skipCount = skipCount;
	}

	@Override
	public Date getDateAdded() {
		return dateAdded;
	}

	@Override
	public Date getDateModified() {
		return dateModified;
	}

	@Override
	public int getPlayCount() {
		return playCount;
	}

	@Override
	public RatingTo getRating() {
		return new RatingTo(rating);
	}

	@Override
	public Date getDateSkipped() {
		return dateSkipped;
	}

	@Override
	public int getSkipCount() {
		return skipCount;
	}

	@Override
	public String getSongUuid() {
		return songUuid;
	}

}
