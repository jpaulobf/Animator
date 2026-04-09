package br.com.animator.ui.options;

import br.com.animator.ui.CoreGameLogic;

/**
 * author: Joao Paulo Faria
 */
public interface GameSoundOptionScreen extends CoreGameLogic {
	
	/**
	 * Advances to the next option
	 */
	public void nextOption();
	
	/**
	 * Returns to the previous option
	 */
	public void previousOption();
	
	/**
	 * Checks if should return to the main options menu
	 * @return
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Checks if hovering over the Music enable/disable menu
	 * @return
	 */
	public boolean isOverEnableMusic();
	
	/**
	 * Checks if hovering over the SFX enable/disable menu
	 * @return
	 */
	public boolean isOverEnableSFX();
	
	/**
	 * Checks if hovering over the Music volume menu
	 * @return
	 */
	public boolean isOverMusicVolume();
	
	/**
	 * Checks if hovering over the SFX volume menu
	 * @return
	 */
	public boolean isOverSFXVolume();
	
	/**
	 * Enables music
	 */
	public void setMusicEnable();
	
	/**
	 * Disables music
	 */
	public void setMusicDisable();
	
	/**
	 * Enables SFX
	 */
	public void setSFXEnable();
	
	/**
	 * Disables SFX
	 */
	public void setSFXDisable();
	
	/**
	 * Increases music volume
	 */
	public void increaseMusicVolume();
	
	/**
	 * Decreases music volume
	 */
	public void decreaseMusicVolume();
	
	/**
	 * Increases SFX volume
	 */
	public void increaseSFXVolume();
	
	/**
	 * Decreases SFX volume
	 */
	public void decreaseSFXVolume();
	
}