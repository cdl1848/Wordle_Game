package com.mycompany.wordle_game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * GameScreen
 *
 * Represents the main game play view Acts as a container for all visual
 * components used during game play
 *
 * @author garrett
 */
public class GameScreen {

    private Controller controller;
    private Label scoreLabel;
    private Label livesLabel;

    public void show(Stage stage) {
        controller = new Controller();
        controller.startModeOne();

        // Used for debugging purposes
        System.out.println(controller.debugWord());

        scoreLabel = new Label("Score: " + controller.getTotalScore());
        livesLabel = new Label("Lives: " + controller.getLives());

        VBox topBox = new VBox(10, scoreLabel, livesLabel);
        topBox.setAlignment(Pos.CENTER);

        GameBoard board = new GameBoard(controller, this::refreshScore);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(board);

        Scene scene = new Scene(root, 350, 400);

        stage.setScene(scene);
        stage.setTitle("Wordle");
    }

    private void refreshScore() {
        scoreLabel.setText("Score: " + controller.getTotalScore());

        if (controller.currentStatus == Controller.Status.GAME_OVER) {
            livesLabel.setText("Game Over!");
        } else {
            livesLabel.setText("Lives: " + controller.getLives());
        }
    }
}
