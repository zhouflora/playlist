package model;

import org.json.JSONObject;

// Represents a song having a name, an artist, an album, and genre
public class Song {

    private String name;
    private String artist;
    private String album;
    private String genre;

    // EFFECTS: constructs a song with its respective name, artist/creator,
    // album to which the song belongs on, and genre
    public Song(String name, String artist, String album, String genre) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("songName", name);
        json.put("artist", artist);
        json.put("album", album);
        json.put("genre", genre);

        return json;
    }
}
