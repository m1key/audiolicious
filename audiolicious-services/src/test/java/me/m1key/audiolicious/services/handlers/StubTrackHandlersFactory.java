package me.m1key.audiolicious.services.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import me.m1key.audiolicious.domain.to.AudiobookTo;
import me.m1key.audiolicious.domain.to.PodcastTo;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.domain.to.VideoTo;
import me.m1key.audiolicious.objecthandler.TrackHandler;
import me.m1key.audiolicious.objecthandler.handlers.AudiobookHandler;
import me.m1key.audiolicious.objecthandler.handlers.PodcastHandler;
import me.m1key.audiolicious.objecthandler.handlers.SongHandler;
import me.m1key.audiolicious.objecthandler.handlers.VideoHandler;

@ApplicationScoped
public class StubTrackHandlersFactory {

	// TODO: Replace @Inject with @EJB when fix for AS7-1269 released.

	@Inject
	private AudiobookHandler audiobookHandler;
	@Inject
	private PodcastHandler podcastHandler;
	@Inject
	private SongHandler songHandler;
	@Inject
	private VideoHandler videoHandler;

	@Produces
	public Map<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>> getTrackHandlers() {
		Map<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>> handlers = new HashMap<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>>();
		handlers.put(AudiobookTo.class, audiobookHandler);
		handlers.put(PodcastTo.class, podcastHandler);
		handlers.put(SongTo.class, songHandler);
		handlers.put(VideoTo.class, videoHandler);
		return handlers;
	}

}
