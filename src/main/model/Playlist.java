package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.AbstractList;
import java.util.LinkedList;
import java.util.List;

// Represents a playlist having a name, description, number of songs, and list of songs,
// and all the edits that can be made to a playlist
public class Playlist implements Writable {

    private String playlistName;
    private String description;
    private int numSongs;
    private LinkedList<Song> collection;

    // EFFECTS: creates a playlist with a name, description, the number of songs that have been added,
    // and contains the list of songs that have been added
    public Playlist(String name) {
        this.playlistName = name;
        description = "";
        numSongs = 0;
        collection = new LinkedList<>();
    }

    // getters
    public String getPlaylistName() {
        return playlistName;
    }

    public String getDescription() {
        return description;
    }

    public int getNumSongs() {
        return numSongs;
    }

    public LinkedList<Song> getSongCollection() {
        return collection;
    }

    //setters
    public void setPlaylistName(String name) {
        this.playlistName = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    // REQUIRES: song name and artist cannot be empty strings, no dupes
    // MODIFIES: this
    // EFFECTS: adds a song to a playlist
    public void addSong(String name, String artist, String album, String genre) {
        Song song = new Song(name, artist, album, genre);
        collection.add(song);
        numSongs = numSongs + 1;

        EventLog.getInstance().logEvent(new Event("Song was added to playlist with Name: " + name
                + ", Artist: " + artist + ", Album: " + album + ", Genre: " + genre));
    }

    // MODIFIES: this
    // EFFECTS: removes a song from a playlist
    // and adjusts the count of songs still in the playlist accordingly
    public void removeSong(Song song) {
        collection.remove(song);
        numSongs = numSongs - 1;
        EventLog.getInstance().logEvent(new Event("Song was removed from playlist."));
    }

    // EFFECTS: from a given name, shows the details of the corresponding song,
    // including its name, artist, album, and genre, if it exists, otherwise nothing
    public Song getSongDetails(String name) {
        for (Song s : collection) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    // MODIFIES: this, Song
    // EFFECTS: display the names of all the songs that are contained in the
    // specified playlist
    public List<String> viewPlaylist(Playlist selected) {
        collection = selected.getSongCollection();
        List<String> listOfSongNames = new AbstractList<String>() {
            @Override
            public String get(int index) {
                return collection.get(index).getName();
            }

            @Override
            public int size() {
                return collection.size();
            }
        };
        EventLog.getInstance().logEvent(new Event("Playlist was viewed."));
        return listOfSongNames;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playlistName", playlistName);
        json.put("description", description);
        json.put("numSongs", numSongs);
        json.put("songs", songsToJson());
        return json;
    }

    // EFFECTS: returns songs in this workroom as a JSON array
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song song : collection) {
            jsonArray.put(song.toJson());
        }

        return jsonArray;
    }
}