package br.com.animator.game.ui.menu;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import br.com.animator.audio.OggAudio;
import br.com.animator.core.game.IGame;
import br.com.animator.input.GameAction;
import br.com.animator.ui.menu.GameExitMenu;
import br.com.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 */
public class GameExitMenuImpl implements GameExitMenu {

	// --- Properties
	private Integer exitMenuCounter = 0;
	private volatile boolean showExitMenu = false;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	protected ImageUtil imageUtil = null;
	private Integer optionSelected = 0;
	private Integer MENU_BACKGROUND_POSITION_X = 0;
	private Integer MENU_BACKGROUND_POSITION_Y = 0;
	private final static Integer MENU_LABEL_POSITION_X = 22;
	private final static Integer MENU_LABEL_POSITION_Y = 59;
	private final static Integer BUTTON_YES_POSITION_X = 136;
	private final static Integer BUTTON_YES_POSITION_Y = 157;
	private final static Integer BUTTON_NO_POSITION_X = 313;
	private final static Integer BUTTON_NO_POSITION_Y = 157;
	private final static Integer BUTTON_HL_YES_POSITION_X = 125;
	private final static Integer BUTTON_HL_YES_POSITION_Y = 143;
	private final static Integer BUTTON_HL_NO_POSITION_X = 296;
	private final static Integer BUTTON_HL_NO_POSITION_Y = 143;
	private BufferedImage bgExitMenu = null;
	private BufferedImage labelExitMenu = null;
	private BufferedImage buttonYesExitMenu = null;
	private BufferedImage buttonNoExitMenu = null;
	private BufferedImage highlightButtonExitMenu = null;

	/**
	 * Construtor
	 */
	public GameExitMenuImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.bgExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.1");
		this.labelExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.2");
		this.buttonYesExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.3");
		this.buttonNoExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.4");
		this.highlightButtonExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.5");
		this.MENU_BACKGROUND_POSITION_X = (this.PWIDTH / 2) - (this.bgExitMenu.getWidth() / 2);
		this.MENU_BACKGROUND_POSITION_Y = (this.PHEIGHT / 2) - (this.bgExitMenu.getHeight() / 2);
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (this.showExitMenu) {

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

			g2d.drawImage(this.bgExitMenu,
					MENU_BACKGROUND_POSITION_X,
					MENU_BACKGROUND_POSITION_Y,
					this.bgExitMenu.getWidth(),
					this.bgExitMenu.getHeight(),
					null);

			g2d.drawImage(this.labelExitMenu,
					MENU_BACKGROUND_POSITION_X + this.imageUtil.getScaledWidth(MENU_LABEL_POSITION_X),
					MENU_BACKGROUND_POSITION_Y + this.imageUtil.getScaledHeight(MENU_LABEL_POSITION_Y),
					this.labelExitMenu.getWidth(),
					this.labelExitMenu.getHeight(),
					null);

			if (this.optionSelected == 1) {
				g2d.drawImage(this.highlightButtonExitMenu,
						MENU_BACKGROUND_POSITION_X + this.imageUtil.getScaledWidth(BUTTON_HL_YES_POSITION_X),
						MENU_BACKGROUND_POSITION_Y + this.imageUtil.getScaledHeight(BUTTON_HL_YES_POSITION_Y),
						this.highlightButtonExitMenu.getWidth(),
						this.highlightButtonExitMenu.getHeight(),
						null);
			} else {
				g2d.drawImage(this.highlightButtonExitMenu,
						MENU_BACKGROUND_POSITION_X + this.imageUtil.getScaledWidth(BUTTON_HL_NO_POSITION_X),
						MENU_BACKGROUND_POSITION_Y + this.imageUtil.getScaledHeight(BUTTON_HL_NO_POSITION_Y),
						this.highlightButtonExitMenu.getWidth(),
						this.highlightButtonExitMenu.getHeight(),
						null);
			}

			g2d.drawImage(this.buttonYesExitMenu,
					MENU_BACKGROUND_POSITION_X + this.imageUtil.getScaledWidth(BUTTON_YES_POSITION_X),
					MENU_BACKGROUND_POSITION_Y + this.imageUtil.getScaledHeight(BUTTON_YES_POSITION_Y),
					this.buttonYesExitMenu.getWidth(),
					this.buttonYesExitMenu.getHeight(),
					null);

			g2d.drawImage(this.buttonNoExitMenu,
					MENU_BACKGROUND_POSITION_X + this.imageUtil.getScaledWidth(BUTTON_NO_POSITION_X),
					MENU_BACKGROUND_POSITION_Y + this.imageUtil.getScaledHeight(BUTTON_NO_POSITION_Y),
					this.buttonNoExitMenu.getWidth(),
					this.buttonNoExitMenu.getHeight(),
					null);
		}
	}

	@Override
	public void update(long frametime) {
		this.exitMenuCounter++;
	}

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);

		this.bgExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.1");
		this.labelExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.2");
		this.buttonYesExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.3");
		this.buttonNoExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.4");
		this.highlightButtonExitMenu = this.imageUtil.loadScaledImage("GameExitMenu.5");

		this.MENU_BACKGROUND_POSITION_X = (this.PWIDTH / 2) - (this.bgExitMenu.getWidth() / 2);
		this.MENU_BACKGROUND_POSITION_Y = (this.PHEIGHT / 2) - (this.bgExitMenu.getHeight() / 2);
	}

	public void resetCounters() {
		this.exitMenuCounter = 0;
		this.optionSelected = 0;
	}

	public void nextGameOption() {
		this.optionSelected = (this.optionSelected + 1) % 2;
	}

	public void previousGameOption() {
		this.optionSelected = (this.optionSelected - 1);
		if (this.optionSelected < 0) {
			this.optionSelected = 1;
		}
	}

	public boolean isShowingExitMenu() {
		return (this.showExitMenu);
	}

	public boolean isToExit() {
		return (this.optionSelected == 1);
	}

	public void showExitMenu() {
		this.showExitMenu = true;
	}

	public void hideExitMenu() {
		this.showExitMenu = false;
		this.resetCounters();
	}

	@Override
	public boolean finished() {
		return false;
	}

	@Override
	public void handleInput(IGame game, GameAction action) {
		switch (action) {
            case LEFT:
				OggAudio.getAudio("menu.change").play();
				this.previousGameOption();
                break;
            case RIGHT:
				OggAudio.getAudio("menu.change").play();
                this.nextGameOption();
                break;
            case START:
            case BUTTON_1: // Enter maps to Start or B1
				OggAudio.getAudio("menu.select").play();
                if (this.isToExit()) {
                    game.stopGame();
                } else {
                    this.hideExitMenu();
                }
                break;
            default: break;
        }
	}
}