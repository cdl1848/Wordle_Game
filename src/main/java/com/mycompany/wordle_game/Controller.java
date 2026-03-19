/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wordle_game;

import java.util.Scanner;

/**
 * Contructs Controller Class that contains Game states in the from of enum State
 */
public class Controller {
    Worker worker = new Worker();
    ModeOne mode = new ModeOne(worker);
    //Scanner input = new Scanner(System.in);
    //TurnResult turnresult = new TurnResult();
    private String gameWord;
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
    
    public int getTotalScore(){ 
        return mode.getTotalScore();
    }
    public State getState(){
        return currentState;
    }
    public void startGame() { 
        worker.startNewRound();
        playGame();
        gameWord = worker.getGameWord();
    }
    
    public Worker.Color[] submitGuess(String guess){

    guess = guess.trim().toLowerCase();

    if (!worker.isValidGuess(guess)){
        currentStatus = Status.INVALID;
        return null;
    }

    Worker.Color[] result = worker.compare(guess, gameWord);

    // WIN
    if (worker.allGreen(result)) {

        currentStatus = Status.WIN;

        mode.runMode();
        worker.startNewRound();
        gameWord = worker.getGameWord();

        return result;
    }

    // ROUND LOST
    if (worker.getAttempts() >= 6) {

        if (mode.getLives() <= 1) {

            currentStatus = Status.GAME_OVER;

            endMode();
            mode.resetLives();

        } else {

            currentStatus = Status.ROUND_LOST;

            mode.loseLife();
            worker.startNewRound();
            gameWord = worker.getGameWord();
        }

        return result;
    }

    currentStatus = Status.CONTINUE;
    return result;
}

    public int getLives() {
    return mode.getLives();
}

public int getAttempts() {
    return worker.getAttempts();
}

public String debugWord() {
    return gameWord;
}
}

