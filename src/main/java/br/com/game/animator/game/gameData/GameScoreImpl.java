package br.com.game.animator.game.gameData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import br.com.game.animator.util.ValueComparator;

/**
 * author: Joao Paulo Faria
 *
 */
public class GameScoreImpl implements GameScore {

	//------------------------------------------------------------------------//
	// Properties
	//------------------------------------------------------------------------//
	private ValueComparator valueComparator 		= null;
	private Map<String, Long> storedScores			= null;
	private Map<String, Long> sortedScores			= null;
	
	/**
	 * Constructor.
	 */
	public GameScoreImpl() {

	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#loadHiScores()
	 */
	public void loadHiScores() {
		this.storedScores = new HashMap<String, Long>();
		this.storedScores.put("JoA" + "#" + System.nanoTime(), 150L);
		this.storedScores.put("CAP" + "#" + System.nanoTime(), 128L);
		this.storedScores.put("JP." + "#" + System.nanoTime(), 140L);
		this.storedScores.put("RUD" + "#" + System.nanoTime(), 114L);
		this.storedScores.put("MIC" + "#" + System.nanoTime(), 320L);
		this.storedScores.put("NYC" + "#" + System.nanoTime(), 222L);
		this.storedScores.put("DAD" + "#" + System.nanoTime(), 512L);
		this.storedScores.put("HAR" + "#" + System.nanoTime(), 102L);
		this.storedScores.put("JOE" + "#" + System.nanoTime(), 90L);
		this.storedScores.put("MAC" + "#" + System.nanoTime(), 88L);
		this.valueComparator = new ValueComparator(this.storedScores);
		this.sortedScores = new TreeMap<String, Long>(this.valueComparator);
		this.sortedScores.putAll(this.storedScores);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#saveHighScores()
	 */
	public void saveHighScores() {
		if (this.sortedScores != null) {
			this.sortedScores.clear();
		}
		this.sortedScores = null;
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#getSortedScoresSet()
	 */
	public Set<String> getSortedScoresSet() {
		if (this.sortedScores != null) {
			return (this.sortedScores.keySet());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#getSortedScoresValue(java.lang.String)
	 */
	public Long getSortedScoresValue(String key) {
		if (this.sortedScores != null) {
			return (this.sortedScores.get(key));
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#getStoredScoresValue(java.lang.String)
	 */
	public Long getStoredScoresValue(String key) {
		if (this.storedScores != null) {
			return (this.storedScores.get(key));
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#hasScores()
	 */
	public boolean hasScores() {
		return (this.sortedScores != null && !this.sortedScores.isEmpty() &&
				this.storedScores != null && !this.storedScores.isEmpty());
	}
}