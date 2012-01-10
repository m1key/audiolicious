package me.m1key.audiolicious.services;

import me.m1key.audiolicious.objecthandler.handlers.SongService;

public interface CacheableSongService extends SongService {

	void initialise();

	void finalise();

}
