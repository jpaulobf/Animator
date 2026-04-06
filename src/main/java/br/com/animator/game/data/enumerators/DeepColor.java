package br.com.animator.game.data.enumerators;

/**
 * author: Joao Paulo Faria
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