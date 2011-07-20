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

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.RatingTo;

public interface DataExtractor {

	RatingTo extractRating(Map<XmlNodeName, String> trackValues, XmlNodeName key);

	int extractInt(Map<XmlNodeName, String> trackValues, XmlNodeName key);

	Boolean extractBoolean(Map<XmlNodeName, String> trackValues, XmlNodeName key);

	String extractString(Map<XmlNodeName, String> trackValues, XmlNodeName key);

	Date extractDate(Map<XmlNodeName, String> trackValues, XmlNodeName key);

}