package br.com.animator.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import br.com.animator.core.AbstractGame;
import br.com.animator.game.data.enumerators.ScreenMode;
import br.com.animator.game.factory.CoreGameFactory;
import br.com.animator.game.ui.menu.GameExitMenu;
import br.com.animator.state.GameStateMachine;
import br.com.animator.ui.CoreGameLogic;
import br.com.animator.ui.menu.GameExitMenuImpl;
import br.com.animator.window.Window;

/**
 * Class responsible for managing the game menu, including the developer logo, intro, high score presentation, 
 * main menu, options menu, and exit menu. It handles user input to navigate through these screens and updates the game settings accordingly.
 */
public class Game extends AbstractGame {

    //--- Constants ---//
    private static final String FS_ERROR_TITLE = "Error changing fullscreen mode";
    private static final String FS_ERROR_MESSAGE = "Failed to initialize in FullScreen mode.\n" +
                                                   "Try changing the video mode in Game-Options.";

    //--- Properties ---//
    private CoreGameLogic currentCoreGame;
    private GameStateMachine gameStateMachine;
    private Window gameWindow;
    private GameExitMenu gameExitMenu;

    /**
     * Constructor for the Game class.
     */
    public Game() {
        //do nothing
    }
    
    /**
     * Initializes the game menu by creating instances of the various screens 
     * and options, and setting up their properties based on the game window's 
     * dimensions and aspect ratio.
     */
    public void init() {
        this.gameWindow = this.getGameWindow();
        this.gameStateMachine = new GameStateMachine();
        this.gameExitMenu = new GameExitMenuImpl(
            gameWindow.getPanelWidth(), 
            gameWindow.getPanelHeight(), 
            gameWindow.getCurrentAspectRatio()
        );
        this.updateCurrentCoreGame();
    }

    /**
     * Updates the current core game instance based on state machine.
     * Centralizes the factory call to reduce duplication.
     */
    private void updateCurrentCoreGame() {
        this.currentCoreGame = CoreGameFactory.getInstance(this.gameStateMachine, this.gameWindow);
    }

    /**
     * Updates the game menu based on the current state and the elapsed time since the last frame.
     */
    @Override
    public void update(long frametime) {
        if (!this.loading && this.currentCoreGame.finished()) {
            this.currentCoreGame.resetCounters();
            this.gameStateMachine.gotoNextState();
            this.updateCurrentCoreGame();
        }
        this.currentCoreGame.update(frametime);
    }

    /**
     * Renders the game menu based on the current state and the elapsed time since the last frame.
     * @param delta The time elapsed since the last frame.
     */
    @Override
    public void render(long delta) {
        if (!gameWindow.isReadyToRender()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) gameWindow.getBufferStrategy().getDrawGraphics();
        if (g2d == null) {
            return;
        }

        try {
            // Apply window insets if windowed
            if (!gameWindow.isFullScreen()) {
                g2d.translate(0, gameWindow.getInsets().top);
            }

            // Clear background
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, gameWindow.getPanelWidth(), gameWindow.getPanelHeight());

            // Draw current screen
            this.currentCoreGame.draw(g2d);

            // Draw FPS overlay if enabled
            if (this.isToShowFPS) {
                drawFPSOverlay(g2d);
            }

            // Draw exit menu if visible
            if (this.gameExitMenu.isShowingExitMenu()) {
                this.gameExitMenu.draw(g2d);
            }
        } finally {
            this.graphics2D = g2d;
        }
    }

    /**
     * Draws the FPS/UPS overlay on the screen.
     */
    private void drawFPSOverlay(Graphics2D g2d) {
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
            this.currentCoreGame.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
            this.gameExitMenu.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
        } finally {
            this.loadingDone();
        }
    }

    /**
     * Handles key press events to navigate through the game menu and update settings based on user input.
     */
    @Override
    public void keyPressed(int keyCode, boolean isAltDown) {
        if (gameExitMenu.isShowingExitMenu()) {
            handleExitMenuInput(keyCode);
        } else {
            handleGameInput(keyCode, isAltDown);
        }
    }

    /**
     * Handles input when exit menu is visible.
     */
    private void handleExitMenuInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                gameExitMenu.previousGameOption();
                break;
            case KeyEvent.VK_RIGHT:
                gameExitMenu.nextGameOption();
                break;
            case KeyEvent.VK_ENTER:
                if (gameExitMenu.isToExit()) {
                    stopGame();
                } else {
                    gameExitMenu.hideExitMenu();
                }
                break;
        }
    }

    /**
     * Handles input during normal gameplay.
     */
    private void handleGameInput(int keyCode, boolean isAltDown) {
        // Alt+F4: Show exit menu
        if (isAltDown && keyCode == KeyEvent.VK_F4 && canShowExitMenu()) {
            gameExitMenu.showExitMenu();
            return;
        }

        // Any key in intro: Go to main menu
        if (keyCode != KeyEvent.VK_ALT && gameStateMachine.isInIntro()) {
            gotoMainMenu();
            return;
        }

        // Alt+Enter: Toggle fullscreen
        if (isAltDown && keyCode == KeyEvent.VK_ENTER) {
            handleFullscreenToggle();
            return;
        }

        // P or Pause: Toggle pause
        if ((keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_PAUSE) && 
            !gameStateMachine.isInIntro() && !gameStateMachine.isInOptions()) {
            togglePause();
            return;
        }

        // Default: Handle input for current screen
        currentCoreGame.handleInput(this, keyCode, isAltDown);
    }

    /**
     * Checks if the exit menu can be shown.
     */
    private boolean canShowExitMenu() {
        return !gameStateMachine.isInIntroDev() && !gameStateMachine.isInOptions();
    }

    /**
     * Handles fullscreen toggle via Alt+Enter.
     */
    private void handleFullscreenToggle() {
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
            JOptionPane.showMessageDialog(null, FS_ERROR_MESSAGE, FS_ERROR_TITLE, 
                JOptionPane.ERROR_MESSAGE);
            gameWindow.backToWindow();
            CoreGameFactory.configureGameGraphics(ScreenMode.WINDOWED);
        } finally {
            resumeGame();
        }
    }

    /**
     * Toggles the pause state.
     */
    private void togglePause() {
        if (!isPaused) {
            pauseGame();
        } else {
            resumeGame();
        }
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
     * Navigate to a game state using the provided state navigator.
     * Centralizes state transitions and core game updates.
     */
    private void navigateToState(Runnable stateNavigator) {
        stateNavigator.run();
        updateCurrentCoreGame();
    }

    public void gotoMainOption() {
        navigateToState(() -> gameStateMachine.gotoMainOption());
    }

    public void gotoMainMenu() {
        navigateToState(() -> gameStateMachine.gotoMainMenu());
    }

    public void gotoGameOptions() {
        navigateToState(() -> gameStateMachine.gotoGameOptions());
    }

    public void gotoSFXConfigMenu() {
        navigateToState(() -> gameStateMachine.gotoSFXConfigMenu());
    }

    public void gotoGFXConfigMenu() {
        navigateToState(() -> gameStateMachine.gotoGFXConfigMenu());
    }
}
