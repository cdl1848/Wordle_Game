package com.mycompany.wordle_game;

import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {

        System.out.println("GameRunner started");

        Controller controller = new Controller();
        Scanner input = new Scanner(System.in);

        controller.endMode();

        while (true) {

            // HOME
            if (controller.getState() == Controller.State.End) {

                System.out.println("\n=================");
                System.out.println("WORDLE");
                System.out.println("=================");
                System.out.println("Total Score: " + controller.getTotalScore());
                System.out.println("Press P to Play");
                System.out.println("=================");

                String choice = input.nextLine().trim().toUpperCase();

                if (choice.equals("P")) {
                    controller.startGame();
                }
            }

            // GAME
            else if (controller.getState() == Controller.State.Play) {

                System.out.println("\n--- NEW ROUND ---");
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
                            System.out.println("Correct! New round starting.");
                            System.out.println("New Word: " + controller.debugWord());
                            break;

                        case ROUND_LOST:
                            System.out.println("Round lost! Life lost.");
                            System.out.println("New Word: " + controller.debugWord());
                            break;

                        case GAME_OVER:
                            System.out.println("Out of lives! Returning to home.");
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
