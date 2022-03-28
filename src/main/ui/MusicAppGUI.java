package ui;

import model.Playlist;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents application's main, visual window frame.
// Code heavily modeled after AlarmSystem, repo found here:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// Also had code influenced by this post online:
// https://stackoverflow.com/questions/26151920///
// java-save-user-input-as-a-string-in-a-jframe-gui
// and code base built from https://www.youtube.com/watch?v=Kmgo00avvEw
public class MusicAppGUI extends JFrame {
    private JLabel image;
    private JButton saveButton;
    private JButton loadButton;
    private JButton displaySongsButton;
    private JTextArea showSongs;
    private JButton submitSong = new JButton("Submit");
    private JButton submitArtist = new JButton("Submit");
    private JButton submitAlbum = new JButton("Submit");
    private JButton submitGenre = new JButton("Submit");
    private JTextField userInputS = new JTextField(10);
    private JTextField userInputArt = new JTextField(10);
    private JTextField userInputAlb = new JTextField(10);
    private JTextField userInputG = new JTextField(10);
    private String songNameInput;
    private String artistNameInput;
    private String albumNameInput;
    private String genreNameInput;
    private Playlist playlist;
    private Song song;

    private final JDesktopPane desktop;
    private final JInternalFrame controlPanel;

    private static final String JSON_STORE = "./data/playlist.json";
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;

    // EFFECTS: runs necessary fields and constructs the UI
    public MusicAppGUI() {
        playlist = new Playlist("Favourites");
        song = new Song("", "", "", "");
        songNameInput = "";
        artistNameInput = "";
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        desktop = new JDesktopPane();
        controlPanel = new JInternalFrame("Control Panel", false, false, false, false);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.validate();

        add(desktop);
        setTitle("Welcome to Your Very Own Music Organizer!");
        pack();

        addButtonPanel();
        viewPlaylist();

        controlPanel.setBounds(0, 0, 777, 333);
        controlPanel.setVisible(true);
        desktop.add(controlPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    //  EFFECTS: Helper to add control buttons to the control panel
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 3));

        songNameButtons(buttonPanel, userInputS);
        artistButtons(buttonPanel, userInputArt);
        albumButtons(buttonPanel, userInputAlb);
        genreButtons(buttonPanel, userInputG);

        buttonPanel.add(saveButton());
        buttonPanel.add(loadButton());

        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }

    // EFFECTS: Helper to create buttons for adding song name
    private void songNameButtons(JPanel buttonPanel, JTextField userInputS) {
        buttonPanel.add(new JLabel("Enter song name"));
        buttonPanel.add(userInputS);
        buttonPanel.add(submitSong);
        submitSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSongName(userInputS.getText());
                song.setName(userInputS.getText());
                songNameInput = userInputS.getText();
            }
        });
    }

    // EFFECTS: Helper to create buttons for adding song artist
    public void artistButtons(JPanel buttonPanel, JTextField userInputArt) {
        buttonPanel.add(new JLabel("Enter artist name"));
        buttonPanel.add(userInputArt);
        buttonPanel.add(submitArtist);
        submitArtist.addActionListener(submitArtist.getAction());
        submitArtist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setArtist(userInputArt.getText());
                song.setArtist(userInputArt.getText());
            }
        });
    }

    // EFFECTS: Helper to create buttons for adding song album
    public void albumButtons(JPanel buttonPanel, JTextField userInputAlb) {
        buttonPanel.add(new JLabel("Enter album name"));
        buttonPanel.add(userInputAlb);
        buttonPanel.add(submitAlbum);
        submitAlbum.addActionListener(submitAlbum.getAction());
        submitAlbum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSongAlbum(userInputAlb.getText());
                song.setAlbum(userInputAlb.getText());
            }
        });
    }

    // EFFECTS: Helper to create buttons for adding song genre
    public void genreButtons(JPanel buttonPanel, JTextField userInputG) {
        buttonPanel.add(new JLabel("Enter genre name"));
        buttonPanel.add(userInputG);
        buttonPanel.add(submitGenre);
        submitGenre.addActionListener(submitGenre.getAction());
        submitGenre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSongGenre(userInputG.getText());
                song.setGenre(userInputG.getText());
                addSong();
            }
        });
    }

    // EFFECTS: allows user to save their playlist and any edits made to songs
    private JButton saveButton() {
        JButton saveButton = new JButton("Save New Changes to Your Playlist");
        saveButton.setBounds(20, 20, 200, 30);
        controlPanel.add(saveButton, BorderLayout.AFTER_LAST_LINE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePlaylist();
            }
        });
        return saveButton;
    }

    // EFFECTS: allows user to load their last saved playlist
    private JButton loadButton() {
        JButton loadButton = new JButton("Open From Where You Left Off");
        loadButton.setBounds(250, 20, 200, 30);
        controlPanel.add(loadButton, BorderLayout.BEFORE_FIRST_LINE);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPlaylist();
            }
        });
        return loadButton;
    }

    // EFFECTS: saves any new songs or playlist edits by the user
    // and displays an image of a cat to show success
    private void savePlaylist() {
        try {
            jsonWriter.open();
            jsonWriter.write(playlist);
            jsonWriter.close();
            System.out.println("Saved " + playlist.getPlaylistName() + " to " + JSON_STORE);
            ImageIcon image = new ImageIcon("./data/happy-cat.jpg");
            JLabel imageLabel = new JLabel(image);
            controlPanel.add(imageLabel);
            imageLabel.setVisible(true);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads playlist from file and shows image of cat to show success
    private void loadPlaylist() {
        try {
            playlist = jsonReader.read();
            System.out.println("Loaded " + playlist.getPlaylistName() + " from " + JSON_STORE);
            ImageIcon image = new ImageIcon("./data/musical cat.jpg");
            JLabel imageLabel = new JLabel(image);
            controlPanel.add(imageLabel);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: create the text area for users to see the songs
    // in the playlist last loaded
    private void viewPlaylist() {
        JTextArea showSongs = new JTextArea();
        showSongs.setBounds(0, 500, 500, 10000);
        controlPanel.add(showSongs, BorderLayout.AFTER_LAST_LINE);

        JButton displaySongsButton = new JButton("Show my songs!");
        showSongs.setBounds(0, 400, 70, 70);
        controlPanel.add(displaySongsButton, BorderLayout.BEFORE_FIRST_LINE);
        displaySongsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPlaylist(showSongs);
            }
        });
    }

    // EFFECTS: organize the data of the playlist to show the user
    // names and artists of songs last saved
    private void displayPlaylist(JTextArea showSongs) {
        String songs = "";

        for (int i = 0; i < playlist.getSongCollection().size(); i++) {
            songs += (i + 1) + " " + playlist.getSongCollection().get(i).getName() + " - "
                    + playlist.getSongCollection().get(i).getArtist() + ":\n";
        }
        showSongs.setText(songs);

        ImageIcon image = new ImageIcon("./data/musical cat.jpg");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(700, 0, 50, 50);
        imageLabel.setVisible(true);
        desktop.add(imageLabel, BorderLayout.PAGE_END);
    }

    // EFFECTS: adds new song to playlist
    private void addSong() {
        playlist.addSong(songNameInput, artistNameInput, albumNameInput, genreNameInput);
        desktop.repaint();
        desktop.revalidate();
        controlPanel.repaint();
        controlPanel.revalidate();
    }

    // EFFECTS: Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // getters
    private String getSongName() {
        return songNameInput;
    }

    private String getArtist() {
        return artistNameInput;
    }

    private String getSongAlbum() {
        return albumNameInput;
    }

    private String getSongGenre() {
        return genreNameInput;
    }

    // setters
    private void setSongName(String name) {
        this.songNameInput = name;
    }

    private void setArtist(String artist) {
        artistNameInput = artist;
    }

    private void setSongAlbum(String album) {
        albumNameInput = album;
    }

    private void setSongGenre(String genre) {
        genreNameInput = genre;
    }

    // EFFECTS: starts the application
    public static void main(String[] args) {
        new MusicAppGUI();
    }
}
