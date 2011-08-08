package me.m1key.audiolicious.services.handlers;

import javax.enterprise.context.RequestScoped;

import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;

@RequestScoped
public class StubVideoHandler implements TrackHandler<VideoTo> {

	private int count;

	@Override
	public void handle(VideoTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
