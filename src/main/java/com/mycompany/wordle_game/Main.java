package com.mycompany.wordle_game;

/**
 * @author dallas
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the Wordle Game application.
 *
 * The Main class does not contain game logic. Its responsibility is limited to
 * application startup and high-level scene initialization.
 *
 * @author cdl1848
 */
public class Main extends Application {

    /**
     * Initializes and displays the primary stage of the application. This
     * method is automatically invoked by the JavaFX runtime.
     *
     * @param stage the primary stage provided by the JavaFX framework
     */
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello World!");
        Scene scene = new Scene(label, 400, 300);

        stage.setTitle("Wordle Game");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The standard Java entry point. This method launches the JavaFX
     * application.
     *
     * @param args command-line arguments passed during application launch
     */
    public static void main(String[] args) {
        launch(args);
    }
}
