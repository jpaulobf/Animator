package br.com.game.animator.game.gameUI.options;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * @author João Paulo Faria
 *
 */
public interface GameGraphicsScreen extends CoreGameLogic {

	/**
	 * Avança para a próxima opção do game-option
	 */
	public void nextOption();
	
	/**
	 * Retrocede uma opção do game-option
	 */
	public void previousOption();
	
	/**
	 * Verifica se deve voltar para o main options menu
	 * @return
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Verifica se está sobre o menu apply
	 * @return
	 */
	public boolean isToApply();
	
	/**
	 * Verifica se está sobre o menu Screen Resolution
	 * @return
	 */
	public boolean isOverScreenResolution();
	
	/**
	 * Verifica se está sobre o menu de Triple Buffering
	 * @return
	 */
	public boolean isOverEnableTripleBuffering();
	
	/**
	 * Verifica se está sobre o menu de ScreenMode.
	 * @return
	 */
	public boolean isOverScreenMode();
	
	/**
	 * Verifica se está sobre o menu de DeepColor.
	 * @return
	 */
	public boolean isOverDeepColor();
	
	/**
	 * Avança para o próximo screen-resolution
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
	 * Avança para o próximo screen-mode
	 */
	public void nextScreenMode();
	
	/**
	 * Retrocede o screen-mode
	 */
	public void previousScreenMode();
	
	/**
	 * Avança para o próximo deep color
	 */
	public void nextScreenDeepColor();
	
	/**
	 * Retrocede para o deep color anterior
	 */
	public void previousScreenDeepColor();
	
	/**
	 * Aplica as modificações solicitadas.
	 */
	public void applyChanges();
	
	/**
	 * Cancela as mudanças
	 */
	public void cancelChanges();
	
	/**
	 * Exibe label com warning de apply
	 */
	public void resetMustApplyForChanges();
}