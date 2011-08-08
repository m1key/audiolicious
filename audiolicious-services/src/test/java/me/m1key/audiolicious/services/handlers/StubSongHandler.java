package me.m1key.audiolicious.services.handlers;

import javax.enterprise.context.RequestScoped;

import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;

@RequestScoped
public class StubSongHandler implements TrackHandler<SongTo> {

	private int count;

	@Override
	public void handle(SongTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
