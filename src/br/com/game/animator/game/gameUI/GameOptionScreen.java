package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

/**
 * @author Jo�o Paulo
 *
 */
public interface GameOptionScreen {

	/**
	 * @param g2d
	 * Renderiza a Introdu��o.
	 */
	public void drawOptionScreen(Graphics2D g2d);
	
	/**
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 * Atualiza o tamanho do canvas, para renderiza��o.
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
	
	/**
	 * Atualiza as anima��es.
	 */
	public void update();
	
	/**
	 * Reseta os contadores
	 */
	public void resetCounters();
	
	/**
	 * Avan�a para a pr�xima op��o do game-option
	 */
	public void nextOption();
	
	/**
	 * Retrocede uma op��o do game-option
	 */
	public void previousOption();
	
	/**
	 * Verifica se deve voltar para o main-menu
	 * @return
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Verifica se est� sobre o menu de dificuldades.
	 * @return
	 */
	public boolean isOverGameDifficulty();
	
	/**
	 * Verifica se est� sobre o menu de Vidas.
	 * @return
	 */
	public boolean isOverRestsSelection();
	
	/**
	 * Verifica se est� sobre o menu de Extra-Life
	 * @return
	 */
	public boolean isOverExtraLifeSelection();
	
	/**
	 * Verifica se est� sobre o menu de Continues.
	 * @return
	 */
	public boolean isOverContinuesSelection();
	
	
	/**
	 * Verifica se est� sobre o menu de legendas.
	 * @return
	 */
	public boolean isOverEnableSubtitles();
	
	/**
	 * Avan�a para a pr�xima op��o de dificuldade
	 */
	public void setNextDifficulty();
	
	/**
	 * Retrocede uma op��o de dificuldade
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
	 * Avan�a para a pr�xima op��o de pontos para uma vida extra 
	 */
	public void setNextExtraLifeAtPoints();
	
	/**
	 * Retrocede uma op��o de pontos para uma vida extra 
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