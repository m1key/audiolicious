package me.m1key.audiolicious.services.handlers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;

import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;

@Alternative
@RequestScoped
public class StubAudiobookHandler implements TrackHandler<AudiobookTo> {

	private int count;

	@Override
	public void handle(AudiobookTo track) {
		count++;
	}

	public int getCount() {
		return count;
	}

}
