package me.m1key.audiolicious.services.handlers;

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.SongHandler;

@Singleton
@Local({ SongHandler.class, TrackHandler.class, StubSongHandler.class })
public class StubSongHandler implements SongHandler {

	private int count;

	@Override
	public void handle(SongTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
