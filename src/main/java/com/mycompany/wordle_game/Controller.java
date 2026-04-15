package com.mycompany.wordle_game;

/**
 * Central coordinator for the Wordle game, managing game state, turn processing, and delegation to mode-specific logic.
 * The controller owns the shared worker to ModeOne, ModeTwo, ModeThree, and the PersistenceManager.
 * All UI CODE should interact with the game through this class exclusively.
 */
public class Controller {

    private final Worker worker = new Worker();
    private final PersistenceManager perMan = new PersistenceManager();
    private final ModeOne modeOne = new ModeOne(worker, this);
    private final ModeTwo modeTwo = new ModeTwo(worker, this, perMan);
    private final ModeThree modeThree = new ModeThree(worker, this, perMan);

    /**
     * Represents the high-level session state of the game.
     */
    public enum State {
        /** Game is being played in any mode */
        Play,
        /** Game is not being ran in any mode (home screen)*/
        End
    }

    /**
     * Represents the outcome of the most recently submitted guess.
     */
    public enum Status {
        /** invalid word entered*/
        INVALID,
        /** Game running as normal */
        CONTINUE,
        /** Round Won */
        WIN,
        /** Round Lost */
        ROUND_LOST,
        /** Game over due to lack of lives or timed out */
        GAME_OVER
    }

    /**
     * Represents the currently active game mode.
     */
    public enum GameMode {
        /** Scored mode with lives*/
        MODE_ONE,
        /** Timed mode */
        MODE_TWO,
        /** Endless mode */
        MODE_THREE
    }

    public State currentState;
    public Status currentStatus;
    public GameMode currentMode;

    /**
     * Constructs a controller with the game initially in the End State.
     */
    public Controller() {
        currentState = State.End;
    }

    /**
     * Changes Game State Into End.
     */
    public void endMode() {
        currentState = State.End;
    }

    /**
     * Changes Game State Into Play.
     */
    public void playGame() {
        currentState = State.Play;
    }

    /**
     * Starts a new Mode 1 game session. Resets the score, lives, and starts the first round via worker.
     */
    public void startModeOne() {
        currentMode = GameMode.MODE_ONE;
        worker.startNewRound();
        modeOne.resetScore();
        modeOne.resetLives();
        playGame();
    }

    /**
     * Starts a new Mode 2 game session. Resets session data, starts the timer, and begins the first round.
     */
    public void startModeTwo() {
        currentMode = GameMode.MODE_TWO;
        worker.startNewRound();
        modeTwo.reset();
        modeTwo.modeTwoTimer();
        playGame();
    }

    /**
     * Starts a new Mode 3 game session. Begins the first round and changes state to play.
     */
    public void startModeThree() {
        currentMode = GameMode.MODE_THREE;
        modeThree.startMode();
        playGame();
    }

    /**
     * Processes a single guess for the active game mode.
     * The following logic is applied in order:
     *  Guess is trimmed and lowercased.
     *  If the guess is not in allowed word status is set to invalid and null is returned.
     *  The guess is compared against the game word via worker.
     *  Mode-specific logic determines scoring, round progression, and game termination.
     *
     * @param guess the string entered by the player
     * @return Color array describing the result of the guess, or {@code null} if the guess was invalid.
     */
    public Worker.Color[] submitGuess(String guess) {

    guess = guess.trim().toLowerCase();

    if (!worker.isValidGuess(guess)) {
        currentStatus = Status.INVALID;
        return null;
    }

    Worker.Color[] result = worker.compare(guess);

    switch (currentMode) {
        case MODE_ONE:
            currentStatus = modeOne.processResult(result);
            if (currentStatus == Status.GAME_OVER) {
                perMan.updateModeOneScore(modeOne.getTotalScore());
                modeOne.resetLives();
            }
            break;
        /*
        case MODE_TWO:
            currentStatus = modeTwo.processResult(result);
            break;
*/
        case MODE_THREE:
            currentStatus = modeThree.processResult(result);
            break;
    }

    return result;
}

    /**
     * returns the total score reported by Mode one
     * @return total score
     */
    public int getTotalScore() {
        return modeOne.getTotalScore();
    }
    /**
     * returns the High score reported by Mode one
     * @return High score
     */
    public int getModeOneHighScore(){
        return perMan.getModeOneHighScore();
    }
    /**
     * returns the Last game score reported by Mode one
     * @return Last game's score
     */
    public int getModeOneLastScore(){
        return perMan.getModeOneLastScore();
    }

    /**
     * returns the number of wins as reported in mode two
     * @return the number of wins
     */
    public int getModeTwoWins() {
        return modeTwo.getWins();
    }

   /**
     * returns the number of wins as reported in mode three
     * @return the number of wins
     */
    public int getModeThreeWins() {
        return modeThree.getWins();
    }

    /**
     * returns the number of losses as reported in mode three
     * @return the number of losses
     */
    public int getModeThreeLosses() {
        return modeThree.getLosses();
    }

    /**
     * returns the number of lives as reported in mode one
     * @return the number of lives
     */
    public int getLives() {
        return modeOne.getLives();
    }

    /**
     * attempt count as reported by worker.
     * @return attempt count
     */
    public int getAttempts() {
        return worker.getAttempts();
    }

    /**
     * returns the state of the game depending on if a game is running or not
     * @return the state of the game Play or End.
     */
    public State getState() {
        return currentState;
     }

    /**
     * returns the game word as reported by worker.
     * @return the game word
     */
    public String debugWord() {
        return worker.getGameWord();
    }
}



