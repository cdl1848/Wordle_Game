package com.mycompany.wordle_game;
/**
 * COntructs the Modes class, includes a worker object, controller object, and 2 score ints
 * @author dallas
 */
public class Modes {
    Worker worker = new Worker();
    Controller controller = new Controller();
    int scoreone= 0;
    int scoretwo= 0;

/**
 * currently does nothing but break my game :) 
 * @return int
 */
public int getScoreOne(){
    return scoreone;
}
/**
 * currently does nothing but break my game :) 
 * @return int
 */
public int getScoreTwo(){
    return scoretwo;
}

/**
 * Sets score one in the file for saving reasons 
 */
private void setScoreOne(){
    // updates score one for which mode its called for reads from or too file 
}

/**
 * Sets score one in the file for saving reasons 
 */
private void setScoreTwo(){
    //updates socre two for wich mode its called for reads from  or too file 
}


}