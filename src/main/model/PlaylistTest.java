package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaylistTest {
    private Playlist testPlaylist;
    private Song song;
    private List<Song> songList;

    @BeforeEach
    void runBefore() {
        testPlaylist = new Playlist("favourites");
        song = new Song("Ivy", "Frank Ocean", "Blonde", "R&B");
        songList = new LinkedList<>();
    }

    @Test
    void testConstructor() {
        assertEquals("favourites", testPlaylist.getPlaylistName());
        assertEquals("", testPlaylist.getDescription());
        assertEquals(0, testPlaylist.getNumSongs());
        assertTrue(testPlaylist.getSongCollection().isEmpty());
    }

    @Test
    void testSetPlaylistName() {
        testPlaylist.setPlaylistName("renamed");
        assertEquals("renamed", testPlaylist.getPlaylistName());
    }

    @Test
    void testSetPlaylistDescription() {
        testPlaylist.setDescription("new desc!");
        assertEquals("new desc!", testPlaylist.getDescription());
    }

    @Test
    void testAddSongValid() {
        testPlaylist.addSong("Ivy", "Frank Ocean", "Blonde", "R&B");
        assertEquals("Ivy", testPlaylist.getSongCollection().get(0).getName());
    }

    @Test
    void testRemoveSongSuccess() {
        testPlaylist.addSong("Ivy", "Frank Ocean", "Blonde", "R&B");
        testPlaylist.addSong("777", "Joji", "Nectar", "R&B");
        assertEquals(2, testPlaylist.getNumSongs());

        testPlaylist.removeSong(1);
        assertEquals(1, testPlaylist.getNumSongs());
        assertEquals("777", testPlaylist.getSongCollection().get(0).getName());
    }

    @Test
    void testRemoveSongDuplicates() {
        testPlaylist.addSong("Ivy", "Frank Ocean", "Blonde", "R&B");
        testPlaylist.addSong("777", "Joji", "Nectar", "R&B");
        testPlaylist.addSong("777", "Joji", "Nectar", "R&B");
        assertEquals(3, testPlaylist.getNumSongs());

        testPlaylist.removeSong(2);
        assertEquals(2, testPlaylist.getNumSongs());
        assertEquals("777", testPlaylist.getSongCollection().get(1).getName());
    }

    @Test
    void testGetSongDetailsValid() {
        testPlaylist.addSong("Ivy", "Frank Ocean", "Blonde", "R&B");
        testPlaylist.addSong("777", "Joji", "Nectar", "R&B");
        Song songTest = testPlaylist.getSongDetails("777");
        assertEquals("777", songTest.getName());
    }

    @Test
    void testGetSongDetailsNoEntries() {
        Song songTest = testPlaylist.getSongDetails("777");
        assertEquals(null, songTest);
    }

    @Test
    void testViewPlaylist() {
        testPlaylist.addSong("Ivy", "Frank Ocean", "Blonde", "R&B");
        testPlaylist.addSong("777", "Joji", "Nectar", "R&B");
        assertEquals(Arrays.asList("Ivy", "777"), testPlaylist.viewPlaylist(testPlaylist));
    }

    @Test
    void testViewPlaylistNoItems() {
        assertEquals(Arrays.asList(), testPlaylist.viewPlaylist(testPlaylist));
    }

}