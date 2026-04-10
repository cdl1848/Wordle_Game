package com.mycompany.wordle_game;

import java.util.Scanner;

public class TESTMAIN {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Controller controller = new Controller();
        Worker worker = new Worker();
        PersistenceManager pm = new PersistenceManager();

        ModeTwo modeTwo = new ModeTwo(worker, controller, pm);
        // Assuming ModeThree exists based on your snippet
        // ModeThree modeThree = new ModeThree(worker, controller, pm); 

        System.out.println("=== WORDLE TERMINAL ===");
        System.out.println("1 - Mode One (Survival)");
        System.out.println("2 - Mode Two (60s Blitz)");
        System.out.println("3 - Mode Three (Classic)");
        System.out.print("Select Mode: ");

        int mode = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (mode) {
            case 1:
                controller.startGame();
                while (controller.getState() == Controller.State.Play) {
                    System.out.print("Guess: ");
                    String guess = scanner.nextLine();
                    Worker.Color[] result = controller.submitGuessModeOne(guess);

                    if (result == null) continue;

                    printResult(result);
                    System.out.println("Status: " + controller.currentStatus +
                                       " | Score: " + controller.getTotalScore() +
                                       " | Lives: " + controller.getLives());

                    if (controller.currentStatus == Controller.Status.GAME_OVER) break;
                }
                break;

            case 2:
                // 1. RESET scores before starting
                modeTwo.reset(); 
                System.out.println("\n[MODE 2 STARTED - 60 SECONDS ON THE CLOCK]");
                
                // 2. START THE TIMER
                modeTwo.modeTwoTimer(); 

                while (!modeTwo.isTimedOut()) {
                    System.out.print("Guess: ");
                    
                    // The program waits here. If the timer ends while you type, 
                    // you must hit Enter one last time to trigger the exit.
                    String guess = scanner.nextLine();

                    // 3. IMMEDIATELY CHECK IF TIME EXPIRED
                    if (modeTwo.isTimedOut()) {
                        break; 
                    }

                    worker.startNewRound(); // Generate a new target word
                    Worker.Color[] result = worker.compare(guess);
                    modeTwo.updateScores(result);

                    printResult(result);
                    System.out.println("Wins: " + modeTwo.getWins() + " | Attempts: " + worker.getAttempts());
                }

                System.out.println("\n[!!!] TIME EXPIRED [!!!]");
                System.out.println("Final Results -> Wins: " + modeTwo.getWins() + " | Losses: " + modeTwo.getLosses());
                break;

            case 3:
                System.out.println("\nMODE 3 STARTED");
                while (true) {
                    worker.startNewRound();
                    System.out.println("\nNew word started!");
                    
                    boolean wordSolved = false;
                    while (worker.getAttempts() < 6 && !wordSolved) {
                        System.out.print("Guess (or 'exit'): ");
                        String guess = scanner.nextLine();
                        if (guess.equalsIgnoreCase("exit")) return;

                        Worker.Color[] result = worker.compare(guess);
                        printResult(result);

                        if (worker.allGreen(result)) {
                            pm.recordWin();
                            System.out.println("CORRECT!");
                            wordSolved = true;
                        } else if (worker.getAttempts() >= 6) {
                            pm.recordLoss();
                            System.out.println("OUT OF ATTEMPTS.");
                        }
                    }
                    System.out.println("Total Wins: " + pm.getModeThreeWins());
                }
        }
        scanner.close();
        System.exit(0); // Forces the Timer thread to shut down
    }

    // Helper method to keep the main code clean
    private static void printResult(Worker.Color[] result) {
        System.out.print("Result: [ ");
        for (Worker.Color c : result) {
            System.out.print(c + " ");
        }
        System.out.println("]");
    }
}