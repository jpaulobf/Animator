package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

/**
 * @author Jo�o Paulo
 *
 */
public interface GameMainOptionScreen {

	/**
	 * Renderiza a Introdu��o.
	 * @param g2d
	 */
	public void drawMainOptionScreen(Graphics2D g2d);
	
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
	public boolean isToBackToMainMenu();
	
	/**
	 * @return
	 */
	public boolean isToConfigGFX();
	
	/**
	 * @return
	 */
	public boolean isToConfigSFX();
	
	/**
	 * @return
	 */
	public boolean isToConfigKeys();
	
	/**
	 * @return
	 */
	public boolean isToGoToGameOptions();
}