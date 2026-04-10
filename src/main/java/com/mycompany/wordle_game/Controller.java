/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wordle_game;


/**
 * Central coordinator for the Wordle game, managing game state, turn processing, and delegation to mode-specific logic.
 * The controller owns the shared worker to ModeOne and the PersistenceManager
 * All UI CODE should interact with the game through this class exclusively.
 * 
 */
public class Controller {

    private final Worker worker = new Worker();
    private final ModeOne modeOne = new ModeOne(worker, this);
    private final PersistenceManager perMan = new PersistenceManager();
    //Scanner input = new Scanner(System.in);
    //TurnResult turnresult = new TurnResult();
    
    /**
     * Represents the high-level session state of the game.
     */
    public enum State {
        Play,
        End
    }
    /**
     * Represents the outcome of the most recently submitted guess.
     */
    public enum Status {
        INVALID,
        CONTINUE,
        WIN,
        ROUND_LOST,
        GAME_OVER
    }
    public State currentState;
    public Status currentStatus;

    /**
     * Constructs a controller with the game initially in the End State.
     */
    public Controller() {
        currentState = State.End;
    }

    /**
     * Changes Game State Into End.
     */
    public void endMode() {
        currentState = State.End;
    }

    /**
     * Changes Game State Into Play,
     */
    public void playGame() {
        currentState = State.Play;
    }
    /**
     * @return total score as reported by
     */
    public int getTotalScore() {
        return modeOne.getTotalScore();
    }
    /**
     * @return the state of the game Play or End
     */
    public State getState() {
        return currentState;
    }
    /**
     * Starts a new Mode 1 game session. Resets the score, starts the first round via worker and changes state to play
     */
    public void startGame() {
        worker.startNewRound();
        modeOne.resetScore();
        playGame();
    }
    /**
     * Processes a single guess for Mode 1
     * The following logic is applied in order:
     *  Guess is trimmed and lowercased
     *  If the guess is not in allowed word status is set to invalid and null is returned.
     *  The guess is comapred against game word via worker
     *  If all leters are green status win is set, scores are updated, and new round begins.
     *  If attempts are 6 and no win
     *      If last life status game over is set, the session ends, score is saved, lives are reset
     *      otherwise, status round lost is set, life is deducted, new round begins.
     *  Otherwise, status continue is set and the round continues.
     * @param guess the string entered by the player
     * @return Color array describing the result of the guess, or {@code null} if the guess was invalid.
     */
    public Worker.Color[] submitGuessModeOne(String guess) {

        guess = guess.trim().toLowerCase();

        if (!worker.isValidGuess(guess)) {
            currentStatus = Status.INVALID;
            return null;
        }

        Worker.Color[] result = worker.compare(guess);

        // WIN
        if (worker.allGreen(result)) {
            currentStatus = Status.WIN;
            modeOne.runMode();
            worker.startNewRound();
        }
        // ROUND LOST
        else if (worker.getAttempts() >= 6) {
            if (modeOne.getLives() <= 1) {
                currentStatus = Status.GAME_OVER;
                endMode();
                perMan.updateModeOneScore(modeOne.getTotalScore());
                modeOne.resetLives();
            } 
            else {
                currentStatus = Status.ROUND_LOST;
                modeOne.loseLife();
                worker.startNewRound();
            }
        }else {
            currentStatus = Status.CONTINUE;
        }
            return result;
        }
    /**
     * @return current life count as reported by modeOne
     */
    public int getLives() {
        return modeOne.getLives();
    }
    /**
     * @return attempt count as reported by worker
     */
    public int getAttempts() {
        return worker.getAttempts();
    }
    /**
     * @return the game word as reported by worker
     */
    public String debugWord() {
        return worker.getGameWord();
    }
}
