package persistence;

import model.Playlist;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads playlist information from JSON data stored in file
// Modeled after Code in JsonSerializationDemo, repo found here:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Based on public Playlist read() thows IDException {...}
    // EFFECTS: reads playlist from file and returns it;
    // throws IDException if error occurs reading data from file
    public Playlist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlaylist(jsonObject);
    }

    // Method taken from code in JSONReader class, repo found here:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: reads source file as Playlist and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Playlist from JSON object and returns it
    private Playlist parsePlaylist(JSONObject jsonObject) {
        String name = jsonObject.getString("playlistName");
        Playlist playlist = new Playlist(name);
        addSongs(playlist, jsonObject);
        addDescription(playlist, jsonObject);

        return playlist;
    }

    // MODIFIES: playlist
    // EFFECTS: parses song from JSON object and adds it to workroom
    private void addDescription(Playlist playlist, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        playlist.setDescription(description);
    }

    // MODIFIES: playlist
    // EFFECTS: parses song from JSON object and adds it to playlist
    private void addSong(Playlist playlist, JSONObject jsonObject) {
        String songName = jsonObject.getString("songName");
        String artist = jsonObject.getString("artist");
        String album = jsonObject.getString("album");
        String genre = jsonObject.getString("genre");
        playlist.addSong(songName, artist, album, genre);
    }

    // MODIFIES: playlist
    // EFFECTS: parses songs from JSON object and adds them to playlist
    private void addSongs(Playlist playlist, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("songs");
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json;
            addSong(playlist, nextSong);
        }
    }

}
