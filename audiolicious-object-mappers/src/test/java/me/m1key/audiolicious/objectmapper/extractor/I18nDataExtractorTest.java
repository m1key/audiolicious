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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.RatingTo;

import org.junit.Before;
import org.junit.Test;

public class I18nDataExtractorTest {

	private I18nDataExtractor dataExtractor;
	private Map<XmlNodeName, String> trackValues;

	@Before
	public void setup() {
		dataExtractor = new I18nDataExtractor();
		dataExtractor
				.setEnglishValuesProvider(new DefaultEnglishValuesProvider());
		trackValues = new HashMap<XmlNodeName, String>();
	}

	@Test
	public void shouldExtractLegitBooleanTrue() {
		trackValues.put(XmlNodeName.COMPOSER, "true");
		assertTrue(dataExtractor.extractBoolean(trackValues,
				XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractLegitBooleanFalse() {
		trackValues.put(XmlNodeName.COMPOSER, "false");
		assertFalse(dataExtractor.extractBoolean(trackValues,
				XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractBooleanFalseWhenKeyNotPresent() {
		assertFalse(dataExtractor.extractBoolean(trackValues,
				XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractNotNullDateWhenKeyNotPresent() {
		trackValues.put(XmlNodeName.COMPOSER, "2009-04-09T21:31:52Z");
		assertEquals(1239312712000L,
				dataExtractor.extractDate(trackValues, XmlNodeName.COMPOSER)
						.getTime());
	}

	@Test
	public void shouldExtractNullDateWhenKeyNotPresent() {
		assertNull(dataExtractor.extractDate(trackValues, XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractIntValue() {
		trackValues.put(XmlNodeName.COMPOSER, "5");
		assertEquals(5,
				dataExtractor.extractInt(trackValues, XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractZeroForNoKey() {
		assertEquals(0,
				dataExtractor.extractInt(trackValues, XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractRatingValue() {
		trackValues.put(XmlNodeName.COMPOSER, "80");
		assertEquals(new RatingTo(80),
				dataExtractor.extractRating(trackValues, XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractRating0ValueWhenKeyNotPresent() {
		assertEquals(new RatingTo(0),
				dataExtractor.extractRating(trackValues, XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractStringValue() {
		trackValues.put(XmlNodeName.COMPOSER, "Dio");
		assertEquals("Dio",
				dataExtractor.extractString(trackValues, XmlNodeName.COMPOSER));
	}

	@Test
	public void shouldExtractNullStringValueWhenKeyNotPresent() {
		assertNull(dataExtractor.extractString(trackValues,
				XmlNodeName.COMPOSER));
	}
}
