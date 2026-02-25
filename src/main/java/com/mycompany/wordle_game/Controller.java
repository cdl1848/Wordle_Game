/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wordle_game;

/**
 *
 * Contructs Controller Class that contains Game states in the from of enum State
 */
public class Controller {
// java doc comments must answer what is it? and how does it do it? 
    public enum State {
        Play,
        End
    }

    public State currentState;
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
}

