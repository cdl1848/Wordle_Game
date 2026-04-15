package com.mycompany.wordle_game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Implements the scoring system and game-state logic for Mode 2.
 * 
 * The player has 60 seconds to solve as many words as possible. 
 * Each round a guess is evaluated, a win is when all letters are green - a loss is recorded(though not used) when all attempts are exhausted.
 * The mode ends when time expires.
 *
 * @author dallas
 */
public class ModeTwo {

    private boolean timedOut = false;
    private final Worker worker;
    private final Controller controller;
    private final PersistenceManager persistencemanager;
    private int wins = 0;
    private int losses = 0;
    private static final int timerTime = 6000;
    private static final int MAX_ATTEMPTS = 6;
    private Timer timer;

    /**
     * Constructs a ModeTwo instance with references to
     * @param worker used to retrieve attempt counts and evaluate guess results
     * @param controller used to signal game-state changes
     * @param PersistenceManager used to manage Persistence in mode
     */
    public ModeTwo(Worker worker, Controller controller, PersistenceManager persistencemanager) {
        this.worker = worker;
        this.controller = controller;
        this.persistencemanager = persistencemanager;
    }

    /**
     * Starts a 60 second timer. When the timer ends timedOut = true and timer is cancelled.
     */
    public void modeTwoTimer() {
        timedOut = false;

        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                persistencemanager.updateModeTwoScore(wins);
                timedOut = true;
                timer.cancel();
                controller.endMode();
            }
        };
        timer.schedule(task1, timerTime); // <- 60 seconds
    }

    /**
     * Returns whether the 60-second session timer has expired.
     * @return true is timer has ended, false if there is time remaining 
     */
    public boolean isTimedOut() {
        return timedOut;
    }

    /** 
     * Evaluates the result of a completed guess and updates win/loss counts.
     * If all letters are green a win is recorded and points are awarded.
     * If attempts have reached 6 without a win a loss is recorded.
     * @param result the Color array returned by worker for the most recent guess.
     */
    public void updateScores(Worker.Color[] result) {
        if (worker.allGreen(result)) {
            wins++;
        } else if (worker.getAttempts() >= MAX_ATTEMPTS) {
            losses++;
        }
    }

    /**
     * Returns the total number of wins for that game in mode 2
     * @return the win count 
     */
    public int getWins() {
        return wins;
    }

    /**
     * Returns the total number of losses for that game in mode 2
     * @return the loss count (not used currently)
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Resets session state in preparation for a new Mode 2 game.
     * Clears the win count and resets the timedOut.
     * DOES NOT START A TIMER
     */
    public void reset() {
        wins = 0;
        losses = 0;
        timedOut = false;

        if (timer != null) {
            timer.cancel();
        }
    }
    /**
 * Processes the result of a completed guess and updates win/loss counts
 * and round progression for Mode Two.
 *
 * @param result the Color array returned by worker for the most recent guess.
 * @return the resulting game status for the round.
 */
public Controller.Status processResult(Worker.Color[] result) {

    if (isTimedOut()) {
        controller.endMode();
        return Controller.Status.GAME_OVER;
    }

    updateScores(result);

    if (worker.allGreen(result)) {
        worker.startNewRound();
        return Controller.Status.WIN;
    }

    if (worker.getAttempts() >= MAX_ATTEMPTS) {
        worker.startNewRound();
        return Controller.Status.ROUND_LOST;
    }

    return Controller.Status.CONTINUE;
}

}
