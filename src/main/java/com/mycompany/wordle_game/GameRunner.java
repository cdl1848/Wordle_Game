package com.mycompany.wordle_game;

import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {

        Controller controller = new Controller();
        Worker worker = new Worker();
        ModeOne mode = new ModeOne(worker, controller); // your multiplier & scoring
        Scanner input = new Scanner(System.in);

        controller.endMode(); // start at HOME

        while (true) {

            // ---------- HOME SCREEN ----------
            if (controller.currentState == Controller.State.End) {

                printHome(mode.getScoreOne()); // print total score

                String choice = input.nextLine().trim().toUpperCase();

                if (choice.equals("P")) {
                    worker.startNewRound();
                    controller.playGame();
                }
            }

            // ---------- GAME SCREEN ----------
            else if (controller.currentState == Controller.State.Play) {

                printGame();

                String gameWord = worker.getGameWord();
                System.out.println("(TEST word): " + gameWord);

                while (controller.currentState == Controller.State.Play) {

                    if (worker.getAttempts() >= 6) {
                        System.out.println("Out of attempts!");
                        controller.endMode();
                        break;
                    }

                    System.out.print("Enter guess (or G to go home): ");
                    String guess = input.nextLine().trim().toLowerCase();

                    if (guess.equalsIgnoreCase("G")) {
                        controller.endMode();
                        break;
                    }

                    if (!worker.isValidGuess(guess)) {
                        System.out.println("Not a valid word.");
                        continue;
                    }

                    Worker.Color result = worker.compare(guess, gameWord);
                    System.out.println("Result: " + result);

                    // WIN → continue playing with multiplier
                    if (result == Worker.Color.Green) {

                        System.out.println("Correct!");
                        mode.runMode(); // updates multiplier and totalscore

                        // start next round automatically
                        worker.startNewRound();
                        gameWord = worker.getGameWord();

                        System.out.println("\n--- NEXT ROUND ---");
                        System.out.println("(TEST word): " + gameWord);
                    }
                }
            }
        }
    }

    // ---------- ASCII UI ----------

    private static void printHome(int totalScore) {
        System.out.println("\n=======================");
        System.out.println("       WORDLE");
        System.out.println("=======================");
        System.out.println("Total Score: " + totalScore);
        System.out.println("Type P to Play");
        System.out.println("=======================\n");
    }

    private static void printGame() {
        System.out.println("\n=======================");
        System.out.println("       GAME MODE");
        System.out.println("=======================\n");
    }
}
