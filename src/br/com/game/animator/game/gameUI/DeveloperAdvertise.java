package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

import br.com.game.animator.engine.GameEngine;

/**
 * @author Jo�o Paulo
 */
public interface DeveloperAdvertise {

	public final static Float MAX_COUNTER_VALUE = GameEngine.FPS * 5f;
	
	/**
	 * Verifica se a apresenta��o terminou.
	 * @return
	 */
	public boolean developerLogoPresentationFinished();
	
	/**
	 * Renderiza a propaganda do developer.
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
	 * Reinicializa os Counters
	 */
	public void resetCounters();
}