package br.com.game.animator.game;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import br.com.game.animator.engine.GameEngine;
import br.com.game.animator.window.Window;

/**
 * AbstractGame - Abstract class that implements the IGame interface and provides common functionality for all games.
 * This class is responsible for managing the game loop, handling the game window, and providing methods for pausing, resuming, and stopping the game.
 * It also defines abstract methods for updating the game state, rendering the game, and handling key presses, which must be implemented by any 
 * concrete game class that extends this abstract class.
 */
public abstract class AbstractGame implements IGame {

    //--- Properties ---//
    protected Graphics2D graphics2D = null;
    protected Window gameWindow = null;
    protected GameEngine gameEngine = null;
    volatile boolean running = false;
	volatile boolean gameOver = false;
	volatile boolean isPaused = false;
	protected volatile boolean loading = false;
    protected volatile boolean isToShowFPS = true;

    /**
     * Constructor
     */
    public AbstractGame() {
		//do nothing
    }

	public void startGame() {
		this.startGame(60);
	}

	public void startGame(int fps) {
		this.gameWindow = new Window(this);

		this.init();

		this.gameEngine = new GameEngine(this, fps);
	}

	public abstract void init();

    /**
	 * paintScreen - Paint the buffer to the screen.
	 */
	public void paintScreen() {
		try {
			BufferStrategy strategy = gameWindow.getBufferStrategy();
			this.graphics2D = (Graphics2D) strategy.getDrawGraphics();
			this.graphics2D.dispose();
			if (!strategy.contentsLost()) {
				strategy.show();
			} else {
				System.out.println("Contents Lost");
			}
			Toolkit.getDefaultToolkit().sync();
		} catch (Exception e) {
			this.running = false;
		}
	}

    /**
     * Pause the game.
     */
	public void pauseGame() {
		this.isPaused = true;
	}

    /**
     * loading - Set the loading flag to true, indicating that the game is currently loading resources or performing some initialization tasks.
     */
	public void loading() {
		this.loading = true;
	}

    /**
     * loadingDone - Set the loading flag to false, indicating that the game has finished loading resources or initialization tasks.
     */
	public void loadingDone() {
		this.loading = false;
	}

    /**
     * Resume the game.
     */
	public void resumeGame() {
		this.isPaused = false;
	}

    /**
     * Stop the game.
     */
	public void stopGame() {
		gameEngine.stop();
	}

    //--- Abstract Methods ---//
    public abstract void update(long frametime);
    public abstract void render(long delta);
    public abstract void keyPressed(int keyCode, boolean isAltDown);
}