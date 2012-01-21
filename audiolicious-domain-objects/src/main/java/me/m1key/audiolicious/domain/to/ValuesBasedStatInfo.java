package me.m1key.audiolicious.domain.to;

import java.util.Date;

import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.entities.Song;
import me.m1key.audiolicious.domain.entities.StatInfo;

public class ValuesBasedStatInfo implements StatInfo {

	private Library library;
	private Date dateAdded;
	private Date dateModified;
	private int playCount;
	private int rating;
	private Date dateSkipped;
	private int skipCount;
	private String songUuid;
	private Song song;

	public ValuesBasedStatInfo(Library library, String songUuid,
			Date dateAdded, Date dateModified, int playCount, int rating,
			Date dateSkipped, int skipCount, Song song) {
		super();
		this.library = library;
		this.songUuid = songUuid;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.playCount = playCount;
		this.rating = rating;
		this.dateSkipped = dateSkipped;
		this.skipCount = skipCount;
		this.song = song;
	}

	@Override
	public Library getLibrary() {
		return library;
	}

	@Override
	public Date getDateAdded() {
		return dateAdded;
	}

	@Override
	public Date getDateModified() {
		return dateModified;
	}

	@Override
	public int getPlayCount() {
		return playCount;
	}

	@Override
	public RatingTo getRating() {
		return new RatingTo(rating);
	}

	@Override
	public Date getDateSkipped() {
		return dateSkipped;
	}

	@Override
	public int getSkipCount() {
		return skipCount;
	}

	@Override
	public String getSongUuid() {
		return songUuid;
	}

	@Override
	public Song getSong() {
		// TODO REMOVE THIS
		return song;
	}

}
