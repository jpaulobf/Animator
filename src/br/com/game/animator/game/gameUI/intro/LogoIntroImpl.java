package br.com.game.animator.game.gameUI.intro;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import br.com.game.animator.game.Game;
import br.com.game.animator.util.ImageUtil;

/**
 * @author João Paulo Faria
 */
public class LogoIntroImpl implements LogoIntro {

	// --- Properties
	private Integer subIntroCounter = 0;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	private ImageUtil imageUtil = null;

	// -------- SUB INTRO IMAGES
	private BufferedImage logoPart1 = null;
	private BufferedImage logoPart2 = null;
	private BufferedImage logoPart3 = null;
	private BufferedImage logoPart4 = null;
	private BufferedImage logoPart5 = null;
	private BufferedImage buttonPressStart = null;
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

	// -------- SUB INTRO PROPERTIES
	private int calcP1x = 0;
	private int calcP2x = 0;
	private float alpha1 = 0;
	private int scale4factor = 10;
	private int calcP5y = 0;
	private int startTimer = 0;
	private volatile boolean subIntroFinished = false;
	private final static Integer LOGO_P1_POSITION_X = 459;
	private final static Integer LOGO_P1_POSITION_Y = 295;
	private final static Integer LOGO_P2_POSITION_X = 584;
	private final static Integer LOGO_P2_POSITION_Y = 295;
	private final static Integer LOGO_P3_POSITION_X = 675;
	private final static Integer LOGO_P3_POSITION_Y = 363;
	private final static Integer LOGO_P4_POSITION_X = 1000;
	private final static Integer LOGO_P4_POSITION_Y = 301;
	private final static Integer LOGO_P5_POSITION_X = 763;
	private final static Integer LOGO_P5_POSITION_Y = 494;
	private final static Integer LOGO_P6_POSITION_Y = 850;

	/**
	 * Constructor
	 * 
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public LogoIntroImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);

		// -------- SUBINTRO ------------------------------------//
		this.logoPart1 = this.imageUtil.loadScaledImage("/res/images/logo_p1.png");
		this.logoPart2 = this.imageUtil.loadScaledImage("/res/images/logo_p2.png");
		this.logoPart3 = this.imageUtil.loadScaledImage("/res/images/logo_p3.png");
		this.logoPart4 = this.imageUtil.loadScaledImage("/res/images/logo_p4.png");
		this.logoPart5 = this.imageUtil.loadScaledImage("/res/images/logo_p5.png");
		this.buttonPressStart = this.imageUtil.loadScaledImage("/res/images/bt_press.png");

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
		this.logoPart6PositionX = (this.PWIDTH / 2)
				- (this.imageUtil.getScaledWidthForPosition(this.buttonPressStart.getWidth()) / 2);
		this.logoPart6PositionY = this.imageUtil.getScaledHeight(LOGO_P6_POSITION_Y);
		this.calcP2x = this.PWIDTH;
		this.calcP5y = this.PHEIGHT;
	}

	public void update(long deltaTime) {
		this.subIntroCounter++;

		if (this.calcP1x < this.logoPart1PositionX) {
			this.calcP1x = this.calcP1x + 8;
		}
		if (this.calcP1x > this.logoPart1PositionX) {
			this.calcP1x = this.logoPart1PositionX;
		}

		if (this.calcP2x > this.logoPart2PositionX) {
			this.calcP2x = this.calcP2x - 25;
		}
		if (this.calcP2x < this.logoPart2PositionX) {
			this.calcP2x = this.logoPart2PositionX;
		}

		if (this.calcP2x == this.logoPart2PositionX) {
			if (this.alpha1 < 1) {
				this.alpha1 = this.alpha1 + 0.04f;
				this.alpha1 = (this.alpha1 > 1) ? 1 : this.alpha1;
			}
		}

		if (this.calcP2x == this.logoPart2PositionX && this.alpha1 == 1 && this.scale4factor <= 7) {
			if (this.calcP5y > this.logoPart2PositionY) {
				this.calcP5y = this.calcP5y - 20;
			}
			if (this.calcP5y < this.logoPart5PositionY) {
				this.calcP5y = this.logoPart5PositionY;
			}
		}

		if (this.calcP2x == this.logoPart2PositionX && this.alpha1 == 1 && this.scale4factor <= 7
				&& this.calcP5y == this.logoPart5PositionY) {
			this.startTimer = (this.startTimer + 1) % 70;
		}

		if (this.subIntroCounter >= MAX_COUNTER_SUBINTRO_VALUE) {
			this.subIntroFinished = true;
		}
	}

	/**
	 * Draw the intro. This method is called by the game loop.
	 */
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, PWIDTH, PHEIGHT);
		g2d.setColor(Color.BLACK);

		g2d.drawImage(this.logoPart1,
				this.calcP1x,
				this.logoPart1PositionY,
				this.logoPart1.getWidth(),
				this.logoPart1.getHeight(),
				null);

		g2d.drawImage(this.logoPart2,
				this.calcP2x,
				this.logoPart2PositionY,
				this.logoPart2.getWidth(),
				this.logoPart2.getHeight(),
				null);

		if (this.calcP2x == this.logoPart2PositionX) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha1));
			g2d.drawImage(this.logoPart3,
					this.logoPart3PositionX,
					this.logoPart3PositionY,
					this.logoPart3.getWidth(),
					this.logoPart3.getHeight(),
					null);

			if (this.alpha1 == 1) {
				this.scale4factor = this.imageUtil.drawZoomInImage(g2d,
						this.logoPart4,
						this.logoPart4PositionX,
						this.logoPart4PositionY,
						this.scale4factor);
				if (this.scale4factor <= 7) {
					g2d.drawImage(this.logoPart5,
							this.logoPart5PositionX,
							this.calcP5y,
							this.logoPart5.getWidth(),
							this.logoPart5.getHeight(),
							null);

					if (this.calcP5y == this.logoPart5PositionY && this.startTimer >= 0 && this.startTimer <= 40) {
						g2d.drawImage(this.buttonPressStart,
								this.logoPart6PositionX,
								this.logoPart6PositionY,
								this.buttonPressStart.getWidth(),
								this.buttonPressStart.getHeight(),
								null);
					}
				}
			}
		}
	}

	/**
	 * Check if the intro is finished.
	 * 
	 * @return true if the intro is finished, false otherwise
	 */
	public boolean finished() {
		return (this.subIntroFinished);
	}

	/**
	 * Update the graphics of the intro. This method is called by the game loop when
	 * the screen size is changed.
	 * 
	 * @param fullScreen
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.calcP1x = 0;
		this.calcP2x = this.PWIDTH;
		this.calcP5y = this.PHEIGHT;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);

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
		this.logoPart6PositionX = (this.PWIDTH / 2)
				- (this.imageUtil.getScaledWidthForPosition(this.buttonPressStart.getWidth()) / 2);
		this.logoPart6PositionY = this.imageUtil.getScaledHeight(LOGO_P6_POSITION_Y);

		this.logoPart1 = this.imageUtil.loadScaledImage("/res/images/logo_p1.png");
		this.logoPart2 = this.imageUtil.loadScaledImage("/res/images/logo_p2.png");
		this.logoPart3 = this.imageUtil.loadScaledImage("/res/images/logo_p3.png");
		this.logoPart4 = this.imageUtil.loadScaledImage("/res/images/logo_p4.png");
		this.logoPart5 = this.imageUtil.loadScaledImage("/res/images/logo_p5.png");
		this.buttonPressStart = this.imageUtil.loadScaledImage("/res/images/bt_press.png");
	}

	/**
	 * Reset the counters of the intro. This method is called by the game loop when
	 * the intro is finished and the game is restarted.
	 */
	public void resetCounters() {
		this.subIntroCounter = 0;
		this.subIntroFinished = false;
	}

	@Override
	public void handleInput(Game game, int keyCode, boolean isAltDown) {
		//todo
	}
}