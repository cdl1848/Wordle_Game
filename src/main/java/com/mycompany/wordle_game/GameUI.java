package com.mycompany.wordle_game;

/**
 * Sources used for text formatting:
 * https://stackoverflow.com/a/26670258
 * https://stackoverflow.com/a/5238524
 * 
 * @author garrett
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GameBoard board = new GameBoard();

        Scene scene = new Scene(board, 350, 400);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wordle");
        primaryStage.show();
    }
}
