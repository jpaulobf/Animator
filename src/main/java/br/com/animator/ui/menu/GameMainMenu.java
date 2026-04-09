package br.com.animator.ui.menu;

import br.com.animator.core.CoreGameLogic;

/**
 * author: Joao Paulo Faria
 */
public interface GameMainMenu extends CoreGameLogic {

	public final static Float MAX_COUNTER_MAIN_MENU_VALUE = 1200f;

	/**
	 * Advances to the next option
	 */
	public void nextGameOption();
	
	/**
	 * Goes back to the previous option
	 */
	public void previousGameOption();
	
	/**
	 * Checks if 'Exit' is selected
	 */
	public boolean isExitSelected();
	
	/**
	 * Checks if 'Options' is selected
	 */
	public boolean isOptionSelected();
	
	/**
	 * Checks if 'Start Game' is selected
	 */
	public boolean isGameStartSelected();
}