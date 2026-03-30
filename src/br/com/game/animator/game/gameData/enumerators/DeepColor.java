package br.com.game.animator.game.gameData.enumerators;

/**
 * @author João Paulo Faria
 *
 */
@SuppressWarnings("unused")
public enum DeepColor {
	
	TRUE_COLOR_32_BITS (0), 
	HI_COLOR_16_BITS (1);
	private Integer value;

    private DeepColor(Integer value) {
            this.value = value;
    }
    
}