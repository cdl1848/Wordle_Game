package com.mycompany.wordle_game;

import javafx.application.Application;
/**
 * Main
 */
public class Main {
    /**
     * Main
     *
     * Entry point for launching the JavaFX application.
     * Delegates control to the HomePage class, which initializes and displays the primary user interface.
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        Application.launch(HomePage.class, args);
    }
}


