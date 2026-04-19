package br.com.animator;

import javax.swing.SwingUtilities;
import br.com.animator.game.Game;

/**
 * Launcher class to start the game engine.
 */
public class Launcher {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {		
			public void run() {
				new Game().startGame(0);
			}
		});
	}
}