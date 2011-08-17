package me.m1key.audiolicious.services.handlers;

import javax.ejb.Local;
import javax.ejb.Singleton;

import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.PodcastHandler;

@Singleton
@Local({ PodcastHandler.class, TrackHandler.class, StubPodcastHandler.class })
public class StubPodcastHandler implements PodcastHandler {

	private int count;

	@Override
	public void handle(PodcastTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
