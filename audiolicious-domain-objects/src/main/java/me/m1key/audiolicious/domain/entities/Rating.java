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

import javax.persistence.Column;
import javax.persistence.Embeddable;

import me.m1key.audiolicious.domain.to.RatingTo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Embeddable
public class Rating {

	private static final int MAX_STARS = 5;
	private static final int ONE_STAR_STEP = 100 / MAX_STARS;

	@Column(name = "PERCENTAGE")
	private int percentage;

	// For proxying.
	protected Rating() {
	}

	public Rating(int percentage) {
		this.percentage = percentage;
	}

	public Rating(RatingTo albumRating) {
		this.percentage = albumRating.getPercentage();
	}

	public int getStars() {
		return percentage / ONE_STAR_STEP;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(percentage).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Rating))
			return false;
		Rating castOther = (Rating) other;
		return new EqualsBuilder().append(percentage, castOther.percentage)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("percentage", percentage)
				.toString();
	}

}
