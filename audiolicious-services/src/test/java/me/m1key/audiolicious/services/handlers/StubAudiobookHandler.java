package me.m1key.audiolicious.services.handlers;

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.AudiobookHandler;

@Singleton
@Local({ AudiobookHandler.class, TrackHandler.class, StubAudiobookHandler.class })
public class StubAudiobookHandler implements AudiobookHandler {

	private int count;

	@Override
	public void handle(AudiobookTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
