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

import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.RatingTo;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

@Singleton
@Local(DataExtractor.class)
public class I18nDataExtractor implements DataExtractor {

	@EJB
	private EnglishValuesProvider englishValuesProvider;

	private static DateTimeFormatter parser = ISODateTimeFormat
			.dateTimeNoMillis();

	@Override
	public RatingTo extractRating(Map<XmlNodeName, String> trackValues,
			XmlNodeName key) {
		return new RatingTo(extractInt(trackValues, key));
	}

	@Override
	public int extractInt(Map<XmlNodeName, String> trackValues, XmlNodeName key) {
		if (trackValues.get(key) == null) {
			return 0;
		} else {
			return new Integer(trackValues.get(key));
		}
	}

	@Override
	public Boolean extractBoolean(Map<XmlNodeName, String> trackValues,
			XmlNodeName key) {
		return Boolean.valueOf(trackValues.get(key));
	}

	@Override
	public String extractString(Map<XmlNodeName, String> trackValues,
			XmlNodeName key) {
		String possiblyNonEnglishValue = trackValues.get(key);
		return englishValueFor(key, possiblyNonEnglishValue);
	}

	private String englishValueFor(XmlNodeName key,
			String possiblyNonEnglishValue) {
		String englishValueForThisKeyAndValue = getEnglishvalueForThisKeyAndValue(
				key, possiblyNonEnglishValue);
		if (possiblyNonEnglishValueIsNotOverwritten(englishValueForThisKeyAndValue)) {
			return possiblyNonEnglishValue;
		} else {
			return englishValueForThisKeyAndValue;
		}
	}

	private String getEnglishvalueForThisKeyAndValue(XmlNodeName key,
			String possiblyNonEnglishValue) {
		return englishValuesProvider.getValueFor(key, possiblyNonEnglishValue);
	}

	private boolean possiblyNonEnglishValueIsNotOverwritten(
			String englishValueForThisKeyAndValue) {
		return englishValueForThisKeyAndValue == null;
	}

	@Override
	public Date extractDate(Map<XmlNodeName, String> trackValues,
			XmlNodeName key) {
		if (trackValues.get(key) == null) {
			return null;
		} else {
			return parser.parseDateTime(trackValues.get(key)).toDate();
		}
	}

	public void setEnglishValuesProvider(
			EnglishValuesProvider englishValuesProvider) {
		this.englishValuesProvider = englishValuesProvider;
	}

}
