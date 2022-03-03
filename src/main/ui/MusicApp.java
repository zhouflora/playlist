package ui;

import model.Playlist;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// The console interface for a music organizer application
// Many methods in this class are taken from/inspired by the Teller App
// Reference to that app can be found here: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
public class MusicApp {
    private static final String JSON_STORE = "./data/playlist.json";
    private Playlist playlist;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: start the console
    public MusicApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        playlist = new Playlist("Favourites");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMusicApp();
    }

    // Code taken directly from AccountNotRobust - TellerApp Class
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMusicApp() {
        System.out.println("Welcome to your very own Music Organizer!");
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // Code revamped from AccountNotRobust - TellerApp Class
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addSong();
        } else if (command.equals("r")) {
            removeSong();
        } else if (command.equals("v")) {
            viewPlaylistOverview();
        } else if (command.equals("c")) {
            changeName();
        } else if (command.equals("e")) {
            editDescription();
        } else if (command.equals("s")) {
            savePlaylist();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: save the playlist to file
    private void savePlaylist() {
        try {
            jsonWriter.open();
            jsonWriter.write(playlist);
            jsonWriter.close();
            System.out.println("Saved " + playlist.getPlaylistName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            playlist = jsonReader.read();
            System.out.println("Loaded " + playlist.getPlaylistName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Code taken from AccountNotRobust - TellerApp Class
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add song");
        System.out.println("\tr -> remove song");
        System.out.println("\tv -> view playlist");
        System.out.println("\tc -> change playlist name");
        System.out.println("\te -> edit playlist description");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }

    // Code inspired by doDeposit() in AccountNotRobust - TellerApp.java
    // MODIFIES: this
    // EFFECTS: add song to a chosen playlist
    private void addSong() {
        System.out.print("Enter title of song: ");
        String name = input.next();
        System.out.print("Enter song's artist: ");
        String artist = input.next();
        System.out.print("Enter song's album name: ");
        String album = input.next();
        System.out.print("Enter genre of song: ");
        String genre = input.next();

        playlist.addSong(name, artist, album, genre);

        System.out.print("\n Song has been successfully added!\n");

    }

    // MODIFIES: this
    // EFFECTS: remove song from chosen playlist
    public void removeSong() {
        System.out.print("\n Enter name of song you wish to remove: ");
        String name = input.next();
        System.out.print("\n Enter artist of song you wish to remove: ");
        String artist = input.next();
        System.out.print("\n Enter album to which the song you wish to remove belongs: ");
        String album = input.next();
        System.out.print("\n Enter genre of song you wish to remove: ");
        String genre = input.next();

        playlist.removeSong(name, artist, album, genre);
        System.out.print("\n Song has been successfully removed! \n");
    }

    // EFFECTS: display names of all songs that have been added to that playlist
    public void viewPlaylistOverview() {
        System.out.printf("\n" + playlist.getPlaylistName());
        System.out.printf("\n" + playlist.getDescription());
        System.out.printf("\n The songs in this playlist are: \n");

        for (int i = 0; i < playlist.getSongCollection().size(); i++) {
            System.out.printf("\t %s %s \n", ((i + 1) + ")"),
                    playlist.getSongCollection().get(i).getName() + " - "
                    + playlist.getSongCollection().get(i).getArtist());
        }

        System.out.print("\n Would you like to view the details of a particular song?");
        System.out.print("\n y or n\n");
        String result = input.next();

        if (result.equals("y")) {
            System.out.print("\n Please type the name of the song you would like to view\n");
            String name = input.next();
            Song song = playlist.getSongDetails(name);
            System.out.print("\n Song name: " + song.getName()
                    + "\n Artist: " + song.getArtist()
                    + "\n Album: " + song.getAlbum()
                    + "\n Genre: " + song.getGenre() + "\n");
        } else if (result != "n") {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: change name of selected playlist
    public void changeName() {
        System.out.println("\nChange playlist name?");
        System.out.println("What should the new name be?");
        String changeTo = input.next();
        playlist.setPlaylistName(changeTo);

        System.out.printf("Playlist name changed to:\n" + playlist.getPlaylistName());
    }

    // MODIFIES: this
    // EFFECTS: change description of selected playlist
    public void editDescription() {
        System.out.println("\n Change playlist description?");
        System.out.println("What should the new description be?");
        String changeTo = input.next();
        playlist.setDescription(changeTo);

        System.out.printf("\n Description has been successfully changed to: \n"
                + playlist.getDescription());
    }

}