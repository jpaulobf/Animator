package br.com.game.animator.game.factory;

import br.com.game.animator.window.Window;
import br.com.game.animator.game.gameUI.CoreGameLogic;
import br.com.game.animator.game.gameUI.advertise.DeveloperAdvertiseImpl;
import br.com.game.animator.game.gameUI.intro.GameIntroImpl;
import br.com.game.animator.game.gameUI.intro.GameSubIntroImpl;
import br.com.game.animator.game.gameUI.loading.LoadingImpl;
import br.com.game.animator.game.gameUI.menu.GameMainMenuImpl;
import br.com.game.animator.game.gameUI.score.GameScorePresentationImpl;
import br.com.game.animator.game.state.GameStateMachine;

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
                return new DeveloperAdvertiseImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());

            case SUB_INTRO_SCREEN:
                return new GameSubIntroImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());

            case INTRO_SCREEN:
                return new GameIntroImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());

            case HIGH_SCORE_SCREEN:
                return new GameScorePresentationImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());

            case MAIN_MENU_SCREEN:
                return new GameMainMenuImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());

            case GAME_OVER_SCREEN:
                return null;

            case IN_GAME_SCREEN:
                return null;

            case IN_GAME_OPTION_SCREEN:
                return null;

            case MAIN_OPTION_SCREEN:
                return null;

            case LOADING:
                return new LoadingImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());

            default:
                System.out
                        .println("CoreGameFactory: No matching state found for " + gameStateMachine.getCurrentState());
                return null;
        }
    }

}
