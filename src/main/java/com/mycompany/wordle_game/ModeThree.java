package com.mycompany.wordle_game;

public class ModeThree extends Modes {
    private int time = 0;
    private int totalscore = 0;
    private Worker worker; 
    
   public ModeThree(Worker worker) {
        this.worker = worker;
    }
}