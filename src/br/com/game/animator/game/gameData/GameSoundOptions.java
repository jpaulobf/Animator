package br.com.game.animator.game.gameData;

/**
 * @author João Paulo Faria
 *
 */
public interface GameSoundOptions {

	/**
	 * @return
	 * Verifica se a m�sica est� ligada
	 */
	public boolean getMusicEnabled();
	
	/**
	 * @return
	 * Determina se a m�sica estar� ligada ou desligada
	 */
	public void setMusicEnabled(boolean enable);
	
	/**
	 * @return
	 * Verifica se o SFX est� ligado
	 */
	public boolean getSFXEnabled();
	
	/**
	 * @return
	 * Determina se o SFX estar� ligado ou desligado
	 */
	public void setSFXEnabled(boolean enable);
	
	/**
	 * Amplia o volume das m�sicas
	 */
	public Integer getMusicVolume();
	
	/**
	 * Reduz o volume das m�sicas
	 */
	public void setMusicVolume(Integer volume);
	
	/**
	 * Amplia o volume dos SFX
	 */
	public Integer getSFXVolume();
	
	/**
	 * Reduz o volume dos SFX
	 */
	public void setSFXVolume(Integer volume);
	
}