package persistence;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests for JsonWriter class
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Playlist playlist = new Playlist("Favourites");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPlaylist() {
        try {
            Playlist playlist = new Playlist("Favourites");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlaylist.json");
            writer.open();
            writer.write(playlist);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlaylist.json");
            playlist = reader.read();
            assertEquals("Favourites", playlist.getPlaylistName());
            assertEquals(0, playlist.getNumSongs());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlaylist() {
        try {
            Playlist playlist = new Playlist("Favourites");
            playlist.addSong("Cleopatra", "The Lumineers", "Cleopatra", "Indie Folk");
            playlist.addSong("Eventually", "Tama Impala", "Current", "Alternative");
            playlist.addSong("Heat Lightning", "Mitski", "Laurel Hell", "Indie");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlaylist.json");
            writer.open();
            writer.write(playlist);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlaylist.json");
            playlist = reader.read();
            assertEquals("Favourites", playlist.getPlaylistName());
            assertEquals(3, playlist.getNumSongs());

            Song song1 = new Song("Cleopatra", "The Lumineers", "Cleopatra", "Indie Folk");
            Song song2 = new Song("Eventually", "Tama Impala", "Current", "Alternative");
            Song song3 = new Song("Heat Lightning", "Mitski", "Laurel Hell", "Indie");
            checkSong("Cleopatra", "The Lumineers", "Cleopatra", "Indie Folk", song1);
            checkSong("Eventually", "Tama Impala", "Current", "Alternative", song2);
            checkSong("Heat Lightning", "Mitski", "Laurel Hell", "Indie", song3);
            checkDescription("", playlist);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
