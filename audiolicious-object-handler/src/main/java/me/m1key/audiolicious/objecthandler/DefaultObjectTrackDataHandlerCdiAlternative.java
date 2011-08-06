/* 
 * Audiolicious - Your Music Library Statistics
 * Copyright (C) 2011, Michal Huniewicz
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.m1key.me
 */

package me.m1key.audiolicious.objecthandler;

import java.io.File;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import me.m1key.audiolicious.domain.to.TrackTo;
import me.m1key.audiolicious.libraryparser.LibraryParser;

@RequestScoped
public class DefaultObjectTrackDataHandlerCdiAlternative implements ObjectTrackDataHandler {

	private LibraryParser libraryParser;
	private Map<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>> handlers;

	// For proxying.
	protected DefaultObjectTrackDataHandlerCdiAlternative() {
	}

	@Inject
	public DefaultObjectTrackDataHandlerCdiAlternative(
			LibraryParser libraryParser,
			Map<Class<? extends TrackTo>, TrackHandler<? extends TrackTo>> handlers) {
		this.libraryParser = libraryParser;
		this.handlers = handlers;
	}

	@Override
	public void execute(File libraryFile) {
		libraryParser.process(libraryFile);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void handle(TrackTo track) {
		TrackHandler g = handlers.get(track.getClass());
		g.handle(track);
	}
}
