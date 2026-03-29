package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

/**
 * @author Jo�o Paulo
 *
 */
public interface Loading {

	/**
	 * Renderiza a tela de loading.
	 * @param g2d
	 */
	public void draw(Graphics2D g2d);
	
	/**
	 * Atualiza as anima��es.
	 */
	public void update();
	
	/**
	 * Atualiza o tamanho do canvas, para renderiza��o.
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 */
	public void updateCanvasProperties(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
	
	/**
	 * Reseta os contadores
	 */
	public void resetCounters();
	
}