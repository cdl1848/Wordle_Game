package com.mycompany.wordle_game;

/**
 * GameScreen
 * 
 * Represents the main gameplay view
 * Acts as a container for all visual components used during gameplay
 * 
 * @author garrett
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameScreen extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        TESTMAIN board = new TESTMAIN();

        Scene scene = new Scene(board, 350, 400);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wordle");
        primaryStage.show();
    }
}
