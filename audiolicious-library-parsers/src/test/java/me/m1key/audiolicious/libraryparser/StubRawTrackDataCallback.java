package me.m1key.audiolicious.libraryparser;

import java.util.List;
import java.util.Map;

import me.m1key.audiolicious.commons.XmlNodeName;

public interface StubRawTrackDataCallback extends RawTrackDataCallback {

	void feed(Map<XmlNodeName, String> trackValues);

	List<Map<XmlNodeName, String>> getRawTrackData();

	Map<XmlNodeName, String> getTrack(String trackId);

}