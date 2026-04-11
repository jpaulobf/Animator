package br.com.animator.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import br.com.animator.audio.AudioManager;
import br.com.animator.game.data.enumerators.ScreenMode;
import br.com.animator.game.factory.CoreGameFactory;
import br.com.animator.input.ButtonMapper;
import br.com.animator.input.GameAction;
import br.com.animator.state.GameStateMachine;
import br.com.animator.ui.menu.GameExitMenu;
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

	//--- Constants ---//
    private static final String FS_ERROR_TITLE = "Error changing fullscreen mode";
    private static final String FS_ERROR_MESSAGE = "Failed to initialize in FullScreen mode.\n" +
                                                   "Try changing the video mode in Game-Options.";

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

	//--- Properties ---//
    protected CoreGameLogic currentCoreGame;
    protected GameStateMachine gameStateMachine;
    protected GameExitMenu gameExitMenu;

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

		// Configure visibility and native peer before init
		if (this.renderer.isNative()) {
			if (this.gameWindow.isFullScreen()) {
				this.gameWindow.setFullScreen();
			} else {
				this.gameWindow.setVisible(true);
			}
		} else {
			this.gameWindow.addNotify();
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
		AudioManager.cleanup();
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


	/**
     * Updates the current core game instance based on state machine.
     * Centralizes the factory call to reduce duplication.
     */
    protected void updateCurrentCoreGame() {
        this.currentCoreGame = CoreGameFactory.getInstance(this.gameStateMachine, this.gameWindow);
    }

	/**
     * Draws the FPS/UPS overlay on the screen.
     */
    protected void drawFPSOverlay(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g2d.setColor(Color.RED);
        String fpsText = String.format("Média de FPS / UPS: %d / %d", 
            (int) gameEngine.getAverageFPS(), 
            (int) gameEngine.getAverageUPS());
        g2d.drawString(fpsText, 10, 20);
    }

	/**
     * Updates the game settings based on the current state of the game window.
     */
    public void updateGameSettings(boolean isFullScreen, Integer pWIDTH, Integer pHEIGHT) {
        // Validate parameters
        if (pWIDTH == null || pWIDTH <= 0 || pHEIGHT == null || pHEIGHT <= 0) {
            System.err.println("Invalid window dimensions: " + pWIDTH + "x" + pHEIGHT);
            return;
        }

        this.loading();
        try {
            Integer currentAspectRatio = gameWindow.getCurrentAspectRatio();
            CoreGameFactory.clearCache();
            this.currentCoreGame.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
            this.gameExitMenu.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
        } finally {
            this.loadingDone();
        }
    }

	/**
     * Toggles the pause state.
     */
    protected void togglePause() {
        if (!isPaused) {
            pauseGame();
        } else {
            resumeGame();
        }
    }

	/**
     * Handles fullscreen toggle via Alt+Enter.
     */
    protected void handleFullscreenToggle() {
        pauseGame();
        try {
            if (!gameWindow.isFullScreen()) {
                gameWindow.switchToFullScreen();
                CoreGameFactory.configureGameGraphics(ScreenMode.FULLSCREEN);
            } else {
                gameWindow.backToWindow();
                CoreGameFactory.configureGameGraphics(ScreenMode.WINDOWED);
            }
        } catch (Exception e) {
            System.err.println("Fullscreen toggle failed: " + e.getMessage());
            JOptionPane.showMessageDialog(null, FS_ERROR_MESSAGE, FS_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            gameWindow.backToWindow();
            CoreGameFactory.configureGameGraphics(ScreenMode.WINDOWED);
        } finally {
            resumeGame();
        }
    }

	/**
	 * Handle with the System Shortcuts
	 * @param keyCode
	 * @param isAltDown
	 * @return
	 */
	protected boolean handleSystemShortcuts(int keyCode, boolean isAltDown) {
        if (isAltDown && keyCode == KeyEvent.VK_F4 && canShowExitMenu()) {
            gameExitMenu.showExitMenu();
            return true;
        }
        if (isAltDown && keyCode == KeyEvent.VK_ENTER) {
            handleFullscreenToggle();
            return true;
        }
        return false;
    }

	/**
     * Checks if the exit menu can be shown.
     */
    private boolean canShowExitMenu() {
        return !gameStateMachine.isInIntroDev() && !gameStateMachine.isInOptions();
    }

	/**
     * loading - Set the loading flag to true, indicating that the game is currently
     * loading resources or performing some initialization tasks.
     */
    public void loading() {
        gameStateMachine.setLoadingState();
        updateCurrentCoreGame();
        this.loading = true;
    }

    /**
     * loadingDone - Set the loading flag to false, indicating that the game has
     * finished loading resources or initialization tasks.
     */
    public void loadingDone() {
        gameStateMachine.unloadState();
        updateCurrentCoreGame();
        this.loading = false;
    }

    /**
     * Shows the exit menu.
     */
    public void showExitMenu() {
        gameExitMenu.showExitMenu();
    }

	/**
     * Handles logical actions when exit menu is visible.
     */
    protected void handleExitMenuAction(GameAction action) {
        switch (action) {
            case LEFT:
                gameExitMenu.previousGameOption();
                break;
            case RIGHT:
                gameExitMenu.nextGameOption();
                break;
            case START:
            case BUTTON_1: // Enter maps to Start or B1
                if (gameExitMenu.isToExit()) {
                    stopGame();
                } else {
                    gameExitMenu.hideExitMenu();
                }
                break;
            default: break;
        }
    }

    /**
     * Process Keyboards keys-pressed
     * @param keyCode
     * @param isAltDown
     */
	public void processKey(int keyCode, boolean isAltDown) {
        GameAction action = ButtonMapper.getKeyboardAction(keyCode);
        if (gameExitMenu.isShowingExitMenu()) {
            if (action != null) handleExitMenuAction(action);
            return;
        }

        // Atalhos de Sistema (Não mapeados em GameAction)
        if (handleSystemShortcuts(keyCode, isAltDown)) return;

        if (action != null)
            this.keyPressed(action);
	}

    /**
     * Process Joystick buttons-pressed
     * @param joystickId
     * @param buttonCode
     */
    public void processJoystickButton(int joystickId, int buttonCode) {
        GameAction action = ButtonMapper.getJoystickAction(buttonCode);
        if (gameExitMenu.isShowingExitMenu()) {
            if (action != null) handleExitMenuAction(action);
            return;
        }

        if (action != null)
            this.joystickButtonPressed(action);
    }

    /**
     * Process Joystick hat-moved
     * @param joystickId
     * @param hatId
     * @param state
     */
    public void processJoystickHat(int joystickId, int hatId, byte state) {
        if (state == 0) return;
        GameAction action = ButtonMapper.getHatAction(state);
        if (gameExitMenu.isShowingExitMenu()) {
            if (action != null) handleExitMenuAction(action);
            return;
        }

        if (action != null)
            this.joystickHatMoved(action);
    }

    // --- Abstract Methods ---//
	public abstract void update(long frametime);
	public abstract void render(long delta);
    public abstract void joystickButtonPressed(GameAction action);
    public abstract void joystickHatMoved(GameAction action);
    public abstract void keyPressed(GameAction action);
}