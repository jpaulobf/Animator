package br.com.animator.state;

import java.util.Map;

import br.com.animator.config.GameConfig;

/**
 * Class responsable for managing the game menu, including the developer logo, intro, high score presentation, 
 * main menu, options menu, and exit menu. It handles user input to navigate through these screens and updates the game settings accordingly.
 */
public class GameStateMachine {

    //--- Configuration
    private final GameConfig config = GameConfig.getInstance();

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
     * Constructor initializes the game state machine with the initial state based on configuration.
     * If DEV_LOGO_SCREEN is enabled (config.ini), starts there.
     * Otherwise, starts at SUB_INTRO_SCREEN (mandatory screen).
     */
    public GameStateMachine() {
        this.setupInitialState();
        this.backupState = this.currentState;
    }

    /**
     * Sets up the initial state based on GameConfig settings.
     * DEV_LOGO_SCREEN can be disabled via config.ini to skip directly to SUB_INTRO_SCREEN.
     */
    private void setupInitialState() {
        if (config.isDevLogoEnabled()) {
            this.currentState = GameStates.DEV_LOGO_SCREEN;
        } else {
            this.currentState = GameStates.SUB_INTRO_SCREEN;
        }
    }

    /**
     * Transitions to the next state in the automated game flow.
     * Respects configuration settings for optional screens (DEV_LOGO, INTRO, HIGH_SCORE).
     * SUB_INTRO_SCREEN is always mandatory.
     */
    public void gotoNextState() {
        GameStates nextState = calculateNextState(this.currentState);
        if (nextState != null) {
            navigateTo(nextState);
        }
    }

    /**
     * Calculates the next state based on current state and configuration.
     * Handles dynamic transitions when screens are enabled/disabled.
     * @param current The current state
     * @return The next state, or null if no valid transition exists
     */
    private GameStates calculateNextState(GameStates current) {
        switch (current) {
            case DEV_LOGO_SCREEN:
                return GameStates.SUB_INTRO_SCREEN;

            case SUB_INTRO_SCREEN:
                // SUB_INTRO -> INTRO (if enabled) -> HIGH_SCORE (if INTRO disabled but HS enabled) -> SUB_INTRO (if both disabled)
                if (config.isIntroScreenEnabled()) {
                    return GameStates.INTRO_SCREEN;
                } else if (config.isHighScoreScreenEnabled()) {
                    return GameStates.HIGH_SCORE_SCREEN;
                } else {
                    return GameStates.SUB_INTRO_SCREEN;  // Loop if both disabled
                }

            case INTRO_SCREEN:
                // INTRO -> HIGH_SCORE (if enabled) -> SUB_INTRO (if HS disabled)
                if (config.isHighScoreScreenEnabled()) {
                    return GameStates.HIGH_SCORE_SCREEN;
                } else {
                    return GameStates.SUB_INTRO_SCREEN;
                }

            case HIGH_SCORE_SCREEN:
                return GameStates.SUB_INTRO_SCREEN;  // Always loop back to SUB_INTRO

            default:
                return null;  // No automatic transition for other states
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
