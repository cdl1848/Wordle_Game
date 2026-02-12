package com.mycompany.wordle_game;

public class ModeOne extends Modes {
    private int lives = 3;
    private int multiply = 1;
    protected int totalscore = 0;
    private Worker worker; 
    
   public ModeOne(Worker worker , Controller controller) {
        this.worker = worker;
        this.controller = controller;
    }
   
  
    private int calculateScore(int attempt) {
        switch(attempt) {
            case 1: return 100 * multiply;
            case 2: return 90 * multiply;
            case 3: return 75 * multiply;
            case 4: return 50 * multiply;
            case 5: return 25 * multiply;
            case 6: return 10 * multiply;
            default: return 0;
        }
    } // Calculates Score for Mode one based on attempts
    private void updateMultiplier(int attempt) {
        switch(attempt) {
            case 1:
            case 2:
                multiply = 3;
                break;
            case 3:
            case 4:
                multiply = 2;
                break;
            default:
                multiply = 1;
    }
} //calculates the multiplier based on attempts
    
    public void runMode(){
        int attempt = worker.getAttempts();
        int score = calculateScore(attempt);
        totalscore += score;
        updateMultiplier(attempt);
        if (attempt > 6 ){
            lives --;
        }
        if (lives <= 0){
            controller.endMode(); // in controler
        }
        
    }  //updates stuff in the game
}