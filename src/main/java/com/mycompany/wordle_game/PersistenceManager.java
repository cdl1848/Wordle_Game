package com.mycompany.wordle_game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

/**
 * Manages persistent storage of player statistics across all game modes.
 * Data is stored in a plain text file using a simple key=value format.
 * Data is loaded into a hashmap at construction.
 * Tracked statistics:
 *   Mode 1 high score m1_high and last score m1_last
 *   Mode 2 high score m2_high and last score m2_last
 *   Mode 3 total wins m3_wins and total losses m3_losses
 */
public class PersistenceManager {

    private static final String FILE_NAME = "UserData.txt";
    private Map<String, Integer> data = new HashMap<>();
    
    /**
     * Constructs a PersistenceManager and loads saved data.
     */
    public PersistenceManager() {
        load();
    }

    /**
     * Loads player data from file to hashmap
     * 
     * If file does not exist it makes a HashMap with default values, m1_high,
     * m1_last, m2_high, m2_last, m3_wins, m3_losses sets all values to 0. If
     * file does exist it reads lines and looks for an "=" splits the line at
     * that "=" and uses the 0 index(left side) to the hash key and then 1
     * index(right side) as the hash value. updates the HashMap with these
     * values.
     */
    private void load() {

        File file = new File(FILE_NAME);

        // Initialize defaults if file doesn't exist
        if (!file.exists()) {
            data.put("m1_high", 0);
            data.put("m1_last", 0);
            data.put("m2_high", 0);
            data.put("m2_last", 0);
            data.put("m3_wins", 0);
            data.put("m3_losses", 0);
            save();
            return;
        }
        try (Scanner reader = new Scanner(new File(FILE_NAME))) {
            String line;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                if (line.isEmpty() || !line.contains("=")) {
                    continue;
                }

                String[] parts = line.split("=");
                if (parts.length != 2) {
                    continue;
                }

                String key = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                data.put(key, value);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the file with data from the HashMap. Grabs the hash key
     * concatenates that with and "=" then concatenates that with the hash value
     * to put that into the .txt. Also creates the file if no file exists.
     */
    private void save() {
        try (FileWriter writer = new FileWriter(new File(FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the high score for mode one from the hash map.
     *
     * @return the Mode 1 high score
     */
    public int getModeOneHighScore() {
        return data.getOrDefault("m1_high", 0);
    }

    /**
     * Gets the last score for mode one from the hash map.
     *
     * @return the Mode 1 last score
     */
    public int getModeOneLastScore() {
        return data.getOrDefault("m1_last", 0);
    }

    /**
     * Records a new score for Mode 1. The last-score entry is always updated;
     * the high-score entry is updated only if score is larger than high score.
     *
     * @param score the score achieved at the end of a Mode 1 game.
     */
    public void updateModeOneScore(int score) {
        data.put("m1_last", score); // always update last score
        if (score > getModeOneHighScore()) {
            data.put("m1_high", score);
        }

        save();
    }

    /**
     * Gets the Hight score for mode 2 from the hash map.
     *
     * @return the Mode 2 high score
     */
    public int getModeTwoHighWins() {
        return data.getOrDefault("m2_high", 0);
    }

    /**
     * Gets the Last score for mode 2 from the hash map.
     *
     * @return the Mode 2 last score
     */
    public int getModeTwoLastWins() {
        return data.getOrDefault("m2_last", 0);
    }

    /**
     * records a new score for Mode 2. The last-score entry is always updated;
     * the high-score entry is updated only if score is larger than the high score.
     *
     * @param score the score achieved at the end of a Mode 2 game
     */
    public void updateModeTwoScore(int score) {
        data.put("m2_last", score);
        if (score > getModeTwoHighWins()) {
            data.put("m2_high", score);
        }
        save();
    }

    /**
     * Gets the number of wins in mode 3 from the hash map.
     *
     * @return the Mode 3 win count
     */
    public int getModeThreeWins() {
        return data.getOrDefault("m3_wins", 0);
    }

    /**
     * Gets the number of losses in mode 3 from the hash map.
     *
     * @return the Mode 3 loss count
     */
    public int getModeThreeLosses() {
        return data.getOrDefault("m3_losses", 0);
    }

    /**
     * Increments the Mode 3 win counter by one and persists the change.
     */
    public void recordWin() {
        int wins = getModeThreeWins() + 1;
        data.put("m3_wins", wins);
        save();
    }

    /**
     * Increments the Mode 3 loss counter by one and persists the change.
     */
    public void recordLoss() {
        int losses = getModeThreeLosses() + 1;
        data.put("m3_losses", losses);
        save();
    }
}
