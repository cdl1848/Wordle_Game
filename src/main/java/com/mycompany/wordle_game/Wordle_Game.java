package com.mycompany.wordle_game;
/**
 * @author dallas
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Wordle_Game extends Application {
    
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello World!");
        Scene scene = new Scene(label, 400, 300);
        
        stage.setTitle("Wordle Game");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
       launch(args);
    }
}

