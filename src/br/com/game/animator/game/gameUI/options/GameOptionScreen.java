package br.com.game.animator.game.gameUI.options;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * @author João Paulo Faria
 */
public interface GameOptionScreen extends CoreGameLogic {

	/**
	 * Avança para a próxima opção do game-option
	 */
	public void nextOption();
	
	/**
	 * Retrocede uma opção do game-option
	 */
	public void previousOption();
	
	/**
	 * Verifica se deve voltar para o main-menu
	 * @return
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Verifica se está sobre o menu de dificuldades.
	 * @return
	 */
	public boolean isOverGameDifficulty();
	
	/**
	 * Verifica se está sobre o menu de Vidas.
	 * @return
	 */
	public boolean isOverRestsSelection();
	
	/**
	 * Verifica se está sobre o menu de Extra-Life
	 * @return
	 */
	public boolean isOverExtraLifeSelection();
	
	/**
	 * Verifica se está sobre o menu de Continues.
	 * @return
	 */
	public boolean isOverContinuesSelection();
	
	
	/**
	 * Verifica se está sobre o menu de legendas.
	 * @return
	 */
	public boolean isOverEnableSubtitles();
	
	/**
	 * Avança para a próxima opção de dificuldade
	 */
	public void setNextDifficulty();
	
	/**
	 * Retrocede uma opção de dificuldade
	 */
	public void setPreviousDifficulty();
	
	/**
	 * Adiciona uma vida ao Game
	 */
	public void addRest();
	
	/**
	 * Retira uma vida do Game
	 */
	public void subRest();
	
	/**
	 * Avança para a próxima opção de pontos para uma vida extra 
	 */
	public void setNextExtraLifeAtPoints();
	
	/**
	 * Retrocede uma opção de pontos para uma vida extra 
	 */
	public void setPreviousExtraLifeAtPoints();
	
	/**
	 * Adiciona um continue ao Game
	 */
	public void addContinues();
	
	/**
	 * Retira um continue do Game
	 */
	public void subContinues();
	
	/**
	 * Habilita as legendas
	 */
	public void enableSubtitles();
	
	/**
	 * Desabilita as legendas
	 */
	public void disableSubtitles();
}