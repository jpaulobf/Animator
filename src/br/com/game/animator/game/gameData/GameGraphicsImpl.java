package br.com.game.animator.game.gameData;

import java.util.Map;

import br.com.game.animator.game.gameData.enumerators.DeepColor;
import br.com.game.animator.game.gameData.enumerators.ScreenMode;

/**
 * @author Jo�o Paulo
 */
public class GameGraphicsImpl implements GameGraphics {
	
	//------------------------------------------------------------------------//
	//--------   PROPRIEDADES DO GAME GRAPHIC  -------------------------------//
	//------------------------------------------------------------------------//
	private Integer screenWidth										= null;
	private Integer screenHeight									= null;
	private DeepColor screenDeepColor								= null;
	private boolean tripleBufferEnabled								= false;
	private ScreenMode screenMode									= null;
	private Map<String, GameGraphics> availableScreenResolutions 	= null;
	
	/**
	 * Construtor Clone.
	 */
	public GameGraphicsImpl(GameGraphics gameGraphics) {
		this.screenWidth			= gameGraphics.getScreenWidth();
		this.screenHeight			= gameGraphics.getScreenHeight();
		this.screenDeepColor		= gameGraphics.getScreenDeepColor();
		this.tripleBufferEnabled	= gameGraphics.getTripleBufferEnabled();
		this.screenMode				= gameGraphics.getScreenMode();
	}
	
	/**
	 * Construtor padr�o.
	 */
	public GameGraphicsImpl(boolean fullscreen, boolean tripleBuffering) {
		this.tripleBufferEnabled = tripleBuffering;
		if (fullscreen) {
			this.screenMode	= ScreenMode.FULLSCREEN;
		} else {
			this.screenMode	= ScreenMode.WINDOWED;
		}
	}
	
	/**
	 * @param screenWidth
	 * @param screenHeight
	 * @param screenDeepColor
	 */
	public GameGraphicsImpl(Integer screenWidth, Integer screenHeight, Integer screenDeepColor) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		if (screenDeepColor == 32) {
			this.screenDeepColor = DeepColor.TRUE_COLOR_32_BITS;
		} else {
			this.screenDeepColor = DeepColor.HI_COLOR_16_BITS;
		}
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#getScreenWidth()
	 */
	public Integer getScreenWidth() {
		return (this.screenWidth);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#getScreenHeight()
	 */
	public Integer getScreenHeight() {
		return (this.screenHeight);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#getScreenDeepColor()
	 */
	public DeepColor getScreenDeepColor() {
		return (this.screenDeepColor);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#getTripleBufferEnabled()
	 */
	public boolean getTripleBufferEnabled() {
		return (this.tripleBufferEnabled);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#getScreenMode()
	 */
	public ScreenMode getScreenMode() {
		return (this.screenMode);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#setScreenWidth(java.lang.Integer)
	 */
	public void setScreenWidth(Integer screenWidth) {
		this.screenWidth = screenWidth;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#setScreenHeight(java.lang.Integer)
	 */
	public void setScreenHeight(Integer screenHeight) {
		this.screenHeight = screenHeight;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#setScreenDeepColor(br.com.animator.gameData.enumerators.DeepColor)
	 */
	public void setScreenDeepColor(DeepColor deepColor) {
		this.screenDeepColor = deepColor;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#setTripleBufferEnabled(boolean)
	 */
	public void setTripleBufferEnabled(boolean enableTripleBuffer) {
		this.tripleBufferEnabled = enableTripleBuffer;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#setScreenMode(br.com.animator.gameData.enumerators.ScreenMode)
	 */
	public void setScreenMode(ScreenMode screenMode) {
		this.screenMode = screenMode;
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#getAvailableScreenResolutions()
	 */
	public Map<String, GameGraphics> getAvailableScreenResolutions() {
		return (this.availableScreenResolutions);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#setAvailableScreenResolutions(java.util.Map)
	 */
	public void setAvailableScreenResolutions(Map<String, GameGraphics> availableScreenResolutions) {
		this.availableScreenResolutions = availableScreenResolutions;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameData.GameGraphics#cloneProperties(br.com.animator.gameData.GameGraphics)
	 */
	public void cloneProperties(GameGraphics gameGraphics) {
		this.screenWidth			= gameGraphics.getScreenWidth();
		this.screenHeight			= gameGraphics.getScreenHeight();
		this.screenDeepColor		= gameGraphics.getScreenDeepColor();
		this.tripleBufferEnabled	= gameGraphics.getTripleBufferEnabled();
		this.screenMode				= gameGraphics.getScreenMode();
	}
}