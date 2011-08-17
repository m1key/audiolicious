package me.m1key.audiolicious.services.handlers;

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.VideoHandler;

@Singleton
@Local({ StubVideoHandler.class, TrackHandler.class, VideoHandler.class })
public class StubVideoHandler implements VideoHandler {

	private int count;

	@Override
	public void handle(VideoTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
