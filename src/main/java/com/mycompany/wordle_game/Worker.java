package com.mycompany.wordle_game;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

public class Worker {
    private int attempts = 0;
    private String gameword;
    private String[] wordList;
    private HashSet<String> allowedWords = new HashSet<>();
    private final Random rand = new Random();
    public enum Color {
        Green,
        Yellow,
        Red,
    }
    
    /**
     * Contructs the Worker Class, contains a call to Load words and Load allowed words
     */
    public Worker() {
        loadWordList();
        loadAllowedWords();
    }
    
/**
 * Loads the allowed word list from a file into a hashSet called allowed Words
 */
  private void loadAllowedWords() {
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
 * Loads the Game word list from a file into a Array called WordList, Array is used so grabing one at random is done easily
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
 * Takes a random word from wordList using random.nextInt
 * @return String GameWord
 */
    public String getGameWord() {
        String GameWord = wordList[rand.nextInt(wordList.length)];
        return GameWord;
    }
    
    public String UserInput(Scanner input){
        String guess; 
        while (true){
            System.out.print("enter a guess");
            guess = input.nextLine().toLowerCase();
            
            if (allowedWords.contains(guess)){
                break;
            }
            else {
                System.out.println("Not A word, Try Again");
            }
        }
        return guess;
    }
/**
 * Compares the user input to the Game word, If it is correct returns the enum value Green, if letter is in the word returns enum yellow, if not in word at all returns enum red
 * @param guess
 * @param gameWord
 * @return enum Color
 */
    public Color compare(String guess, String gameWord) {
            attempts ++;
            for (int i = 0; i < guess.length(); i++) {
                char g = guess.charAt(i);
                char w = gameWord.charAt(i);
                if (g == w) {
                    return Color.Green;
                } else if (gameWord.indexOf(g) != -1) {
                   return Color.Yellow;
                } else {
                    return Color.Red;
                }
        }
            return Color.Red;
    }
/**
 * gets the attemps 
 * @return int attempts
 */
    public int getAttempts(){
        return attempts;
    }
    
/**
 * resets values such as attempts and gets a new random word
 */
    public void startNewRound(){
        attempts = 0;
        gameword = getGameWord();
             
    }
/**
 * compares user input to allowedWords to check if word is not gibrish
 * @param guess
 * @return boolean
 */
    public boolean isValidGuess(String guess){
                    if (allowedWords.contains(guess)){
                return true;
            }
         return false;
    }
    
    
    
   
 /*
    public void GameRun(Scanner input){
         String gameWord = getGameWord(); // Run Worker to get random word 
         System.out.println(gameWord); // TESTER PRINT
        
        // Run the game for loop 6 attempts, break is if you win
        for (int i =0; i<6; i++){
            String guess = UserInput(input);
            compare(guess, gameWord);   
            if (guess.equals(gameWord)){
                System.out.print("Conrgats you win");
               break;
            }
            System.out.print("you lose");
        }
        // Lose / finish signal
        System.out.println("Game word was: " + gameWord);
    }
*/
}
