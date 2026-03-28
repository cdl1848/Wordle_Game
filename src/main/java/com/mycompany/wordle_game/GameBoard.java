package com.mycompany.wordle_game;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

/**
 * GameBoard
 *
 * Responsible for managing the grid of text fields which represents the
 * playable word grid (6 rows x 5 columns)
 *
 * Sources used for text formatting: https://stackoverflow.com/a/26670258
 * https://stackoverflow.com/a/5238524
 *
 * Color hex values taken from: https://www.color-hex.com/color-palette/1012607
 *
 * @author garrett
 */
public class GameBoard extends GridPane {

    // 6x5 2D array of text field objects used to move between fields
    private TextField[][] grid = new TextField[6][5];

    private String userInput = "";

    String color;

    /**
     * Helper method that moves right of current field when letter is entered
     *
     * @param row
     * @param col
     */
    private void moveForward(int row, int col) {
        // Do not move forward if on the last column in a row
        if (col < 4) {
            grid[row][col + 1].requestFocus();
        }
    }

    /**
     * Helper method that moves left of current field when letter is deleted
     *
     * @param row
     * @param col
     */
    private void moveBackward(int row, int col) {
        if (col > 0) {
            grid[row][col - 1].requestFocus();
        }
    }

    public GameBoard(Controller controller) {
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

                // Center text and set font for each text field
                textField.setStyle("-fx-alignment: center; -fx-font-size: 18");

                // Variables used for listener to move forward and backward
                final int currentRow = row;
                final int currentCol = col;

                // Prevent from moving to next grid until letter is entered and
                // to next row if guess has not been entered
                textField.setDisable(true);

                // Listener that allows 1 letter max per field and handles 
                // forward movement between fields
                textField.textProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            // Checks for 0 or 1 occurence of a character
                            if (!newValue.matches("[a-zA-Z]?")) {
                                textField.setText(oldValue);
                            } else {
                                textField.setText(newValue.toUpperCase());
                            }

                            // If a letter was entered, move forward
                            if (newValue.length() == 1) {
                                // Only move forward if not at the end of a row
                                if (currentCol + 1 < 5) {
                                    grid[currentRow][currentCol + 1].setDisable(false);
                                    moveForward(currentRow, currentCol);
                                }
                            }
                        }
                );

                // Handle backspace and enter key press
                textField.setOnKeyPressed(event -> {
                    // Move backwards from current field if backspace key is pressed
                    if (event.getCode() == KeyCode.BACK_SPACE) {
                        if (textField.getText().isEmpty()) {
                            moveBackward(currentRow, currentCol);
                        }
                    }

                    // Process the user input if enter is pressed at the last column in a row
                    if (currentCol == 4) {
                        if (event.getCode() == KeyCode.ENTER) {
                            for (int i = 0; i <= currentCol; i++) {
                                userInput += grid[currentRow][i].getText();
                            }

                            // User input is sent to Controller, which returns an array of color enums
                            Worker.Color[] colors = controller.submitGuessModeOne(userInput);
                            userInput = "";

                            // Check for invalid input
                            if (colors != null) {
                                // Set color CSS color based on enum value
                                for (int i = 0; i <= currentCol; i++) {
                                    switch (colors[i]) {
                                        case Green:
                                            color = "#6ca965";
                                            break;
                                        case Yellow:
                                            color = "#c8b653";
                                            break;
                                        case Gray:
                                            color = "#787c7f";
                                            break;
                                        default:
                                            color = "#ffffff";
                                    }

                                    // Update the text fields background color and text color
                                    grid[currentRow][i].setStyle("-fx-background-color: " + color + "; -fx-text-fill: #ffffff");

                                    // Disable the text fields in the row
                                    grid[currentRow][i].setDisable(true);
                                }

                                // Enable the next row
                                if (currentRow < 5) {
                                    grid[currentRow + 1][0].setDisable(false);
                                }
                            } else {

                            }
                        }
                    }
                });

                // Stores in memory
                grid[row][col] = textField;

                // Adds to FX GridPane to appear on screen
                add(textField, col, row);
            }
        }

        // Once a letter is entered into the text field, the subsequent field
        // in the current row will be enabled
        grid[0][0].setDisable(false);
    }

    public TextField[][] getGrid() {
        return grid;
    }
}
