package br.com.game.animator.game.gameUI.menu;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * @author João Paulo Faria
 *
 */
public interface GameExitMenu extends CoreGameLogic {

	/**
	 * Displays the Exit menu
	 */
	public void showExitMenu();

	/**
	 * Hides the Exit menu
	 */
	public void hideExitMenu();

	/**
	 * Checks if the menu is being displayed.
	 * 
	 * @return
	 */
	public boolean isShowingExitMenu();

	/**
	 * 
	 */
	public void nextGameOption();

	/**
	 * 
	 */
	public void previousGameOption();

	/**
	 * @return
	 */
	public boolean isToExit();
}