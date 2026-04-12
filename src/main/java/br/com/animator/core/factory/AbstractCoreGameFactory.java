package br.com.animator.core.factory;

import br.com.animator.core.game.CoreGameLogic;
import br.com.animator.state.GameStateMachine;
import br.com.animator.state.GameStates;
import java.util.EnumMap;
import java.util.Map;

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
public class AbstractCoreGameFactory {

    protected static final Map<GameStates, CoreGameLogic> screenCache = new EnumMap<>(GameStates.class);

    /**
     * getInstance - Factory method to create instances of CoreGameLogic based on
     * the current state of the game.
     * 
     * @param gameStateMachine
     * @return
     */
    public static CoreGameLogic getChachedInstance(GameStateMachine gameStateMachine, GameStates state, boolean isCacheable) {    

        // Se o estado já estiver no cache, retornamos ele (exceto estados que exigem reinicialização)
        if (screenCache.containsKey(state) && isCacheable) {
            return screenCache.get(state);
        }

        return null;
    }

    /**
     * Clears the screen cache. Should be called when resolution or aspect ratio changes.
     */
    public static void clearCache() {
        screenCache.clear();
    }
}