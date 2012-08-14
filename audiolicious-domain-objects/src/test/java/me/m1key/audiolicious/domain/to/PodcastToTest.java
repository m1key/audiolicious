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

import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.RatingTo;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class PodcastToTest {

	@Test
	public void testEquals() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		assertEquals(podcast1, podcast2);
	}

	@Test
	public void testNotEquals1() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark2",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals2() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio2", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals3() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio2", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals4() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock2", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals5() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible2", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals6() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(80), 100, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals7() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 99, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals8() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1984, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals9() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, true, 0, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals10() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 1, 0, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals11() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 1, false);
		assertFalse(podcast1.equals(podcast2));
	}

	@Test
	public void testNotEquals() {
		Date dateAdded = new Date();
		Date dateModified = new Date();
		PodcastTo podcast1 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, false);
		PodcastTo podcast2 = new PodcastTo("Invisible", "Rainbow in the Dark",
				"Dio", "Dio", 1983, "Rock", dateAdded, dateModified,
				new RatingTo(100), 100, false, 0, 0, true);
		assertFalse(podcast1.equals(podcast2));
	}

}
