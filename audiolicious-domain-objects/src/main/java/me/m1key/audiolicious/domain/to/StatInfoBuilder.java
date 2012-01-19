package me.m1key.audiolicious.domain.to;

import java.util.Date;

import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.StatInfo;

public class StatInfoBuilder {

	private Library library;
	private String songUuid;
	private Date dateAdded;
	private Date dateModified;
	private int playCount;
	private int rating;
	private Date dateSkipped;
	private int skipCount;

	public StatInfoBuilder withLibrary(Library library) {
		this.library = library;
		return this;
	}

	public StatInfoBuilder withSongUuid(String songUuid) {
		this.songUuid = songUuid;
		return this;
	}

	public StatInfoBuilder withDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public StatInfoBuilder withDateModified(Date dateModified) {
		this.dateModified = dateModified;
		return this;
	}

	public StatInfoBuilder withPlayCount(int playCount) {
		this.playCount = playCount;
		return this;
	}

	public StatInfoBuilder withRating(int rating) {
		this.rating = rating;
		return this;
	}

	public StatInfoBuilder withDateSkipped(Date dateSkipped) {
		this.dateSkipped = dateSkipped;
		return this;
	}

	public StatInfoBuilder withSkipCount(int skipCount) {
		this.skipCount = skipCount;
		return this;
	}

	public StatInfo build() {
		return new ValuesBasedStatInfo(library, songUuid, dateAdded, dateModified,
				playCount, rating, dateSkipped, skipCount);
	}

}
