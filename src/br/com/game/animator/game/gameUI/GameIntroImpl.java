package br.com.game.animator.game.gameUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.game.animator.util.ImageUtil;

/**
 * @author Jo�o Paulo
 */
public class GameIntroImpl implements GameIntro {
	
	//----------------------------------------------------------------------//
	//--------   PROPRIEDADES 				--------------------------------//
	//----------------------------------------------------------------------//
	private Integer subIntroCounter						= 0;
	private Integer PWIDTH								= null;
	private Integer PHEIGHT								= null;
	private ImageUtil imageUtil							= null;
	
	//----------------------------------------------------------------------//
	//--------   SUB INTRO IMAGES			--------------------------------//
	//----------------------------------------------------------------------//
	private BufferedImage logoPart1						= null;
	private BufferedImage logoPart2 					= null;
	private BufferedImage logoPart3 					= null;
	private BufferedImage logoPart4 					= null;
	private BufferedImage logoPart5 					= null;
	private BufferedImage buttonPressStart				= null;
	private Integer logoPart1PositionX					= null;
	private Integer logoPart1PositionY					= null;
	private Integer logoPart2PositionX					= null;
	private Integer logoPart2PositionY					= null;
	private Integer logoPart3PositionX					= null;
	private Integer logoPart3PositionY					= null;
	private Integer logoPart4PositionX					= null;
	private Integer logoPart4PositionY					= null;
	private Integer logoPart5PositionX					= null;
	private Integer logoPart5PositionY					= null;
	private Integer logoPart6PositionX					= null;
	private Integer logoPart6PositionY					= null;
	
	//----------------------------------------------------------------------//
	//--------   PROPRIEDADES SUB INTRO ------------------------------------//
	//----------------------------------------------------------------------//
	private int calcP1x									= 0;
	private int calcP2x									= 0;
	private float alpha1								= 0;
	private int scale4factor							= 10;
	private int calcP5y									= 0;
	private int startTimer								= 0;
	private volatile boolean subIntroFinished 			= false;
	private final static Integer LOGO_P1_POSITION_X 	= 459;
	private final static Integer LOGO_P1_POSITION_Y 	= 295;
	private final static Integer LOGO_P2_POSITION_X 	= 584;
	private final static Integer LOGO_P2_POSITION_Y 	= 295;
	private final static Integer LOGO_P3_POSITION_X 	= 675;
	private final static Integer LOGO_P3_POSITION_Y 	= 363;
	private final static Integer LOGO_P4_POSITION_X 	= 1000;
	private final static Integer LOGO_P4_POSITION_Y 	= 301;
	private final static Integer LOGO_P5_POSITION_X 	= 763;
	private final static Integer LOGO_P5_POSITION_Y 	= 494;
	private final static Integer LOGO_P6_POSITION_Y 	= 850;

	//----------------------------------------------------------------------//
	//--------    INTRO IMAGES				--------------------------------//
	//----------------------------------------------------------------------//
	private BufferedImage transparenceTop				= null;
	private BufferedImage transparenceBase				= null;
	private BufferedImage introText1 					= null;
	private BufferedImage introText2 					= null;
	private Integer introText1PositionX					= null;
	private Integer introText1PositionY					= null;
	private Integer introText2PositionX					= null;
	private Integer introText2PositionY					= null;
	private Integer transTopPositionX					= null;
	private Integer transTopPositionY					= null;
	private Integer transBasePositionX					= null;
	private Integer transBasePositionY					= null;
	private Integer transTopPositionWidth				= null;
	private Integer transTopPositionHeight				= null;
	private Integer transBasePositionWidth				= null;
	private Integer transBasePositionHeight				= null;
	
	//----------------------------------------------------------------------//
	//--------   PROPRIEDADES INTRO 	------------------------------------//
	//----------------------------------------------------------------------//
	private volatile boolean introFinished 				= false;
	private float calcText1y							= 0f;
	private float calcText2y							= 0f;
	
	/**
	 * Construtor padr�o
	 */
	public GameIntroImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 			= pwidth;
		this.PHEIGHT 			= pheight;
		this.imageUtil 			= new ImageUtil(pwidth, pheight, currentAspectRatio);
		
		//----------------------------------------------------------------------//
		//--------   SUBINTRO 				------------------------------------//
		//----------------------------------------------------------------------//
		this.logoPart1 			= this.imageUtil.loadScaledImage("/res/images/logo_p1.png");
		this.logoPart2 			= this.imageUtil.loadScaledImage("/res/images/logo_p2.png");
		this.logoPart3 			= this.imageUtil.loadScaledImage("/res/images/logo_p3.png");
		this.logoPart4 			= this.imageUtil.loadScaledImage("/res/images/logo_p4.png");
		this.logoPart5 			= this.imageUtil.loadScaledImage("/res/images/logo_p5.png");
		this.buttonPressStart	= this.imageUtil.loadScaledImage("/res/images/bt_press.png");
		
		this.logoPart1PositionX	= this.imageUtil.getScaledWidthForPosition(LOGO_P1_POSITION_X);
		this.logoPart1PositionY	= this.imageUtil.getScaledHeight(LOGO_P1_POSITION_Y);
		this.logoPart2PositionX	= this.imageUtil.getScaledWidthForPosition(LOGO_P2_POSITION_X);
		this.logoPart2PositionY	= this.imageUtil.getScaledHeight(LOGO_P2_POSITION_Y);
		this.logoPart3PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P3_POSITION_X);
		this.logoPart3PositionY	= this.imageUtil.getScaledHeight(LOGO_P3_POSITION_Y);
		this.logoPart4PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P4_POSITION_X);
		this.logoPart4PositionY	= this.imageUtil.getScaledHeight(LOGO_P4_POSITION_Y);
		this.logoPart5PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P5_POSITION_X);
		this.logoPart5PositionY	= this.imageUtil.getScaledHeight(LOGO_P5_POSITION_Y);
		this.logoPart6PositionX = (this.PWIDTH / 2) - (this.imageUtil.getScaledWidthForPosition(this.buttonPressStart.getWidth()) / 2);
		this.logoPart6PositionY	= this.imageUtil.getScaledHeight(LOGO_P6_POSITION_Y);
		this.calcP2x			= this.PWIDTH;
		this.calcP5y			= this.PHEIGHT;
		
		//----------------------------------------------------------------------//
		//--------   INTRO 					------------------------------------//
		//----------------------------------------------------------------------//
		this.transparenceTop			= this.imageUtil.loadScaledImage("/res/images/transparence_intro_top.png");
		this.transparenceBase			= this.imageUtil.loadScaledImage("/res/images/transparence_intro_bottom.png");
		this.introText1 				= this.imageUtil.loadScaledImage("/res/images/text_intro_1.png");
		this.introText2 				= this.imageUtil.loadScaledImage("/res/images/text_intro_2.png");
		this.calcText1y					= -this.PHEIGHT;
		this.calcText2y					= -this.PHEIGHT;
		this.introText1PositionX		= (int)((this.PWIDTH / 2) - (this.introText1.getWidth() / 2));
		this.introText1PositionY		= this.introText1.getHeight();
		this.introText2PositionX		= (int)((this.PWIDTH / 2) - (this.introText2.getWidth() / 2));
		this.introText2PositionY		= this.introText2.getHeight();
		this.transTopPositionX			= (int)((this.PWIDTH / 2) - (this.transparenceTop.getWidth() / 2));
		this.transTopPositionY			= 0;
		this.transBasePositionX			= (int)((this.PWIDTH / 2) - (this.transparenceBase.getWidth() / 2));
		this.transBasePositionY			= this.PHEIGHT - this.transparenceBase.getHeight();
		this.transTopPositionWidth		= this.transparenceTop.getWidth();
		this.transTopPositionHeight		= this.transparenceTop.getHeight();
		this.transBasePositionWidth		= this.transparenceBase.getWidth();
		this.transBasePositionHeight	= this.transparenceBase.getHeight();
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#updateSubIntro()
	 */
	public void updateSubIntro() {
		//---------------------------------------------------------//
		//--- Mant�m o counter atualizado a cada chamada do 	---//
		//--- update. 											---//
		//---------------------------------------------------------//
		this.subIntroCounter++;
		
		//----------------------------------------------------------------------//
		if (this.calcP1x < this.logoPart1PositionX) {
			this.calcP1x = this.calcP1x + 8;
		} if (this.calcP1x > this.logoPart1PositionX) {
			this.calcP1x = this.logoPart1PositionX;
		}
		
		//----------------------------------------------------------------------//
		if (this.calcP2x > this.logoPart2PositionX) {
			this.calcP2x = this.calcP2x - 25;
		} if (this.calcP2x < this.logoPart2PositionX) {
			this.calcP2x = this.logoPart2PositionX;
		}
		//----------------------------------------------------------------------//
		
		if (this.calcP2x == this.logoPart2PositionX) {
			if (this.alpha1 < 1) {
				this.alpha1 = this.alpha1 + 0.04f;
				this.alpha1 = (this.alpha1 > 1)?1:this.alpha1; 
			}
		}
		
		//----------------------------------------------------------------------//
		if (this.calcP2x == this.logoPart2PositionX && this.alpha1 == 1 && this.scale4factor <= 7) {
			if (this.calcP5y > this.logoPart2PositionY) {
				this.calcP5y = this.calcP5y - 20;
			} if (this.calcP5y < this.logoPart5PositionY) {
				this.calcP5y = this.logoPart5PositionY;
			}
		}
		
		//----------------------------------------------------------------------//
		if (this.calcP2x == this.logoPart2PositionX && this.alpha1 == 1 && this.scale4factor <= 7 && this.calcP5y == this.logoPart5PositionY) {
			this.startTimer = (this.startTimer + 1) % 70;
		}
		//----------------------------------------------------------------------//
		
		if (this.subIntroCounter >= MAX_COUNTER_SUBINTRO_VALUE) {
			this.subIntroFinished = true;
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#drawSubIntro(java.awt.Graphics2D)
	 */
	public void drawSubIntro(Graphics2D g2d) {
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
		
		//----------------------------------------------------------------------//
		if (this.calcP2x == this.logoPart2PositionX) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha1));
			g2d.drawImage(this.logoPart3,
						  this.logoPart3PositionX,
						  this.logoPart3PositionY,
						  this.logoPart3.getWidth(),
						  this.logoPart3.getHeight(),
						  null);
			
			//----------------------------------------------------------------------//
			if (this.alpha1 == 1) {
				this.scale4factor = this.imageUtil.drawZoomInImage(g2d, 
											   					   this.logoPart4, 
											   					   this.logoPart4PositionX, 
											   					   this.logoPart4PositionY, 
											   					   this.scale4factor);
				//----------------------------------------------------------------------//
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
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#updateIntro()
	 */
	public void updateIntro() {
		float textSpeedy = 1f;
		
		//----------------------------------------------------------------------//
		if (this.calcText1y < this.introText1PositionY) {
			this.calcText1y = this.calcText1y + textSpeedy;
		} if (this.calcText1y > this.introText1PositionY) {
			this.calcText1y = this.introText1PositionY;
		}
		//----------------------------------------------------------------------//
		
		if (this.calcText1y >= (this.introText1PositionY - (this.PHEIGHT / 2))) {
			if (this.calcText2y < this.introText2PositionY) {
				this.calcText2y = this.calcText2y + textSpeedy;
			} if (this.calcText2y > this.introText2PositionY) {
				this.calcText2y = this.introText2PositionY;
			}
		}
		
		if (this.calcText2y == this.introText2PositionY) {
			this.introFinished = true;
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#drawIntro(java.awt.Graphics2D)
	 */
	public void drawIntro(Graphics2D g2d) {
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.PWIDTH, this.PHEIGHT);
		
		g2d.drawImage(this.introText1,
					  this.introText1PositionX, 									//destino X0
					  0, 															//destino Y0
					  this.introText1PositionX + this.introText1.getWidth(), 		//destino X1
					  this.PHEIGHT, 												//destino Y1
					  0, 															//source X0
					  (int)this.calcText1y,											//source Y0
					  this.introText1.getWidth(),  									//source X1
					  (int)this.calcText1y + this.PHEIGHT, 							//source Y1
					  null);
		
		if (this.calcText1y >= (this.introText1PositionY - (this.PHEIGHT / 2))) {
			g2d.drawImage(this.introText2,
						  this.introText2PositionX, 								//destino X0
						  0, 														//destino Y0
						  this.introText2PositionX + this.introText2.getWidth(), 	//destino X1
						  this.PHEIGHT, 											//destino Y1
						  0, 														//source X0
						  (int)this.calcText2y,										//source Y0
						  this.introText2.getWidth(),  								//source X1
						  (int)this.calcText2y + this.PHEIGHT, 						//source Y1
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
		
		/*
		
				g2d.drawImage(this.introText1, 
					  this.introText1PositionX, 
					  (int)this.calcText1y, 
					  this.introText1.getWidth(), 
				  	this.introText1.getHeight(),
					  null);
		
		if (this.calcText1y <= (this.introText1PositionY + (this.PHEIGHT / 2))) {
			g2d.drawImage(this.introText2, 
						  this.introText2PositionX, 
						  (int)this.calcText2y, 
						  this.introText2.getWidth(), 
						  this.introText2.getHeight(), 
						  null);
		}
		
		
		
		
		
					  
					  */
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#subIntroFinished()
	 */
	public boolean subIntroFinished() {
		return (this.subIntroFinished);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#introFinished()
	 */
	public boolean introFinished() {
		return (this.introFinished);
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#updateCanvasPropertiesSubIntro(boolean, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public void updateCanvasPropertiesSubIntro(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 	= pwidth;
		this.PHEIGHT 	= pheight;
		this.calcP1x	= 0;
		this.calcP2x	= this.PWIDTH;
		this.calcP5y	= this.PHEIGHT;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);
		
		this.logoPart1PositionX	= this.imageUtil.getScaledWidthForPosition(LOGO_P1_POSITION_X);
		this.logoPart1PositionY	= this.imageUtil.getScaledHeight(LOGO_P1_POSITION_Y);
		this.logoPart2PositionX	= this.imageUtil.getScaledWidthForPosition(LOGO_P2_POSITION_X);
		this.logoPart2PositionY	= this.imageUtil.getScaledHeight(LOGO_P2_POSITION_Y);
		this.logoPart3PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P3_POSITION_X);
		this.logoPart3PositionY	= this.imageUtil.getScaledHeight(LOGO_P3_POSITION_Y);
		this.logoPart4PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P4_POSITION_X);
		this.logoPart4PositionY	= this.imageUtil.getScaledHeight(LOGO_P4_POSITION_Y);
		this.logoPart5PositionX = this.imageUtil.getScaledWidthForPosition(LOGO_P5_POSITION_X);
		this.logoPart5PositionY	= this.imageUtil.getScaledHeight(LOGO_P5_POSITION_Y);
		this.logoPart6PositionX = (this.PWIDTH / 2) - (this.imageUtil.getScaledWidthForPosition(this.buttonPressStart.getWidth()) / 2);
		this.logoPart6PositionY	= this.imageUtil.getScaledHeight(LOGO_P6_POSITION_Y);
		
		this.logoPart1 			= this.imageUtil.loadScaledImage("/res/images/logo_p1.png");
		this.logoPart2 			= this.imageUtil.loadScaledImage("/res/images/logo_p2.png");
		this.logoPart3 			= this.imageUtil.loadScaledImage("/res/images/logo_p3.png");
		this.logoPart4 			= this.imageUtil.loadScaledImage("/res/images/logo_p4.png");
		this.logoPart5 			= this.imageUtil.loadScaledImage("/res/images/logo_p5.png");
		this.buttonPressStart	= this.imageUtil.loadScaledImage("/res/images/bt_press.png");
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#updateCanvasProperties(boolean, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public void updateCanvasProperties(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 					= pwidth;
		this.PHEIGHT 					= pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);
		
		this.transparenceTop			= this.imageUtil.loadScaledImage("/res/images/transparence_intro_top.png");
		this.transparenceBase			= this.imageUtil.loadScaledImage("/res/images/transparence_intro_bottom.png");
		this.introText1 				= this.imageUtil.loadScaledImage("/res/images/text_intro_1.png");
		this.introText2 				= this.imageUtil.loadScaledImage("/res/images/text_intro_2.png");
		
		this.introText1PositionX		= (int)((this.PWIDTH / 2) - (this.introText1.getWidth() / 2));
		this.introText1PositionY		= this.introText1.getHeight();
		this.introText2PositionX		= (int)((this.PWIDTH / 2) - (this.introText2.getWidth() / 2));
		this.introText2PositionY		= this.introText2.getHeight();
		this.transTopPositionX			= (int)((this.PWIDTH / 2) - (this.transparenceTop.getWidth() / 2));
		this.transTopPositionY			= 0;
		this.transBasePositionX			= (int)((this.PWIDTH / 2) - (this.transparenceBase.getWidth() / 2));
		this.transBasePositionY			= this.PHEIGHT - this.transparenceBase.getHeight();
		this.transTopPositionWidth		= this.transparenceTop.getWidth();
		this.transTopPositionHeight		= this.transparenceTop.getHeight();
		this.transBasePositionWidth		= this.transparenceBase.getWidth();
		this.transBasePositionHeight	= this.transparenceBase.getHeight();
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameUI.GameIntro#resetCounters()
	 */
	public void resetCounters() {
		this.subIntroCounter	= 0;
		this.subIntroFinished 	= false;
		this.introFinished		= false;
		this.calcText1y			= -this.PHEIGHT;
		this.calcText2y			= -this.PHEIGHT;
	}
}