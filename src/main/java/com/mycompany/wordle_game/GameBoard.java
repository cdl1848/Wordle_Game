/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wordle_game;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author garrett
 */
public class GameBoard extends GridPane{
    
    public GameBoard() {
        
        // Set spacing between grids
        setHgap(5);
        setVgap(5);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                TextField textField = new TextField();

                // Make text field into a square
                textField.setPrefSize(50, 50);
                textField.setMinSize(50, 50);
                textField.setMaxSize(50, 50);

                // Center text
                textField.setStyle("-fx-alignment: center; -fx-font-size: 18");

                // Restrict text to one uppercase character
                textField.textProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            if (!newValue.matches("[a-zA-Z]?")) {
                                textField.setText(oldValue);
                            } else {
                                textField.setText(newValue.toUpperCase());
                            }
                        }
                );

                add(textField, col, row);
            }
        }
    }
}
