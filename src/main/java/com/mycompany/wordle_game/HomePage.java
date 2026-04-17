package com.mycompany.wordle_game;

/**
 * @author cdl1848
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.Cursor;

    /**
     * HomePage
     *
     * Entry point for the JavaFX application and main menu for the game.
     *   
     * Displays available game modes and persistent player statistics.
     * Provides navigation to the different game screens and refreshes displayed scores when returning from gameplay.
     * 
     * Acts as the top-level UI container and initializes the Controller
     *
     * @author garrett
     */
public class HomePage extends Application {

    private Controller controller;
    private Text highScoreModeOne;
    private Text lastScoreModeOne;
    private Text modeThreeWins;
    private Text modeThreeLosses;
    
    /**
     * Builds the home screen UI using JavaFX
     * 
     * Initializes the controller, creates buttons, and displays scores. 
     * @param primaryStage the main stage for the aplication
     */
    public void start(Stage primaryStage) {
        controller = new Controller();

        // Title
        Text title = new Text("Word Blitz");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        
        highScoreModeOne = new Text();
        highScoreModeOne.setFill(Color.WHITE);

        lastScoreModeOne = new Text();
        lastScoreModeOne.setFill(Color.WHITE);

        modeThreeWins = new Text();
        modeThreeWins.setFill(Color.WHITE);

        modeThreeLosses = new Text();
        modeThreeLosses.setFill(Color.WHITE);
        
        // Main layout
        VBox layout = new VBox(30);
        layout.setStyle("-fx-background-color: #5C5857;");
        layout.setAlignment(Pos.CENTER);
        Scene homeScene = new Scene(layout, 350, 400);
        homeScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
            if (newWindow != null) {
                newWindow.setOnShown(event -> refreshScores());
            }
        });
        // Mode One Button
        Button modeOneButton = new Button("Scored");
        modeOneButton.setPrefSize(120, 40);
        modeOneButton.setStyle("-fx-background-color: #6ca965;");
        modeOneButton.setCursor(Cursor.HAND) ;
        modeOneButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen(controller);
            primaryStage.setFullScreen(false);
            primaryStage.setResizable(false);
            gameScreen.show(primaryStage,homeScene,this::refreshScores );
        });
        VBox modeOneBox = new VBox(5);
        modeOneBox.setAlignment(Pos.CENTER);
        modeOneBox.getChildren().addAll(
                modeOneButton,
                highScoreModeOne,
                lastScoreModeOne);

        // Mode Two Button
        Button modeTwoButton = new Button("Infinite");
        modeTwoButton.setPrefSize(120, 40);
        modeTwoButton.setStyle("-fx-background-color: #6ca965;");
        modeTwoButton.setCursor(Cursor.HAND) ;
        modeTwoButton.setOnAction(e -> {
            GameScreenModeThree modeThreeScreen = new GameScreenModeThree(controller);
            modeThreeScreen.show(primaryStage, homeScene, this::refreshScores);
        });
        VBox modeTwoBox = new VBox(5);
        modeTwoBox.setAlignment(Pos.CENTER);
        modeTwoBox.getChildren().addAll(
                modeTwoButton,
                modeThreeWins,
                modeThreeLosses);

        // Container for buttons (positions them side by side)
        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(modeOneBox, modeTwoBox );
       
        layout.getChildren().addAll(title, buttonLayout);
        refreshScores();


        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Word Blitz Home");
        primaryStage.show();
    }
    /**
      * Refreshes all displayed statistics on the home screen.
     * 
     * Updates:
     *  - Mode One high score and last score
     *  - Mode Three total wins and losses
     * 
     * Values are retrieved from the controller, which delegates to persistence.
     */
    private void refreshScores() {
    highScoreModeOne.setText("High Score: " + controller.getModeOneHighScore());
    lastScoreModeOne.setText("Last Score: " + controller.getModeOneLastScore());
    modeThreeWins.setText("Wins: " + controller.getModeThreeWins());
    modeThreeLosses.setText("Losses: " + controller.getModeThreeLosses());
}
}


