package br.com.animator.game.ui.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import br.com.animator.audio.OggAudio;
import br.com.animator.audio.OggAudio.AudioType;
import br.com.animator.core.game.IGame;
import br.com.animator.game.Game;
import br.com.animator.input.GameAction;
import br.com.animator.ui.menu.GameMainMenu;
import br.com.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 *
 */
public class GameMainMenuImpl implements GameMainMenu {

	// Properties
	private Integer mainMenuCounter = 0;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	protected ImageUtil imageUtil = null;
	private Integer optionSelected = 0;
	private final static Integer LOGO_P1_POSITION_X = 459;
	private final static Integer LOGO_P1_POSITION_Y = 105;
	private final static Integer LOGO_P2_POSITION_X = 584;
	private final static Integer LOGO_P2_POSITION_Y = 105;
	private final static Integer LOGO_P3_POSITION_X = 675;
	private final static Integer LOGO_P3_POSITION_Y = 173;
	private final static Integer LOGO_P4_POSITION_X = 1000;
	private final static Integer LOGO_P4_POSITION_Y = 111;
	private final static Integer LOGO_P5_POSITION_X = 763;
	private final static Integer LOGO_P5_POSITION_Y = 304;
	private final static Integer START_POSITION_X = 766;
	private final static Integer START_POSITION_Y = 707;
	private final static Integer START_OVER_POSITION_X = 485;
	private final static Integer START_OVER_POSITION_Y = 696;
	private final static Integer OPTIONS_POSITION_X = 825;
	private final static Integer OPTIONS_POSITION_Y = 805;
	private final static Integer OPTIONS_OVER_POSITION_X = 485;
	private final static Integer OPTIONS_OVER_POSITION_Y = 802;
	private final static Integer QUIT_POSITION_X = 885;
	private final static Integer QUIT_POSITION_Y = 911;
	private final static Integer QUIT_OVER_POSITION_X = 485;
	private final static Integer QUIT_OVER_POSITION_Y = 905;

	// -------- LOGO IMAGES
	private BufferedImage logoPart1 = null;
	private BufferedImage logoPart2 = null;
	private BufferedImage logoPart3 = null;
	private BufferedImage logoPart4 = null;
	private BufferedImage logoPart5 = null;
	private BufferedImage startGameButton = null;
	private BufferedImage optionsButton = null;
	private BufferedImage quitGameButton = null;
	private BufferedImage mainMenuHighlight = null;
	private Integer selectorPositionX = null;
	private Integer selectorPositionY = null;

	private Integer selectorPositionX1 = null;
	private Integer selectorPositionY1 = null;
	private Integer selectorPositionX2 = null;
	private Integer selectorPositionY2 = null;
	private Integer selectorPositionX3 = null;
	private Integer selectorPositionY3 = null;

	private Integer logoPart1PositionX = null;
	private Integer logoPart1PositionY = null;
	private Integer logoPart2PositionX = null;
	private Integer logoPart2PositionY = null;
	private Integer logoPart3PositionX = null;
	private Integer logoPart3PositionY = null;
	private Integer logoPart4PositionX = null;
	private Integer logoPart4PositionY = null;
	private Integer logoPart5PositionX = null;
	private Integer logoPart5PositionY = null;
	private Integer logoPart6PositionX = null;
	private Integer logoPart6PositionY = null;
	private Integer logoPart7PositionX = null;
	private Integer logoPart7PositionY = null;
	private Integer logoPart8PositionX = null;
	private Integer logoPart8PositionY = null;

	private Integer selectorWidth = null;
	private Integer selectorHeight = null;
	private Integer logoPart1Width = null;
	private Integer logoPart1Height = null;
	private Integer logoPart2Width = null;
	private Integer logoPart2Height = null;
	private Integer logoPart3Width = null;
	private Integer logoPart3Height = null;
	private Integer logoPart4Width = null;
	private Integer logoPart4Height = null;
	private Integer logoPart5Width = null;
	private Integer logoPart5Height = null;
	private Integer startGameButtonWidth = null;
	private Integer startGameButtonHeight = null;
	private Integer optionsButtonWidth = null;
	private Integer optionsButtonHeight = null;
	private Integer quitGameButtonWidth = null;
	private Integer quitGameButtonHeight = null;
	private OggAudio menuSound;
	private OggAudio menuMusic;

	/**
	 * Construtor
	 * 
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public GameMainMenuImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		// ---------------------------------------------------------//
		// --- Atualiza o tamanho do canvas. ---//
		// ---------------------------------------------------------//
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);

		this.logoPart1 = this.imageUtil.loadScaledImage("/images/logo_p1.png");
		this.logoPart2 = this.imageUtil.loadScaledImage("/images/logo_p2.png");
		this.logoPart3 = this.imageUtil.loadScaledImage("/images/logo_p3.png");
		this.logoPart4 = this.imageUtil.loadScaledImage("/images/logo_p4.png");
		this.logoPart5 = this.imageUtil.loadScaledImage("/images/logo_p5.png");
		this.startGameButton = this.imageUtil.loadScaledImage("/images/start_game_button.png");
		this.optionsButton = this.imageUtil.loadScaledImage("/images/options_button.png");
		this.quitGameButton = this.imageUtil.loadScaledImage("/images/quit_game_button.png");
		this.mainMenuHighlight = this.imageUtil.loadScaledImage("/images/mainmenu_selection.png");

		this.selectorPositionX1 = this.imageUtil.getScaledWidthForPosition(START_OVER_POSITION_X);
		this.selectorPositionY1 = this.imageUtil.getScaledHeight(START_OVER_POSITION_Y);
		this.selectorPositionX2 = this.imageUtil.getScaledWidthForPosition(OPTIONS_OVER_POSITION_X);
		this.selectorPositionY2 = this.imageUtil.getScaledHeight(OPTIONS_OVER_POSITION_Y);
		this.selectorPositionX3 = this.imageUtil.getScaledWidthForPosition(QUIT_OVER_POSITION_X);
		this.selectorPositionY3 = this.imageUtil.getScaledHeight(QUIT_OVER_POSITION_Y);

		this.selectorPositionX = this.selectorPositionX1;
		this.selectorPositionY = this.selectorPositionY1;

		this.logoPart1PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P1_POSITION_X);
		this.logoPart1PositionY = this.imageUtil.getScaledHeight(LOGO_P1_POSITION_Y);
		this.logoPart2PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P2_POSITION_X);
		this.logoPart2PositionY = this.imageUtil.getScaledHeight(LOGO_P2_POSITION_Y);
		this.logoPart3PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P3_POSITION_X);
		this.logoPart3PositionY = this.imageUtil.getScaledHeight(LOGO_P3_POSITION_Y);
		this.logoPart4PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P4_POSITION_X);
		this.logoPart4PositionY = this.imageUtil.getScaledHeight(LOGO_P4_POSITION_Y);
		this.logoPart5PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P5_POSITION_X);
		this.logoPart5PositionY = this.imageUtil.getScaledHeight(LOGO_P5_POSITION_Y);
		this.logoPart6PositionX = this.imageUtil.getScaledWidthForPosition(START_POSITION_X);
		this.logoPart6PositionY = this.imageUtil.getScaledHeight(START_POSITION_Y);
		this.logoPart7PositionX = this.imageUtil.getScaledWidthForPosition(OPTIONS_POSITION_X);
		this.logoPart7PositionY = this.imageUtil.getScaledHeight(OPTIONS_POSITION_Y);
		this.logoPart8PositionX = this.imageUtil.getScaledWidthForPosition(QUIT_POSITION_X);
		this.logoPart8PositionY = this.imageUtil.getScaledHeight(QUIT_POSITION_Y);

		this.selectorWidth = this.mainMenuHighlight.getWidth();
		this.selectorHeight = this.mainMenuHighlight.getHeight();
		this.logoPart1Width = this.logoPart1.getWidth();
		this.logoPart1Height = this.logoPart1.getHeight();
		this.logoPart2Width = this.logoPart2.getWidth();
		this.logoPart2Height = this.logoPart2.getHeight();
		this.logoPart3Width = this.logoPart3.getWidth();
		this.logoPart3Height = this.logoPart3.getHeight();
		this.logoPart4Width = this.logoPart4.getWidth();
		this.logoPart4Height = this.logoPart4.getHeight();
		this.logoPart5Width = this.logoPart5.getWidth();
		this.logoPart5Height = this.logoPart5.getHeight();
		this.startGameButtonWidth = this.startGameButton.getWidth();
		this.startGameButtonHeight = this.startGameButton.getHeight();
		this.optionsButtonWidth = this.optionsButton.getWidth();
		this.optionsButtonHeight = this.optionsButton.getHeight();
		this.quitGameButtonWidth = this.quitGameButton.getWidth();
		this.quitGameButtonHeight = this.quitGameButton.getHeight();
		this.menuSound = new OggAudio("/audio/menu1.ogg", AudioType.SFX);
		this.menuMusic = new OggAudio("/audio/menumusic.ogg", AudioType.MUSIC);
		menuMusic.play();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.animator.gameUI.GameMainMenu#mainMenuFinished()
	 */
	public boolean mainMenuFinished() {
		return (this.mainMenuCounter >= MAX_COUNTER_MAIN_MENU_VALUE);
	}

	public void update(long frameTime) {
		this.mainMenuCounter++;

		if (this.optionSelected == 0) {
			this.selectorPositionX = selectorPositionX1;
			this.selectorPositionY = selectorPositionY1;
		} else if (this.optionSelected == 1) {
			this.selectorPositionX = selectorPositionX2;
			this.selectorPositionY = selectorPositionY2;
		} else {
			this.selectorPositionX = selectorPositionX3;
			this.selectorPositionY = selectorPositionY3;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.animator.gameUI.GameMainMenu#drawMainMenu(java.awt.Graphics2D)
	 */
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, PWIDTH, PHEIGHT);
		g2d.setColor(Color.BLACK);

		g2d.drawImage(this.logoPart1,
				this.logoPart1PositionX,
				this.logoPart1PositionY,
				this.logoPart1Width,
				this.logoPart1Height,
				null);

		g2d.drawImage(this.logoPart2,
				this.logoPart2PositionX,
				this.logoPart2PositionY,
				this.logoPart2Width,
				this.logoPart2Height,
				null);

		g2d.drawImage(this.logoPart3,
				this.logoPart3PositionX,
				this.logoPart3PositionY,
				this.logoPart3Width,
				this.logoPart3Height,
				null);

		g2d.drawImage(this.logoPart4,
				this.logoPart4PositionX,
				this.logoPart4PositionY,
				this.logoPart4Width,
				this.logoPart4Height,
				null);

		g2d.drawImage(this.logoPart5,
				this.logoPart5PositionX,
				this.logoPart5PositionY,
				this.logoPart5Width,
				this.logoPart5Height,
				null);

		g2d.drawImage(this.mainMenuHighlight,
				this.selectorPositionX,
				this.selectorPositionY,
				this.selectorWidth,
				this.selectorHeight,
				null);

		g2d.drawImage(this.startGameButton,
				this.logoPart6PositionX,
				this.logoPart6PositionY,
				this.startGameButtonWidth,
				this.startGameButtonHeight,
				null);

		g2d.drawImage(this.optionsButton,
				this.logoPart7PositionX,
				this.logoPart7PositionY,
				this.optionsButtonWidth,
				this.optionsButtonHeight,
				null);

		g2d.drawImage(this.quitGameButton,
				this.logoPart8PositionX,
				this.logoPart8PositionY,
				this.quitGameButtonWidth,
				this.quitGameButtonHeight,
				null);
	}

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);

		this.logoPart1 = this.imageUtil.loadScaledImage("/images/logo_p1.png");
		this.logoPart2 = this.imageUtil.loadScaledImage("/images/logo_p2.png");
		this.logoPart3 = this.imageUtil.loadScaledImage("/images/logo_p3.png");
		this.logoPart4 = this.imageUtil.loadScaledImage("/images/logo_p4.png");
		this.logoPart5 = this.imageUtil.loadScaledImage("/images/logo_p5.png");
		this.startGameButton = this.imageUtil.loadScaledImage("/images/start_game_button.png");
		this.optionsButton = this.imageUtil.loadScaledImage("/images/options_button.png");
		this.quitGameButton = this.imageUtil.loadScaledImage("/images/quit_game_button.png");
		this.mainMenuHighlight = this.imageUtil.loadScaledImage("/images/mainmenu_selection.png");

		this.selectorPositionX1 = this.imageUtil.getScaledWidthForPosition(START_OVER_POSITION_X);
		this.selectorPositionY1 = this.imageUtil.getScaledHeight(START_OVER_POSITION_Y);
		this.selectorPositionX2 = this.imageUtil.getScaledWidthForPosition(OPTIONS_OVER_POSITION_X);
		this.selectorPositionY2 = this.imageUtil.getScaledHeight(OPTIONS_OVER_POSITION_Y);
		this.selectorPositionX3 = this.imageUtil.getScaledWidthForPosition(QUIT_OVER_POSITION_X);
		this.selectorPositionY3 = this.imageUtil.getScaledHeight(QUIT_OVER_POSITION_Y);

		this.logoPart1PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P1_POSITION_X);
		this.logoPart1PositionY = this.imageUtil.getScaledHeight(LOGO_P1_POSITION_Y);
		this.logoPart2PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P2_POSITION_X);
		this.logoPart2PositionY = this.imageUtil.getScaledHeight(LOGO_P2_POSITION_Y);
		this.logoPart3PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P3_POSITION_X);
		this.logoPart3PositionY = this.imageUtil.getScaledHeight(LOGO_P3_POSITION_Y);
		this.logoPart4PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P4_POSITION_X);
		this.logoPart4PositionY = this.imageUtil.getScaledHeight(LOGO_P4_POSITION_Y);
		this.logoPart5PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P5_POSITION_X);
		this.logoPart5PositionY = this.imageUtil.getScaledHeight(LOGO_P5_POSITION_Y);
		this.logoPart6PositionX = this.imageUtil.getScaledWidthForPosition(START_POSITION_X);
		this.logoPart6PositionY = this.imageUtil.getScaledHeight(START_POSITION_Y);
		this.logoPart7PositionX = this.imageUtil.getScaledWidthForPosition(OPTIONS_POSITION_X);
		this.logoPart7PositionY = this.imageUtil.getScaledHeight(OPTIONS_POSITION_Y);
		this.logoPart8PositionX = this.imageUtil.getScaledWidthForPosition(QUIT_POSITION_X);
		this.logoPart8PositionY = this.imageUtil.getScaledHeight(QUIT_POSITION_Y);

		this.selectorWidth = this.mainMenuHighlight.getWidth();
		this.selectorHeight = this.mainMenuHighlight.getHeight();
		this.logoPart1Width = this.logoPart1.getWidth();
		this.logoPart1Height = this.logoPart1.getHeight();
		this.logoPart2Width = this.logoPart2.getWidth();
		this.logoPart2Height = this.logoPart2.getHeight();
		this.logoPart3Width = this.logoPart3.getWidth();
		this.logoPart3Height = this.logoPart3.getHeight();
		this.logoPart4Width = this.logoPart4.getWidth();
		this.logoPart4Height = this.logoPart4.getHeight();
		this.logoPart5Width = this.logoPart5.getWidth();
		this.logoPart5Height = this.logoPart5.getHeight();
		this.startGameButtonWidth = this.startGameButton.getWidth();
		this.startGameButtonHeight = this.startGameButton.getHeight();
		this.optionsButtonWidth = this.optionsButton.getWidth();
		this.optionsButtonHeight = this.optionsButton.getHeight();
		this.quitGameButtonWidth = this.quitGameButton.getWidth();
		this.quitGameButtonHeight = this.quitGameButton.getHeight();
	}

	public void resetCounters() {
		this.mainMenuCounter = 0;
		this.optionSelected = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.animator.gameUI.GameMainMenu#nextGameOption()
	 */
	public void nextGameOption() {
		this.optionSelected = (this.optionSelected + 1) % 3;
		this.mainMenuCounter = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.animator.gameUI.GameMainMenu#previousGameOption()
	 */
	public void previousGameOption() {
		this.optionSelected = (this.optionSelected - 1);
		if (this.optionSelected < 0) {
			this.optionSelected = 2;
		}
		this.mainMenuCounter = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.animator.gameUI.GameMainMenu#isGameStartSelected()
	 */
	public boolean isGameStartSelected() {
		return (this.optionSelected == 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.animator.gameUI.GameMainMenu#isOptionSelected()
	 */
	public boolean isOptionSelected() {
		return (this.optionSelected == 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.animator.gameUI.GameMainMenu#isExitSelected()
	 */
	public boolean isExitSelected() {
		return (this.optionSelected == 2);
	}

	@Override
	public boolean finished() {
		return false;
	}

	@Override
	public void handleInput(IGame game, GameAction action) {
		if (action == GameAction.START || action == GameAction.BUTTON_1) {
			if (this.isExitSelected()) {
				menuMusic.stop();
				((Game)game).showExitMenu();
			} else if (this.isOptionSelected()) {
				((Game)game).gotoMainOption();
			}
		} else if (action == GameAction.UP) {
			this.menuSound.play();
			this.previousGameOption();
		} else if (action == GameAction.DOWN) {
			this.menuSound.play();
			this.nextGameOption();
		}
	}
}