package persistence;

import model.Playlist;
import model.Song;

import static org.junit.jupiter.api.Assertions.assertEquals;

// methods for helping test json code functionality
public class JsonTest {
    // EFFECTS: helps confirm that the inputted song is correct
    protected void checkSong(String name, String artist, String album, String genre, Song song) {
        assertEquals(name, song.getName());
        assertEquals(artist, song.getArtist());
        assertEquals(album, song.getAlbum());
        assertEquals(genre, song.getGenre());
    }

    // EFFECTS: verifies that the inputted playlist description is correct/ up to date
    protected void checkDescription(String desc, Playlist playlist) {
        assertEquals(desc, playlist.getDescription());
    }
}
