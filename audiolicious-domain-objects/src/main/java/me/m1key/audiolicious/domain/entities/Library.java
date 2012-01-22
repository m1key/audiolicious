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

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity(name = "Library")
@Table(name = "LIBRARIES")
public class Library {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	@Column(name = "LIBRARY_ID")
	private Long id;

	@Column(name = "UUID", unique = true, length = 36)
	private String uuid;

	@Column(name = "DATE_ADDED")
	private Date dateAdded;

	@OneToMany(mappedBy = "library", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Stat> stats;

	// For proxying.
	protected Library() {
	}

	// Only used by NullEntitesFactory.
	Library(String uuid) {
		this.uuid = uuid;
	}

	public Library(Date dateAdded) {
		stats = new HashSet<Stat>();

		this.uuid = UUID.randomUUID().toString();
		this.dateAdded = dateAdded;
	}

	public String getUuid() {
		return uuid;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void addStat(Stat stat) {
		// stat.setLibrary(this);
		stats.add(stat);
	}

	public Set<Stat> getStats() {
		return Collections.unmodifiableSet(stats);
	}

	void clearStats() {
		stats.clear();
	}

	public void addStat(StatInfo defaultStatInfo) {
		stats.add(new Stat(this, defaultStatInfo.getSongUuid(), defaultStatInfo
				.getDateAdded(), defaultStatInfo.getDateModified(),
				defaultStatInfo.getDateSkipped(), defaultStatInfo
						.getSkipCount(),
				new Rating(defaultStatInfo.getRating()), defaultStatInfo
						.getPlayCount()));
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(uuid).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Library))
			return false;
		Library castOther = (Library) other;
		return new EqualsBuilder().append(uuid, castOther.uuid).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("uuid", uuid)
				.append("dateAdded", dateAdded).toString();
	}
}
