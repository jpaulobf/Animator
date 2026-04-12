package br.com.animator.game.ui.advertise;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import br.com.animator.core.game.IGame;
import br.com.animator.input.GameAction;
import br.com.animator.ui.advertise.DeveloperAdvertise;
import br.com.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 */
public class DeveloperAdvertiseImpl implements DeveloperAdvertise {

	// --- Properties
	private Integer mainCounter = 0;
	private BufferedImage devLogo = null;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	private ImageUtil imageUtil = null;
	private float alpha = 0f;
	private Integer positionX = 0;
	private Integer positionY = 0;
	private Integer imageWidth = 0;
	private Integer imageHeight = 0;

	/**
	 * Constructor.
	 */
	public DeveloperAdvertiseImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.devLogo = this.imageUtil.loadScaledImage("DeveloperAdvertise.logo");
		this.imageWidth = this.devLogo.getWidth();
		this.imageHeight = this.devLogo.getHeight();
		this.positionX = (this.PWIDTH / 2) - (this.imageWidth / 2);
		this.positionY = (this.PHEIGHT / 2) - (this.imageHeight / 2);
	}

	/**
	 * Update the advertise animation.
	 * 
	 * @param frametime
	 */
	public void update(long frametime) {
		this.mainCounter++;
		int factor = 60;

		if (this.mainCounter <= MAX_COUNTER_VALUE) {
			if (this.mainCounter >= (factor * 0.4)) {
				if (this.mainCounter <= factor * 2.5) {
					this.alpha = (float) this.mainCounter / (float) (factor * 2.5);
				} else if (this.mainCounter > factor * 2.5 && this.mainCounter <= factor * 4) {
					this.alpha = 1;
				} else if (this.mainCounter > factor * 4 && this.mainCounter <= factor * 5) {
					this.alpha = ((float) (100f / (float) factor) * (factor * 5f - this.mainCounter)) / 100f;
				}
			}
		}
	}

	/**
	 * Draw the advertise animation.
	 */
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.PWIDTH, this.PHEIGHT);

		if (this.devLogo != null) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));
			g2d.drawImage(this.devLogo,
					this.positionX,
					this.positionY,
					this.imageWidth,
					this.imageHeight,
					null);
		}
	}

	/**
	 * Update the graphics properties when the screen size or aspect ratio changes.
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);
		this.devLogo = this.imageUtil.loadScaledImage("DeveloperAdvertise.logo");
		this.imageWidth = this.devLogo.getWidth();
		this.imageHeight = this.devLogo.getHeight();
		this.positionX = (this.PWIDTH / 2) - (this.imageWidth / 2);
		this.positionY = (this.PHEIGHT / 2) - (this.imageHeight / 2);
	}

	/**
	 * Reset the animation counters.
	 */
	public void resetCounters() {
		this.mainCounter = 0;
	}

	/**
	 * Check if the animation has finished.
	 */
	public boolean finished() {
		return (mainCounter >= MAX_COUNTER_VALUE);
	}

	@Override
	public void handleInput(IGame game, GameAction action) {
		//todo
	}
}