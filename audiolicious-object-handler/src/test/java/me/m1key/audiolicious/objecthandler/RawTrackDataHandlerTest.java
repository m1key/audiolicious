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

package me.m1key.audiolicious.objecthandler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.m1key.audiolicious.commons.XmlNodeName;
import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.AudiobookToBuilder;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.PodcastToBuilder;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.SongToBuilder;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.domain.to.VideoToBuilder;
import me.m1key.audiolicious.objectmapper.AggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.DefaultAggregateTrackMapper;
import me.m1key.audiolicious.objectmapper.TrackMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultAudiobookMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultPodcastMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultSongMapper;
import me.m1key.audiolicious.objectmapper.trackmappers.DefaultVideoMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RawTrackDataHandlerTest {
	private DefaultRawTrackDataHandler handler;
	private AggregateTrackMapper aggregateMapper;
	private Map<XmlNodeName, String> receivedTrackValues;

	@Mock
	private ObjectTrackDataHandler objectTrackDataHandler;
	@Mock
	private DefaultAudiobookMapper audiobookMapper;
	@Mock
	private DefaultPodcastMapper podcastMapper;
	@Mock
	private DefaultSongMapper songMapper;
	@Mock
	private DefaultVideoMapper videoMapper;

	private static final String LIBRARY_UUID = UUID.randomUUID().toString();

	@Before
	public void setup() {
		receivedTrackValues = new HashMap<XmlNodeName, String>();
		aggregateMapper = createAggregateMapper();
		handler = new DefaultRawTrackDataHandler();
		handler.setMapper(aggregateMapper);
		handler.setObjectTrackDataHandler(objectTrackDataHandler);
	}

	@Test
	public void shouldCallHandleForAudiobok() {
		AudiobookTo audiobook = new AudiobookToBuilder("Invisible").build();

		when(audiobookMapper.canMap(receivedTrackValues)).thenReturn(true);
		when(audiobookMapper.map(receivedTrackValues)).thenReturn(audiobook);

		handler.handle(receivedTrackValues, LIBRARY_UUID);

		verify(objectTrackDataHandler, times(1))
				.handle(audiobook, LIBRARY_UUID);
	}

	@Test
	public void shouldCallHandleForPodcast() {
		PodcastTo podcast = new PodcastToBuilder("Invisible").build();

		when(podcastMapper.canMap(receivedTrackValues)).thenReturn(true);
		when(podcastMapper.map(receivedTrackValues)).thenReturn(podcast);

		handler.handle(receivedTrackValues, LIBRARY_UUID);

		verify(objectTrackDataHandler, times(1)).handle(podcast, LIBRARY_UUID);
	}

	@Test
	public void shouldCallHandleForSong() {
		SongTo song = new SongToBuilder("Invisible").build();

		when(songMapper.canMap(receivedTrackValues)).thenReturn(true);
		when(songMapper.map(receivedTrackValues)).thenReturn(song);

		handler.handle(receivedTrackValues, LIBRARY_UUID);

		verify(objectTrackDataHandler, times(1)).handle(song, LIBRARY_UUID);
	}

	@Test
	public void shouldCallHandleForVideo() {
		VideoTo video = new VideoToBuilder("Invisible").build();

		when(videoMapper.canMap(receivedTrackValues)).thenReturn(true);
		when(videoMapper.map(receivedTrackValues)).thenReturn(video);

		handler.handle(receivedTrackValues, LIBRARY_UUID);

		verify(objectTrackDataHandler, times(1)).handle(video, LIBRARY_UUID);
	}

	private AggregateTrackMapper createAggregateMapper() {
		Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers = getAllKnownTracksMappers();
		DefaultAggregateTrackMapper aggregateTrackMapper = new DefaultAggregateTrackMapper();
		aggregateTrackMapper.setMappers(mappers);
		return aggregateTrackMapper;
	}

	private Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> getAllKnownTracksMappers() {
		Map<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>> mappers = new HashMap<Class<? extends TrackTo>, TrackMapper<? extends TrackTo>>();
		mappers.put(AudiobookTo.class, audiobookMapper);
		mappers.put(PodcastTo.class, podcastMapper);
		mappers.put(SongTo.class, songMapper);
		mappers.put(VideoTo.class, videoMapper);
		return mappers;
	}

}
