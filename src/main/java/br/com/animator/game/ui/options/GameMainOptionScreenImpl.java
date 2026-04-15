package br.com.animator.game.ui.options;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import br.com.animator.audio.OggAudio;
import br.com.animator.core.game.IGame;
import br.com.animator.game.Game;
import br.com.animator.input.GameAction;
import br.com.animator.ui.options.GameMainOptionScreen;
import br.com.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 */
public class GameMainOptionScreenImpl implements GameMainOptionScreen {

	// --- Properties
	private Integer mainOptionsMenuCounter = 0;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	protected ImageUtil imageUtil = null;
	private Integer optionSelected = 0;
	private final static Integer BUTTON_GAME_OPTIONS_POSITION_X = 114;
	private final static Integer BUTTON_GAME_OPTIONS_POSITION_Y = 329;
	private final static Integer BUTTON_CONFIG_KEYS_POSITION_X = 114;
	private final static Integer BUTTON_CONFIG_KEYS_POSITION_Y = 446;
	private final static Integer BUTTON_CONFIG_GFX_POSITION_X = 114;
	private final static Integer BUTTON_CONFIG_GFX_POSITION_Y = 564;
	private final static Integer BUTTON_CONFIG_SFX_POSITION_X = 114;
	private final static Integer BUTTON_CONFIG_SFX_POSITION_Y = 682;
	private final static Integer BUTTON_BACK_MAINMENU_POSITION_X = 114;
	private final static Integer BUTTON_BACK_MAINMENU_POSITION_Y = 804;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_X = 0;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_1 = 306;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_2 = 427;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_3 = 546;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_4 = 661;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_5 = 780;
	private BufferedImage backgroundGameOptions = null;
	private BufferedImage btGameOptions = null;
	private BufferedImage btConfigKeys = null;
	private BufferedImage btConfigGFX = null;
	private BufferedImage btConfigSFX = null;
	private BufferedImage btBackMainMenu = null;
	private BufferedImage highlightButton = null;

	/**
	 * Constructor
	 * 
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public GameMainOptionScreenImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		// --- Atualiza o tamanho do canvas.
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.backgroundGameOptions = this.imageUtil.loadImage("GameMainOption.1");
		this.btGameOptions = this.imageUtil.loadImage("GameMainOption.2");
		this.btConfigKeys = this.imageUtil.loadImage("GameMainOption.3");
		this.btConfigGFX = this.imageUtil.loadImage("GameMainOption.4");
		this.btConfigSFX = this.imageUtil.loadImage("GameMainOption.5");
		this.btBackMainMenu = this.imageUtil.loadImage("GameMainOption.6");
		this.highlightButton = this.imageUtil.loadImage("GameMainOption.7");
	}

	public void update(long frametime) {
		this.mainOptionsMenuCounter++;
	}

	public void draw(Graphics2D g2d) {
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

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil.updateCanvasProperties(this.PWIDTH, this.PHEIGHT, currentAspectRatio);
	}

	public void resetCounters() {
		this.mainOptionsMenuCounter = 0;
		this.optionSelected = 0;
	}

	public void nextGameOption() {
		this.optionSelected = (this.optionSelected + 1) % 5;
	}

	public void previousGameOption() {
		this.optionSelected = (this.optionSelected - 1);
		if (this.optionSelected < 0) {
			this.optionSelected = 4;
		}
	}

	public boolean isToGoToGameOptions() {
		return (this.optionSelected == 0);
	}

	public boolean isToConfigKeys() {
		return (this.optionSelected == 1);
	}

	public boolean isToConfigGFX() {
		return (this.optionSelected == 2);
	}

	public boolean isToConfigSFX() {
		return (this.optionSelected == 3);
	}

	public boolean isToBackToMainMenu() {
		return (this.optionSelected == 4);
	}

	@Override
	public boolean finished() {
		return false;
	}

	@Override
	public void handleInput(IGame game, GameAction action) {
		if (action == GameAction.START || action == GameAction.BUTTON_1) {
			if (this.isToBackToMainMenu()) {
				OggAudio.getAudio("menu.back").play();
				this.resetCounters();
				((Game)game).gotoMainMenu();
			} else {
				OggAudio.getAudio("menu.select").play();
				if (this.isToGoToGameOptions()) {
					this.resetCounters();
					((Game)game).gotoGameOptions();
				} else if (this.isToConfigSFX()) {
					this.resetCounters();
					((Game)game).gotoSFXConfigMenu();
				} else if (this.isToConfigGFX()) {
					this.resetCounters();
					((Game)game).gotoGFXConfigMenu();
				}
			}
		} else if (action == GameAction.UP) {
			OggAudio.getAudio("menu.change").play();
			this.previousGameOption();
		} else if (action == GameAction.DOWN) {
			OggAudio.getAudio("menu.change").play();
			this.nextGameOption();
		}
	}
}