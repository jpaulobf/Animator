package br.com.animator.game.state;

/**
 * Enum representing the different states of the game, including the developer
 * logo screen, intro screens, high score presentation, main menu, game over
 * screen, in-game screen, options screens, and loading screen. This enum is
 * used by the GameStateMachine to manage the flow of the game and determine
 * which screen to display based on user input and game events.
 */
public enum GameStates {

    DEV_LOGO_SCREEN,
    SUB_INTRO_SCREEN,
    INTRO_SCREEN,
    HIGH_SCORE_SCREEN,
    MAIN_MENU_SCREEN,
    GAME_OVER_SCREEN,
    IN_GAME_SCREEN,
    IN_GAME_OPTION_SCREEN,
    MAIN_OPTION_SCREEN,
    GAME_OPTIONS_SCREEN,
    SFX_CONFIG_MENU_SCREEN,
    GFX_CONFIG_MENU_SCREEN,
    LOADING

}
