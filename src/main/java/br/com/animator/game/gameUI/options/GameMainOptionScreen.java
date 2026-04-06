package br.com.animator.game.gameUI.options;

import br.com.animator.game.gameUI.CoreGameLogic;

/**
 * author: Joao Paulo Faria
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