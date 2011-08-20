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

import java.util.ResourceBundle;

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.commons.XmlNodeName;

@Singleton
@Local(EnglishValuesProvider.class)
public class DefaultEnglishValuesProvider implements EnglishValuesProvider {

	private ResourceBundle resourceBundle;

	public DefaultEnglishValuesProvider() {
		resourceBundle = ResourceBundle.getBundle("englishValues");
	}

	@Override
	public String getValueFor(XmlNodeName xmlNodeName,
			String possiblyNonEnglishValue) {
		String keyInEnglishValues = toKey(xmlNodeName, possiblyNonEnglishValue);
		if (resourceBundle.containsKey(keyInEnglishValues)) {
			return resourceBundle.getString(keyInEnglishValues);
		} else {
			return null;
		}
	}

	private String toKey(XmlNodeName xmlNodeName, String possiblyNonEnglishValue) {
		return String.format("%s.%s", xmlNodeName, possiblyNonEnglishValue);
	}

}
