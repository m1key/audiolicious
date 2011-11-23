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

package me.m1key.audiolicious.libraryparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;

import me.m1key.audiolicious.commons.XmlNodeName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

@Stateful
@Local(LibraryParser.class)
public class VtdItunesLibraryParser implements LibraryParser {

	private static final boolean NAMESPACE_AWARE = false;
	private static final String SPACE = " ";
	private static final String UNDERSCORE = "_";
	private static Logger log = LoggerFactory
			.getLogger(VtdItunesLibraryParser.class);

	private File xmlFile;

	private VTDGen generator;
	private VTDNav navigator;
	private AutoPilot trackMainKey;
	private AutoPilot trackKey;
	private AutoPilot trackCurrentKey;
	private AutoPilot trackCurrentValue;

	@EJB
	private RawTrackDataHandler rawTrackDataHandler;

	@Override
	public void process(File xmlFile, String libraryUuid) {
		setXmlFile(xmlFile);
		initialiseXPathProcessing();
		verifyCanParseFile();
		bindNavigator();

		while (hasTrack()) {
			rawTrackDataHandler.handle(extractTrackValues(), libraryUuid);
		}
	}

	public void setCallback(RawTrackDataHandler callback) {
		this.rawTrackDataHandler = callback;
	}

	private void setXmlFile(File xmlFile) {
		try {
			verifyFileExists(xmlFile);
		} catch (FileNotFoundException e) {
			throw new XmlParseException(e);
		}
		this.xmlFile = xmlFile;
	}

	private void verifyFileExists(File xmlFile) throws FileNotFoundException {
		if (!xmlFile.exists()) {
			throw new FileNotFoundException(String.format("File not found[%s]",
					xmlFile.getAbsoluteFile()));
		}
	}

	private void initialiseXPathProcessing() {
		generator = new VTDGen();
		trackMainKey = new AutoPilot();
		trackKey = new AutoPilot();
		trackCurrentKey = new AutoPilot();
		trackCurrentValue = new AutoPilot();

		try {
			trackMainKey
					.selectXPath("/plist/dict/dict/key[parent::*/preceding-sibling::*/text() = 'Tracks']");
			trackKey.selectXPath("./following-sibling::dict[1]/key");
			trackCurrentKey.selectXPath(".");
			trackCurrentValue.selectXPath("./following-sibling::*");
		} catch (XPathParseException e) {
			throw new XmlParseException(e);
		}
	}

	private void verifyCanParseFile() {
		if (!generator.parseFile(xmlFile.getAbsolutePath(), NAMESPACE_AWARE)) {
			throw new XmlParseException(String.format(
					"Failed to parse file [%s].", xmlFile.getAbsolutePath()));
		}
	}

	private Map<XmlNodeName, String> extractTrackValues() {
		try {
			Map<XmlNodeName, String> trackValues = new HashMap<XmlNodeName, String>();

			while (trackHasValue()) {
				String trackKeyStringValue = extractTrackKeyStringValue();
				if (isRecognised(trackKeyStringValue)) {
					XmlNodeName trackKey = extractTrackKey(trackKeyStringValue);
					String trackValue = extractTrackValue();
					log.debug("Key [{}], value [{}].", trackKey, trackValue);
					trackValues.put(trackKey, trackValue);
				} else {
					log.warn("Unknown key [{}].", trackKeyStringValue);
				}
			}
			trackKey.resetXPath();
			return trackValues;
		} catch (XPathEvalException e) {
			throw new XmlParseException(e);
		} catch (NavException e) {
			throw new XmlParseException(e);
		}
	}

	private boolean isRecognised(String key) {
		try {
			extractTrackKey(key);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	private String extractTrackKeyStringValue() {
		return trackCurrentKey.evalXPathToString();
	}

	private XmlNodeName extractTrackKey(String key) {
		return XmlNodeName.valueOf(toEnumName(key));
	}

	private String toEnumName(String value) {
		return value.toUpperCase().replaceAll(SPACE, UNDERSCORE);
	}

	private String extractTrackValue() throws NavException {
		String currentValue = trackCurrentValue.evalXPathToString();
		if (isBooleanNode(currentValue)) {
			return booleanNodeValue();
		} else {
			return stringNodeValue();
		}
	}

	private String stringNodeValue() throws NavException {
		return navigator.toNormalizedString(navigator.getCurrentIndex() + 3);
	}

	private String booleanNodeValue() throws NavException {
		return Boolean.valueOf(nextBooleanNodeValue()).toString();
	}

	private String nextBooleanNodeValue() throws NavException {
		return navigator.toString(navigator.getCurrentIndex() + 2);
	}

	private boolean isBooleanNode(String currentValue) {
		return "".equals(currentValue);
	}

	private boolean trackHasValue() throws XPathEvalException, NavException {
		return trackKey.evalXPath() != -1;
	}

	private boolean hasTrack() {
		try {
			return trackMainKey.evalXPath() != -1;
		} catch (XPathEvalException e) {
			throw new XmlParseException(e);
		} catch (NavException e) {
			throw new XmlParseException(e);
		}
	}

	private void bindNavigator() {
		navigator = generator.getNav();
		trackMainKey.bind(navigator);
		trackKey.bind(navigator);
		trackCurrentKey.bind(navigator);
		trackCurrentValue.bind(navigator);
	}
}
