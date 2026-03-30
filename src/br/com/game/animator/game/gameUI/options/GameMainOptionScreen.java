package br.com.game.animator.game.gameUI.options;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * @author João Paulo Faria
 */
public interface GameMainOptionScreen extends CoreGameLogic {
	
	public void nextGameOption();
	
	public void previousGameOption();
	
	public boolean isToBackToMainMenu();
	
	public boolean isToConfigGFX();
	
	public boolean isToConfigSFX();
	
	public boolean isToConfigKeys();
	
	public boolean isToGoToGameOptions();
}