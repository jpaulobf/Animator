package br.com.animator.game.data;

import java.util.Set;

/**
 * author: Joao Paulo Faria
 *
 */
public interface GameScore {
	
	/**
	 * Reads the HiScores
	 */
	public void loadHiScores();
	
	/**
	 * Saves the HiScores
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