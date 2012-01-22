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
 */

package me.m1key.audiolicious.commons;

public enum XmlNodeName {

    ALBUM_ARTIST {
        @Override
        public String toString() {
            return "Album Artist";
        }
    },

    ALBUM_RATING {
        @Override
        public String toString() {
            return "Album Rating";
        }
    },

    ALBUM_RATING_COMPUTED {
        @Override
        public String toString() {
            return "Album Rating Computed";
        }
    },

    ALBUM {
        @Override
        public String toString() {
            return "Album";
        }
    },

    ARTIST {
        @Override
        public String toString() {
            return "Artist";
        }
    },

    ARTWORK_COUNT {
        @Override
        public String toString() {
            return "Artwork Count";
        }
    },

    BIT_RATE {
        @Override
        public String toString() {
            return "Bit Rate";
        }
    },

    COMMENTS {
        @Override
        public String toString() {
            return "Comments";
        }
    },

    COMPILATION {
        @Override
        public String toString() {
            return "Compilation";
        }
    },

    COMPOSER {
        @Override
        public String toString() {
            return "Composer";
        }
    },

    CONTENT_RATING {
        @Override
        public String toString() {
            return "Content Rating";
        }
    },

    DATE_ADDED {
        @Override
        public String toString() {
            return "Date Added";
        }
    },

    DATE_MODIFIED {
        @Override
        public String toString() {
            return "Date Modified";
        }
    },

    DISC_COUNT {
        @Override
        public String toString() {
            return "Disc Count";
        }
    },

    DISC_NUMBER {
        @Override
        public String toString() {
            return "Disc Number";
        }
    },

    FILE_FOLDER_COUNT {
        @Override
        public String toString() {
            return "File Folder Count";
        }
    },

    GENRE {
        @Override
        public String toString() {
            return "Genre";
        }
    },

    HAS_VIDEO {
        @Override
        public String toString() {
            return "Has Video";
        }
    },

    HD {
        @Override
        public String toString() {
            return "HD";
        }
    },

    KIND {
        @Override
        public String toString() {
            return "Kind";
        }
    },

    LIBRARY_FOLDER_COUNT {
        @Override
        public String toString() {
            return "Library Folder Count";
        }
    },

    LOCATION {
        @Override
        public String toString() {
            return "Location";
        }
    },

    MOVIE {
        @Override
        public String toString() {
            return "Movie";
        }
    },

    MUSIC_VIDEO {
        @Override
        public String toString() {
            return "Music Video";
        }
    },

    NAME {
        @Override
        public String toString() {
            return "Name";
        }
    },

    NORMALIZATION {
        @Override
        public String toString() {
            return "Normalization";
        }
    },

    PERSISTENT_ID {
        @Override
        public String toString() {
            return "Persistent ID";
        }
    },

    PLAY_COUNT {
        @Override
        public String toString() {
            return "Play Count";
        }
    },

    PLAY_DATE {
        @Override
        public String toString() {
            return "Play Date";
        }
    },

    PLAY_DATE_UTC {
        @Override
        public String toString() {
            return "Play Date UTC";
        }
    },

    PODCAST {
        @Override
        public String toString() {
            return "Podcast";
        }
    },

    PROTECTED {
        @Override
        public String toString() {
            return "Protected";
        }
    },

    PURCHASED {
        @Override
        public String toString() {
            return "Purchased";
        }
    },

    RATING {
        @Override
        public String toString() {
            return "Rating";
        }
    },

    RELEASE_DATE {
        @Override
        public String toString() {
            return "Release Date";
        }
    },

    SAMPLE_RATE {
        @Override
        public String toString() {
            return "Sample Rate";
        }
    },

    SIZE {
        @Override
        public String toString() {
            return "Size";
        }
    },

    SKIP_COUNT {
        @Override
        public String toString() {
            return "Skip Count";
        }
    },

    SKIP_DATE {
        @Override
        public String toString() {
            return "Skip Date";
        }
    },

    SORT_ALBUM {
        @Override
        public String toString() {
            return "Sort Album";
        }
    },

    SORT_ALBUM_ARTIST {
        @Override
        public String toString() {
            return "Sort Album Artist";
        }
    },

    SORT_ARTIST {
        @Override
        public String toString() {
            return "Sort Artist";
        }
    },

    SORT_NAME {
        @Override
        public String toString() {
            return "Sort Name";
        }
    },

    TOTAL_TIME {
        @Override
        public String toString() {
            return "Total Time";
        }
    },

    TRACK_COUNT {
        @Override
        public String toString() {
            return "Track Count";
        }
    },

    TRACK_ID {
        @Override
        public String toString() {
            return "Track ID";
        }
    },

    TRACK_NUMBER {
        @Override
        public String toString() {
            return "Track Number";
        }
    },

    TRACK_TYPE {
        @Override
        public String toString() {
            return "Track Type";
        }
    },

    YEAR {
        @Override
        public String toString() {
            return "Year";
        }
    },

    VIDEO_HEIGHT {
        @Override
        public String toString() {
            return "Video Height";
        }
    },

    VIDEO_WIDTH {
        @Override
        public String toString() {
            return "Video Width";
        }
    },

}
