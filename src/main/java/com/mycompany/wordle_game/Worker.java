package com.mycompany.wordle_game;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

/**
 * Handles game logic for the Wordle game, including word loading,Guess validation, and result comparison.
 * Loads two words list 
 *  GuessWordList.txt - pool of possible answer words 
 *  AllowedWords.txt - set of valid guess submissions. 
 * @author dallas
 */
public class Worker {

    private int attempts = 0;
    private String gameWord;
    private String[] wordList;
    private HashSet<String> allowedWords = new HashSet<>();
    private final Random rand = new Random();

    /**
     * Represents the result of comparing a single letter in the users guess against the current game word. 
     */
    public enum Color {
        /** The letter is in the correct position. */
        Green,
         /** The letter is in the word but is not in the correct position. */
        Yellow,
         /** The letter is not the word. */
        Gray,
    }

    /**
     * Constructs a Worker instance and loads both word lists.
     */
    public Worker() {
        loadWordList();
        loadAllowedWords();
    }

    /**
     * Loads the list of words that are valid user inputs, Words are trimmed and converted to lowercase. They are then put in a hashset.
     * Throws a RuntimeExeption if llowedWords.txt is not found.
     */
    private void loadAllowedWords() {
        System.out.println("Loading AllowedWords.txt...");
        InputStream is = Worker.class
                .getClassLoader()
                .getResourceAsStream("AllowedWords.txt");
        if (is == null) {
            throw new RuntimeException("AllowedWords.txt not found");
        }
        Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            allowedWords.add(scanner.nextLine().trim().toLowerCase());
        }
        scanner.close();
    }

    /**
     * Loads the list of words the game is allowed to use as an answer. 
     * During loading an arraylist is used for sizing, then converted to an array for random indexing for words. 
     * Throws a RuntimeExeption if GuessWordList.txt is not found.
     */
    private void loadWordList() {
        InputStream is = Worker.class
                .getClassLoader()
                .getResourceAsStream("GuessWordList.txt");
        if (is == null) {
            throw new RuntimeException("GuessWordList.txt not found");
        }
        ArrayList<String> temp = new ArrayList<>();
        Scanner scanner = new Scanner(is);

        while (scanner.hasNextLine()) {
            temp.add(scanner.nextLine().trim());
        }
        scanner.close();
        // Convert to array
        wordList = temp.toArray(new String[0]);
    }

    /**
     * Compares a 5 letter guess against current gameWord and returns a Color for each position.
     * Letters in correct position are marked green.
     * Letters that appear elsewhere are marked yellow.
     * All other letters are marked gray.
     * each call increments attempts by 1.
     * @param guess the 5-letter word submitted by the player; must be lowercase.
     * @return Color[5] array where each element reflects the result for the corresponding letter of guess.
     */
    public Color[] compare(String guess) {
        String word = gameWord;
        int length = 5;
        Color[] result = new Color[5];
        boolean[] used = new boolean[5];
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == word.charAt(i)) {
                result[i] = Color.Green;
                used[i] = true;
            }
        }
        for (int i = 0; i < length; i++) {

            if (result[i] == Color.Green) {
                continue;
            }
            char g = guess.charAt(i);
            boolean found = false;
            for (int j = 0; j < length; j++) {
                if (!used[j] && g == word.charAt(j)) {
                    found = true;
                    used[j] = true;
                    break;
                }
            }
            result[i] = found ? Color.Yellow : Color.Gray;
        }
        attempts++;
        return result;
    }

    /**
     * Returns the number of attempts during the round.
     * @return the number of attempts made so far this round     
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Begins a new game round by resetting the attempt counter to zero
     * and selecting a new random answer word from wordList.     
     */
    public void startNewRound() {
        attempts = 0;
        gameWord = wordList[rand.nextInt(wordList.length)];

    }
    /** 
     * returns the Game word chosen at random during startNewRound()
     * @return the game word for the current round
     */
    public String getGameWord() {
        return gameWord;
    }

    /**
     * Checks whether a guess is a recognised word by looking for it in the allowedWords set. 
     *
     * @param guess the word to validate; should be lowercase and 5 letters
     * @return true if the guess is in the allowedWords list, else it returns false
     */
    public boolean isValidGuess(String guess) {
        if (allowedWords.contains(guess)) {
            return true;
        }
        return false;
    }
    /** 
     * Determines whether every position in a comparison result is green, indicates weather guess was correct.
     * @param result a Color array
     * @return true if all elements are green, false if any element is not green.
     */
    public boolean allGreen(Color[] result) {
        for (Color c : result) {
            if (c != Color.Green) {
                return false;
            }
        }
        return true;
    }
}
