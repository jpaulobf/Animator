package br.com.game.animator.game.gameData;

import br.com.game.animator.game.gameData.enumerators.GameDifficulty;

/**
 * @author João Paulo Faria
 *
 */
public interface GameOptions {

	/**
	 * @return
	 * Recupera a dificuldade atual do game
	 */
	public GameDifficulty getGameDifficulty();
	
	/**
	 * @param gameDifficulty
	 * Define a dificuldade atual do game
	 */
	public void setGameDifficulty(GameDifficulty gameDifficulty);
	
	/**
	 * @return
	 * Recupera a quantidade de vidas restantes
	 */
	public Integer getGameRestNumber();
	
	/**
	 * @param restNumber
	 * Informa a quantidade de vidas restantes
	 */
	public void setGameRestNumber(Integer restNumber);
	
	/**
	 * @return
	 * Recupera o montante de pontos que d�o nova vida
	 */
	public Integer getExtraLifeAtEachHowMuchPoints();
	
	/**
	 * @param points
	 * Informa a quantidade de pontos que d�o nova vida
	 */
	public void setExtraLifeAtEachHowMuchPoints(Integer points);
	
	/**
	 * @return
	 * Recupera o n�mero de continues restante: -1 = infinito.
	 */
	public Integer getGameContinuesNumber();
	
	/**
	 * @param continues
	 * Informa a quantidade de continues para o game: -1 = infinito
	 */
	public void setGameContinuesNumber(Integer continues); //-1 = infinity
	
	/**
	 * @return
	 * Recupera se deve ou n�o exibir legendas
	 */
	public boolean getSubtitlesEnabled();
	
	/**
	 * @param enable
	 * Define se deve ou n�o exibir legendas.
	 */
	public void setSubtitlesEnabled(boolean enable);
}