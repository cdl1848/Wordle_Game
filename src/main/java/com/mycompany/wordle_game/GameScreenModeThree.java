package com.mycompany.wordle_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.Cursor;


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
    private Label scoreLabel;
    private Label lossesLabel;
    private Button returnHome;
    
    /**
     * Constructs a GameScreenModeThree with a reference to the central controller
     * @param controller used to retrieve game state, scores, and process game logic
     */
    public GameScreenModeThree(Controller controller) {
        this.controller = controller;
    }
    /**
     * Builds and displays the main game scene on the provided stage.
     * 
     * Initializes Mode One, creates UI components (score, lives, board, and navigation),
     * and connects UI updates to the controller state.
     *
     * @param stage the primary stage where the game scene will be displayed
     * @param homeScene the scene to return to when the home button is pressed
     * @param refreshCallback callback executed when returning home to refresh external UI state
     */
    public void show(Stage stage, Scene homeScene, Runnable refreshCallback) {
        controller.startModeThree();

        // Used for debugging purposes
        System.out.println(controller.debugWord());

        scoreLabel = new Label("Wins : " + controller.getModeThreeWins());
        scoreLabel.setTextFill(Color.WHITE);
        lossesLabel = new Label("Losses: " + controller.getModeThreeLosses());
        lossesLabel.setTextFill(Color.WHITE);

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
        returnHome.setCursor(Cursor.HAND) ;


        // Remove padding
        returnHome.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        returnHome.setOnAction(event -> {
        refreshCallback.run();  // Refresh the HomePage scores
        stage.setScene(homeScene);
        });

        VBox gameContainer = new VBox(10);
        gameContainer.setAlignment(Pos.CENTER);

        // Top bar contains score, losses, and home button
        BorderPane topBar = new BorderPane();

        // Wins and losses on the left
        VBox centerBox = new VBox(5, scoreLabel, lossesLabel);
        centerBox.setAlignment(Pos.CENTER);

        // Home button on the right
        HBox rightBox = new HBox(returnHome);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        topBar.setCenter(centerBox);
        topBar.setRight(rightBox);
        // Set top bar width to be less than board for alignment purposes
        topBar.setMaxWidth(250);

        topBar.setStyle("-fx-background-color: #5C5857;");

        GameBoard board = new GameBoard(controller, this::refreshWins );

        gameContainer.getChildren().addAll(topBar, board);
        
        BorderPane root = new BorderPane();
        root.setCenter(gameContainer);
        root.setStyle("-fx-background-color: #5C5857;");

        Scene scene = new Scene(root, 350, 400);
        // Sets opacity for disabled text fields to normal
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("Word Blitz");
    }
    /**
     * Refreshes all displayed statistics on the home screen.
     * 
     * Updates:
     *  - Mode Three total wins and losses
     * 
     * Values are retrieved from the controller, which delegates to persistence.
     */
    private void refreshWins () {
        scoreLabel.setText("Wins : " + controller.getModeThreeWins ());

        if (controller.currentStatus == Controller.Status.GAME_OVER) {
            lossesLabel.setText("Game Over!");
        } else {
            lossesLabel.setText("Losses: " + controller.getModeThreeLosses());
        }
        
        // Used for debugging purposes
        System.out.println(controller.debugWord());
    }
}


