package com.mycompany.wordle_game;
import java.util.Timer;
import java.util.TimerTask;
        
/**
 * Class for ModeTwo - will handle scores, timer, and value updating
 * @author dallas
 */
public class ModeTwo {
    private boolean timedOut = false;
    private int totalscore = 0;
    private Worker worker; 
    private Controller controller;
    private int wins = 0;
    private int losses = 0;
    
   public ModeTwo(Worker worker, Controller controller ) {
        this.worker = worker;
        this.controller = controller;
        
    }
   public void ModeTwoTimer() {
    Timer timer = new Timer(); 
    TimerTask task1 = new TimerTask() {
        public void run() {
            timedOut = true;
                timer.cancel();
        }
    };
   timer.schedule(task1, 60000); // <-  60 seconds 
   }

   public int getScore(){
       return totalscore;
   }
   public boolean isTimedOut(){
       return timedOut;
   }
public void updateaScores(Worker.Color[] result){
        if (worker.allGreen(result)){
            wins ++;
        }
        else if (worker.getAttempts() >= 6){
            losses ++;
    }
        
    }  //updates stuff in the game

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
    public void reset(){
        wins = 0;
        timedOut = false;
        
    }
}
