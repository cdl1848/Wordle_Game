package com.mycompany.wordle_game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * GameScreen
 *
 * Represents the main game play view Acts as a container for all visual
 * components used during game play
 *
 * @author garrett
 */
public class GameScreenModeThree{

    private Controller controller;
    private Label winsLabel;
    private Label lossesLabel;

    public void show(Stage stage) {
        controller = new Controller();
        controller.startModeThree();

        // Used for debugging purposes
        System.out.println(controller.debugWord());

        winsLabel = new Label("Wins: " + controller.getModeThreeWins());
        winsLabel.setTextFill(Color.WHITE);
        lossesLabel = new Label("Losses: " + controller.getModeThreeLosses());
        lossesLabel.setTextFill(Color.WHITE);

        VBox topBox = new VBox(10, winsLabel, lossesLabel);
        topBox.setStyle("-fx-background-color: #5C5857;");
        topBox.setAlignment(Pos.CENTER);

        GameBoard board2 = new GameBoard(controller, this::refreshScore);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setStyle("-fx-background-color: #5C5857;");
        root.setCenter(board2);

        Scene scene = new Scene(root, 350, 400);

        stage.setScene(scene);
        stage.setTitle("WordBlitz");
    }

    private void refreshScore() {
        winsLabel.setText("Wins: " + controller.getModeThreeWins());

        if (controller.currentStatus == Controller.Status.GAME_OVER) {
            lossesLabel.setText("Game Over!");
        } else {
            lossesLabel.setText("Losses: " + controller.getModeThreeLosses());
        }
    }
}
