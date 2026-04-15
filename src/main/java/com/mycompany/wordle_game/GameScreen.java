package com.mycompany.wordle_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class GameScreen {

    private Controller controller;
    private Label scoreLabel;
    private Label livesLabel;
    private Button returnHome;

    public void show(Stage stage, Scene homeScene) {
        controller = new Controller();
        controller.startModeOne();

        scoreLabel = new Label("Score: " + controller.getTotalScore());
        scoreLabel.setTextFill(Color.WHITE);
        livesLabel = new Label("Lives: " + controller.getLives());
        livesLabel.setTextFill(Color.WHITE);

        // Create image view for return home button
        Image homeImg = new Image(getClass().getResource("/icons/home.png").toExternalForm());
        ImageView homeImgView = new ImageView(homeImg);

        // Set size
        homeImgView.setFitWidth(30);
        homeImgView.setFitHeight(30);
        // Maintain aspect ratio
        homeImgView.setPreserveRatio(true);

        returnHome = new Button();
        returnHome.setGraphic(homeImgView);

        // Remove padding
        returnHome.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        returnHome.setOnAction(event -> {
            stage.setScene(homeScene);
        });

        VBox gameContainer = new VBox(10);
        gameContainer.setAlignment(Pos.CENTER);

        // Top bar contains score, lives, and home button
        BorderPane topBar = new BorderPane();

        // Score and lives on the left
        VBox centerBox = new VBox(5, scoreLabel, livesLabel);
        centerBox.setAlignment(Pos.CENTER);

        // Home button on the right
        HBox rightBox = new HBox(returnHome);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        topBar.setCenter(centerBox);
        topBar.setRight(rightBox);
        // Set top bar width to be less than board for alignment purposes
        topBar.setMaxWidth(250);

        topBar.setStyle("-fx-background-color: #5C5857;");

        GameBoard board = new GameBoard(controller, this::refreshScore);

        gameContainer.getChildren().addAll(topBar, board);
        
        BorderPane root = new BorderPane();
        root.setCenter(gameContainer);
        root.setStyle("-fx-background-color: #5C5857;");

        Scene scene = new Scene(root, 350, 400);
        stage.setScene(scene);
        stage.setTitle("Word Blitz");
    }

    private void refreshScore() {
        scoreLabel.setText("Score: " + controller.getTotalScore());

        if (controller.currentStatus == Controller.Status.GAME_OVER) {
            livesLabel.setText("Game Over!");
        } else {
            livesLabel.setText("Lives: " + controller.getLives());
        }
        
        // Used for debugging purposes
        System.out.println(controller.debugWord());
    }
}
