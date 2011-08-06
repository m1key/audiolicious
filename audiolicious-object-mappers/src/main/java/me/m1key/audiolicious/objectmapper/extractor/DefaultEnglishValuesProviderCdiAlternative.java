package me.m1key.audiolicious.objectmapper.extractor;

import java.util.ResourceBundle;

import me.m1key.audiolicious.commons.XmlNodeName;

public class DefaultEnglishValuesProviderCdiAlternative implements
		EnglishValuesProvider {

	private ResourceBundle resourceBundle;

	public DefaultEnglishValuesProviderCdiAlternative() {
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
