package br.com.animator.input;

/**
 * Enum representing the logical actions in the game, 
 * decoupled from physical input (keyboard/joystick).
 */
public enum GameAction {
    UP, DOWN, LEFT, RIGHT, 
    BUTTON_1, BUTTON_2, BUTTON_3, BUTTON_4, 
    BUTTON_5, BUTTON_6, BUTTON_7, BUTTON_8, 
    START, SELECT;

    /**
     * Utility to check if action is directional.
     */
    public boolean isDirectional() {
        return this == UP || this == DOWN || this == LEFT || this == RIGHT;
    }
}