package br.com.animator.core;

import java.awt.image.BufferedImage;

/**
 * Interface that defines the basic structure of a game. It includes methods for
 * updating the game state, rendering graphics, handling user input, and
 * managing game settings. Implementing this interface allows for a consistent
 * way to create and manage games within the framework.
 */
public interface IGame {

    /**
     * Updates the game state based on the elapsed time since the last update. This
     * method is called regularly to ensure that the game logic is processed and the
     * game world is updated.
     *
     * @param frametime
     */
    public void update(long frametime);

    /**
     * Renders the game graphics based on the current state of the game. This method
     * is called after the update method to draw the game world and any visual
     * elements on the screen.
     * 
     * @param delta
     */
    public void render(long delta);

    /**
     * Handles the painting of the game screen. This method is responsible for
     * drawing the final output to the screen after all rendering is done. It may
     * involve double buffering or other techniques to ensure smooth visuals.
     */
    public void paintScreen();

    /**
     * Updates the game settings, such as screen mode and dimensions. This method
     * allows for dynamic changes to the game configuration, such as switching
     * between full-screen and windowed mode or adjusting the resolution.
     * 
     * @param isFullScreen
     * @param pWIDTH
     * @param pHEIGHT
     */
    public void updateGameSettings(boolean isFullScreen, Integer pWIDTH, Integer pHEIGHT);

    /**
     * Pauses the game.
     */
    public void pauseGame();

    /**
     * loading - Set the loading flag to true, indicating that the game is currently
     * loading resources or performing some initialization tasks.
     */
    public void loading();

    /**
     * loadingDone - Set the loading flag to false, indicating that the game has
     * finished loading resources or initialization tasks.
     */
    public void loadingDone();

    /**
     * Resume the game.
     */
    public void resumeGame();

    /**
     * Stop the game.
     */
    public void stopGame();

    /*
     * Handles key press events.
     * 
     * @param keyCode
     * 
     * @param isAltDown
     */
    public void processKey(int keyCode, boolean isAltDown);

    /**
     * Starts the game.
     */
    public void startGame();

    /**
     * Starts the game with a specific frame rate.
     * 
     * @param fps
     */
    public void startGame(int fps);

    /**
     * Get the main draw buffer where Java2D rendering occurs.
     * 
     * @return The BufferedImage used as a backbuffer
     */
    public BufferedImage getMainBuffer();

    /**
     * Detect the Joystick pressed button
     * @param bid
     * @return
     */
    public void processJoystickButton(int joystickId, int buttonId);

    /**
     * Detect the Joystick hat moved
     * @param joystickId
     * @param hatId
     * @param state
     */
    public void processJoystickHat(int joystickId, int hatId, byte state);

    /**
     * Toggles Fast Forward mode (Unlimited FPS).
     * @param active true to enable fast forward, false to return to normal speed.
     */
    public void toggleFastForward(boolean active);

    /**
     * Sets the target frames per second dynamically.
     * @param fps
     */
    public void setTargetFPS(int fps);
}
