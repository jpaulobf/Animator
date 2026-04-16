package br.com.animator.game.data;

/**
 * author: Joao Paulo Faria
 *
 */
public interface GameSoundOptions {

	/**
	 * @return
	 * Checks if music is enabled
	 */
	public boolean getMusicEnabled();
	
	/**
	 * Sets whether music is enabled
	 */
	public void setMusicEnabled(boolean enable);
	
	/**
	 * Checks if SFX is enabled
	 */
	public boolean getSFXEnabled();
	
	/**
	 * Sets whether SFX is enabled
	 */
	public void setSFXEnabled(boolean enable);
	
	/**
	 * Gets the music volume
	 */
	public Integer getMusicVolume();
	
	/**
	 * Sets the music volume
	 */
	public void setMusicVolume(Integer volume);
	
	/**
	 * Gets the SFX volume
	 */
	public Integer getSFXVolume();
	
	/**
	 * Sets the SFX volume
	 */
	public void setSFXVolume(Integer volume);

	public int getOldMusicVolume();

	public void setOldMusicVolume(int oldMusicVolume);

	public int getOldSFXVolume();

	public void setOldSFXVolume(int oldSFXVolume);
}