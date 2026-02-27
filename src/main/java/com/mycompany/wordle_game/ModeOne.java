package com.mycompany.wordle_game;

public class ModeOne extends Modes {
    private int lives = 3;
    private int multiply = 1;
    protected int totalscore = 0;
    private Worker worker; 
/**
 * Constructer for mode one creates pointers to worker and controller
 * @param worker 
 */
   public ModeOne(Worker worker) {
        this.worker = worker;
        this.controller = controller;
    }
   
  /** 
   * Calculates Score for Mode One, and returns it as an int
   * @param attempt
   */
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
    }
    /**
     * Updates the Multiplier for the Score to calculate
     * @param attempt 
     */
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
public int getTotalScore(){
        return totalscore;
    }
/**
 * Updates values such as attempt, score, total score, and lives. If lives equals 0 then runs endmode switching the state to END ending the game
 */
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
/**
 * returns lives for modeone
 * @return 
 */
    public int getLives(){
        return lives;
    }
    /**
     * Updates lives for modeone 
     */
    public void loseLife() {
        lives--;
    }
    /** 
     * resets lives back to 3 for modeone 
     */
    public void resetLives() {
        lives = 3;
    }
}