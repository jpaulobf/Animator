package br.com.animator.game.gameData.enumerators;

/**
 * author: Joao Paulo Faria
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