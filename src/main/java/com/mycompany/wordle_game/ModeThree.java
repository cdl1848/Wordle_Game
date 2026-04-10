package com.mycompany.wordle_game;

/**
 * Implements the scoring system and game-state logic for Mode 3.
 * 
 * The player is presented with an infinite stream of words.
 * Each round a guess is evaluated:
 *  - A win is recorded when all letters are green.
 *  - A loss is recorded when all six attempts are exhausted.
 * 
 * This mode has no time limit and no lives. The session ends only
 * when the user exits via the UI. Wins and losses are persisted
 * across sessions through the PersistenceManager.
 * 
 * @author dallas
 */
public class ModeThree {

    private final Worker worker;
    private final Controller controller;
    private final PersistenceManager perMan;

    private static final int MAX_ATTEMPTS = 6;

    /**
     * Constructs a ModeThree instance with references to
     * @param worker used to retrieve attempt counts and evaluate guess results
     * @param controller used to signal game-state changes
     * @param perMan used to persist win and loss statistics
     */
    public ModeThree(Worker worker, Controller controller, PersistenceManager perMan) {
        this.worker = worker;
        this.controller = controller;
        this.perMan = perMan;
    }

    /**
     * Starts Mode 3 by beginning the first round.
     */
    public void startMode() {
        worker.startNewRound();
    }

    /**
     * Evaluates the result of a completed guess and updates persistent win/loss counts.
     * If all letters are green a win is recorded.
     * If attempts have reached 6 without a win a loss is recorded.
     * A new round begins immediately after either outcome.
     *
     * @param result the Color array returned by worker for the most recent guess.
     */
    public void processResult(Worker.Color[] result) {
        if (worker.allGreen(result)) {
            perMan.recordWin();
            worker.startNewRound();
        } 
        else if (worker.getAttempts() >= MAX_ATTEMPTS) {
            perMan.recordLoss();
            worker.startNewRound();
        }
    }

    /**
     * @return the total number of wins persisted across sessions
     */
    public int getWins() {
        return perMan.getModeThreeWins();
    }

    /**
     * @return the total number of losses persisted across sessions
     */
    public int getLosses() {
        return perMan.getModeThreeLosses();
    }
}
