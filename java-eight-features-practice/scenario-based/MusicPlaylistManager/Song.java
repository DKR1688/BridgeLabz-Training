package MusicPlaylistManager;

public class Song {
	private String title;
	private String artist;

	public Song(String title, String artist) {
		this.title = title;
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	@Override
	public String toString() {
		return "'" + title + "' by " + artist;
	}

	// Important: equals & hashCode for Set duplicate prevention
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Song))
			return false;
		Song song = (Song) o;
		return title.equals(song.title) && artist.equals(song.artist);
	}

	@Override
	public int hashCode() {
		return title.hashCode() + artist.hashCode();
	}
}