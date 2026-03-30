package com.mycompany.wordle_game;
/** 
 * The scoring system and manipulator for mode one, calculates scores, handles lives, and tracks attempts.
 * @author dallas
 */
public class ModeOne {
    private int lives = 3;
    private int multiply = 1;
    protected int totalscore = 0;
    private Worker worker; 
    private Controller controller;
/**
 * Makes a pointer for worker and controller when created.
 * @param worker 
 */
   public ModeOne(Worker worker) {
        this.worker = worker;
        this.controller = controller;
    }
   
  /** 
   * Calculates Score for Mode One starting at 100 points for attempt one down to 10 points for attempt 6. Multiplies the scores by the multiplier determined by number of attempts of the last game.
   * @param attempt
   * @return int
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
     * Updates the Multiplier based on number of attempts, for 1 and 2 the multiplier is 3. 3 and 4 multiplier is 2, anything else multiplier is 1.
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
/**
 * @return totalscore
 */
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
 * @return lives
 */
    public int getLives(){
        return lives;
    }
    /**
     * Updates lives for modeone, removes one life. 
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
    public void resetscore(){
      totalscore = 0;
    }
}