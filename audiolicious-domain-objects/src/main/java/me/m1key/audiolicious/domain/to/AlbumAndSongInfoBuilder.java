package me.m1key.audiolicious.domain.to;

import java.util.Date;

import me.m1key.audiolicious.domain.entities.AlbumAndSongInfo;

public class AlbumAndSongInfoBuilder {

	private Date dateAdded;
	private Date dateModified;
	private String albumName;
	private String albumArtist;
	private String artist;
	private String genre;
	private boolean compilation;
	private String name;
	private RatingTo rating;
	private int playCount;
	private Date skipDate;
	private int skipCount;
	private int year;
	private String composer;
	private boolean albumRatingComputed;
	private RatingTo albumRating;
	private boolean hasVideo;
	private int trackNumber;
	private int discNumber;
	private int totalTime;

	public AlbumAndSongInfoBuilder(String name) {
		this.name = name;
	}

	public AlbumAndSongInfoBuilder withDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public AlbumAndSongInfoBuilder withDateModified(Date dateModified) {
		this.dateModified = dateModified;
		return this;
	}

	public AlbumAndSongInfoBuilder withAlbumName(String albumName) {
		this.albumName = albumName;
		return this;
	}

	public AlbumAndSongInfoBuilder withAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
		return this;
	}

	public AlbumAndSongInfoBuilder withArtist(String artist) {
		this.artist = artist;
		return this;
	}

	public AlbumAndSongInfoBuilder withGenre(String genre) {
		this.genre = genre;
		return this;
	}

	public AlbumAndSongInfoBuilder withRating(int rating) {
		this.rating = new RatingTo(rating);
		return this;
	}

	public AlbumAndSongInfoBuilder withPlayCount(int playCount) {
		this.playCount = playCount;
		return this;
	}

	public AlbumAndSongInfoBuilder withYear(int year) {
		this.year = year;
		return this;
	}

	public AlbumAndSongInfoBuilder withHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
		return this;
	}

	public AlbumAndSongInfoBuilder withComposer(String composer) {
		this.composer = composer;
		return this;
	}

	public AlbumAndSongInfoBuilder withCompilation(boolean compilation) {
		this.compilation = compilation;
		return this;
	}

	public AlbumAndSongInfoBuilder withSkipCount(int skipCount) {
		this.skipCount = skipCount;
		return this;
	}

	public AlbumAndSongInfoBuilder withSkipDate(Date skipDate) {
		this.skipDate = skipDate;
		return this;
	}

	public AlbumAndSongInfoBuilder withAlbumRatingComputed(
			boolean albumRatingComputed) {
		this.albumRatingComputed = albumRatingComputed;
		return this;
	}

	public AlbumAndSongInfoBuilder withAlbumRating(int albumRating) {
		this.albumRating = new RatingTo(albumRating);
		return this;
	}

	public AlbumAndSongInfoBuilder withTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
		return this;
	}

	public AlbumAndSongInfoBuilder withDiscNumber(int discNumber) {
		this.discNumber = discNumber;
		return this;
	}

	public AlbumAndSongInfoBuilder withTotalTime(int totalTime) {
		this.totalTime = totalTime;
		return this;
	}

	public AlbumAndSongInfo build() {
		return new SongTo(name, albumName, artist, albumArtist, year, composer,
				genre, compilation, dateAdded, dateModified, rating, playCount,
				skipDate, skipCount, albumRatingComputed, albumRating,
				hasVideo, trackNumber, discNumber, totalTime);
	}

}
