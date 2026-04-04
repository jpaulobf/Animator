package br.com.game.animator.game.factory;

import br.com.game.animator.window.Window;
import br.com.game.animator.game.gameUI.CoreGameLogic;
import br.com.game.animator.game.gameUI.advertise.DeveloperAdvertiseImpl;
import br.com.game.animator.game.gameUI.intro.GameIntroImpl;
import br.com.game.animator.game.gameUI.intro.LogoIntroImpl;
import br.com.game.animator.game.gameUI.loading.LoadingImpl;
import br.com.game.animator.game.gameUI.menu.GameMainMenuImpl;
import br.com.game.animator.game.gameUI.score.GameScorePresentationImpl;
import br.com.game.animator.game.state.GameStateMachine;
import br.com.game.animator.game.state.GameStates;

/**
 * CoreGameFactory - Factory class responsible for creating instances of
 * CoreGameLogic based on the current state of the game.
 * This factory centralizes the creation logic for different game states,
 * allowing for easy maintenance and scalability as new states and corresponding
 * logic are added to the game.
 * Each case in the switch statement corresponds to a specific game state, and
 * the factory method returns an instance of the corresponding CoreGameLogic
 * implementation.
 *
 */
public class CoreGameFactory {

    private static CoreGameLogic coreGameLogic;
    private static GameStates currentState;

    /**
     * getInstance - Factory method to create instances of CoreGameLogic based on
     * the current state of the game.
     * 
     * @param gameStateMachine
     * @param gameWindow
     * @return
     */
    public static CoreGameLogic getInstance(GameStateMachine gameStateMachine, Window gameWindow) {
        switch (gameStateMachine.getCurrentState()) {

            case DEV_LOGO_SCREEN:
                if (currentState != GameStates.DEV_LOGO_SCREEN) {
                    coreGameLogic = new DeveloperAdvertiseImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                                                                gameWindow.getCurrentAspectRatio());
                    currentState = GameStates.DEV_LOGO_SCREEN;
                }
                break;

            case SUB_INTRO_SCREEN:
                if (currentState != GameStates.SUB_INTRO_SCREEN) {
                    coreGameLogic = new LogoIntroImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                                                         gameWindow.getCurrentAspectRatio());
                    currentState = GameStates.SUB_INTRO_SCREEN;
                }
                break;

            case INTRO_SCREEN:
                if (currentState != GameStates.INTRO_SCREEN) {
                    coreGameLogic = new GameIntroImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                    currentState = GameStates.INTRO_SCREEN;
                }
                break;

            case HIGH_SCORE_SCREEN:
                if (currentState != GameStates.HIGH_SCORE_SCREEN) {
                    coreGameLogic = new GameScorePresentationImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                    currentState = GameStates.HIGH_SCORE_SCREEN;
                }
                break;

            case MAIN_MENU_SCREEN:
                if (currentState != GameStates.MAIN_MENU_SCREEN) {
                    coreGameLogic = new GameMainMenuImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                            gameWindow.getCurrentAspectRatio());
                    currentState = GameStates.MAIN_MENU_SCREEN;
                }
                break;

            case GAME_OVER_SCREEN:
                if (currentState != GameStates.GAME_OVER_SCREEN) {
                    coreGameLogic = null;
                    currentState = GameStates.GAME_OVER_SCREEN;
                }
                break;

            case IN_GAME_SCREEN:
                if (currentState != GameStates.IN_GAME_SCREEN) {
                    coreGameLogic = null;
                    currentState = GameStates.IN_GAME_SCREEN;
                }
                break;

            case IN_GAME_OPTION_SCREEN:
                if (currentState != GameStates.IN_GAME_OPTION_SCREEN) {
                    coreGameLogic = null;
                    currentState = GameStates.IN_GAME_OPTION_SCREEN;
                }
                break;

            case MAIN_OPTION_SCREEN:
                if (currentState != GameStates.MAIN_OPTION_SCREEN) {
                    coreGameLogic = null;
                    currentState = GameStates.MAIN_OPTION_SCREEN;
                }
                break;

            case LOADING:
                if (currentState != GameStates.LOADING) {
                    coreGameLogic = new LoadingImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                            gameWindow.getCurrentAspectRatio());
                    currentState = GameStates.LOADING;
                }
                break;

            default:
                System.out
                        .println("CoreGameFactory: No matching state found for " + gameStateMachine.getCurrentState());
                coreGameLogic = null;
        }

        return coreGameLogic;
    }
}