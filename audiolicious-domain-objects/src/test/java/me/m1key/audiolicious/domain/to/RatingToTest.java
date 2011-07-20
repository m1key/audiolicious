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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import me.m1key.audiolicious.domain.to.RatingTo;

import org.junit.Test;

public class RatingToTest {

	@Test
	public void testEquals() {
		for (int i = 0; i <= 100; i++) {
			RatingTo rating1 = new RatingTo(i);
			RatingTo rating2 = new RatingTo(i);
			assertEquals(rating1, rating2);
		}
	}

	@Test
	public void testNotEquals1() {
		for (int i = 0; i < 100; i++) {
			RatingTo rating1 = new RatingTo(i);
			RatingTo rating2 = new RatingTo(i + 1);
			assertFalse(rating1.equals(rating2));
		}
	}

	@Test
	public void testNotEquals2() {
		for (int i = 0; i < 100; i++) {
			RatingTo rating1 = new RatingTo(i + 1);
			RatingTo rating2 = new RatingTo(i);
			assertFalse(rating1.equals(rating2));
		}
	}
}
