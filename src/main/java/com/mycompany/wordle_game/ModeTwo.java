package com.mycompany.wordle_game;
import java.util.Timer;
import java.util.TimerTask;
        
/**
 * Class for ModeTwo - will handle scores, timer, and value updating
 * @author dallas
 */
public class ModeTwo extends Modes {
    private int timeout = 0;
    private int totalscore = 0;
    private Worker worker; 
    private Controller controller;
    
    
   public ModeTwo(Worker worker ) {
        this.worker = worker;
        this.controller = controller;
        
    }
   public void ModeTwoTimer() {
    Timer timer = new Timer(); 
    TimerTask task1 = new TimerTask() {
        public void run() {
            timeout=1;

        }
    };
   timer.schedule(task1, 5000); // <- 5 seconds 
   
   
   }

   public int getScore(){
       return totalscore;
   }

}


