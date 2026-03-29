package br.com.game.animator.util;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class AltTabStopper implements Runnable {
	
     private boolean working = true;
     private JFrame frame;

     public AltTabStopper(JFrame frame) {
          this.frame = frame;
     }

     public void stop() {
          this.working = false;
     }

     public static AltTabStopper create(JFrame frame) {
         AltTabStopper stopper = new AltTabStopper(frame);
         new Thread(stopper, "Alt-Tab Stopper").start();
         return stopper;
     }

     public void run() {
         try {
             Robot robot = new Robot();
             while (working) {
                  robot.keyRelease(KeyEvent.VK_ALT);
                  robot.keyRelease(KeyEvent.VK_TAB);
                  frame.requestFocus();
                  try { Thread.sleep(10); } catch(Exception ex) {}
             }
         } catch (Exception e) { 
        	 e.printStackTrace(); System.exit(-1); 
         }
     }
}