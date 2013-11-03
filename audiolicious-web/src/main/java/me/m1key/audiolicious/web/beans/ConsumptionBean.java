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
import javax.inject.Inject;

import me.m1key.audiolicious.services.LibraryImporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ApplicationScoped
public class ConsumptionBean {

	private static Logger log = LoggerFactory.getLogger(ConsumptionBean.class);
	
	@Inject
	private LibraryImporter libraryImporter;

	public void consume(File savedLibraryFile, String libraryUuid) {
		log.info("Consuming library file [{}].", savedLibraryFile);
		// TODO make an asynchronous call.
		libraryImporter.importLibrary(savedLibraryFile);
	}

}
