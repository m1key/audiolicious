package me.m1key.audiolicious.services;

import java.util.Date;

import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.StatInfo;
import me.m1key.audiolicious.domain.to.RatingTo;
import me.m1key.audiolicious.domain.to.SongTo;

public class ToBasedStatInfo implements StatInfo {

	private SongTo songTo;
	private String songUuid;
	private Library library;

	@Override
	public Library getLibrary() {
		return library;
	}

	public ToBasedStatInfo(SongTo songTo, String songUuid, Library library) {
		if (songTo == null) {
			throw new IllegalArgumentException(
					"Null SongTo passed to DefaultStatInfo constructor.");
		}
		if (songUuid == null) {
			throw new IllegalArgumentException(
					"Null uuid passed to DefaultStatInfo constructor.");
		}

		this.songTo = songTo;
		this.songUuid = songUuid;
		this.library = library;
	}

	@Override
	public Date getDateAdded() {
		return songTo.getDateAdded();
	}

	@Override
	public Date getDateModified() {
		return songTo.getDateModified();
	}

	@Override
	public int getPlayCount() {
		return songTo.getPlayCount();
	}

	@Override
	public RatingTo getRating() {
		return songTo.getRating();
	}

	@Override
	public Date getDateSkipped() {
		return songTo.getSkipDate();
	}

	@Override
	public int getSkipCount() {
		return songTo.getSkipCount();
	}

	@Override
	public String getSongUuid() {
		return songUuid;
	}

}
