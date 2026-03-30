package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

/**
 * @author Jo�o Paulo
 *
 */
public interface GameMainMenu {

	/**
	 * 
	 */
	public final static Float MAX_COUNTER_MAIN_MENU_VALUE = 1200f;

	/**
	 * Verifica se � para sair do Main Menu.
	 * @return
	 */
	public boolean mainMenuFinished();
	
	/**
	 * Atualiza as anima��es.
	 */
	public void update();
	
	/**
	 * Renderiza o Main Menu.
	 * @param g2d
	 */
	public void drawMainMenu(Graphics2D g2d);
	
	/**
	 * Atualiza o tamanho do canvas, para renderiza��o.
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
	
	/**
	 * Avan�a uma posi��o no gameoptions
	 */
	public void nextGameOption();
	
	/**
	 * Retorna uma posi��o no gameoptions
	 */
	public void previousGameOption();
	
	/**
	 * Reinicializa os Counters
	 */
	public void resetCounters();
	
	
	/**
	 * Verifica se selecionou 'Sair'
	 * @return
	 */
	public boolean isExitSelected();
	
	/**
	 * Verifica se selecionou 'Options'
	 * @return
	 */
	public boolean isOptionSelected();
	
	/**
	 * Verifica se selecionou 'Start Game'
	 * @return
	 */
	public boolean isGameStartSelected();
}