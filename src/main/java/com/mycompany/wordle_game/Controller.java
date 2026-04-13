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
        Play,
        End
    }

    /**
     * Represents the outcome of the most recently submitted guess.
     */
    public enum Status {
        INVALID,
        CONTINUE,
        WIN,
        ROUND_LOST,
        GAME_OVER
    }

    /**
     * Represents the currently active game mode.
     */
    public enum GameMode {
        MODE_ONE,
        MODE_TWO,
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
     * @return total score as reported by Mode One.
     */
    public int getTotalScore() {
        return modeOne.getTotalScore();
    }
    public int getModeOneHighScore(){
        return perMan.getModeOneHighScore();
    }
    public int getModeOneLastScore(){
        return perMan.getModeOneLastScore();
    }

    /**
     * @return the number of wins in Mode Two.
     */
    public int getModeTwoWins() {
        return modeTwo.getWins();
    }

    /**
     * @return the number of wins in Mode Three.
     */
    public int getModeThreeWins() {
        return modeThree.getWins();
    }

    /**
     * @return the number of losses in Mode Three.
     */
    public int getModeThreeLosses() {
        return modeThree.getLosses();
    }

    /**
     * @return current life count as reported by Mode One.
     */
    public int getLives() {
        return modeOne.getLives();
    }

    /**
     * @return attempt count as reported by worker.
     */
    public int getAttempts() {
        return worker.getAttempts();
    }

    /**
     * @return the state of the game Play or End.
     */
    public State getState() {
        return currentState;
     }

    /**
     * @return the game word as reported by worker.
     */
    public String debugWord() {
        return worker.getGameWord();
    }
}



