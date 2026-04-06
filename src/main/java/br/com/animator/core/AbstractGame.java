package br.com.animator.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.animator.window.Window;
import br.com.animator.window.renderer.Renderer;
import br.com.animator.window.renderer.RendererFactory;

/**
 * AbstractGame - Abstract class that implements the IGame interface and
 * provides common functionality for all games.
 * This class is responsible for managing the game loop, handling the game
 * window, and providing methods for pausing, resuming, and stopping the game.
 * It also defines abstract methods for updating the game state, rendering the
 * game, and handling key presses, which must be implemented by any
 * concrete game class that extends this abstract class.
 */
public abstract class AbstractGame implements IGame {

	// --- Properties ---//
	protected Graphics2D graphics2D = null;
	protected BufferedImage mainBuffer = null;
	protected Window gameWindow = null;
	protected GameEngine gameEngine = null;
	protected Renderer renderer = null;
	protected volatile boolean running = false;
	protected volatile boolean gameOver = false;
	protected volatile boolean isPaused = false;
	protected volatile boolean loading = false;
	protected volatile boolean isToShowFPS = true;

	/**
	 * Constructor
	 */
	public AbstractGame() {
		// do nothing
	}

	public void startGame() {
		this.startGame(60);
	}

	public void startGame(int fps) {
		this.gameWindow = new Window(this);

		// Initialize the renderer based on configuration
		this.renderer = RendererFactory.createRenderer();

		// Configura visibilidade e peer nativo ANTES do init para garantir dimensões
		if (!(this.renderer instanceof br.com.animator.window.renderer.LWJGLRenderer)) {
			if (this.gameWindow.isFullScreen()) {
				this.gameWindow.setFullScreen();
			} else {
				this.gameWindow.setVisible(true);
			}
		} else {
			this.gameWindow.addNotify(); // Cria o peer nativo sem mostrar a janela (evita flicker)
		}

		// Inicializa o buffer de desenho apenas se for usar OpenGL
		if (this.renderer instanceof br.com.animator.window.renderer.LWJGLRenderer) {
			int w = gameWindow.getPanelWidth();
			int h = gameWindow.getPanelHeight();
			this.mainBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			this.graphics2D = this.mainBuffer.createGraphics();
		}

		// Inicializa o estado lógico do jogo primeiro
		this.init();

		this.renderer.init(this.gameWindow);

		this.gameEngine = new GameEngine(this, fps);
	}

	public abstract void init();

	/**
	 * paintScreen - Paint the buffer to the screen using the configured renderer.
	 */
	public void paintScreen() {
		if (renderer == null) {
			return;
		}

		try {
			renderer.render(this.graphics2D);
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
	 * Resume the game.
	 */
	public void resumeGame() {
		this.isPaused = false;
	}

	/**
	 * Stop the game.
	 */
	public void stopGame() {
		if (renderer != null) {
			renderer.dispose();
		}
		gameEngine.stop();
	}

	public Window getGameWindow() {
		return gameWindow;
	}

	public BufferedImage getMainBuffer() {
		return mainBuffer;
	}

	/**
	 * Get the current renderer instance.
	 * Useful for subclasses or other components that need direct renderer access.
	 * 
	 * @return The Renderer instance
	 */
	public Renderer getRenderer() {
		return renderer;
	}

	// --- Abstract Methods ---//
	public abstract void update(long frametime);

	public abstract void render(long delta);

	public abstract void keyPressed(int keyCode, boolean isAltDown);
}