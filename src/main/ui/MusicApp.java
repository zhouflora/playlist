package ui;

import model.Playlist;
import model.Song;

import java.util.List;
import java.util.Scanner;

// The console interface for a music organizer application
// Many methods in this class are taken from/inspired by the Teller App
// Reference to that app can be found here: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
public class MusicApp {
    private Playlist favourites;
    private Playlist playlist1;
    private Playlist playlist2;
    private Playlist playlist3;
    private Playlist playlist4;
    private Playlist playlist5;
    private Playlist playlist6;
    private Playlist playlist7;
    private Playlist playlist8;
    private Playlist playlist9;
    private Playlist playlist10;
    private Scanner input;

    // EFFECTS: start the console
    public MusicApp() {
        runMusicApp();
    }

    // Code taken directly from AccountNotRobust - TellerApp Class
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMusicApp() {
        System.out.println("Welcome to your very own Music Organizer!");
        boolean keepGoing = true;
        String command;

        initialize();

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
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // Method revamped from AccountNotRobust - TellerApp Class
    // MODIFIES: this
    // EFFECTS: initializes playlists
    private void initialize() {
        favourites = new Playlist("favourites");
        playlist1 = new Playlist("new playlist 1");
        playlist2 = new Playlist("new playlist 2");
        playlist3 = new Playlist("new playlist 3");
        playlist4 = new Playlist("new playlist 4");
        playlist5 = new Playlist("new playlist 5");
        playlist6 = new Playlist("new playlist 6");
        playlist7 = new Playlist("new playlist 7");
        playlist8 = new Playlist("new playlist 8");
        playlist9 = new Playlist("new playlist 9");
        playlist10 = new Playlist("new playlist 10");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // Code taken from AccountNotRobust - TellerApp Class
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add song");
        System.out.println("\tr -> remove song");
        System.out.println("\tv -> view playlists");
        System.out.println("\tc -> change playlist name");
        System.out.println("\te -> edit playlist description");
        System.out.println("\tq -> quit");
    }

    // Code inspired by doDeposit() in AccountNotRobust - TellerApp.java
    // MODIFIES: this
    // EFFECTS: add song to a chosen playlist
    private void addSong() {
        Playlist selected = selectPlaylist();
        System.out.print("Enter title of song: ");
        String name = input.next();
        System.out.print("Enter song's artist: ");
        String artist = input.next();
        System.out.print("Enter song's album name: ");
        String album = input.next();
        System.out.print("Enter genre of song: ");
        String genre = input.next();

        selected.addSong(name, artist, album, genre);

        System.out.print("\n Song has been successfully added!\n");

    }

    // MODIFIES: this
    // EFFECTS: remove song from chosen playlist
    public void removeSong() {
        Playlist selected = selectPlaylist();
        System.out.print("\n Enter name of song you wish to remove: ");
        String name = input.next();
        System.out.print("\n Enter artist of song you wish to remove: ");
        String artist = input.next();
        System.out.print("\n Enter album to which the song you wish to remove belongs: ");
        String album = input.next();
        System.out.print("\n Enter genre of song you wish to remove: ");
        String genre = input.next();

        selected.removeSong(name, artist, album, genre);
        System.out.print("\n Song has been successfully removed! \n");
    }

    // EFFECTS: display names of all songs that have been added to that playlist
    public void viewPlaylistOverview() {
        Playlist selected = selectPlaylist();
        List<String> display = selected.viewPlaylist(selected);

        System.out.printf("\n" + selected.getPlaylistName());
        System.out.printf(selected.getDescription());
        System.out.printf("\n The songs in this playlist are " + String.valueOf(display));

        System.out.print("\n Would you like to view the details of a particular song?");
        System.out.print("\n y or n\n");
        String result = input.next();

        if (result.equals("y")) {
            System.out.print("\n Please type the name of the song you would like to view\n");
            String name = input.next();
            Song song = selected.getSongDetails(name);
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
        Playlist selected = selectPlaylist();
        System.out.println("What should the new name be?");
        String changeTo = input.next();
        selected.setPlaylistName(input.next());

        System.out.printf("Playlist name changed to:\n" + selected.getPlaylistName());
    }

    // MODIFIES: this
    // EFFECTS: change description of selected playlist
    public void editDescription() {
        System.out.println("\n Change playlist description?");
        Playlist selected = selectPlaylist();
        System.out.println("What should the new description be?");
        String changeTo = input.next();
        selected.setPlaylistName(input.next());

        System.out.printf("Description has been successfully changed to: \n"
                + selected.getDescription());
    }


    // Code inspired by selectAccount() in AccountNotRobust - TellerApp
    // EFFECTS: prompts user to select between available playlists
    // and returns selection
    private Playlist selectPlaylist() {
        String selection = ""; //force entry into loop

        while (!(selection.equals("f") || selection.equals("p1")
                || selection.equals("p2") || selection.equals("p3")
                || selection.equals("p4") || selection.equals("p5")
                || selection.equals("p6") || selection.equals("p7")
                || selection.equals("p8") || selection.equals("p9")
                || selection.equals("p10"))) {
            playlistDefinitions();
            selection = input.next();
            selection = selection.toLowerCase();
        }

        return playlistSelections(selection);

    }

    // EFFECTS: print out full playlist names for user readability
    public void playlistDefinitions() {
        System.out.println("\nType...");
        System.out.print("\t f for " + favourites.getPlaylistName());
        System.out.print("\n \t p1 for " + playlist1.getPlaylistName());
        System.out.print("\n \t p2 for " + playlist2.getPlaylistName());
        System.out.print("\n \t p3 for " + playlist3.getPlaylistName());
        System.out.print("\n \t p4 for " + playlist4.getPlaylistName());
        System.out.print("\n \t p5 for " + playlist5.getPlaylistName());
        System.out.print("\n \t p6 for " + playlist6.getPlaylistName());
        System.out.print("\n \t p7 for " + playlist7.getPlaylistName());
        System.out.print("\n \t p8 for " + playlist8.getPlaylistName());
        System.out.print("\n \t p9 for " + playlist9.getPlaylistName());
        System.out.print("\n \t p10 for " + playlist10.getPlaylistName());
        System.out.print("\n");
    }

    // EFFECTS: returns user's playlist selection
    public Playlist playlistSelections(String selection) {
        if (selection.equals("f")) {
            return favourites;
        } else if (selection.equals("p1")) {
            return playlist1;
        } else if (selection.equals("p2")) {
            return playlist2;
        } else if (selection.equals("p3")) {
            return playlist3;
        } else if (selection.equals("p4")) {
            return playlist4;
        } else if (selection.equals("p5")) {
            return playlist5;
        } else if (selection.equals("p6")) {
            return playlist6;
        } else if (selection.equals("p7")) {
            return playlist7;
        } else if (selection.equals("p8")) {
            return playlist8;
        } else if (selection.equals("p9")) {
            return playlist9;
        } else {
            return playlist10;
        }
    }

}
