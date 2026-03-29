package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

import br.com.game.animator.engine.GameEngine;

/**
 * @author Jo�o Paulo
 */
public interface GameIntro {
	
	public final static Float MAX_COUNTER_SUBINTRO_VALUE 	= GameEngine.FPS * 15f;

	/**
	 * Verifica se a sub apresenta��o terminou.
	 * @return
	 */
	public boolean subIntroFinished();
	
	/**
	 * Verifica se a apresenta��o terminou.
	 * @return
	 */
	public boolean introFinished();
	
	/**
	 * Atualiza as anima��es.
	 */
	public void updateSubIntro();
	
	/**
	 * Atualiza as anima��es.
	 */
	public void updateIntro();
	
	/**
	 * Renderiza a Introdu��o.
	 * @param g2d
	 */
	public void drawIntro(Graphics2D g2d);
	
	/**
	 * Renderiza a Sub Introdu��o.
	 * @param g2d
	 */
	public void drawSubIntro(Graphics2D g2d);
	
	/**
	 * Atualiza o tamanho do canvas, para renderiza��o.
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 */
	public void updateCanvasProperties(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
	
	/**
	 * Atualiza o tamanho do canvas, para renderiza��o.
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 */
	public void updateCanvasPropertiesSubIntro(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
	
	/**
	 * Reinicializa os Counters
	 */
	public void resetCounters();
}