package ui;

import model.Playlist;
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
    private JTextField userInputS = new JTextField();
    private JTextField userInputArt = new JTextField();
    private JTextField userInputAlb = new JTextField();
    private JTextField userInputG = new JTextField();
    private String songNameInput;
    private String artistNameInput;
    private String albumNameInput;
    private String genreNameInput;
    private Playlist playlist;

    private final JDesktopPane desktop;
    private final JInternalFrame controlPanel;

    private static final String JSON_STORE = "./data/playlist.json";
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;

    public MusicAppGUI() {
        playlist = new Playlist("Favourites");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        desktop = new JDesktopPane();
        controlPanel = new JInternalFrame("Control Panel", false, false, false, false);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.validate();

        setContentPane(desktop);
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


    /**
     * Helper to add control buttons.
     */
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 3));

        buttonPanel.add(new JLabel("Enter song name"));
        buttonPanel.add(userInputS);
        buttonPanel.add(new JButton("Submit"));
        songNameInput = userInputS.getText();

        buttonPanel.add(new JLabel("Enter artist name"));
        buttonPanel.add(userInputArt);
        buttonPanel.add(new JButton("Submit"));
        artistNameInput = userInputArt.getText();

        buttonPanel.add(new JLabel("Enter album name"));
        buttonPanel.add(userInputAlb);
        buttonPanel.add(new JButton("Submit"));
        albumNameInput = userInputAlb.getText();

        buttonPanel.add(new JLabel("Enter genre name"));
        buttonPanel.add(userInputG);
        buttonPanel.add(new JButton("Submit"));
        genreNameInput = userInputG.getText();

        buttonPanel.add(saveButton());
        buttonPanel.add(loadButton());

        controlPanel.add(buttonPanel, BorderLayout.WEST);
        addSong();
        desktop.repaint();
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

    // EFFECTS: loads playlist from file
    private void loadPlaylist() {
        try {
            playlist = jsonReader.read();
            System.out.println("Loaded " + playlist.getPlaylistName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            ImageIcon image = new ImageIcon("./data/crying cat.jpg");
            JLabel imageLabel = new JLabel(image);
        }
    }

    // EFFECTS: see the songs in the playlist last loaded
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

    // EFFECTS: see the songs in the playlist last loaded
    private void displayPlaylist(JTextArea showSongs) {
        String songs = "";

        for (int i = 0; i < playlist.getSongCollection().size(); i++) {
            songs += (i + 1) + playlist.getSongCollection().get(i).getName() + " - "
                    + playlist.getSongCollection().get(i).getArtist() + ":\n";
        }
        showSongs.setText(songs);
    }

    // EFFECTS: adds new song to playlist
    private void addSong() {
        playlist.addSong(songNameInput, artistNameInput, albumNameInput, genreNameInput);
    }

    /**
     * Helper to centre main application window on desktop
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            savePlaylist();
        } else if (e.getSource() == loadButton) {
            loadPlaylist();
        } else if (e.getSource() == displaySongsButton) {
            displayPlaylist(showSongs);
        }
    }

    // starts the application
    public static void main(String[] args) {
        new MusicAppGUI();
    }
}
