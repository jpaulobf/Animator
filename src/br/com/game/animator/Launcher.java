package br.com.game.animator;

import javax.swing.SwingUtilities;

import br.com.game.animator.engine.GameEngine;

/**
 * Launcher class to start the game engine.
 */
public class Launcher {
    	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new GameEngine();
			}
		});
	}
}