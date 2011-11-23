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

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

public class LibraryToTest {

	private static final String RANDOM_UUID = UUID.randomUUID().toString();
	private static final String RANDOM_UUID12 = UUID.randomUUID().toString();

	@Test
	public void testEquals1() {
		LibraryTo libraryTo1 = new LibraryTo(RANDOM_UUID, new Date());
		LibraryTo libraryTo2 = new LibraryTo(RANDOM_UUID, new Date());

		assertEquals("Two library TOs should be equal even if the "
				+ "date is different so long as the uuid is identical.",
				libraryTo1, libraryTo2);
	}

	@Test
	public void testEquals2() {
		Date date = new Date();
		LibraryTo libraryTo1 = new LibraryTo(RANDOM_UUID, date);
		LibraryTo libraryTo2 = new LibraryTo(RANDOM_UUID, date);

		assertEquals("Two library TOs should be equal if the "
				+ "date is identical and the uuid is identical.", libraryTo1,
				libraryTo2);
	}

	@Test
	public void testNotEquals1() {
		LibraryTo libraryTo1 = new LibraryTo(RANDOM_UUID, new Date());
		LibraryTo libraryTo2 = new LibraryTo(RANDOM_UUID12, new Date());

		assertFalse("Two library TOs should be not equal even if the "
				+ "uuids are not identical.", libraryTo1.equals(libraryTo2));
	}

}
