/* 
 * Audiolicious - Your Music Library Statistics
 * Copyright (C) 2012, Michal Huniewicz
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

package me.m1key.audiolicious.domain.entities;

import me.m1key.audiolicious.domain.to.AlbumAndSongInfoBuilder;
import me.m1key.audiolicious.domain.to.StatInfoBuilder;

public class SampleData {

	public static AlbumAndSongInfo album1Song1Info() {
		return new AlbumAndSongInfoBuilder("Stand Up and Shout")
				.withAlbumName("Holy Diver").withAlbumRating(80)
				.withGenre("Rock").withArtist("Dio").withYear(1983).build();
	}

	public static StatInfo album1Song1Stat() {
		Library library = new Library("Library UUID");
		StatInfo statInfo = new StatInfoBuilder().withRating(100)
				.withLibrary(library).build();
		return statInfo;
	}

	public static AlbumAndSongInfo album1Song2Info() {
		return new AlbumAndSongInfoBuilder("Holy Diver")
				.withAlbumName("Holy Diver").withAlbumRating(100)
				.withGenre("Rock").withArtist("Dio").withYear(1983).build();
	}

	public static StatInfo album1Song2Stat() {
		Library library = new Library("Library UUID");
		StatInfo statInfo = new StatInfoBuilder().withRating(100)
				.withLibrary(library).build();
		return statInfo;
	}

	public static AlbumAndSongInfo album2Song1Info() {
		return new AlbumAndSongInfoBuilder("Jesus, Mary & the Holy Ghost")
				.withAlbumName("Strange Highways").withAlbumRating(80)
				.withGenre("Rock").withArtist("Dio").withYear(1994).build();
	}

	public static StatInfo album2Song1Stat() {
		Library library = new Library("Library UUID");
		StatInfo statInfo = new StatInfoBuilder().withRating(100)
				.withLibrary(library).build();
		return statInfo;
	}

	public static AlbumAndSongInfo album2Song2Info() {
		return new AlbumAndSongInfoBuilder("Firehead")
				.withAlbumName("Strange Highways").withAlbumRating(80)
				.withGenre("Rock").withArtist("Dio").withYear(1994).build();
	}

	public static StatInfo album2Song2Stat() {
		Library library = new Library("Library UUID");
		StatInfo statInfo = new StatInfoBuilder().withRating(100)
				.withLibrary(library).build();
		return statInfo;
	}

}
