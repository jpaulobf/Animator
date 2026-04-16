package br.com.animator.game.data;

/**
 * author: Joao Paulo Faria
 *
 */
public class GameSoundOptionsImpl implements GameSoundOptions {

	private boolean musicEnabled 	= true;
	private boolean sfxEnabled		= true;
	private Integer musicVolume		= null;
	private Integer sfxVolume		= null;
	
	public GameSoundOptionsImpl() {
		this.musicEnabled 	= true;
		this.sfxEnabled 	= true;
		this.musicVolume 	= 9;
		this.sfxVolume		= 9;
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#getMusicEnabled()
	 */
	public boolean getMusicEnabled() {
		return (this.musicEnabled);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#setMusicEnabled(boolean)
	 */
	public void setMusicEnabled(boolean enable) {
		this.musicEnabled = enable;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#getSFXEnabled()
	 */
	public boolean getSFXEnabled() {
		return (this.sfxEnabled);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#setSFXEnabled(boolean)
	 */
	public void setSFXEnabled(boolean enable) {
		this.sfxEnabled = enable;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#getMusicVolume()
	 */
	public Integer getMusicVolume() {
		return (this.musicVolume);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#setMusicVolume(java.lang.Integer)
	 */
	public void setMusicVolume(Integer volume) {
		this.musicVolume = volume;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#getSFXVolume()
	 */
	public Integer getSFXVolume() {
		return (this.sfxVolume);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameSoundOptions#setSFXVolume(java.lang.Integer)
	 */
	public void setSFXVolume(Integer volume) {
		this.sfxVolume = volume;
	}
}