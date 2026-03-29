package br.com.game.animator.game.gameUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.game.animator.util.ImageUtil;

/**
 * @author Jo�o Paulo
 * @description Classe respons�vel pela exibi��o de propagandas do developer. 
 * 				Deve respeitar os m�todos da Interface.
 */
public class DeveloperAdvertiseImpl implements DeveloperAdvertise {

	//----------------------------------------------------------------------//
	//--------   PROPRIEDADES 				--------------------------------//
	//----------------------------------------------------------------------//
	private Integer mainCounter		= 0;
	private BufferedImage devLogo	= null;
	private Integer PWIDTH			= null;
	private Integer PHEIGHT			= null;
	private ImageUtil imageUtil		= null;
	private float alpha				= 0f;
	private Integer positionX		= 0;
	private Integer positionY		= 0;
	private Integer imageWidth		= 0;
	private Integer imageHeight		= 0;
	
	/**
	 * Construtor.
	 */
	public DeveloperAdvertiseImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 		= pwidth;
		this.PHEIGHT 		= pheight;
		this.imageUtil 		= new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.devLogo 		= this.imageUtil.loadScaledImage("/res/images/logo_phoenix.png");
		this.imageWidth		= this.devLogo.getWidth();
		this.imageHeight	= this.devLogo.getHeight();
		this.positionX		= (this.PWIDTH / 2) - (this.imageWidth / 2);
		this.positionY		= (this.PHEIGHT / 2) - (this.imageHeight / 2);
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.DeveloperAdvertise#update()
	 */
	public void update() {
		//---------------------------------------------------------//
		//--- Mant�m o counter atualizado a cada chamada do 	---//
		//--- update. 											---//
		//---------------------------------------------------------//
		this.mainCounter++;
		int factor = 60;
		
		if (this.mainCounter <= MAX_COUNTER_VALUE) {
			if (this.mainCounter >= (factor * 0.4)) {
				if (this.mainCounter <= factor * 2.5) {
					this.alpha = (float)this.mainCounter / (float)(factor * 2.5);
				} else if (this.mainCounter > factor * 2.5 && this.mainCounter <= factor * 4) {
					this.alpha = 1;
				} else if (this.mainCounter > factor * 4 && this.mainCounter <= factor * 5) {
					this.alpha = ((float)(100f / (float)factor) * (factor * 5f - this.mainCounter))/100f;
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.DeveloperAdvertise#draw(java.awt.Graphics2D)
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
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.DeveloperAdvertise#updateCanvasProperties(boolean, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public void updateCanvasProperties(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 		= pwidth;
		this.PHEIGHT 		= pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);
		this.devLogo 		= this.imageUtil.loadScaledImage("/res/images/logo_phoenix.png");
		this.imageWidth		= this.devLogo.getWidth();
		this.imageHeight	= this.devLogo.getHeight();
		this.positionX		= (this.PWIDTH / 2) - (this.imageWidth / 2);
		this.positionY		= (this.PHEIGHT / 2) - (this.imageHeight / 2);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.DeveloperAdvertise#resetCounters()
	 */
	public void resetCounters() {
		this.mainCounter = 0; 
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.DeveloperAdvertise#developerLogoPresentationFinished()
	 */
	public boolean developerLogoPresentationFinished() {
		return (mainCounter >= MAX_COUNTER_VALUE);
	}
}