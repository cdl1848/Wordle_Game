package com.mycompany.wordle_game;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * GameBoard
 *
 * Responsible for managing the grid of text fields that represent the playable
 * word grid (6 rows x 5 columns)
 *
 * Sources used for text formatting: https://stackoverflow.com/a/26670258
 * https://stackoverflow.com/a/5238524
 *
 * @author garrett
 */
public class GameBoard extends GridPane {

    /**
     * A 6x5 two-dimensional array of TextField objects representing the
     * playable grid. Each row corresponds to a guess attempt, and each column
     * corresponds to a letter position.
     */
    private TextField[][] grid = new TextField[6][5];

    /**
     * Constructs the GameBoard and initializes the 6x5 grid layout. Each
     * TextField is configured to: - Maintain square dimensions - Center-align
     * text - Accept only one alphabetical character - Automatically move focus
     * forward upon valid input
     */
    public GameBoard() {

        setHgap(5);
        setVgap(5);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                TextField textField = new TextField();

                textField.setPrefSize(50, 50);
                textField.setMinSize(50, 50);
                textField.setMaxSize(50, 50);

                textField.setStyle("-fx-alignment: center; -fx-font-size: 18");

                final int currentRow = row;
                final int currentCol = col;

                textField.textProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            if (!newValue.matches("[a-zA-Z]?")) {
                                textField.setText(oldValue);
                            } else {
                                textField.setText(newValue.toUpperCase());
                            }

                            if (newValue.length() == 1) {
                                moveForward(currentRow, currentCol);
                            }
                        }
                );

                grid[row][col] = textField;
                add(textField, col, row);
            }
        }
    }

    /**
     * Moves focus to the next TextField to the right when a letter is entered.
     *
     * @param row the current row index
     * @param col the current column index
     */
    private void moveForward(int row, int col) {
        if (col < 4) {
            grid[row][col + 1].requestFocus();
        }
    }

    /**
     * Moves focus to the previous TextField to the left when deleting input.
     *
     * @param row the current row index
     * @param col the current column index
     */
    private void moveBackward(int row, int col) {
        if (col > 0) {
            grid[row][col - 1].requestFocus();
        }
    }

    /**
     * Returns the 2D grid of TextField components.
     *
     * @return the 6x5 array representing the game board
     */
    public TextField[][] getGrid() {
        return grid;
    }
}
