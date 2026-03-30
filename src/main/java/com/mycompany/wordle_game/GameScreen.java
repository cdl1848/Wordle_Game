package com.mycompany.wordle_game;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * GameScreen
 * 
 * Represents the main game play view
 * Acts as a container for all visual components used during game play
 * 
 * @author garrett
 */
public class GameScreen {
    
    public void show(Stage stage) {
        Controller controller = new Controller();
        controller.startGame();
        
        // Used for debugging purposes
        System.out.println(controller.debugWord());
        
        GameBoard board = new GameBoard(controller);
        Scene scene = new Scene(board, 350, 400);
        
        stage.setScene(scene);
        stage.setTitle("Wordle");
    }
}
