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

    @Test
    void testSetSong() {
        testSong.setName("777");
        assertEquals("777", testSong.getName());
        testSong.setArtist("Joji");
        assertEquals("Joji", testSong.getArtist());
        testSong.setAlbum("Nectar");
        assertEquals("Nectar", testSong.getAlbum());
        testSong.setGenre("Indie");
        assertEquals("Indie",testSong.getGenre());
    }
}