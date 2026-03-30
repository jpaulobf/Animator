package br.com.game.animator.game.gameUI.menu;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * @author João Paulo Faria
 */
public interface GameMainMenu extends CoreGameLogic {

	public final static Float MAX_COUNTER_MAIN_MENU_VALUE = 1200f;

	/**
	 * Avança uma posição no gameoptions
	 */
	public void nextGameOption();
	
	/**
	 * Retorna uma posição no gameoptions
	 */
	public void previousGameOption();
	
	/**
	 * Verifica se selecionou 'Sair'
	 * @return
	 */
	public boolean isExitSelected();
	
	/**
	 * Verifica se selecionou 'Options'
	 * @return
	 */
	public boolean isOptionSelected();
	
	/**
	 * Verifica se selecionou 'Start Game'
	 * @return
	 */
	public boolean isGameStartSelected();
}