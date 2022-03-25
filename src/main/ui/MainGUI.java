package ui;

import javax.swing.*;
import java.io.IOException;

// A more accessible way for users to run MusicAppGUI
public class MainGUI {
    public static void main(String[] args) {
        JFrame ui = new MusicAppGUI();
        ui.setVisible(true);
    }
}
