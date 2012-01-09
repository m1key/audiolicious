package me.m1key.audiolicious.domain.to;

import me.m1key.audiolicious.domain.entities.AlbumInfo;

public class AlbumInfoBuilder {

	private String name;
	private int rating;

	public AlbumInfoBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public AlbumInfoBuilder withRating(int rating) {
		this.rating = rating;
		return this;
	}

	public AlbumInfo build() {
		return new DefaultAlbumInfo(name, new RatingTo(rating));
	}

}
