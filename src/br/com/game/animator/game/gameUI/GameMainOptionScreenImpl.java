package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.game.animator.util.ImageUtil;

/**
 * @author Jo�o Paulo
 *
 */
public class GameMainOptionScreenImpl implements GameMainOptionScreen {
	
	//----------------------------------------------------------------------//
	//--------   PROPRIEDADES 				--------------------------------//
	//----------------------------------------------------------------------//
	private Integer mainOptionsMenuCounter							= 0;
	private Integer PWIDTH											= null;
	private Integer PHEIGHT											= null;
	protected ImageUtil imageUtil									= null;
	private Integer optionSelected									= 0;
	private final static Integer BUTTON_GAME_OPTIONS_POSITION_X 	= 114;
	private final static Integer BUTTON_GAME_OPTIONS_POSITION_Y 	= 329;
	private final static Integer BUTTON_CONFIG_KEYS_POSITION_X 		= 114;
	private final static Integer BUTTON_CONFIG_KEYS_POSITION_Y 		= 446;
	private final static Integer BUTTON_CONFIG_GFX_POSITION_X 		= 114;
	private final static Integer BUTTON_CONFIG_GFX_POSITION_Y 		= 564;
	private final static Integer BUTTON_CONFIG_SFX_POSITION_X 		= 114;
	private final static Integer BUTTON_CONFIG_SFX_POSITION_Y 		= 682;
	private final static Integer BUTTON_BACK_MAINMENU_POSITION_X 	= 114;
	private final static Integer BUTTON_BACK_MAINMENU_POSITION_Y 	= 804;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_X 		= 0;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_1 		= 306;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_2 		= 427;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_3 		= 546;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_4 		= 661;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_5 		= 780;
	private BufferedImage backgroundGameOptions						= null;
	private BufferedImage btGameOptions								= null;
	private BufferedImage btConfigKeys								= null;
	private BufferedImage btConfigGFX								= null;
	private BufferedImage btConfigSFX								= null;
	private BufferedImage btBackMainMenu							= null;
	private BufferedImage highlightButton							= null;
	
	/**
	 * Construtor Padr�o
	 * @param pwidth
	 * @param pheight
	 */
	public GameMainOptionScreenImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		//---------------------------------------------------------//
		//--- Atualiza o tamanho do canvas.               		---//
		//---------------------------------------------------------//
		this.PWIDTH 						= pwidth;
		this.PHEIGHT 						= pheight;
		this.imageUtil 						= new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.backgroundGameOptions			= this.imageUtil.loadImage("/res/images/bg_option_screen.png");
		this.btGameOptions					= this.imageUtil.loadImage("/res/images/opt_game_option_button.png");
		this.btConfigKeys					= this.imageUtil.loadImage("/res/images/opt_configure_keys_button.png");
		this.btConfigGFX					= this.imageUtil.loadImage("/res/images/opt_gfx_settings_button.png");
		this.btConfigSFX					= this.imageUtil.loadImage("/res/images/opt_sfx_settings_button.png");
		this.btBackMainMenu					= this.imageUtil.loadImage("/res/images/opt_back_mainmenu_button.png");
		this.highlightButton				= this.imageUtil.loadImage("/res/images/opt_mainoption_selector.png");
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#update()
	 */
	public void update() {
		//---------------------------------------------------------//
		//--- Mant�m o counter atualizado a cada chamada do 	---//
		//--- update. 											---//
		//---------------------------------------------------------//
		this.mainOptionsMenuCounter++;
	}
	
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#drawMainOptionScreen(java.awt.Graphics2D)
	 */
	public void drawMainOptionScreen(Graphics2D g2d) {
		g2d.drawImage(this.backgroundGameOptions, 
					  0,
					  0, 
					  this.imageUtil.getScaledWidth(this.backgroundGameOptions.getWidth()),
					  this.imageUtil.getScaledHeight(this.backgroundGameOptions.getHeight()),
					  null);
		
		int selector = 0;
		if (this.optionSelected == 0) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_1;
		} else if (this.optionSelected == 1) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_2;
		} else if (this.optionSelected == 2) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_3;
		} else if (this.optionSelected == 3) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_4;
		} else if (this.optionSelected == 4) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_5;
		}
		
		g2d.drawImage(this.highlightButton, 
					  this.imageUtil.getScaledWidth(BUTTON_HIGHLIGHT_POSITION_X),
					  this.imageUtil.getScaledHeight(selector),
					  this.imageUtil.getScaledWidth(this.highlightButton.getWidth()),
					  this.imageUtil.getScaledHeight(this.highlightButton.getHeight()),
					  null);
		
		g2d.drawImage(this.btGameOptions, 
					  this.imageUtil.getScaledWidth(BUTTON_GAME_OPTIONS_POSITION_X),
					  this.imageUtil.getScaledHeight(BUTTON_GAME_OPTIONS_POSITION_Y), 
					  this.imageUtil.getScaledWidth(this.btGameOptions.getWidth()),
					  this.imageUtil.getScaledHeight(this.btGameOptions.getHeight()),
					  null);
		
		g2d.drawImage(this.btConfigKeys, 
					  this.imageUtil.getScaledWidth(BUTTON_CONFIG_KEYS_POSITION_X),
					  this.imageUtil.getScaledHeight(BUTTON_CONFIG_KEYS_POSITION_Y), 
					  this.imageUtil.getScaledWidth(this.btConfigKeys.getWidth()),
					  this.imageUtil.getScaledHeight(this.btConfigKeys.getHeight()),
					  null);
		
		g2d.drawImage(this.btConfigGFX, 
					  this.imageUtil.getScaledWidth(BUTTON_CONFIG_GFX_POSITION_X),
					  this.imageUtil.getScaledHeight(BUTTON_CONFIG_GFX_POSITION_Y), 
					  this.imageUtil.getScaledWidth(this.btConfigGFX.getWidth()),
					  this.imageUtil.getScaledHeight(this.btConfigGFX.getHeight()),
					  null);
		
		g2d.drawImage(this.btConfigSFX, 
					  this.imageUtil.getScaledWidth(BUTTON_CONFIG_SFX_POSITION_X),
					  this.imageUtil.getScaledHeight(BUTTON_CONFIG_SFX_POSITION_Y), 
					  this.imageUtil.getScaledWidth(this.btConfigSFX.getWidth()),
					  this.imageUtil.getScaledHeight(this.btConfigSFX.getHeight()),
					  null);
		
		g2d.drawImage(this.btBackMainMenu, 
					  this.imageUtil.getScaledWidth(BUTTON_BACK_MAINMENU_POSITION_X),
					  this.imageUtil.getScaledHeight(BUTTON_BACK_MAINMENU_POSITION_Y), 
					  this.imageUtil.getScaledWidth(this.btBackMainMenu.getWidth()),
					  this.imageUtil.getScaledHeight(this.btBackMainMenu.getHeight()),
					  null);

	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#updateGraphics(boolean, java.lang.Integer, java.lang.Integer)
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 	= pwidth;
		this.PHEIGHT 	= pheight;
		this.imageUtil.updateCanvasProperties(this.PWIDTH, this.PHEIGHT, currentAspectRatio);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#resetCounters()
	 */
	public void resetCounters() {
		this.mainOptionsMenuCounter = 0;
		this.optionSelected 		= 0;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#nextGameOption()
	 */
	public void nextGameOption() {
		this.optionSelected = (this.optionSelected + 1) % 5;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#previousGameOption()
	 */
	public void previousGameOption() {
		this.optionSelected = (this.optionSelected - 1);
		if (this.optionSelected < 0) {
			this.optionSelected = 4;	
		}
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#isToGoToGameOptions()
	 */
	public boolean isToGoToGameOptions() {
		return (this.optionSelected == 0);
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#isToConfigKeys()
	 */
	public boolean isToConfigKeys() {
		return (this.optionSelected == 1);
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#isToConfigGFX()
	 */
	public boolean isToConfigGFX() {
		return (this.optionSelected == 2);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#isToConfigSFX()
	 */
	public boolean isToConfigSFX() {
		return (this.optionSelected == 3);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameMainOptionScreen#isToBackToMainMenu()
	 */
	public boolean isToBackToMainMenu() {
		return (this.optionSelected == 4);
	}
}