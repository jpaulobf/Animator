package br.com.animator.game.data;

import java.util.Map;

import br.com.animator.game.data.enumerators.DeepColor;
import br.com.animator.game.data.enumerators.ScreenMode;

/**
 * author: Joao Paulo Faria
 */
public interface GameGraphics {

	/**
	 * @return
	 * Gets the active screen width.
	 */
	public Integer getScreenWidth();
	
	/**
	 * @return
	 * Gets the active screen height.
	 */
	public Integer getScreenHeight();
	
	/**
	 * @return
	 * Gets the active screen color depth.
	 */
	public DeepColor getScreenDeepColor();
	
	/**
	 * @return
	 * Checks if Triple Buffer is enabled.
	 */
	public boolean getTripleBufferEnabled();
	
	/**
	 * @return
	 * Gets the Screen Mode (Fullscreen/Window)
	 */
	public ScreenMode getScreenMode();
	
	/**
	 * @param screenWidth
	 * Sets the screen width.
	 */
	public void setScreenWidth(Integer screenWidth);
	
	/**
	 * @param screenHeight
	 * Sets the screen height.
	 */
	public void setScreenHeight(Integer screenHeight);
	
	/**
	 * @param deepColor
	 * Sets the screen color depth.
	 */
	public void setScreenDeepColor(DeepColor deepColor);
	
	/**
	 * @param enableTripleBuffer
	 * Enables or disables the triple-buffer.
	 */
	public void setTripleBufferEnabled(boolean enableTripleBuffer);
	
	/**
	 * @param screenMode
	 * Sets the screen mode (Fullscreen/Window)
	 */
	public void setScreenMode(ScreenMode screenMode);
	
	/**
	 * @return
	 * Gets the list of available resolutions.
	 */
	public Map<String, GameGraphics> getAvailableScreenResolutions();
	
	/**
	 * Stores the list of available resolutions.
	 * @param availableScreenResolutions The map of available screen resolutions
	 */
	public void setAvailableScreenResolutions(Map<String, GameGraphics> availableScreenResolutions);
	
	/**
	 * Clones simple properties (non-lists)
	 */
	public void cloneProperties(GameGraphics gameGraphics);
	
}