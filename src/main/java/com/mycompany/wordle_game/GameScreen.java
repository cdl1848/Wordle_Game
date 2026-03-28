package com.mycompany.wordle_game;

/**
 * GameScreen
 * 
 * Represents the main game play view
 * Acts as a container for all visual components used during game play
 * 
 * @author garrett
 */

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameScreen {
    
    public void show(Stage stage) {
        Controller controller = new Controller();
        controller.startGame();
        
        GameBoard board = new GameBoard(controller);

        Scene scene = new Scene(board, 350, 400);
        
        stage.setScene(scene);
        stage.setTitle("Wordle");
    }
}
