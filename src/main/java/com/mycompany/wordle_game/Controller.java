/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wordle_game;

/**
 *
 * @author dallas
 */
public class Controller {

    public enum State {
        Play,
        End
    }

    public State currentState;

    public void endMode() {
        currentState = State.End;
    }
    public void playGame() {
        currentState = State.Play; 
    }
}
