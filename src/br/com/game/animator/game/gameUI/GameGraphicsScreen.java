package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

/**
 * @author Jo�o Paulo
 *
 */
public interface GameGraphicsScreen {

	/**
	 * @param g2d
	 * Renderiza a Introdu��o.
	 */
	public void drawGraphicsOptionScreen(Graphics2D g2d);
	
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
	 * Verifica se deve voltar para o main options menu
	 * @return
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Verifica se est� sobre o menu apply
	 * @return
	 */
	public boolean isToApply();
	
	/**
	 * Verifica se est� sobre o menu Screen Resolution
	 * @return
	 */
	public boolean isOverScreenResolution();
	
	/**
	 * Verifica se est� sobre o menu de Triple Buffering
	 * @return
	 */
	public boolean isOverEnableTripleBuffering();
	
	/**
	 * Verifica se est� sobre o menu de ScreenMode.
	 * @return
	 */
	public boolean isOverScreenMode();
	
	/**
	 * Verifica se est� sobre o menu de DeepColor.
	 * @return
	 */
	public boolean isOverDeepColor();
	
	/**
	 * Avan�a para o pr�ximo screen-resolution
	 */
	public void nextScreenResolution();
	
	/**
	 * Retrocede o screen-resolution
	 */
	public void previousScreenResolution();
	
	/**
	 * Habilita o Triple Buffer
	 */
	public void enableTripleBuffer();
	
	/**
	 * Desabilita o Triple Buffer
	 */
	public void disableTripleBuffer();
	
	/**
	 * Avan�a para o pr�ximo screen-mode
	 */
	public void nextScreenMode();
	
	/**
	 * Retrocede o screen-mode
	 */
	public void previousScreenMode();
	
	/**
	 * Avan�a para o pr�ximo deep color
	 */
	public void nextScreenDeepColor();
	
	/**
	 * Retrocede para o deep color anterior
	 */
	public void previousScreenDeepColor();
	
	/**
	 * Aplica as modifica��es solicitadas.
	 */
	public void applyChanges();
	
	/**
	 * Cancela as mudan�as
	 */
	public void cancelChanges();
	
	/**
	 * Exibe label com warning de apply
	 */
	public void resetMustApplyForChanges();
}