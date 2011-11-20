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

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ApplicationScoped
public class LibrarySaveBean {

	private static Logger log = LoggerFactory.getLogger(LibrarySaveBean.class);

	private static final String XML_EXTENSION = ".xml";
	private static final String PATH_SEPARATOR = "path.separator";
	private static final String TARGET_DIRECTORY_PATH_SYSTEM_VARIABLE_NAME = "ADLCS_DUMP";
	private static final Object EMPTY_STRING = "";

	private String targetDirectoryPath;

	public LibrarySaveBean() {
		String targetDirectoryPathFromOs = System
				.getenv(TARGET_DIRECTORY_PATH_SYSTEM_VARIABLE_NAME);
		if (emptyString(targetDirectoryPathFromOs)) {
			throw new AudioliciousConfigurationException(String.format(
					"You must set up the [%s] environment variable to "
							+ "point to a folder where Audiolicious will save "
							+ "temporary library files.",
					TARGET_DIRECTORY_PATH_SYSTEM_VARIABLE_NAME));
		} else {
			setTargetDirectoryPath(targetDirectoryPathFromOs);
			log.info("Audiolicious dump folder set to [{}].",
					targetDirectoryPath);
		}
	}

	private boolean emptyString(String targetDirectoryPathFromOs) {
		return targetDirectoryPathFromOs == null
				|| targetDirectoryPathFromOs.trim().equals(EMPTY_STRING);
	}

	private void setTargetDirectoryPath(String targetDirectoryPathFromOs) {
		String pathSeparator = System.getProperty(PATH_SEPARATOR);

		if (targetDirectoryPathFromOs.endsWith(pathSeparator)) {
			targetDirectoryPath = targetDirectoryPathFromOs;
		} else {
			targetDirectoryPath = targetDirectoryPathFromOs + pathSeparator;
		}
	}

	public File save(InputStream inputStream, String targetFileName) {
		File libraryFile = new File(fullPath(targetFileName));
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(libraryFile);
			byte buffer[] = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeIfNotNull(inputStream);
			closeIfNotNull(outputStream);
		}
		return libraryFile;
	}

	private String fullPath(String targetFileName) {
		return targetDirectoryPath + targetFileName + XML_EXTENSION;
	}

	private void closeIfNotNull(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
