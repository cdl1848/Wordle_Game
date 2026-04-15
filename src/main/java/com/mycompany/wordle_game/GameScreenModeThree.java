package com.mycompany.wordle_game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
public class GameScreenModeThree {

    private Controller controller;
    private Label winsLabel;
    private Label lossesLabel;

    public void show(Stage stage, Scene homeScene) {
        controller = new Controller();
        controller.startModeThree();

        winsLabel = new Label("Wins: " + controller.getModeThreeWins());
        winsLabel.setTextFill(Color.WHITE);

        lossesLabel = new Label("Losses: " + controller.getModeThreeLosses());
        lossesLabel.setTextFill(Color.WHITE);

        // Home button (same as GameScreen)
        Image homeImg = new Image(getClass().getResource("/icons/home.png").toExternalForm());
        ImageView homeImgView = new ImageView(homeImg);
        homeImgView.setFitWidth(30);
        homeImgView.setFitHeight(30);
        homeImgView.setPreserveRatio(true);
        Button returnHome = new Button();
        returnHome.setGraphic(homeImgView);
        returnHome.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        returnHome.setOnAction(e -> stage.setScene(homeScene));

        BorderPane topBar = new BorderPane();

        VBox centerBox = new VBox(5, winsLabel, lossesLabel);
        centerBox.setAlignment(Pos.CENTER);

        HBox rightBox = new HBox(returnHome);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        topBar.setCenter(centerBox);
        topBar.setRight(rightBox);
        topBar.setMaxWidth(250);
        topBar.setStyle("-fx-background-color: #5C5857;");

        GameBoard board = new GameBoard(controller, this::refreshScore);

        VBox container = new VBox(10, topBar, board);
        container.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(container);
        root.setStyle("-fx-background-color: #5C5857;");

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
        
        // Used for debugging purposes
        System.out.println(controller.debugWord());
    }
}
