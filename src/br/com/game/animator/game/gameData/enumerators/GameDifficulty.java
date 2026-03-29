package br.com.game.animator.game.gameData.enumerators;

/**
 * @author Jo�o Paulo
 *
 */
@SuppressWarnings("unused")
public enum GameDifficulty {

	EASY (0), 
	NORMAL (1), 
	HARD (2);
	private Integer value;

    private GameDifficulty(Integer value) {
            this.value = value;
    }
}