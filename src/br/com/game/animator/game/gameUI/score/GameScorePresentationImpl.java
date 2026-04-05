package br.com.game.animator.game.gameUI.score;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import br.com.game.animator.game.Game;
import br.com.game.animator.game.gameData.GameScore;
import br.com.game.animator.game.gameData.GameScoreImpl;
import br.com.game.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 */
public class GameScorePresentationImpl implements GameScorePresentation {
	
	//--- Properties
	private Integer hiScoreCounter 							= 0;
	private Integer PWIDTH									= null;
	private Integer PHEIGHT									= null;
	protected ImageUtil imageUtil							= null;
	protected String avatarName								= null;
	protected Long scoreValue								= null;
	
	//--- Constants
	protected final static Integer LOWER_W					= 30;
	protected final static Integer LOWER_Y					= 55;
	protected final static Integer UPPER_W					= 40;
	protected final static Integer UPPER_Y					= 55;
	protected final static Integer NUMBER_W					= 40;
	protected final static Integer NUMBER_Y					= 55;
	protected final static Integer HEIGHT_SPACE 			= 78;
	protected final static Integer NAME_SCORE_SPACE 		= 300;
	
	//--- Imagens for letters, numbers and logo.
	private BufferedImage [] upperLetter					= null;
	private BufferedImage [] lowerLetter					= null;
	private BufferedImage [] numbers						= null;
	private BufferedImage hallOfFameLogo					= null;
	private Map <String, BufferedImage> mapLetters			= null;
	
	//----------------------------------------------------------------------//
	// Support properties
	//----------------------------------------------------------------------//
	private GameScore gameScore								= null;

	//----------------------------------------------------------------------//
	// Zoom effect properties
	//----------------------------------------------------------------------//
	protected static final Integer DEFAULT_ZOOM_IN_SCALE	= 30;
	private Integer zoomInScaleHallLogo						= DEFAULT_ZOOM_IN_SCALE;
	
	//----------------------------------------------------------------------//
	// Score entry effect properties
	//----------------------------------------------------------------------//
	private Integer maxRankToShow							= 0;
	private Integer [] individualRankPosition				= new Integer[10];
	private Integer maxNickToShow							= 0;
	private Integer [] individualNickPosition				= new Integer[30];
	private Integer maxScoreToShow							= 0;
	private Integer [] individualScorePosition				= new Integer[30];
	boolean nickDone 										= false;
	boolean scoreDone 										= false;
	
	/**
	 * Constructor.
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public GameScorePresentationImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 				= pwidth;
		this.PHEIGHT 				= pheight;
		this.imageUtil 				= new ImageUtil(this.PWIDTH, this.PHEIGHT, currentAspectRatio);
		this.gameScore				= new GameScoreImpl();

		for (int cnt = 0; cnt < this.individualRankPosition.length; cnt++) {
			this.individualRankPosition[cnt] = 0;
		}
		for (int cnt = 0; cnt < this.individualNickPosition.length; cnt++) {
			this.individualNickPosition[cnt] = PWIDTH;
		}
		for (int cnt = 0; cnt < this.individualScorePosition.length; cnt++) {
			this.individualScorePosition[cnt] = PHEIGHT;
		}

		this.upperLetter			= new BufferedImage[26];
		this.lowerLetter			= new BufferedImage[26];
		this.numbers				= new BufferedImage[11];
		this.mapLetters				= new HashMap<String, BufferedImage>();
		this.hallOfFameLogo			= this.imageUtil.loadScaledImage("/res/images/hall.png");
		BufferedImage numbersImages	= this.imageUtil.loadImage("/res/images/numbers.png");
		BufferedImage lowersImages 	= this.imageUtil.loadImage("/res/images/letter_lower.png");
		BufferedImage uppersImages 	= this.imageUtil.loadImage("/res/images/letter_upper.png");
		BufferedImage copy 			= null;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration graphicsConfig = ge.getDefaultScreenDevice().getDefaultConfiguration();
		int transparency = numbersImages.getColorModel().getTransparency();
		for (int cnt = 0; cnt < 10; cnt++) {
			copy = graphicsConfig.createCompatibleImage(NUMBER_W, NUMBER_Y, transparency);
			copy.createGraphics().drawImage(numbersImages, 0, 0, NUMBER_W, NUMBER_Y, cnt * NUMBER_W, 0, (cnt * NUMBER_W) + NUMBER_W, NUMBER_Y, null);
			this.numbers[cnt] = copy;
		}

		copy = graphicsConfig.createCompatibleImage(2 * NUMBER_W, NUMBER_Y, transparency);
		copy.createGraphics().drawImage(numbersImages, 0, 0, NUMBER_W, NUMBER_Y, NUMBER_W, 0, (2 * NUMBER_W), NUMBER_Y, null);
		copy.createGraphics().drawImage(numbersImages, NUMBER_W, 0, 2 * NUMBER_W, NUMBER_Y, 0, 0, NUMBER_W, NUMBER_Y, null);
		this.numbers[10] = copy;

		transparency = lowersImages.getColorModel().getTransparency();
		for (int cnt = 0; cnt < 26; cnt++) {
			copy = graphicsConfig.createCompatibleImage(LOWER_W, LOWER_Y, transparency);
			copy.createGraphics().drawImage(lowersImages, 0, 0, LOWER_W, LOWER_Y, cnt * LOWER_W, 0, (cnt * LOWER_W) + LOWER_W, LOWER_Y, null);
			this.lowerLetter[cnt] = copy;
		}

		transparency = uppersImages.getColorModel().getTransparency();
		for (int cnt = 0; cnt < 26; cnt++) {
			copy = graphicsConfig.createCompatibleImage(UPPER_W, UPPER_Y, transparency);
			copy.createGraphics().drawImage(uppersImages, 0, 0, UPPER_W, UPPER_Y, cnt * UPPER_W, 0, (cnt * UPPER_W) + UPPER_W, UPPER_Y, null);
			this.upperLetter[cnt] = copy;
		}

		for (int cnt = String.valueOf("A").codePointAt(0); cnt <= String.valueOf("Z").codePointAt(0); cnt++) {
			this.mapLetters.put(String.valueOf(Character.toChars(cnt)), this.upperLetter[cnt - String.valueOf("A").codePointAt(0)]);	
			this.mapLetters.put(String.valueOf(Character.toChars(cnt + 32)), this.lowerLetter[cnt + 32 - String.valueOf("a").codePointAt(0)]);	
		}

		this.lowerLetter = null;
		this.upperLetter = null;
	}

	public boolean finished() {
		return (this.hiScoreCounter >= MAX_HI_SCORE_COUNTER_VALUE);
	}
	
	public void update(long frametime) {
		this.hiScoreCounter++;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#drawHiScores(java.awt.Graphics2D)
	 */
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, PWIDTH, PHEIGHT);
		g2d.setColor(Color.BLACK);

		if (!this.gameScore.hasScores()) {
			this.gameScore.loadHiScores();
		}

		this.zoomInScaleHallLogo = this.imageUtil.drawZoomInImage(g2d, 
				 												  this.hallOfFameLogo, 
				 												  (this.PWIDTH / 2) - (this.hallOfFameLogo.getWidth() / 2), 
				 												  this.imageUtil.getScaledHeight(HEIGHT_SPACE), 
				 												  this.zoomInScaleHallLogo);

		int finalRankPosition	= (this.PWIDTH / 2) - this.imageUtil.getScaledWidth(NAME_SCORE_SPACE) / 2 - (5 * this.imageUtil.getScaledWidth(UPPER_W));
		int actualScorePosition	= finalRankPosition;
		int counter 			= 0;
		int rankCounter			= 0;
		int nickCounter			= 0;
		int scoreCounter		= 0;
		int height 				= this.imageUtil.getScaledHeight(HEIGHT_SPACE) * 2 + 
						  	  	  this.imageUtil.getScaledHeight(this.hallOfFameLogo.getHeight());
		BufferedImage image		= null;
		String value			= null;

		if (this.zoomInScaleHallLogo == 1) {
			if (this.gameScore.hasScores()) {
				
				//---------------------------------------------------------//
				//--- Itera pelos scores.                        		---//
				//---------------------------------------------------------//
				for (String key : this.gameScore.getSortedScoresSet()) {

					//---------------------------------------------------------//
					//--- Recupera o Nick, o Score e a Imagem de Ranking.   ---//
					//---------------------------------------------------------//
					value 	= String.valueOf(this.gameScore.getStoredScoresValue(key));
					key 	= key.substring(0, key.indexOf("#"));
					image	= this.numbers[++counter];
					
					if (image != null) {
						
						
						if (this.individualRankPosition[rankCounter] < finalRankPosition) {
							this.individualRankPosition[rankCounter] = individualRankPosition[rankCounter] + 20;
						} if (this.individualRankPosition[rankCounter] > finalRankPosition) {
							this.individualRankPosition[rankCounter] = finalRankPosition;
						}
						

						g2d.drawImage(image, 
									  this.individualRankPosition[rankCounter], 
									  height,
									  this.imageUtil.getScaledWidth(image.getWidth()),
									  this.imageUtil.getScaledHeight(image.getHeight()),
									  null);
						

						if (this.individualRankPosition[rankCounter] > finalRankPosition / 4) {
							

							actualScorePosition += 4 * this.imageUtil.getScaledWidth(UPPER_W);
							

							for (int cnt = 0; cnt < key.length(); cnt++) {
								

								if (cnt == (key.length() - 1)) {
									this.nickDone = true;
								} else {
									this.nickDone = false;
								}
								

								image = this.mapLetters.get(String.valueOf(key.charAt(cnt)));
								

								int finalNickPosition = actualScorePosition;
								

								if (this.individualNickPosition[nickCounter] > finalNickPosition) {
									this.individualNickPosition[nickCounter] = individualNickPosition[nickCounter] - 30;
								} if (this.individualNickPosition[nickCounter] < finalNickPosition) {
									this.individualNickPosition[nickCounter] = finalNickPosition;
								} if (image == null) {
									this.individualNickPosition[nickCounter] = finalNickPosition;
								}
								

								if (this.individualNickPosition[nickCounter] != finalNickPosition) {
									image = this.imageUtil.getRotatedImage(image, this.individualNickPosition[nickCounter] % 360);
								}
								if (image != null) {
									g2d.drawImage(image,
											      this.individualNickPosition[nickCounter], 
											      height, 
											      this.imageUtil.getScaledWidth(image.getWidth()),
											      this.imageUtil.getScaledHeight(image.getHeight()),
											      null);
								}
								

								if (this.individualNickPosition[nickCounter] == finalNickPosition) {
									this.maxNickToShow = nickCounter + 1;
								}


								actualScorePosition += this.imageUtil.getScaledWidth(UPPER_W);
	

								if (nickCounter >= this.maxNickToShow) {
									this.nickDone = false;
									break;
								} else {
									nickCounter++;
								}
							}
							
							//---------------------------------------------------------//
							//--- Só exibo o score após exibir o nick completo. 	---//
							//---------------------------------------------------------//
							if (this.nickDone) {
								
								for (int cnt = 0; cnt < value.length(); cnt++) {
									

									if (cnt == (value.length() - 1)) {
										this.scoreDone = true;
									} else {
										this.scoreDone = false;
									}
									

									image = this.numbers[Integer.parseInt(String.valueOf(value.charAt(cnt)))];
									

									int finalScorePosition = actualScorePosition + this.imageUtil.getScaledWidth(NAME_SCORE_SPACE);
									int finalScorePositionY = height;
									

									if (this.individualScorePosition[scoreCounter] > finalScorePositionY) {
										this.individualScorePosition[scoreCounter] = individualScorePosition[scoreCounter] - 60;
									} if (this.individualScorePosition[scoreCounter] < finalScorePositionY) {
										this.individualScorePosition[scoreCounter] = finalScorePositionY;
									}
									

									if (image != null) {
										g2d.drawImage(image, 
													  finalScorePosition, 
													  this.individualScorePosition[scoreCounter], 
													  this.imageUtil.getScaledWidth(image.getWidth()),
													  this.imageUtil.getScaledHeight(image.getHeight()),
													  null);
									}
									

									if (this.individualScorePosition[scoreCounter] == finalScorePositionY) {
										this.maxScoreToShow = scoreCounter + 1;
									}
									

									actualScorePosition += this.imageUtil.getScaledWidth(UPPER_W);
									
									//---------------------------------------------------------//
									//--- Se o contador de exibição for maior que o máximo  ---//
									//--- que me proponho a mostrar, coibo o incremento.	---//
									//--- Assim, o número permanece no limite do que desejo ---//
									//--- exibir.											---//
									//---------------------------------------------------------//
									if (scoreCounter >= this.maxScoreToShow) {
										this.scoreDone = false;
										break;
									} else {
										scoreCounter++;
									}
								}
							}
						}
						
						//---------------------------------------------------------//
						//--- Se o ranking chegar a posição final, permito que  ---//
						//--- o próximo seja exibido.							---//
						//---------------------------------------------------------//
						if (this.individualRankPosition[rankCounter] == finalRankPosition && this.nickDone && this.scoreDone) {
							this.maxRankToShow = rankCounter + 1;
						}
					}
					
					//---------------------------------------------------------//
					//--- Reseto o actualScorePosition e somo 1 espaço a    ---//
					//--- altura.											---//
					//---------------------------------------------------------//
					actualScorePosition = finalRankPosition;	
					height += this.imageUtil.getScaledHeight(HEIGHT_SPACE);
					
					//---------------------------------------------------------//
					//--- Se o contador de exibição for maior que o máximo  ---//
					//--- que me proponho a mostrar, coibo o incremento.	---//
					//--- Assim, o número permanece no limite do que desejo ---//
					//--- exibir.											---//
					//---------------------------------------------------------//
					if (rankCounter >= this.maxRankToShow) {
						break;
					} else {
						rankCounter++;
					}
				}
			}
		}
	}

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 	= pwidth;
		this.PHEIGHT 	= pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);
		
		for (int cnt = 0; cnt < this.individualRankPosition.length; cnt++) {
			this.individualRankPosition[cnt] = 0;
		}
		for (int cnt = 0; cnt < this.individualNickPosition.length; cnt++) {
			this.individualNickPosition[cnt] = PWIDTH;
		}
		for (int cnt = 0; cnt < this.individualScorePosition.length; cnt++) {
			this.individualScorePosition[cnt] = PWIDTH;
		}
	}

	public void resetCounters() {
		this.hiScoreCounter = 0;
		this.maxRankToShow							= 0;
		this.maxNickToShow							= 0;
		this.maxScoreToShow							= 0;
		this.nickDone 								= false;
		this.scoreDone 								= false;

		for (int cnt = 0; cnt < this.individualRankPosition.length; cnt++) {
			this.individualRankPosition[cnt] = 0;
		}
		for (int cnt = 0; cnt < this.individualNickPosition.length; cnt++) {
			this.individualNickPosition[cnt] = PWIDTH;
		}
		for (int cnt = 0; cnt < this.individualScorePosition.length; cnt++) {
			this.individualScorePosition[cnt] = PHEIGHT;
		}
	}

	@Override
	public void handleInput(Game game, int keyCode, boolean isAltDown) {
		//todo
	}
}