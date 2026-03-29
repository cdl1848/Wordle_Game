package com.mycompany.wordle_game;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;


/**
* Handles persistence for all game modes using a plain TXT file
* Stores Mode 1 and 2 scores and Mode 3 wins/losses
*/
public class PersistenceManager {
    private static final String FILE_NAME = "UserData.txt";
    private Map<String, Integer> data = new HashMap<>();

    
    public PersistenceManager() {
        load();
    }
    
    
    /**
    * If file does not exist it makes a HashMap with default values, m1_high, m1_last, m2_high, m2_last, m3_wins, m3_losses sets all values to 0.
    * If file does exist it reads lines and looks for an "=" splits the line at that "=" and uses the 0 index(left side) to the hash key and then 1 index(right side) as the hash value. updates the HashMap with these values.
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
    * Updates the file with data from the HashMap. Grabs the hash key concatenates that with and "=" then concatenates that with the hash value to put that into the .txt. Also creates the file if no file exists. 
    */
    private void save() {
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
    * Gets the high score for mode one from the hash map.
    * @return 
    */
    public int getModeOneHighScore() {
        return data.getOrDefault("m1_high", 0);
    }
    /** 
    * Gets the last score for mode one from the hash map.
    * @return data.getOrDefault("m1_last", 0)
    */
    public int getModeOneLastScore() {
        return data.getOrDefault("m1_last", 0);
    }
    /**
    * Updates the HashMap with new scores for mode one. If the last score is higher than the high score high score is updated. It then saves to the .txt file.
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
    * Gets the Hight score for mode 2 from the hash map.
    * @return data.getOrDefault("m2_high", 0)
    */
    public int getModeTwoHighScore() {
        return data.getOrDefault("m2_high", 0);
    }
    
    
    /** 
    * Gets the Last score for mode 2 from the hash map.
    * @return data.getOrDefault("m2_last", 0)
    */
    public int getModeTwoLastScore() {
        return data.getOrDefault("m2_last", 0);
    }
    
    
    /**
    * Updates the HashMap with new scores for mode two. If the last score is higher than the high score high score is updated. It then saves to the .txt file.
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
    * Gets the number of wins in mode 3 from the hash map.
    * @return data.getOrDefault("m3_wins", 0)
    */
    public int getModeThreeWins() {
        return data.getOrDefault("m3_wins", 0);
    }
    
    
    /** 
    * Gets the number of losses in mode 3 from the hash map.
    * @return data.getOrDefault("m3_losses", 0)
    */
    public int getModeThreeLosses() {
        return data.getOrDefault("m3_losses", 0);
    }
    
    
    /**
    * When a game is won this adds 1 to the value of m3_wins in the hash map then saves to the .txt.
    */
    public void recordWin() {
        int wins = getModeThreeWins() + 1;
        data.put("m3_wins", wins);
        save();
    }
    
    
    /**
    * When a game is lost this adds 1 to the value of m3_loss in the hash map, then saves to the .txt.
    */
    public void recordLoss() {
        int losses = getModeThreeLosses() + 1;
        data.put("m3_losses", losses);
        save();
    }
}
