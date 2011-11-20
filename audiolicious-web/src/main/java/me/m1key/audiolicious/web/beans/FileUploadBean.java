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
 * 
 * NOTE:
 * Based on sample code by Ilya Shaikovsky.
 * http://livedemo.exadel.com/richfaces-demo/richfaces/fileUpload.jsf?c=fileUpload
 */

package me.m1key.audiolicious.web.beans;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
@ManagedBean
@ApplicationScoped
public class FileUploadBean {

	private static Logger log = LoggerFactory.getLogger(FileUploadBean.class);

	@Inject
	private ConsumptionBean consumer;
	private ArrayList<UploadedFile> files = new ArrayList<UploadedFile>();

	public void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFiles().get((Integer) object).getData());
		stream.close();
	}

	public void listener(FileUploadEvent event) throws Exception {
		log.info("Received file upload event [{}].", event);
		UploadedFile uploadedFile = event.getUploadedFile();
		String libraryUuid = UUID.randomUUID().toString();
		File savedLibraryFile = saveFile(uploadedFile);
		consumer.consume(savedLibraryFile, libraryUuid);
		files.add(uploadedFile);
	}

	public String clearUploadData() {
		files.clear();
		return null;
	}

	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<UploadedFile> files) {
		this.files = files;
	}

	private File saveFile(UploadedFile uploadedFile) {
		// TODO Auto-generated method stub
		return null;
	}

}