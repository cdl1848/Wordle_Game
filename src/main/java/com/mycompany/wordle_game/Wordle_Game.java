package com.mycompany.wordle_game;
/**
 * @author dallas
 */
import java.util.Scanner;
public class Wordle_Game {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Create Scanner
        Worker worker = new Worker();          // Create Worker
        worker.GameRun(input);
        // System.out.println(gameWord); // TESTER PRINT
        //test
    }
}

