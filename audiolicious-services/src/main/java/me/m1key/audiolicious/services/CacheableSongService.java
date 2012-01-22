package me.m1key.audiolicious.services;

import me.m1key.audiolicious.objecthandler.handlers.StatefulSongService;

public interface CacheableSongService extends StatefulSongService {

	void initialise();

	void finalise();

}
