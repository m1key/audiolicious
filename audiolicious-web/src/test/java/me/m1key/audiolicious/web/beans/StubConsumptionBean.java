package me.m1key.audiolicious.web.beans;

import java.io.File;

import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

@Alternative
@Singleton
public class StubConsumptionBean extends ConsumptionBean {

	private boolean consumeHasBeenCalled;

	@Override
	public void consume(File savedLibraryFile, String libraryUuid) {
		consumeHasBeenCalled = true;
	}

	public boolean hasConsumeBeenCalled() {
		return consumeHasBeenCalled;
	}

}
