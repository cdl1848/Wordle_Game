package com.mycompany.wordle_game;

/**
 * Implements the scoring system and game-state logic for Mode 1.
 * Player starts with 3 lives and loses one each time a word is not guessed within 6 attempts. 
 * Points are Points are awarded per round based on how quickly a word is guessed. 
 * then multiplied by a streak multiplier that grows when the player guesses efficiently in consecutive rounds.
 * @author dallas
 */
public class ModeOne {

    private int lives = 3;
    private int multiplier = 1;
    private int totalScore = 0;
    private static final int MAX_ATTEMPTS = 6;
    private final Worker worker;
    private final Controller controller;

    /**
     * Constructs a ModeOne with references to
     * @param worker used to retrieve attempt counts
     * @param controller used to signal game-state changes
     */
    public ModeOne(Worker worker, Controller controller) {
        this.worker = worker;
        this.controller = controller;
    }

    /**
     * Calculates Score for Mode One starting at 100 points for attempt one down
     * to 10 points for attempt 6. Multiplies the scores by the multiplier
     * determined by number of attempts of the last game.
     *
     * @param attempt the number of guesses used to solve the word 
     * @return the points awarded for this round
     */
    private int calculateScore(int attempt) {
        switch (attempt) {
            case 1:
                return 100 * multiplier;
            case 2:
                return 90 * multiplier;
            case 3:
                return 75 * multiplier;
            case 4:
                return 50 * multiplier;
            case 5:
                return 25 * multiplier;
            case 6:
                return 10 * multiplier;
            default:
                return 0;
        }
    }

    /**
     * Updates the Multiplier based on number of attempts
     * 1 and 2 = multiplier 3.
     * 3 and 4 = multiplier 2
     * anything else = multiplier 1.
     *
     * @param attempt the number of guesses used to solve the word this round
     */
    private void updateMultiplier(int attempt) {
        switch (attempt) {
            case 1:
            case 2:
                multiplier = 3;
                break;
            case 3:
            case 4:
                multiplier = 2;
                break;
            default:
                multiplier = 1;
        }
    }

    /**
     * @return the total score accumulated across all rounds so far
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Processes the outcome of a completed round. In order, this method:
     * Retrieves the attempt count from worker.
     * Calculates and adds the round score to totalScore
     * Updates the multiplier for next round
     * Deducts a life if the word was not guessed within 6 attempts.
     * Calls endMode if no lives remain.
     */
    public void runMode() {
        int attempt = worker.getAttempts();
        int score = calculateScore(attempt);
        totalScore += score;
        updateMultiplier(attempt);
        if (attempt > MAX_ATTEMPTS) {
            loseLife();
        }
        if (lives <= 0) {
            controller.endMode(); // in controler
        }

    }

    /**
     * @return current life count
     */
    public int getLives() {
        return lives;
    }

    /**
     * Deducts one life from the player's remaining lives.
     */
    public void loseLife() {
        lives--;
    }

    /**
     * Resets the player's life count back to the starting value of 3 
     */
    public void resetLives() {
        lives = 3;
    }

    /**
     * Resets the player's total score back to 0
     */
    public void resetScore() {
        totalScore = 0;
    }
}
