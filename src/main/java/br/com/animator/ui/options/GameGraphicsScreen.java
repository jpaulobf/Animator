package br.com.animator.ui.options;

import br.com.animator.core.game.CoreGameLogic;

/**
 * author: Joao Paulo Faria
 *
 */
public interface GameGraphicsScreen extends CoreGameLogic {

	/**
	 * Advances to the next option
	 */
	public void nextOption();
	
	/**
	 * Returns to the previous option
	 */
	public void previousOption();
	
	/**
	 * Checks if should return to the main options menu
	 * @return
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Checks if hovering over the Apply menu
	 * @return
	 */
	public boolean isToApply();
	
	/**
	 * Checks if hovering over the Screen Resolution menu
	 * @return
	 */
	public boolean isOverScreenResolution();
	
	/**
	 * Checks if hovering over the Triple Buffering menu
	 * @return
	 */
	public boolean isOverEnableTripleBuffering();
	
	/**
	 * Checks if hovering over the Screen Mode menu
	 * @return
	 */
	public boolean isOverScreenMode();
	
	/**
	 * Checks if hovering over the Deep Color menu
	 * @return
	 */
	public boolean isOverDeepColor();
	
	/**
	 * Advances to the next screen resolution
	 */
	public void nextScreenResolution();
	
	/**
	 * Returns to the previous screen resolution
	 */
	public void previousScreenResolution();
	
	/**
	 * Enables Triple Buffering
	 */
	public void enableTripleBuffer();
	
	/**
	 * Disables Triple Buffering
	 */
	public void disableTripleBuffer();
	
	/**
	 * Advances to the next screen mode
	 */
	public void nextScreenMode();
	
	/**
	 * Returns to the previous screen mode
	 */
	public void previousScreenMode();
	
	/**
	 * Advances to the next deep color
	 */
	public void nextScreenDeepColor();
	
	/**
	 * Returns to the previous deep color
	 */
	public void previousScreenDeepColor();
	
	/**
	 * Applies the requested changes
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