package com.mycompany.wordle_game;
import com.mycompany.wordle_game.Controller;
import com.mycompany.wordle_game.Worker;
/** 
 * Holds the information needed for the GUI to display and function.
 * @author dallas
 */
public class TurnResult {
    private Worker worker; 
    Controller controller = new Controller();
    private Controller.Status status; // WIN, CONTINUE, ROUND_LOST, GAME_OVER, INVALID
    private Worker.Color[] feedback;  // color per letter
    private int attempts;             // attempts left / current attempt
    private int totalScore;           // updated total score
    private int lives;                // remaining lives
    private String gameWord;          // optional for debugging / final round

    // getters for all fields
}