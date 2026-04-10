package com.mycompany.wordle_game;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Point3D;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * GameBoard
 *
 * Responsible for managing the grid of text fields which represents the
 * playable word grid (6 rows x 5 columns)
 *
 * Sources used for text formatting: https://stackoverflow.com/a/26670258
 * https://stackoverflow.com/a/5238524
 *
 * Sources used for animation:
 * https://www.tutorialspoint.com/javafx/javafx_rotate_transition.htm
 * https://www.javaspring.net/javafx/javafx_sequential_transition_unleashing_the_power_of_animated_interfaces/
 *
 * Color hex values taken from: https://www.color-hex.com/color-palette/1012607
 *
 * Help with general purpose debugging and refactoring:
 * https://chatgpt.com/c/697d09b0-0438-832a-8cce-d80990487f4d
 *
 * @author garrett
 */
public class GameBoard extends GridPane {

    // 6x5 2D array of text field objects used to move between fields
    private TextField[][] grid = new TextField[6][5];

    // Stores user input as a concatenated string
    private String userInput = "";

    // Callback to refresh the score display in GameScreen
    private Runnable refreshScore;

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

    /**
     * Helper method that will play tile flipping animation and change
     * respective color for each tile in the current row
     *
     * @param row
     * @param colors
     */
    private SequentialTransition createRowAnimation(int row, Worker.Color[] colors) {
        // Stores the flip animations for each tile in a row
        SequentialTransition rowAnimation = new SequentialTransition();

        // Create a flip animation for each tile in the row
        for (int i = 0; i < 5; i++) {
            TextField tile = grid[row][i];

            SequentialTransition tileFlip = createTileFlip(tile, colors[i]);

            // Disable tile after animation finishes
            tileFlip.setOnFinished(event -> tile.setDisable(true));

            rowAnimation.getChildren().add(tileFlip);
        }

        return rowAnimation;
    }

    /**
     * Helper method that creates a tile flip animation for each tile in a row
     * and changes its color corresponding to its respective color enum value
     *
     * @param tile
     * @param colorEnum
     * @return
     */
    private SequentialTransition createTileFlip(TextField tile, Worker.Color colorEnum) {
        // Split the flip animation into two
        // Color will change at the halfway point when the tile is no longer visible
        RotateTransition flip = new RotateTransition();
        flip.setNode(tile);
        flip.setDuration(Duration.millis(200));
        flip.setAxis(new Point3D(1, 0, 0));
        flip.setFromAngle(0);
        flip.setToAngle(90);

        // Apply color at halfway point
        flip.setOnFinished(event -> {
            tile.setStyle("-fx-background-color: " + getColorHex(colorEnum) + "; -fx-text-fill: #ffffff");
        });

        RotateTransition flipBack = new RotateTransition();
        flipBack.setNode(tile);
        flipBack.setDuration(Duration.millis(200));
        flipBack.setAxis(new Point3D(1, 0, 0));
        flipBack.setFromAngle(90);
        flipBack.setToAngle(0);

        // Chain the two flip animations together
        SequentialTransition tileFlip = new SequentialTransition(flip, flipBack);

        return tileFlip;
    }

    /**
     * Helper method that will return a color hex value given color enum
     *
     * @param color
     * @return
     */
    private String getColorHex(Worker.Color color) {
        switch (color) {
            case Green:
                return "#6ca965";
            case Yellow:
                return "#c8b653";
            case Gray:
                return "#787c7f";
            default:
                return "#ffffff";
        }
    }

    private void resetBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                grid[row][col].clear();
                grid[row][col].setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
                grid[row][col].setStyle("-fx-alignment: center; -fx-font-size: 18");
            }
        }

        grid[0][0].setDisable(false);
        grid[0][0].requestFocus();
    }

    public GameBoard(Controller controller, Runnable refreshScore) {
        this.refreshScore = refreshScore;

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
                            Worker.Color[] colors = controller.submitGuess(userInput);
                            userInput = "";

                            // If the input is valid, apply color change to row and check status from Controller
                            if (colors != null) {
                                SequentialTransition rowAnimation = createRowAnimation(currentRow, colors);

                                rowAnimation.setOnFinished(e -> {
                                    // Refresh score label after backend processes valid guess
                                    refreshScore.run();

                                    // NEED TO CHANGE STATUS HANDLING
                                    // Get status of Controller after user input is submitted
                                    Controller.Status status = controller.currentStatus;

                                    // Used for debugging
                                    System.out.println("Status: " + status);

                                    // Update GameBoard base on Controller status
                                    switch (status) {
                                        case WIN:
                                            System.out.println("You win!");
                                            resetBoard();
                                            System.out.println(controller.debugWord());
                                            break;

                                        case ROUND_LOST:
                                            System.out.println("Round Lost!");
                                            resetBoard();
                                            System.out.println(controller.debugWord());
                                            break;

                                        case CONTINUE:
                                            // Enable the next row
                                            if (currentRow < 5) {
                                                grid[currentRow + 1][0].setDisable(false);
                                            }
                                            break;

                                        case GAME_OVER:
                                            System.out.println("Game Over!");
                                            break;

                                        case INVALID:
                                            break;
                                    }
                                });
                                
                                rowAnimation.play();
                            } else {
                                System.out.println("Invalid guess");
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
