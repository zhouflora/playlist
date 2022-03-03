package persistence;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Playlist playlist = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPlaylist() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlaylist.json");
        try {
            Playlist playlist = reader.read();
            assertEquals("Favourites", playlist.getPlaylistName());
            assertEquals(0, playlist.getSongCollection().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlaylist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlaylist.json");
        try {
            Playlist playlist = reader.read();
            assertEquals("Favourites", playlist.getPlaylistName());
            List<Song> songs = playlist.getSongCollection();
            assertEquals(1, songs.size());
            Song testSong = new Song("Cleopatra", "The Lumineers", "Cleopatra", "Indie Folk");
            checkSong("Cleopatra", "The Lumineers", "Cleopatra", "Indie Folk",
                    testSong);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
