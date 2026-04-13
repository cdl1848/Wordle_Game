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
import javafx.scene.text.FontPosture;

public class HomePage extends Application {

    private Controller controller;
    
    public void start(Stage primaryStage) {
        controller = new Controller();

        // Title
        Text title = new Text("Word Blitz");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        Text HishScoreModeOne = new Text("High Score: " + controller.getModeOneHighScore());
        HishScoreModeOne.setFill(Color.WHITE);
        
        Text LastScoreModeOne= new Text("Last Score: " + controller.getModeOneLastScore());
        LastScoreModeOne.setFill(Color.WHITE);
        
        Text modeThreeWins = new Text("Wins: " + controller.getModeThreeWins());
        modeThreeWins.setFill(Color.WHITE);
        
        Text modeThreeLosses= new Text("Loses: " + controller.getModeThreeLosses());
        modeThreeLosses.setFill(Color.WHITE);
        
         // Mode One Button
        Button modeOneButton = new Button("Scored");
        modeOneButton.setPrefSize(120, 40);
        modeOneButton.setStyle("-fx-background-color: #6ca965;");
        modeOneButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen();
            primaryStage.setFullScreen(false);
            primaryStage.setResizable(false);
            gameScreen.show(primaryStage);
        });
        VBox modeOneBox = new VBox(5);
        modeOneBox.setAlignment(Pos.CENTER);
        modeOneBox.getChildren().addAll(
                modeOneButton,
                HishScoreModeOne,
                LastScoreModeOne);

        // Mode Two Button
        Button modeTwoButton = new Button("Infinite");
        modeTwoButton.setPrefSize(120, 40);
        modeTwoButton.setStyle("-fx-background-color: #6ca965;");
        modeTwoButton.setOnAction(e -> {
            GameScreenModeThree modeThreeScreen = new GameScreenModeThree();
            modeThreeScreen.show(primaryStage);
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
       

        // Main layout
        VBox layout = new VBox(30);
        layout.setStyle("-fx-background-color: #5C5857;");
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, buttonLayout);
        

        Scene homeScene = new Scene(layout, 350, 400);
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Word Blitz Home");
        primaryStage.show();
    }
}
