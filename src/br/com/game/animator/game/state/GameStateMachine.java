package br.com.game.animator.game.state;

import java.util.Map;

/**
 * Class responsable for managing the game menu, including the developer logo, intro, high score presentation, 
 * main menu, options menu, and exit menu. It handles user input to navigate through these screens and updates the game settings accordingly.
 */
public class GameStateMachine {

    //--- State Transitions Mapping
    private static final Map<GameStates, GameStates> STATE_TRANSITIONS = Map.ofEntries(
        Map.entry(GameStates.DEV_LOGO_SCREEN, GameStates.SUB_INTRO_SCREEN),
        Map.entry(GameStates.SUB_INTRO_SCREEN, GameStates.INTRO_SCREEN),
        Map.entry(GameStates.INTRO_SCREEN, GameStates.HIGH_SCORE_SCREEN),
        Map.entry(GameStates.HIGH_SCORE_SCREEN, GameStates.SUB_INTRO_SCREEN)
    );

    //--- State Groups for Classification
    private static final Map<GameStates, String> STATE_GROUPS = Map.ofEntries(
        Map.entry(GameStates.SUB_INTRO_SCREEN, "INTRO"),
        Map.entry(GameStates.INTRO_SCREEN, "INTRO"),
        Map.entry(GameStates.HIGH_SCORE_SCREEN, "INTRO"),
        Map.entry(GameStates.MAIN_OPTION_SCREEN, "OPTIONS"),
        Map.entry(GameStates.GAME_OPTIONS_SCREEN, "OPTIONS"),
        Map.entry(GameStates.SFX_CONFIG_MENU_SCREEN, "OPTIONS"),
        Map.entry(GameStates.GFX_CONFIG_MENU_SCREEN, "OPTIONS")
    );

    //--- properties
    private GameStates currentState;
    private GameStates backupState;

    /**
     * Constructor initializes the game state machine with the default state, which is the developer logo screen.
     */
    public GameStateMachine() {
        this.currentState = GameStates.DEV_LOGO_SCREEN;
        this.backupState = this.currentState;
    }

    /**
     * Transitions to the next state in the automated game flow (intro -> menu -> options).
     * Uses centralized STATE_TRANSITIONS map to define valid transitions.
     */
    public void gotoNextState() {
        GameStates nextState = STATE_TRANSITIONS.get(this.currentState);
        if (nextState != null) {
            navigateTo(nextState);
        }
    }

    /**
     * Generic method to navigate to any target state and backup the previous state.
     * Ensures consistent state updates across all navigation calls.
     * @param targetState The state to navigate to
     */
    private void navigateTo(GameStates targetState) {
        this.backupState = this.currentState;
        this.currentState = targetState;
    }

    /**
     * Returns the current state of the game, which can be used to determine which screen to display and how to handle user input.
     * @return
     */
    public GameStates getCurrentState() {
        return currentState;
    }

    public void setLoadingState() {
        this.currentState = GameStates.LOADING;
    }

    public void unloadState() {
        this.currentState = backupState;
    }

    /**
     * Checks if the current state belongs to the INTRO group.
     * @return true if in intro screens (sub-intro, intro, or high score)
     */
    public boolean isInIntro() {
        return "INTRO".equals(STATE_GROUPS.get(this.currentState));
    }

    public boolean isInIntroDev() {
        return this.currentState == GameStates.DEV_LOGO_SCREEN;
    }

    public void gotoMainMenu() {
        navigateTo(GameStates.MAIN_MENU_SCREEN);
    }

    public boolean isInMainMenu() {
        return this.currentState == GameStates.MAIN_MENU_SCREEN;
    }

    public void gotoMainOption() {
        navigateTo(GameStates.MAIN_OPTION_SCREEN);
    }

    public void gotoGameOptions() {
        navigateTo(GameStates.GAME_OPTIONS_SCREEN);
    }

    public void gotoSFXConfigMenu() {
        navigateTo(GameStates.SFX_CONFIG_MENU_SCREEN);
    }

    public void gotoGFXConfigMenu() {
        navigateTo(GameStates.GFX_CONFIG_MENU_SCREEN);
    }

    /**
     * Navigates to the game over screen.
     */
    public void gotoGameOver() {
        navigateTo(GameStates.GAME_OVER_SCREEN);
    }

    /**
     * Checks if the current state belongs to the OPTIONS group.
     * @return true if in options screens (main option, game options, SFX, or GFX config)
     */
    public boolean isInOptions() {
        return "OPTIONS".equals(STATE_GROUPS.get(this.currentState));
    }
}
