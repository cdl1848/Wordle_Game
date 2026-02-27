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
    Scanner input = new Scanner(System.in);
    TurnResult turnresult = new TurnResult();
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
/**
 * Handles the Guess and returns turnresult class.
 * @param guess
 * @return 
 */
    public TurnResult submitGuess(String guess){
        return turnresult;
    }
}
