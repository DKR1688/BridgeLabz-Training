package MusicPlaylistManager;

public class SongApp {
	public static void main(String[] args) {
        PlaylistManager manager = new PlaylistManager();

        try {
            manager.addSong(new Song("Shape of You", "Ed Sheeran"));
            manager.addSong(new Song("Blinding Lights", "The Weeknd"));
            manager.addSong(new Song("Shape of You", "Ed Sheeran")); // duplicate
        } catch (SongAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        manager.showPlaylist();

        manager.playSong();
        manager.playSong();

        manager.showHistory();
        manager.showPlaylist();
    }
}
