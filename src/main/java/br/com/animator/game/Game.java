package br.com.animator.game;

import java.awt.Color;
import java.awt.Graphics2D;
import br.com.animator.core.game.AbstractGame;
import br.com.animator.game.ui.menu.GameExitMenuImpl;
import br.com.animator.input.GameAction;
import br.com.animator.state.GameStateMachine;

/**
 * Class responsible for managing the game menu, including the developer logo, intro, high score presentation, 
 * main menu, options menu, and exit menu. It handles user input to navigate through these screens and updates the game settings accordingly.
 */
public class Game extends AbstractGame {

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
        this.gameStateMachine = new GameStateMachine();
        this.gameExitMenu = new GameExitMenuImpl(
            gameWindow.getPanelWidth(), 
            gameWindow.getPanelHeight(), 
            gameWindow.getCurrentAspectRatio()
        );
        this.updateCurrentCoreGame();
    }

    /**
     * Updates the game menu based on the current state and the elapsed time since the last frame.
     */
    @Override
    public void update(long frametime) {
        this.gameWindow.pollJoysticks();

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
        Graphics2D g2d = null;

        if (this.renderer.isNative()) {
            if (!gameWindow.isReadyToRender()) return;
            g2d = (Graphics2D) gameWindow.getBufferStrategy().getDrawGraphics();
        } else {
            g2d = this.graphics2D;
        }

        if (g2d == null) return;

        try {
            // Limpa o fundo do buffer
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
        } catch (Exception e) {
            System.err.println("Error during game rendering: " + e.getMessage());
        } finally {
            // No modo native, precisamos liberar o context do BufferStrategy
            if (this.renderer.isNative() && g2d != null) {
                g2d.dispose();
            }
        }
    }

    /**
     * Handles key press events to navigate through the game menu and update settings based on user input.
     */
    @Override
    public void keyPressed(GameAction action) {
        handleGameAction(action);
    }

    /**
     * Trata o pressionamento de botões do Joystick.
     * @param buttonCode O código do botão pressionado.
     */
    @Override
    public void joystickButtonPressed(GameAction action) {
        handleGameAction(action);
    }

    /**
     * Trata a mudança de estado do DPAD/Hat.
     */
    @Override
    public void joystickHatMoved(GameAction action) {
        handleGameAction(action);
    }

    /**
     * Handles logical actions during normal gameplay.
     */
    protected void handleGameAction(GameAction action) {
        // Any action in intro: Go to main menu
        if (gameStateMachine.isInIntro()) {
            gotoMainMenu();
            return;
        }

        // Default: Handle input for current screen
        currentCoreGame.handleInput(this, action);
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
