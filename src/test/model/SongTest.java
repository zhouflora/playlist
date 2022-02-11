package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    private Song testSong;

    @BeforeEach
    void runBefore() {
        testSong = new Song("Ivy", "Frank Ocean", "Blonde", "R&B");
    }

    @Test
    void testConstructor() {
        assertEquals("Ivy", testSong.getName());
        assertEquals("Frank Ocean", testSong.getArtist());
        assertEquals("Blonde", testSong.getAlbum());
        assertEquals("R&B", testSong.getGenre());
    }
}