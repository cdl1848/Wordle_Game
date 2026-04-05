/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wordle_game;

import java.util.Scanner;

/**
 * Constructs Controller Class that contains Game states in the form of enum
 * State
 */
public class Controller {

    Worker worker = new Worker();
    ModeOne modeOne = new ModeOne(worker);
    PersistenceManager PerMan = new PersistenceManager();
    //Scanner input = new Scanner(System.in);
    //TurnResult turnresult = new TurnResult();

    public enum State {
        Play,
        End
    }

    public enum Status {
        INVALID,
        CONTINUE,
        WIN,
        ROUND_LOST,
        GAME_OVER
    }
    public State currentState;
    public Status currentStatus;

    public Controller() {
        currentState = State.End;
    }

    /**
     * Changes Game State Into End,
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

    public int getTotalScore() {
        return modeOne.getTotalScore();
    }

    public State getState() {
        return currentState;
    }

    public void startGame() {
        worker.startNewRound();
        modeOne.resetscore();
        playGame();
    }

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

            return result;
        }

        // ROUND LOST
        if (worker.getAttempts() >= 6) {

            if (modeOne.getLives() <= 1) {

                currentStatus = Status.GAME_OVER;

                endMode();
                PerMan.updateModeOneScore(getTotalScore());
                modeOne.resetLives();

            } else {

                currentStatus = Status.ROUND_LOST;

                modeOne.loseLife();
                worker.startNewRound();
            }

            return result;
        }

        currentStatus = Status.CONTINUE;
        return result;
    }

    public int getLives() {
        return modeOne.getLives();
    }

    public int getAttempts() {
        return worker.getAttempts();
    }

    public String debugWord() {
        return worker.getGameWord();
    }
}
