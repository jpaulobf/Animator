package br.com.game.animator.game.state;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * Class responsable for managing the game menu, including the developer logo, intro, high score presentation, 
 * main menu, options menu, and exit menu. It handles user input to navigate through these screens and updates the game settings accordingly.
 */
public class GameStateMachine {

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
     * Checks if the current state has finished its execution, which is determined by the finished() method of the CoreGameLogic interface.
     * @param currentStateLogic
     * @return
     */
    public boolean currentStateFinished(CoreGameLogic currentStateLogic) {
        return currentStateLogic.finished();
    }

    /**
     * Transitions to the next state in the game flow based on the current state. The transitions are defined as follows:
     * - DEV_LOGO_SCREEN -> SUB_INTRO_SCREEN
     * - SUB_INTRO_SCREEN -> INTRO_SCREEN
     * - INTRO_SCREEN -> HIGH_SCORE_SCREEN
     * - HIGH_SCORE_SCREEN -> SUB_INTRO_SCREEN
     */
    public void gotoNextState() {
        if (this.currentState == GameStates.DEV_LOGO_SCREEN) {
            this.currentState = GameStates.SUB_INTRO_SCREEN;
        } else if (this.currentState == GameStates.SUB_INTRO_SCREEN) {
            this.currentState = GameStates.INTRO_SCREEN;
        } else if (this.currentState == GameStates.INTRO_SCREEN) {
            this.currentState = GameStates.HIGH_SCORE_SCREEN;
        } else if (this.currentState == GameStates.HIGH_SCORE_SCREEN) {
            this.currentState = GameStates.SUB_INTRO_SCREEN;
        }
        this.backupState = this.currentState;
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

    public boolean isInIntro() {
        return (this.currentState == GameStates.SUB_INTRO_SCREEN || this.currentState == GameStates.INTRO_SCREEN || this.currentState == GameStates.HIGH_SCORE_SCREEN);
    }

    public boolean isInIntroDev() {
        return this.currentState == GameStates.DEV_LOGO_SCREEN;
    }

    public void gotoMainMenu() {
        this.currentState = GameStates.MAIN_MENU_SCREEN;
    }

    public boolean isInMainMenu() {
        return this.currentState == GameStates.MAIN_MENU_SCREEN;
    }

    public void gotoMainOption() {
        this.currentState = GameStates.MAIN_OPTION_SCREEN;
    }

    public void gotoGameOptions() {
        this.currentState = GameStates.GAME_OPTIONS_SCREEN;
    }

    public void gotoSFXConfigMenu() {
        this.currentState = GameStates.SFX_CONFIG_MENU_SCREEN;
    }

    public void gotoGFXConfigMenu() {
        this.currentState = GameStates.GFX_CONFIG_MENU_SCREEN;
    }

    public boolean isInOptions() {
        return (this.currentState == GameStates.MAIN_OPTION_SCREEN || this.currentState == GameStates.GAME_OPTIONS_SCREEN || 
                this.currentState == GameStates.SFX_CONFIG_MENU_SCREEN || this.currentState == GameStates.GFX_CONFIG_MENU_SCREEN);
    }
}
