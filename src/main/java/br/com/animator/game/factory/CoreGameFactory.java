package br.com.animator.game.factory;

import br.com.animator.core.factory.AbstractCoreGameFactory;
import br.com.animator.core.game.CoreGameLogic;
import br.com.animator.game.data.GameGraphics;
import br.com.animator.game.data.GameGraphicsImpl;
import br.com.animator.game.data.GameOptions;
import br.com.animator.game.data.GameOptionsImpl;
import br.com.animator.game.data.GameSoundOptions;
import br.com.animator.game.data.GameSoundOptionsImpl;
import br.com.animator.game.ui.advertise.DeveloperAdvertiseImpl;
import br.com.animator.game.ui.intro.GameIntroImpl;
import br.com.animator.game.ui.intro.LogoIntroImpl;
import br.com.animator.game.ui.loading.LoadingImpl;
import br.com.animator.game.ui.menu.GameMainMenuImpl;
import br.com.animator.game.ui.options.GameGraphicsScreenImpl;
import br.com.animator.game.ui.options.GameMainOptionScreenImpl;
import br.com.animator.game.ui.options.GameOptionScreenImpl;
import br.com.animator.game.ui.options.GameSoundOptionScreenImpl;
import br.com.animator.game.ui.score.GameScorePresentationImpl;
import br.com.animator.state.GameStateMachine;
import br.com.animator.state.GameStates;
import br.com.animator.window.Window;

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
public class CoreGameFactory extends AbstractCoreGameFactory {

    private static GameGraphics gameGraphics;
    private static GameOptions gameOptions = new GameOptionsImpl();
	private static GameSoundOptions gameSoundOptions = new GameSoundOptionsImpl();
    
    /**
     * getInstance - Factory method to create instances of CoreGameLogic based on
     * the current state of the game.
     * 
     * @param gameStateMachine
     * @param gameWindow
     * @return
     */
    public static CoreGameLogic getInstance(GameStateMachine gameStateMachine, Window gameWindow) {    

        // Returns cached version
        GameStates state = gameStateMachine.getCurrentState();
        CoreGameLogic logic = AbstractCoreGameFactory.getChachedInstance(gameStateMachine, state, isCacheable(state));
        if (logic != null) return logic;
        
        if (gameGraphics == null) {
            gameGraphics = new GameGraphicsImpl(gameWindow.isFullScreen(), gameWindow.isTripleBuffering());
        }
        switch (state) {
            case DEV_LOGO_SCREEN:
                logic = new DeveloperAdvertiseImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case SUB_INTRO_SCREEN:
                logic = new LogoIntroImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case INTRO_SCREEN:
                logic = new GameIntroImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case HIGH_SCORE_SCREEN:
                logic = new GameScorePresentationImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case MAIN_MENU_SCREEN:
                logic = new GameMainMenuImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case GAME_OPTIONS_SCREEN:
                logic = new GameOptionScreenImpl(gameOptions, gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case MAIN_OPTION_SCREEN:
                logic = new GameMainOptionScreenImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case SFX_CONFIG_MENU_SCREEN:
                logic = new GameSoundOptionScreenImpl(gameSoundOptions, gameWindow.getPanelWidth(),
                        gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
                break;

            case GFX_CONFIG_MENU_SCREEN:
                logic = new GameGraphicsScreenImpl(gameGraphics, gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;

            case LOADING:
                logic = new LoadingImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(),
                        gameWindow.getCurrentAspectRatio());
                break;
                
            case GAME_OVER_SCREEN:
            case IN_GAME_SCREEN:
            case IN_GAME_OPTION_SCREEN:
                logic = null;
                break;

            default:
                System.out.println("CoreGameFactory: No matching state found for " + state);
        }

        if (logic != null && isCacheable(state)) {
            screenCache.put(state, logic);
        }

        return logic;
    }

    /**
     * Defines which states should be cached. 
     * Screens like LOADING or IN_GAME usually should be fresh instances.
     */
    protected static boolean isCacheable(GameStates state) {
        return switch (state) {
            case LOADING, IN_GAME_SCREEN, GAME_OVER_SCREEN -> false;
            default -> true;
        };
    }

    // public static void configureGameGraphics(ScreenMode screenMode) {
    //     if (gameGraphics != null) {
    //         gameGraphics.setScreenMode(screenMode);
    //     }
    // }
}