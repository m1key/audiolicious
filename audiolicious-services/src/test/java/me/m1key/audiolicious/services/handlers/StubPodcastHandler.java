package me.m1key.audiolicious.services.handlers;

import javax.enterprise.context.RequestScoped;

import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;

@RequestScoped
public class StubPodcastHandler implements TrackHandler<PodcastTo> {

	private int count;

	@Override
	public void handle(PodcastTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
