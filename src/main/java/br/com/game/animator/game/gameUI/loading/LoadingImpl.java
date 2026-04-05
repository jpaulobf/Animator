package br.com.game.animator.game.gameUI.loading;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import br.com.game.animator.game.Game;
import br.com.game.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 */
public class LoadingImpl implements Loading {

	// --- Properties
	private Integer loadingCounter = 0;
	private BufferedImage loading1 = null;
	private BufferedImage loading2 = null;
	private BufferedImage loading3 = null;
	private BufferedImage loading4 = null;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	private ImageUtil imageUtil = null;
	private Integer positionX = 0;
	private Integer positionY = 0;
	private Integer imageWidth = 0;
	private Integer imageHeight = 0;
	private Integer currentStatus = 0;

	/**
	 * Constructor
	 * 
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public LoadingImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.loading1 = this.imageUtil.loadScaledImage("/images/loading_1a.png");
		this.loading2 = this.imageUtil.loadScaledImage("/images/loading_2a.png");
		this.loading3 = this.imageUtil.loadScaledImage("/images/loading_3a.png");
		this.loading4 = this.imageUtil.loadScaledImage("/images/loading_4a.png");
		this.imageWidth = this.loading1.getWidth();
		this.imageHeight = this.loading1.getHeight();
		this.positionX = (this.PWIDTH / 2) - (this.imageWidth / 2);
		this.positionY = (this.PHEIGHT / 2) - (this.imageHeight / 2);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, this.PWIDTH, this.PHEIGHT);

		if (this.currentStatus == 0) {
			g2d.drawImage(this.loading1,
					this.positionX,
					this.positionY,
					this.imageWidth,
					this.imageHeight,
					null);
		} else if (this.currentStatus == 1) {
			g2d.drawImage(this.loading2,
					this.positionX,
					this.positionY,
					this.imageWidth,
					this.imageHeight,
					null);
		} else if (this.currentStatus == 2) {
			g2d.drawImage(this.loading3,
					this.positionX,
					this.positionY,
					this.imageWidth,
					this.imageHeight,
					null);
		} else {
			g2d.drawImage(this.loading4,
					this.positionX,
					this.positionY,
					this.imageWidth,
					this.imageHeight,
					null);
		}
	}

	public void update(long frametime) {
		this.loadingCounter = (this.loadingCounter + 2) % 400;
		if (this.loadingCounter >= 0 && this.loadingCounter < 100) {
			this.currentStatus = 0;
		} else if (this.loadingCounter >= 100 && this.loadingCounter < 200) {
			this.currentStatus = 1;
		} else if (this.loadingCounter >= 200 && this.loadingCounter < 300) {
			this.currentStatus = 2;
		} else {
			this.currentStatus = 3;
		}
	}

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.positionX = (this.PWIDTH / 2) - (this.imageWidth / 2);
		this.positionY = (this.PHEIGHT / 2) - (this.imageHeight / 2);
	}

	public void resetCounters() {
		this.loadingCounter = 0;
	}

	public boolean finished() {
		return false;
	}

	@Override
	public void handleInput(Game game, int keyCode, boolean isAltDown) {
		//no input handling for loading screen
	}
}