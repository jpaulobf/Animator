package br.com.game.animator.game.gameData.enumerators;

/**
 * @author Jo�o Paulo
 *
 */
@SuppressWarnings("unused")
public enum ScreenMode {
	
	FULLSCREEN (0), 
	WINDOWED (1);
	private Integer value;

    private ScreenMode(Integer value) {
            this.value = value;
    }
    
}