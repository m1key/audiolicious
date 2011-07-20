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

package me.m1key.audiolicious.objectmapper.extractor;

import static org.junit.Assert.assertEquals;
import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.objectmapper.extractor.DefaultEnglishValuesProvider;

import org.junit.Before;
import org.junit.Test;

public class DefaultEnglishValuesProviderTest {

	private DefaultEnglishValuesProvider defaultEnglishValuesProvider;

	@Before
	public void setup() {
		defaultEnglishValuesProvider = new DefaultEnglishValuesProvider();
	}

	@Test
	public void testAudioboek() {
		assertEquals("Audiobook", defaultEnglishValuesProvider.getValueFor(
				XmlNodeName.GENRE, "Audioboek"));
	}

	@Test
	public void testKeyWithSpaces() {
		assertEquals("Metallica", defaultEnglishValuesProvider.getValueFor(
				XmlNodeName.ALBUM_ARTIST, "Metallika"));
	}

	@Test
	public void testKeyAndNonEnglishValueWithSpaces() {
		assertEquals("George Washington",
				defaultEnglishValuesProvider.getValueFor(
						XmlNodeName.ALBUM_ARTIST, "Jerzy Waszyngton"));
	}

}
