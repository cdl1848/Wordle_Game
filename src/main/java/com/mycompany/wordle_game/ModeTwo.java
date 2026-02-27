package com.mycompany.wordle_game;

/**
 * Class for ModeTow - will handle scores, timer, and value updating
 * @author dallas
 * @author dallas
 */
public class ModeTwo extends Modes {
    private int time = 0;
    private int totalscore = 0;
    private Worker worker; 
    
   public ModeTwo(Worker worker) {
        this.worker = worker;
    }
}
   

