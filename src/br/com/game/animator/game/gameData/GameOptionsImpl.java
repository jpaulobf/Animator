package br.com.game.animator.game.gameData;

import br.com.game.animator.game.gameData.enumerators.GameDifficulty;

/**
 * @author João Paulo Faria
 *
 */
public class GameOptionsImpl implements GameOptions {
	
	//------------------------------------------------------------------------//
	// Game Options Properties
	//------------------------------------------------------------------------//
	private GameDifficulty gameDifficulty 			= null;
	private Integer restNumber						= null;
	private Integer extraLifeAtEachHowMuchPoints	= null;
	private Integer continues						= null;
	private boolean enableSubtitles					= false;

	/**
	 * Constructor.
	 */
	public GameOptionsImpl() {
		this.gameDifficulty 				= GameDifficulty.NORMAL;
		this.restNumber						= 5;
		this.extraLifeAtEachHowMuchPoints	= 100;
		this.continues						= 5;
		this.enableSubtitles				= false;
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#getGameDifficulty()
	 */
	public GameDifficulty getGameDifficulty() {
		return (this.gameDifficulty);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#setGameDifficulty(br.com.animator.gameData.GameDifficulty)
	 */
	public void setGameDifficulty(GameDifficulty gameDifficulty) {
		this.gameDifficulty = gameDifficulty;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#getGameRestNumber()
	 */
	public Integer getGameRestNumber() {
		return (this.restNumber);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#setGameRestNumber(java.lang.Integer)
	 */
	public void setGameRestNumber(Integer restNumber) {
		this.restNumber = restNumber;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#getExtraLifeAtEachHowMuchPoints()
	 */
	public Integer getExtraLifeAtEachHowMuchPoints() {
		return (this.extraLifeAtEachHowMuchPoints);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#setExtraLifeAtEachHowMuchPoints(java.lang.Integer)
	 */
	public void setExtraLifeAtEachHowMuchPoints(Integer points) {
		this.extraLifeAtEachHowMuchPoints = points;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#getGameContinuesNumber()
	 */
	public Integer getGameContinuesNumber() {
		return (this.continues);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#setGameContinuesNumber(java.lang.Integer)
	 */
	public void setGameContinuesNumber(Integer continues) {
		this.continues = continues;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#getSubtitlesEnabled()
	 */
	public boolean getSubtitlesEnabled() {
		return (this.enableSubtitles);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameOptions#setSubtitlesEnabled(boolean)
	 */
	public void setSubtitlesEnabled(boolean enable) {
		this.enableSubtitles = enable;
	}
}