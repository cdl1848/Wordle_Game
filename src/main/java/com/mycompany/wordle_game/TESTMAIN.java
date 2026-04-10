package com.mycompany.wordle_game;

import java.util.Random;

/**
 * Stress tests the Controller class using only valid five-letter guesses.
 * Mode Two is intentionally excluded.
 */
public class TESTMAIN {

    private static final Random RANDOM = new Random();
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int WORD_LENGTH = 5;

    public static void main(String[] args) {
        System.out.println("===== CONTROLLER STRESS TEST STARTED =====\n");

        Controller controller = new Controller();

        stressModeOne(controller);
        stressModeThree(controller);
        rapidFireTest(controller);
        stateTransitionTest(controller);

        System.out.println("\n===== CONTROLLER STRESS TEST COMPLETED =====");
    }

    /**
     * Generates a random five-letter lowercase word.
     */
    private static String randomFiveLetterWord() {
        StringBuilder sb = new StringBuilder(WORD_LENGTH);
        for (int i = 0; i < WORD_LENGTH; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    /**
     * Converts a color array into a readable string.
     */
    private static String colorArrayToString(Worker.Color[] colors) {
        if (colors == null) return "INVALID";
        StringBuilder sb = new StringBuilder();
        for (Worker.Color c : colors) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Stress test for Mode One.
     * Simulates repeated full game sessions.
     */
    private static void stressModeOne(Controller controller) {
        System.out.println("---- Stress Testing Mode One ----");

        controller.startModeOne();
        int iterations = 5000;

        for (int i = 0; i < iterations; i++) {
            String guess = randomFiveLetterWord();
            Worker.Color[] result = controller.submitGuess(guess);

            if (controller.currentStatus == Controller.Status.GAME_OVER) {
                System.out.println("GAME OVER at iteration: " + i);
                System.out.println("Final Score: " + controller.getTotalScore());
                controller.startModeOne();
            }

            if (i % 1000 == 0 && i != 0) {
                System.out.println("Mode One Progress: " + i + " guesses processed.");
            }
        }

        System.out.println("Mode One stress test completed.\n");
    }

    /**
     * Stress test for Mode Three.
     * Simulates continuous infinite gameplay.
     */
    private static void stressModeThree(Controller controller) {
        System.out.println("---- Stress Testing Mode Three ----");

        controller.startModeThree();
        int iterations = 8000;

        for (int i = 0; i < iterations; i++) {
            String guess = randomFiveLetterWord();
            controller.submitGuess(guess);

            if (controller.currentStatus == Controller.Status.WIN ||
                controller.currentStatus == Controller.Status.ROUND_LOST) {

                if (i % 500 == 0) {
                    System.out.println(
                        "Round Completed at iteration " + i +
                        " | Wins: " + controller.getModeThreeWins() +
                        " | Losses: " + controller.getModeThreeLosses()
                    );
                }
            }
        }

        System.out.println("Mode Three stress test completed.\n");
    }

    /**
     * Rapid-fire test to evaluate performance under heavy load.
     */
    private static void rapidFireTest(Controller controller) {
        System.out.println("---- Rapid Fire Performance Test ----");

        controller.startModeThree();
        int iterations = 20000;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            controller.submitGuess(randomFiveLetterWord());
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println(iterations + " guesses processed in " + duration + " ms");
        System.out.printf("Average Time per Guess: %.5f ms%n",
                (double) duration / iterations);

        System.out.println("Rapid fire test completed.\n");
    }

    /**
     * Tests correctness of state transitions.
     */
    private static void stateTransitionTest(Controller controller) {
        System.out.println("---- Testing State Transitions ----");

        controller.startModeOne();
        assert controller.getState() == Controller.State.Play : "Mode One failed to start.";

        controller.endMode();
        assert controller.getState() == Controller.State.End : "End mode failed.";

        controller.startModeThree();
        assert controller.getState() == Controller.State.Play : "Mode Three failed to start.";

        System.out.println("State transition test completed.\n");
    }
}
