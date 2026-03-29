package br.com.game.animator.game.gameScore;

import java.awt.Graphics2D;

import br.com.game.animator.engine.GameEngine;

/**
 * @author Jo�o Paulo
 */
public interface GameScorePresentation {

	public final static Float MAX_HI_SCORE_COUNTER_VALUE = GameEngine.FPS * 28f;
	
	/**
	 * Verifica se a apresenta��o terminou.
	 * @return
	 */
	public boolean hiScorePresentationFinished();
	
	/**
	 * Renderiza os hiscores.
	 * @param g2d
	 */
	public void drawHiScores(Graphics2D g2d);
	
	/**
	 * Atualiza o tamanho do canvas, para renderiza��o.
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
	
	/**
	 * Reinicializa os Counters
	 */
	public void resetCounters();
	
	/**
	 * Atualiza as anima��es.
	 */
	public void update();
}