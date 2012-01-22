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

package me.m1key.audiolicious.domain.to;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import me.m1key.audiolicious.domain.entities.Library;

public class LibraryTo {

	private String uuid;
	private Date dateAdded;

	public LibraryTo(Library library) {
		this.uuid = library.getUuid();
		this.dateAdded = library.getDateAdded();
	}

	public String getUuid() {
		return uuid;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode())
				.append(getUuid()).append(getDateAdded()).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof LibraryTo))
			return false;
		LibraryTo castOther = (LibraryTo) other;
		return new EqualsBuilder().append(getUuid(), castOther.getUuid())
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
				.append("uuid", getUuid()).append("dateAdded", getDateAdded())
				.toString();
	}

}
