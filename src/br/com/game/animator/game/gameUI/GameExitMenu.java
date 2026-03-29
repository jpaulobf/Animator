package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

/**
 * @author Jo�o Paulo
 *
 */
public interface GameExitMenu {
	
	/**
	 * Renderiza a Introdu��o.
	 * @param g2d
	 */
	public void drawExitMenu(Graphics2D g2d);
	
	/**
	 * Atualiza o tamanho do canvas, para renderiza��o.
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
	
	/**
	 * Atualiza as anima��es.
	 */
	public void update();
	
	/**
	 * Exibe o menu Exit
	 */
	public void showExitMenu();
	
	/**
	 * Esconde o menu Exit
	 */
	public void hideExitMenu();
	
	/**
	 * Verifica se o menu est� sendo exibido.
	 * @return
	 */
	public boolean isShowingExitMenu();
	
	/**
	 * Reseta os contadores
	 */
	public void resetCounters();
	
	/**
	 * 
	 */
	public void nextGameOption();
	
	/**
	 * 
	 */
	public void previousGameOption();
	
	/**
	 * @return
	 */
	public boolean isToExit();

}