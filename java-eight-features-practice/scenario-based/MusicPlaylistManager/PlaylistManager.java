package MusicPlaylistManager;

import java.util.*;
public class PlaylistManager {
    private LinkedList<Song> playlist;
    private Stack<Song> history;
    private Set<Song> songSet;

    public PlaylistManager() {
        playlist = new LinkedList<>();
        history = new Stack<>();
        songSet = new HashSet<>();
    }

    public void addSong(Song song) throws SongAlreadyExistsException {
        if (songSet.contains(song)) {
            throw new SongAlreadyExistsException("Song already exists: " + song);
        }
        playlist.add(song);
        songSet.add(song);
        System.out.println("Added: " + song);
    }

    public void playSong() {
        if (playlist.isEmpty()) {
            System.out.println("No songs in playlist!");
            return;
        }
        Song current = playlist.removeFirst();
        history.push(current);
        System.out.println("Now playing: " + current);
    }

    public void showPlaylist() {
        System.out.println("Playlist: " + playlist);
    }

    public void showHistory() {
        System.out.println("Recently played: " + history);
    }
}
