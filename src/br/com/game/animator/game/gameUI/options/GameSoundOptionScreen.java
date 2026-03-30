package br.com.game.animator.game.gameUI.options;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * @author João Paulo Faria
 */
public interface GameSoundOptionScreen extends CoreGameLogic {
	
	/**
	 * Avança para a próxima opção do game-option
	 */
	public void nextOption();
	
	/**
	 * Retrocede uma opção do game-option
	 */
	public void previousOption();
	
	/**
	 * Verifica se deve voltar para o main options menu
	 * @return
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Verifica se está sobre o menu de Habilitar/Desabilitar M�sicas
	 * @return
	 */
	public boolean isOverEnableMusic();
	
	/**
	 * Verifica se está sobre o menu de Habilitar/Desabilitar SFX
	 * @return
	 */
	public boolean isOverEnableSFX();
	
	/**
	 * Verifica se está sobre o menu de Volume de Músicas
	 * @return
	 */
	public boolean isOverMusicVolume();
	
	/**
	 * Verifica se está sobre o menu de Volume de SFX.
	 * @return
	 */
	public boolean isOverSFXVolume();
	
	/**
	 * Habilita as Músicas
	 */
	public void setMusicEnable();
	
	/**
	 * Desabilita as Músicas
	 */
	public void setMusicDisable();
	
	/**
	 * Habilita os SFX
	 */
	public void setSFXEnable();
	
	/**
	 * Desabilita os SFX
	 */
	public void setSFXDisable();
	
	/**
	 * Aumenta o volume das Músicas 
	 */
	public void increaseMusicVolume();
	
	/**
	 * Diminui o volume das Músicas 
	 */
	public void decreaseMusicVolume();
	
	/**
	 * Aumenta o volume dos SFX
	 */
	public void increaseSFXVolume();
	
	/**
	 * Diminui o volume dos SFXs
	 */
	public void decreaseSFXVolume();
	
}