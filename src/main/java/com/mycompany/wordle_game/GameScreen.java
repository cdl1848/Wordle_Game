package com.mycompany.wordle_game;

/**
 * GameScreen
 *
 * Represents the main gameplay view Acts as a container for all visual
 * components used during gameplay
 *
 * @author garrett
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The GameScreen class represents the primary game window displayed to the user
 * during gameplay.
 *
 * This class extends {@link javafx.application.Application} and is responsible
 * for initializing and displaying the graphical game interface.
 *
 * The GameScreen creates the {@link GameBoard} view component and embeds it
 * within a JavaFX Scene. It does not implement game logic; instead, it serves
 * as a visual container for gameplay interaction.
 *
 * @author cdl1848
 */
public class GameScreen extends Application {

    /**
     * Initializes and displays the main game stage.
     *
     * @param primaryStage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage primaryStage) {
        GameBoard board = new GameBoard();

        Scene scene = new Scene(board, 350, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Wordle");
        primaryStage.show();
    }
}
