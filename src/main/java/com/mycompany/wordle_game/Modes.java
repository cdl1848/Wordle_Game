package com.mycompany.wordle_game;
// for citing https://chatgpt.com/share/69c19c2f-2c5c-8010-95cc-5077987a79e5 on monday march 23 2026 used this for the Load and Save function 
import java.io.*;
import java.util.*;


/**
 * Handles persistence for all game modes using a plain TXT file
 * Stores Mode 1 & 2 scores and Mode 3 wins/losses
 */
public class Modes {
    private static final String FILE_NAME = "modes.txt";
    private Map<String, Integer> data = new HashMap<>();

    
    public Modes() {
        load();
    }
    
    
    /**
     * if file does not exist it makes a hash map with default values, m1_high, m1_last, m2_high, m2_last, m3_wins, m3_losses sets all values to 0.
     * if file does exist it reads lines and looks for an "=" splits the line at that "=" and uses the 0 index(left side) to the hash key and then 1 index(right side) as the hash value. updates the hash map with these values.
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
            save(); // create the file with defaults
            return;
        }
            try(Scanner reader = new Scanner(new File(FILE_NAME))){
                String line;
                while (reader.hasNextLine()) {
                    line = reader.nextLine();
                    if (line.isEmpty() || !line.contains("=")) continue;

                    String[] parts = line.split("=");
                    if (parts.length != 2) continue;

                    String key = parts[0].trim();
                    int value = Integer.parseInt(parts[1].trim());
                    data.put(key, value);
                }
            }
            catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            }
    }
    
    
    /**
     * updates the file with data from the hash map. grabs the hash key concatenates that with and "=" then concatenates that with the hash value to put that into the txt. Also creates the file if no file exists. 
     */
    private void save() {
            File file = new File(FILE_NAME);
            try (FileWriter writer = new FileWriter(new File(FILE_NAME))) {
                for (Map.Entry<String, Integer> entry : data.entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.write(System.getProperty( "line.separator" ));
                }
            }
         catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /** 
    * gets the high score for mode one from the hash map
    * @return 
    */
    public int getModeOneHighScore() {
        return data.getOrDefault("m1_high", 0);
    }
    /** 
    * gets the last score for mode one from the hash map
    * @return 
    */
    public int getModeOneLastScore() {
        return data.getOrDefault("m1_last", 0);
    }
    /**
     * updates the hashmap with new scores for mode one. If the last score is higher than the high score high score is updated. It then saves to the txt file.
     * @param score 
     */
    public void updateModeOneScore(int score) {
        data.put("m1_last", score); // always update last score
        if (score > getModeOneHighScore()) {
            data.put("m1_high", score);
        }

        save();
    }
    
    
    /** 
     * gets the Hight score for mode 2 from the hash map.
     * @return 
     */
    public int getModeTwoHighScore() {
        return data.getOrDefault("m2_high", 0);
    }
    
    
    /** 
     * gets the Last score for mode 2 from the hash map.
     * @return 
     */
    public int getModeTwoLastScore() {
        return data.getOrDefault("m2_last", 0);
    }
    
    
    /**
     * updates the hashmap with new scores for mode two. If the last score is higher than the high score high score is updated. It then saves to the txt file.
     * @param score 
     */
    public void updateModeTwoScore(int score) {
        data.put("m2_last", score);
        if (score > getModeTwoHighScore()) {
            data.put("m2_high", score);
        }
        save();
    }
    
    
    /** 
     * gets the number of wins in mode 3 from the hash map.
     * @return 
     */
    public int getModeThreeWins() {
        return data.getOrDefault("m3_wins", 0);
    }
    
    
    /** 
     * gets the number of losses in mode 3 from the hash map.
     * @return 
     */
    public int getModeThreeLosses() {
        return data.getOrDefault("m3_losses", 0);
    }
    
    
    /**
     * when a game is won this adds 1 to the value of m3_wins in the hash map then saves to the txt
     */
    public void recordWin() {
        int wins = getModeThreeWins() + 1;
        data.put("m3_wins", wins);
        save();
    }
    
    
    /**
     * when a game is lost this adds 1 to the value of m3_loss in the hash map, then saves to the txt
     */
    public void recordLoss() {
        int losses = getModeThreeLosses() + 1;
        data.put("m3_losses", losses);
        save();
    }
}
