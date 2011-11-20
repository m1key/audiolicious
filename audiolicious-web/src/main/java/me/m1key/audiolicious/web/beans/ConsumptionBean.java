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

package me.m1key.audiolicious.web.beans;

import java.io.File;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ApplicationScoped
public class ConsumptionBean /* Implements LibraryConsumer */{

	private static Logger log = LoggerFactory.getLogger(ConsumptionBean.class);

	public void consume(File savedLibraryFile, String libraryUuid) {
		log.info("Consuming library file [{}].", savedLibraryFile);
		// TODO make an asynchronous call.
		// librariesBeingConsumed.add(libraryUuid);
		// libraryManager.parse(savedLibraryFile, libraryUuid, this)
	}
	
	/*
	@Override
	public void libraryConsumed(String libraryUuid) {
		librariesBeingConsumed.remove(libraryUuid);
		librariesConsumed.add(libraryUuid); // But don't keep more than 5.
	}
	 */

}
