package com.mycompany.wordle_game;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

//make the class
public class Worker {
// create variables needed in multiple functions and lists too.
private String[] wordList;
private HashSet<String> allowedWords = new HashSet<>();
private final Random rand = new Random();

// Constructor - when its loaded the word list is loaded too
    public Worker() {
        loadWordList();
        loadAllowedWords();
    }
    // Creates the allowed word Hash set - used over array casue of how big it is 
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
// Loads the words that can be used as game words into an array so they can be grabbed at random.
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
// Grabs a random word from the array to send to the game to use as the game word
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
    
    public void compare(String guess, String gameWord) {
            for (int i = 0; i < guess.length(); i++) {
                char g = guess.charAt(i);
                char w = gameWord.charAt(i);
            
                if (g == w) {
                    System.out.print((i + 1) + ",Green ");
                } else if (gameWord.indexOf(g) != -1) {
                   System.out.print((i + 1) + ",Yellow ");
                } else {
                    System.out.print((i + 1) + ",Red ");
                }
            
        }
        System.out.println();
    }
    
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
}
