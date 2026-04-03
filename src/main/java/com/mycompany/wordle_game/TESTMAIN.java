/*
package com.mycompany.wordle_game;

import java.util.Scanner;

public class TESTMAIN {

    public static void main(String[] args) {

        System.out.println("GameRunner started");

        Scanner input = new Scanner(System.in);

        Controller controller = new Controller();
        Worker worker = new Worker();
        ModeTwo modeTwo = new ModeTwo(worker, controller);

        controller.endMode();

        while (true) {

            // ================= HOME =================
            if (controller.getState() == Controller.State.End) {

                System.out.println("\n=================");
                System.out.println("WORDLE");
                System.out.println("=================");
                System.out.println("1: Mode One");
                System.out.println("2: Mode Two (60s Speed)");
                System.out.println("3: Mode Three (N/A)");
                System.out.println("=================");

                String choice = input.nextLine().trim();

                // ---------- MODE ONE ----------
                if (choice.equals("1")) {
                    controller.startGame();
                }

                // ---------- MODE TWO ----------
                else if (choice.equals("2")) {

                    System.out.println("\n--- MODE TWO (60s SPEED) ---");

                    modeTwo.reset();

                    worker.startNewRound();
                    String gameWord = worker.getGameWord();

                    modeTwo.ModeTwoTimer(); // 60 seconds

                    while (!modeTwo.isTimedOut()) {

                        System.out.println("\nWins: " + modeTwo.getWins());
                        System.out.println("Attempt: " + (worker.getAttempts() + 1));

                        System.out.print("Enter guess: ");
                        String guess = input.nextLine().trim().toLowerCase();

                        if (!worker.isValidGuess(guess)) {
                            System.out.println("Invalid word.");
                            continue;
                        }

                        Worker.Color[] result = worker.compare(guess, gameWord);

                        // print colors
                        for (Worker.Color c : result) {
                            System.out.print(c + " ");
                        }
                        System.out.println();

                        // WIN
                        if (worker.allGreen(result)) {
                            modeTwo.runModeModeOne(result);

                            System.out.println("Correct!");

                            worker.startNewRound();
                            gameWord = worker.getGameWord();
                        }

                        // FAILED ROUND → skip word
                        else if (worker.getAttempts() >= 6) {
                            System.out.println("Skipping word...");
                            worker.startNewRound();
                            gameWord = worker.getGameWord();
                        }
                    }

                    // ---------- TIME UP ----------
                    System.out.println("\nTIME UP!");
                    System.out.println("Total Wins: " + modeTwo.getWins());
                    System.out.println("Returning to home...");

                    controller.endMode();
                }
            }

            // ================= MODE ONE =================
            else if (controller.getState() == Controller.State.Play) {

                System.out.println("\n--- MODE ONE ---");
                System.out.println("Word (debug): " + controller.debugWord());

                while (controller.getState() == Controller.State.Play) {

                    System.out.println(
                        "\nLives: " + controller.getLives() +
                        " | Score: " + controller.getTotalScore() +
                        " | Attempt: " + (controller.getAttempts() + 1)
                    );

                    System.out.print("Enter guess: ");
                    String guess = input.nextLine();

                    Worker.Color[] result = controller.submitGuessModeOne(guess);

                    if (result == null) {
                        System.out.println("Invalid word.");
                        continue;
                    }

                    // print colors
                    for (Worker.Color c : result) {
                        System.out.print(c + " ");
                    }
                    System.out.println();

                    // status messages
                    switch (controller.currentStatus) {

                        case WIN:
                            System.out.println("Correct! New round.");
                            System.out.println("New Word: " + controller.debugWord());
                            break;

                        case ROUND_LOST:
                            System.out.println("Round lost!");
                            System.out.println("New Word: " + controller.debugWord());
                            break;

                        case GAME_OVER:
                            System.out.println("Out of lives!");
                            break;

                        default:
                            break;
                    }

                    if (controller.getState() == Controller.State.End) {
                        break;
                    }
                }
            }
        }
    }
}
*/