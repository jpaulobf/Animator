package br.com.animator.ui.options;

import br.com.animator.core.game.CoreGameLogic;

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