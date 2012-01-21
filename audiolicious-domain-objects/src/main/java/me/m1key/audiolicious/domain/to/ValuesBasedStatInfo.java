package me.m1key.audiolicious.domain.to;

import java.util.Date;

import me.m1key.audiolicious.domain.entities.StatInfo;

public class ValuesBasedStatInfo implements StatInfo {

	private Date dateAdded;
	private Date dateModified;
	private int playCount;
	private int rating;
	private Date dateSkipped;
	private int skipCount;
	private String songUuid;

	public ValuesBasedStatInfo(String songUuid, Date dateAdded,
			Date dateModified, int playCount, int rating, Date dateSkipped,
			int skipCount) {
		super();
		this.songUuid = songUuid;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.playCount = playCount;
		this.rating = rating;
		this.dateSkipped = dateSkipped;
		this.skipCount = skipCount;
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

}
