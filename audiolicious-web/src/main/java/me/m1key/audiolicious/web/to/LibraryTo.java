package me.m1key.audiolicious.web.to;

import java.util.Date;

public class LibraryTo {

	private String uuid;
	private Date uploadTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

}
