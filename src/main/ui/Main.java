package ui;

import java.io.FileNotFoundException;

// A more accessible way for users to run the Music App
public class Main {
    public static void main(String[] args) {
        try {
            new MusicApp();
            //new MusicAppGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
