package br.com.animator.game.gameUI.intro;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.animator.game.Game;
import br.com.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 */
public class GameIntroImpl implements GameIntro {

	// --- Properties
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	private ImageUtil imageUtil = null;

	// -------- INTRO IMAGES
	private BufferedImage transparenceTop = null;
	private BufferedImage transparenceBase = null;
	private BufferedImage introText1 = null;
	private BufferedImage introText2 = null;
	private Integer introText1PositionX = null;
	private Integer introText1PositionY = null;
	private Integer introText2PositionX = null;
	private Integer introText2PositionY = null;
	private Integer transTopPositionX = null;
	private Integer transTopPositionY = null;
	private Integer transBasePositionX = null;
	private Integer transBasePositionY = null;
	private Integer transTopPositionWidth = null;
	private Integer transTopPositionHeight = null;
	private Integer transBasePositionWidth = null;
	private Integer transBasePositionHeight = null;
	private volatile boolean introFinished = false;
	private float calcText1y = 0f;
	private float calcText2y = 0f;

	/**
	 * Constructor
	 * 
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public GameIntroImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);

		// -------- INTRO ------------------------------------//
		this.transparenceTop = this.imageUtil.loadScaledImage("/images/transparence_intro_top.png");
		this.transparenceBase = this.imageUtil.loadScaledImage("/images/transparence_intro_bottom.png");
		this.introText1 = this.imageUtil.loadScaledImage("/images/text_intro_1.png");
		this.introText2 = this.imageUtil.loadScaledImage("/images/text_intro_2.png");
		this.calcText1y = -this.PHEIGHT;
		this.calcText2y = -this.PHEIGHT;
		this.introText1PositionX = (int) ((this.PWIDTH / 2) - (this.introText1.getWidth() / 2));
		this.introText1PositionY = this.introText1.getHeight();
		this.introText2PositionX = (int) ((this.PWIDTH / 2) - (this.introText2.getWidth() / 2));
		this.introText2PositionY = this.introText2.getHeight();
		this.transTopPositionX = (int) ((this.PWIDTH / 2) - (this.transparenceTop.getWidth() / 2));
		this.transTopPositionY = 0;
		this.transBasePositionX = (int) ((this.PWIDTH / 2) - (this.transparenceBase.getWidth() / 2));
		this.transBasePositionY = this.PHEIGHT - this.transparenceBase.getHeight();
		this.transTopPositionWidth = this.transparenceTop.getWidth();
		this.transTopPositionHeight = this.transparenceTop.getHeight();
		this.transBasePositionWidth = this.transparenceBase.getWidth();
		this.transBasePositionHeight = this.transparenceBase.getHeight();
	}

	/**
	 * Update the intro logic. This method is called by the game loop.
	 */
	public void update(long deltaTime) {
		float textSpeedy = 1f;

		if (this.calcText1y < this.introText1PositionY) {
			this.calcText1y = this.calcText1y + textSpeedy;
		}
		if (this.calcText1y > this.introText1PositionY) {
			this.calcText1y = this.introText1PositionY;
		}

		if (this.calcText1y >= (this.introText1PositionY - (this.PHEIGHT / 2))) {
			if (this.calcText2y < this.introText2PositionY) {
				this.calcText2y = this.calcText2y + textSpeedy;
			}
			if (this.calcText2y > this.introText2PositionY) {
				this.calcText2y = this.introText2PositionY;
			}
		}

		if (this.calcText2y == this.introText2PositionY) {
			this.introFinished = true;
		}
	}

	/**
	 * Draw the intro. This method is called by the game loop.
	 */
	public void draw(Graphics2D g2d) {

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.PWIDTH, this.PHEIGHT);

		g2d.drawImage(this.introText1,
				this.introText1PositionX, // destination X0
				0, // destination Y0
				this.introText1PositionX + this.introText1.getWidth(), // destination X1
				this.PHEIGHT, // destination Y1
				0, // source X0
				(int) this.calcText1y, // source Y0
				this.introText1.getWidth(), // source X1
				(int) this.calcText1y + this.PHEIGHT, // source Y1
				null);

		if (this.calcText1y >= (this.introText1PositionY - (this.PHEIGHT / 2))) {
			g2d.drawImage(this.introText2,
					this.introText2PositionX, // destination X0
					0, // destination Y0
					this.introText2PositionX + this.introText2.getWidth(), // destination X1
					this.PHEIGHT, // destination Y1
					0, // source X0
					(int) this.calcText2y, // source Y0
					this.introText2.getWidth(), // source X1
					(int) this.calcText2y + this.PHEIGHT, // source Y1
					null);
		}

		g2d.drawImage(this.transparenceTop,
				this.transTopPositionX,
				this.transTopPositionY,
				this.transTopPositionWidth,
				this.transTopPositionHeight,
				null);

		g2d.drawImage(this.transparenceBase,
				this.transBasePositionX,
				this.transBasePositionY,
				this.transBasePositionWidth,
				this.transBasePositionHeight,
				null);
	}

	/**
	 * Check if the intro is finished.
	 * 
	 * @return true if the intro is finished, false otherwise
	 */
	public boolean finished() {
		return (this.introFinished);
	}

	/**
	 * Update the intro graphics. This method is called by the game loop.
	 * 
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);

		this.transparenceTop = this.imageUtil.loadScaledImage("/images/transparence_intro_top.png");
		this.transparenceBase = this.imageUtil.loadScaledImage("/images/transparence_intro_bottom.png");
		this.introText1 = this.imageUtil.loadScaledImage("/images/text_intro_1.png");
		this.introText2 = this.imageUtil.loadScaledImage("/images/text_intro_2.png");

		this.introText1PositionX = (int) ((this.PWIDTH / 2) - (this.introText1.getWidth() / 2));
		this.introText1PositionY = this.introText1.getHeight();
		this.introText2PositionX = (int) ((this.PWIDTH / 2) - (this.introText2.getWidth() / 2));
		this.introText2PositionY = this.introText2.getHeight();
		this.transTopPositionX = (int) ((this.PWIDTH / 2) - (this.transparenceTop.getWidth() / 2));
		this.transTopPositionY = 0;
		this.transBasePositionX = (int) ((this.PWIDTH / 2) - (this.transparenceBase.getWidth() / 2));
		this.transBasePositionY = this.PHEIGHT - this.transparenceBase.getHeight();
		this.transTopPositionWidth = this.transparenceTop.getWidth();
		this.transTopPositionHeight = this.transparenceTop.getHeight();
		this.transBasePositionWidth = this.transparenceBase.getWidth();
		this.transBasePositionHeight = this.transparenceBase.getHeight();
	}

	/**
	 * Reset the intro counters to start the intro again.
	 */
	public void resetCounters() {
		this.introFinished = false;
		this.calcText1y = -this.PHEIGHT;
		this.calcText2y = -this.PHEIGHT;
	}

	@Override
	public void handleInput(Game game, int keyCode, boolean isAltDown) {
		//todo
	}
}