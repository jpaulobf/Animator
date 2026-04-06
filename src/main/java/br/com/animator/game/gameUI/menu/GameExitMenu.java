package br.com.animator.game.gameUI.menu;

import br.com.animator.game.gameUI.CoreGameLogic;

/**
 * author: Joao Paulo Faria
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
	 * Go to the next game option
	 */
	public void nextGameOption();

	/**
	 * Return to the previous game option
	 */
	public void previousGameOption();

	/**
	 * Exits the game
	 * @return
	 */
	public boolean isToExit();
}