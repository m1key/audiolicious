package me.m1key.audiolicious.services;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import me.m1key.audiolicious.commons.qualifiers.NullArtist;
import me.m1key.audiolicious.domain.entities.Artist;
import me.m1key.audiolicious.domain.entities.Library;
import me.m1key.audiolicious.domain.to.SongTo;
import me.m1key.audiolicious.objecthandler.handlers.SongService;

@Singleton
@Local({ CacheableSongService.class, SongService.class })
public class InMemoryCacheableSongService implements CacheableSongService {

	@EJB
	private ArtistRepository artistRepository;
	@Inject
	@NullArtist
	private Artist nullArtist;

	private Map<String, Artist> artistCache = new HashMap<String, Artist>();

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void addSong(SongTo songTo, Library library) {
		Artist artist = getOrCreateArtistByName(getAlbumArtistName(songTo));

		if (artist.addSong(songTo, new FullStatInfo(songTo, library))) {
			artistRepository.createArtist(artist);
		}
	}

	private String getAlbumArtistName(SongTo song) {
		if (song.getAlbumArtist() == null) {
			return song.getArtist();
		} else {
			return song.getAlbumArtist();
		}
	}

	private Artist getOrCreateArtistByName(String albumArtistName) {
		Artist artistFromCache = getArtistCache().get(albumArtistName);

		if (artistFromCache == null) {
			Artist artist = artistRepository.getArtist(albumArtistName);
			if (artist.equals(nullArtist)) {
				artist = new Artist(albumArtistName);
				artistRepository.createArtist(artist);
			}
			getArtistCache().put(albumArtistName, artist);
			return artist;
		} else {
			return artistFromCache;
		}
	}

	@Override
	public void initialise() {
		getArtistCache().clear();
	}

	@Override
	public void finalise() {
		getArtistCache().clear();
	}

	protected Map<String, Artist> getArtistCache() {
		return artistCache;
	}

}
