package br.com.game.animator.game.gameData;

import java.util.Set;

/**
 * @author João Paulo Faria
 *
 */
public interface GameScore {
	
	/**
	 * L� os HiScores
	 */
	public void loadHiScores();
	
	/**
	 * Salva os HiScores
	 */
	public void saveHighScores();
	
	/**
	 * @return
	 */
	public Set<String> getSortedScoresSet();
	
	/**
	 * @return
	 */
	public Long getSortedScoresValue(String key);
	
	/**
	 * @param key
	 * @return
	 */
	public Long getStoredScoresValue(String key);
	
	/**
	 * @return
	 */
	public boolean hasScores();
}