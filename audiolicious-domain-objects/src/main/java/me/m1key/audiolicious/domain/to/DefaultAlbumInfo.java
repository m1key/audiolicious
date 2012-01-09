package me.m1key.audiolicious.domain.to;

import me.m1key.audiolicious.domain.entities.AlbumInfo;

public class DefaultAlbumInfo implements AlbumInfo {

	private String name;
	private RatingTo rating;

	public DefaultAlbumInfo(String name, RatingTo rating) {
		super();
		this.name = name;
		this.rating = rating;
	}

	@Override
	public String getAlbumName() {
		return name;
	}

	@Override
	public RatingTo getAlbumRating() {
		return rating;
	}

}
