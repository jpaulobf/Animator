package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

/**
 * @author Jo�o Paulo
 */
public interface GameSoundOptionScreen {

	/**
	 * @param g2d
	 * Renderiza a Introdu��o.
	 */
	public void drawSoundOptionScreen(Graphics2D g2d);
	
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
	 * Verifica se est� sobre o menu de Habilitar/Desabilitar M�sicas
	 * @return
	 */
	public boolean isOverEnableMusic();
	
	/**
	 * Verifica se est� sobre o menu de Habilitar/Desabilitar SFX
	 * @return
	 */
	public boolean isOverEnableSFX();
	
	/**
	 * Verifica se est� sobre o menu de Volume de M�sicas
	 * @return
	 */
	public boolean isOverMusicVolume();
	
	/**
	 * Verifica se est� sobre o menu de Volume de SFX.
	 * @return
	 */
	public boolean isOverSFXVolume();
	
	/**
	 * Habilita as M�sicas
	 */
	public void setMusicEnable();
	
	/**
	 * Desabilita as M�sicas
	 */
	public void setMusicDisable();
	
	/**
	 * Habilita os SFX
	 */
	public void setSFXEnable();
	
	/**
	 * Desabilita os SFX
	 */
	public void setSFXDisable();
	
	/**
	 * Aumenta o volume das M�sicas 
	 */
	public void increaseMusicVolume();
	
	/**
	 * Diminui o volume das M�sicas 
	 */
	public void decreaseMusicVolume();
	
	/**
	 * Aumenta o volume dos SFX
	 */
	public void increaseSFXVolume();
	
	/**
	 * Diminui o volume dos SFXs
	 */
	public void decreaseSFXVolume();
	
}