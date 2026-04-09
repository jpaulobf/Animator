package br.com.animator.ui.options;

import br.com.animator.core.CoreGameLogic;

/**
 * author: Joao Paulo Faria
 */
public interface GameOptionScreen extends CoreGameLogic {

	/**
	 * Advances to the next option
	 */
	public void nextOption();
	
	/**
	 * Goes back to the previous option
	 */
	public void previousOption();
	
	/**
	 * Checks if should return to main menu
	 */
	public boolean isToBackToMainOption();
	
	/**
	 * Checks if over difficulty menu
	 */
	public boolean isOverGameDifficulty();
	
	/**
	 * Checks if over lives menu
	 */
	public boolean isOverRestsSelection();
	
	/**
	 * Checks if over extra-life menu
	 */
	public boolean isOverExtraLifeSelection();
	
	/**
	 * Checks if over continues menu
	 */
	public boolean isOverContinuesSelection();
	
	
	/**
	 * Checks if over subtitles menu
	 */
	public boolean isOverEnableSubtitles();
	
	/**
	 * Advances to next difficulty
	 */
	public void setNextDifficulty();
	
	/**
	 * Goes back to previous difficulty
	 */
	public void setPreviousDifficulty();
	
	/**
	 * Adds a life
	 */
	public void addRest();
	
	/**
	 * Removes a life
	 */
	public void subRest();
	
	/**
	 * Advances to next extra life points option
	 */
	public void setNextExtraLifeAtPoints();
	
	/**
	 * Goes back to previous extra life points option
	 */
	public void setPreviousExtraLifeAtPoints();
	
	/**
	 * Adds a continue
	 */
	public void addContinues();
	
	/**
	 * Removes a continue
	 */
	public void subContinues();
	
	/**
	 * Enables subtitles
	 */
	public void enableSubtitles();
	
	/**
	 * Disables subtitles
	 */
	public void disableSubtitles();
}