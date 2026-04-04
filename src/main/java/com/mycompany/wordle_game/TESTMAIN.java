package com.mycompany.wordle_game;

import java.util.Scanner;

public class TESTMAIN {

    public static void main(String[] args) {

        System.out.println("GameRunner started");

        Scanner input = new Scanner(System.in);
        Controller controller = new Controller();

        // Start in menu state
        controller.endMode();

        while (true) {

            // ================= MENU =================
            if (controller.getState() == Controller.State.End) {

                System.out.println("\n=================");
                System.out.println("WORDLE");
                System.out.println("=================");
                System.out.println("1: Mode One");
                System.out.println("=================");

                String choice = input.nextLine().trim();

                if (choice.equals("1")) {
                    controller.startGame(); // switches to PLAY
                } else {
                    System.out.println("Invalid choice.");
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

                    // Print result colors
                    for (Worker.Color c : result) {
                        System.out.print(c + " ");
                    }
                    System.out.println();

                    // Handle status
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
                            // Controller should switch to END here
                            break;

                        default:
                            break;
                    }
                }
            }
        }
    }
}
