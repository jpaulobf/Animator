package br.com.game.animator.game.gameData;

import br.com.game.animator.game.gameData.enumerators.GameDifficulty;

/**
 * @author João Paulo Faria
 *
 */
public interface GameOptions {

	/**
	 * @return
	 * Gets the current difficulty level
	 */
	public GameDifficulty getGameDifficulty();
	
	/**
	 * Sets the current difficulty level
	 */
	public void setGameDifficulty(GameDifficulty gameDifficulty);
	
	/**
	 * Gets the remaining number of lives
	 */
	public Integer getGameRestNumber();
	
	/**
	 * Sets the remaining number of lives
	 */
	public void setGameRestNumber(Integer restNumber);
	
	/**
	 * Gets the points threshold for an extra life
	 */
	public Integer getExtraLifeAtEachHowMuchPoints();
	
	/**
	 * Sets the points threshold for an extra life
	 */
	public void setExtraLifeAtEachHowMuchPoints(Integer points);
	
	/**
	 * Gets the remaining number of continues (-1 = infinite)
	 */
	public Integer getGameContinuesNumber();
	
	/**
	 * Sets the number of continues (-1 = infinite)
	 */
	public void setGameContinuesNumber(Integer continues); //-1 = infinity
	
	/**
	 * Checks if subtitles are enabled
	 */
	public boolean getSubtitlesEnabled();
	
	/**
	 * Sets whether subtitles are enabled
	 */
	public void setSubtitlesEnabled(boolean enable);
}