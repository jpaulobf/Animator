package br.com.game.animator.game.gameScore;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import br.com.game.animator.util.ImageUtil;

/**
 * @author Jo�o Paulo
 */
public class GameScorePresentationImpl implements GameScorePresentation {
	
	//----------------------------------------------------------------------//
	//--------   PROPRIEDADES 				--------------------------------//
	//----------------------------------------------------------------------//
	private Integer hiScoreCounter 							= 0;
	private Integer PWIDTH									= null;
	private Integer PHEIGHT									= null;
	protected ImageUtil imageUtil							= null;
	protected String avatarName								= null;
	protected Long scoreValue								= null;
	
	//----------------------------------------------------------------------//
	//--------   CONSTANTES DE ESPA�AMENTO	--------------------------------//
	//----------------------------------------------------------------------//
	protected final static Integer LOWER_W					= 30;
	protected final static Integer LOWER_Y					= 55;
	protected final static Integer UPPER_W					= 40;
	protected final static Integer UPPER_Y					= 55;
	protected final static Integer NUMBER_W					= 40;
	protected final static Integer NUMBER_Y					= 55;
	protected final static Integer HEIGHT_SPACE 			= 78;
	protected final static Integer NAME_SCORE_SPACE 		= 300;
	
	//----------------------------------------------------------------------//
	//--------   Imagens para letras e n�meros -----------------------------//
	//----------------------------------------------------------------------//
	private BufferedImage [] upperLetter					= null;
	private BufferedImage [] lowerLetter					= null;
	private BufferedImage [] numbers						= null;
	private BufferedImage hallOfFameLogo					= null;
	private Map <String, BufferedImage> mapLetters			= null;
	
	//----------------------------------------------------------------------//
	//--------   Propriedades de suporte       -----------------------------//
	//----------------------------------------------------------------------//
	private GameScore gameScore								= null;

	//----------------------------------------------------------------------//
	//--------   Propriedades para efeito de zoom       --------------------//
	//----------------------------------------------------------------------//
	protected static final Integer DEFAULT_ZOOM_IN_SCALE	= 30;
	private Integer zoomInScaleHallLogo						= DEFAULT_ZOOM_IN_SCALE;
	
	//----------------------------------------------------------------------//
	//--------   Propriedades para efeito de entrada de scores -------------//
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
	 * Construtor Padr�o
	 */
	public GameScorePresentationImpl(Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		//---------------------------------------------------------//
		//--- Atualiza o tamanho do canvas.               		---//
		//---------------------------------------------------------//
		this.PWIDTH 				= pwidth;
		this.PHEIGHT 				= pheight;
		
		//---------------------------------------------------------//
		//--- Cria as classes de apoio e score.           		---//
		//---------------------------------------------------------//
		this.imageUtil 				= new ImageUtil(this.PWIDTH, this.PHEIGHT, currentAspectRatio);
		this.gameScore				= new GameScoreImpl();
		
		//---------------------------------------------------------//
		//--- Inicializa com a posi��o inicial.           		---//
		//---------------------------------------------------------//
		for (int cnt = 0; cnt < this.individualRankPosition.length; cnt++) {
			this.individualRankPosition[cnt] = 0;
		}
		for (int cnt = 0; cnt < this.individualNickPosition.length; cnt++) {
			this.individualNickPosition[cnt] = PWIDTH;
		}
		for (int cnt = 0; cnt < this.individualScorePosition.length; cnt++) {
			this.individualScorePosition[cnt] = PHEIGHT;
		}

		//---------------------------------------------------------//
		//--- Inicializa as arrays contenedoras           		---//
		//---------------------------------------------------------//
		this.upperLetter			= new BufferedImage[26];
		this.lowerLetter			= new BufferedImage[26];
		this.numbers				= new BufferedImage[11];
		this.mapLetters				= new HashMap<String, BufferedImage>();

		//---------------------------------------------------------//
		//--- Recupera as imagens.                        		---//
		//---------------------------------------------------------//
		this.hallOfFameLogo			= this.imageUtil.loadScaledImage("/res/images/hall.png");
		BufferedImage numbersImages	= this.imageUtil.loadImage("/res/images/numbers.png");
		BufferedImage lowersImages 	= this.imageUtil.loadImage("/res/images/letter_lower.png");
		BufferedImage uppersImages 	= this.imageUtil.loadImage("/res/images/letter_upper.png");
		BufferedImage copy 			= null;
		
		//---------------------------------------------------------//
		//--- Recupera o GE e o GC.                       		---//
		//---------------------------------------------------------//
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration graphicsConfig = ge.getDefaultScreenDevice().getDefaultConfiguration();

		//---------------------------------------------------------//
		//--- Copia cada peda�o da imagem maior, respeitando a	---//
		//--- transpar�ncia. Come�a com os n�meros.             ---//
		//---------------------------------------------------------//
		int transparency = numbersImages.getColorModel().getTransparency();
		for (int cnt = 0; cnt < 10; cnt++) {
			copy = graphicsConfig.createCompatibleImage(NUMBER_W, NUMBER_Y, transparency);
			copy.createGraphics().drawImage(numbersImages, 0, 0, NUMBER_W, NUMBER_Y, cnt * NUMBER_W, 0, (cnt * NUMBER_W) + NUMBER_W, NUMBER_Y, null);
			this.numbers[cnt] = copy;
		}
		
		//---------------------------------------------------------//
		//--- Cria a imagem com o n�mero 10.					---//
		//---------------------------------------------------------//
		copy = graphicsConfig.createCompatibleImage(2 * NUMBER_W, NUMBER_Y, transparency);
		copy.createGraphics().drawImage(numbersImages, 0, 0, NUMBER_W, NUMBER_Y, NUMBER_W, 0, (2 * NUMBER_W), NUMBER_Y, null);
		copy.createGraphics().drawImage(numbersImages, NUMBER_W, 0, 2 * NUMBER_W, NUMBER_Y, 0, 0, NUMBER_W, NUMBER_Y, null);
		this.numbers[10] = copy;
		
		//---------------------------------------------------------//
		//--- Copia cada peda�o da imagem maior, respeitando a	---//
		//--- transpar�ncia. Letras Min�sculas.	             	---//
		//---------------------------------------------------------//
		transparency = lowersImages.getColorModel().getTransparency();
		for (int cnt = 0; cnt < 26; cnt++) {
			copy = graphicsConfig.createCompatibleImage(LOWER_W, LOWER_Y, transparency);
			copy.createGraphics().drawImage(lowersImages, 0, 0, LOWER_W, LOWER_Y, cnt * LOWER_W, 0, (cnt * LOWER_W) + LOWER_W, LOWER_Y, null);
			this.lowerLetter[cnt] = copy;
		}
		
		//---------------------------------------------------------//
		//--- Copia cada peda�o da imagem maior, respeitando a	---//
		//--- transpar�ncia. Letras Mai�sculas.	             	---//
		//---------------------------------------------------------//
		transparency = uppersImages.getColorModel().getTransparency();
		for (int cnt = 0; cnt < 26; cnt++) {
			copy = graphicsConfig.createCompatibleImage(UPPER_W, UPPER_Y, transparency);
			copy.createGraphics().drawImage(uppersImages, 0, 0, UPPER_W, UPPER_Y, cnt * UPPER_W, 0, (cnt * UPPER_W) + UPPER_W, UPPER_Y, null);
			this.upperLetter[cnt] = copy;
		}

		//---------------------------------------------------------//
		//--- Armazena as letras em MAPS facilitando a 			---//
		//--- recupera��o futura.								---//
		//---------------------------------------------------------//
		for (int cnt = String.valueOf("A").codePointAt(0); cnt <= String.valueOf("Z").codePointAt(0); cnt++) {
			this.mapLetters.put(String.valueOf(Character.toChars(cnt)), this.upperLetter[cnt - String.valueOf("A").codePointAt(0)]);	
			this.mapLetters.put(String.valueOf(Character.toChars(cnt + 32)), this.lowerLetter[cnt + 32 - String.valueOf("a").codePointAt(0)]);	
		}
		
		//---------------------------------------------------------//
		//--- Libera as arrays sem uso.                			---//
		//---------------------------------------------------------//
		this.lowerLetter = null;
		this.upperLetter = null;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#hiScorePresentationFinished()
	 */
	public boolean hiScorePresentationFinished() {
		return (this.hiScoreCounter >= MAX_HI_SCORE_COUNTER_VALUE);
	}
	
	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScorePresentation#update()
	 */
	public void update() {
		//---------------------------------------------------------//
		//--- Mant�m o counter atualizado a cada chamada do 	---//
		//--- update. 											---//
		//---------------------------------------------------------//
		this.hiScoreCounter++;
	}

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#drawHiScores(java.awt.Graphics2D)
	 */
	public void drawHiScores(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, PWIDTH, PHEIGHT);
		g2d.setColor(Color.BLACK);

		//---------------------------------------------------------//
		//--- Ao imprimir o Hi-Score, primeiro verifica se o	---//
		//--- m�todo de leitura foi executado.					---//
		//---------------------------------------------------------//
		if (!this.gameScore.hasScores()) {
			this.gameScore.loadHiScores();
		}
		
		//---------------------------------------------------------//
		//--- Desenha a logo 'Hall of Fame' em Zoom In.			---//
		//---------------------------------------------------------//
		this.zoomInScaleHallLogo = this.imageUtil.drawZoomInImage(g2d, 
				 												  this.hallOfFameLogo, 
				 												  (this.PWIDTH / 2) - (this.hallOfFameLogo.getWidth() / 2), 
				 												  this.imageUtil.getScaledHeight(HEIGHT_SPACE), 
				 												  this.zoomInScaleHallLogo);
		
		//---------------------------------------------------------//
		//--- Imprime o Hi-Score.								---//
		//---------------------------------------------------------//
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
		
		//---------------------------------------------------------//
		//--- Imprime o score individual, iteam a item.			---//
		//--- Somente depois que o logo tiver sido exibido.		---//
		//---------------------------------------------------------//
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
					
					//---------------------------------------------------------//
					//--- S� executo se a imagem n�o for inv�lida.		    ---//
					//---------------------------------------------------------//
					if (image != null) {
						
						//---------------------------------------------------------//
						//--- 'Caminho' com o ranking at� sua posi��o final.    ---//
						//---------------------------------------------------------//
						if (this.individualRankPosition[rankCounter] < finalRankPosition) {
							this.individualRankPosition[rankCounter] = individualRankPosition[rankCounter] + 20;
						} if (this.individualRankPosition[rankCounter] > finalRankPosition) {
							this.individualRankPosition[rankCounter] = finalRankPosition;
						}
						
						//---------------------------------------------------------//
						//--- Desenho o ranking em sua posi��o atual.           ---//
						//---------------------------------------------------------//
						g2d.drawImage(image, 
									  this.individualRankPosition[rankCounter], 
									  height,
									  this.imageUtil.getScaledWidth(image.getWidth()),
									  this.imageUtil.getScaledHeight(image.getHeight()),
									  null);
						
						//---------------------------------------------------------//
						//--- Quando o ranking atinge a posi��o final exibo o   ---//
						//--- nick e depois o score.							---//
						//---------------------------------------------------------//
						if (this.individualRankPosition[rankCounter] > finalRankPosition / 4) {
							
							//---------------------------------------------------------//
							//--- Somo 4 espa�os a posi��o do ranking.              ---//
							//---------------------------------------------------------//
							actualScorePosition += 4 * this.imageUtil.getScaledWidth(UPPER_W);
							
							//---------------------------------------------------------//
							//--- Recupero as letras e imprimo.                     ---//
							//---------------------------------------------------------//
							for (int cnt = 0; cnt < key.length(); cnt++) {
								
								//---------------------------------------------------------//
								//--- Toda vez que percorreu a array at� o final, 		---//
								//--- concluiu-se um ciclo dos nicknames e pode 		---//
								//--- continuar. 										---//
								//---------------------------------------------------------//
								if (cnt == (key.length() - 1)) {
									this.nickDone = true;
								} else {
									this.nickDone = false;
								}
								
								//---------------------------------------------------------//
								//--- Recupera a imagem da letra.                     	---//
								//---------------------------------------------------------//
								image = this.mapLetters.get(String.valueOf(key.charAt(cnt)));
								
								//---------------------------------------------------------//
								//--- Define a posi��o final da letra.                	---//
								//---------------------------------------------------------//
								int finalNickPosition = actualScorePosition;
								
								//---------------------------------------------------------//
								//--- 'Caminho' com o nick at� sua posi��o final.    	---//
								//---------------------------------------------------------//
								if (this.individualNickPosition[nickCounter] > finalNickPosition) {
									this.individualNickPosition[nickCounter] = individualNickPosition[nickCounter] - 30;
								} if (this.individualNickPosition[nickCounter] < finalNickPosition) {
									this.individualNickPosition[nickCounter] = finalNickPosition;
								} if (image == null) {
									this.individualNickPosition[nickCounter] = finalNickPosition;
								}
								
								//---------------------------------------------------------//
								//--- S� desenho se a imagem n�o for inv�lida.		    ---//
								//---------------------------------------------------------//
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
								
								//---------------------------------------------------------//
								//--- Se o nickname chegar a posi��o final, permito que ---//
								//--- o pr�ximo seja exibido.							---//
								//---------------------------------------------------------//
								if (this.individualNickPosition[nickCounter] == finalNickPosition) {
									this.maxNickToShow = nickCounter + 1;
								}

								//---------------------------------------------------------//
								//--- Acresce a posi��o para pr�xima letra.				---//
								//---------------------------------------------------------//
								actualScorePosition += this.imageUtil.getScaledWidth(UPPER_W);
	
								//---------------------------------------------------------//
								//--- Se o contador de exibi��o for maior que o m�ximo  ---//
								//--- que me proponho a mostrar, coibo o incremento.	---//
								//--- Assim, a letra permanece no limite do que desejo 	---//
								//--- exibir.											---//
								//---------------------------------------------------------//
								if (nickCounter >= this.maxNickToShow) {
									this.nickDone = false;
									break;
								} else {
									nickCounter++;
								}
							}
							
							//---------------------------------------------------------//
							//--- S� exibo o score ap�s exibir o nick completo. 	---//
							//---------------------------------------------------------//
							if (this.nickDone) {
								
								for (int cnt = 0; cnt < value.length(); cnt++) {
									
									//---------------------------------------------------------//
									//--- Toda vez que percorreu a array at� o final, 		---//
									//--- concluiu-se um ciclo dos scores e pode-se ent�o	---//
									//--- continuar. 										---//
									//---------------------------------------------------------//
									if (cnt == (value.length() - 1)) {
										this.scoreDone = true;
									} else {
										this.scoreDone = false;
									}
									
									//---------------------------------------------------------//
									//--- Recupera a imagem do score.                     	---//
									//---------------------------------------------------------//
									image = this.numbers[Integer.parseInt(String.valueOf(value.charAt(cnt)))];
									
									//---------------------------------------------------------//
									//--- Define a posi��o final do score.                	---//
									//---------------------------------------------------------//
									int finalScorePosition = actualScorePosition + this.imageUtil.getScaledWidth(NAME_SCORE_SPACE);
									int finalScorePositionY = height;
									
									//---------------------------------------------------------//
									//--- 'Caminho' com o score at� sua posi��o final.    	---//
									//---------------------------------------------------------//
									if (this.individualScorePosition[scoreCounter] > finalScorePositionY) {
										this.individualScorePosition[scoreCounter] = individualScorePosition[scoreCounter] - 60;
									} if (this.individualScorePosition[scoreCounter] < finalScorePositionY) {
										this.individualScorePosition[scoreCounter] = finalScorePositionY;
									}
									
									//---------------------------------------------------------//
									//--- S� desenho se a imagem n�o for inv�lida.		    ---//
									//---------------------------------------------------------//
									if (image != null) {
										g2d.drawImage(image, 
													  finalScorePosition, 
													  this.individualScorePosition[scoreCounter], 
													  this.imageUtil.getScaledWidth(image.getWidth()),
													  this.imageUtil.getScaledHeight(image.getHeight()),
													  null);
									}
									
									//---------------------------------------------------------//
									//--- Se o score chegar a posi��o final, permito que  	---//
									//--- o pr�ximo seja exibido.							---//
									//---------------------------------------------------------//
									if (this.individualScorePosition[scoreCounter] == finalScorePositionY) {
										this.maxScoreToShow = scoreCounter + 1;
									}
									
									//---------------------------------------------------------//
									//--- Acresce a posi��o para o pr�ximo n�mero.			---//
									//---------------------------------------------------------//
									actualScorePosition += this.imageUtil.getScaledWidth(UPPER_W);
									
									//---------------------------------------------------------//
									//--- Se o contador de exibi��o for maior que o m�ximo  ---//
									//--- que me proponho a mostrar, coibo o incremento.	---//
									//--- Assim, o n�mero permanece no limite do que desejo ---//
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
						//--- Se o ranking chegar a posi��o final, permito que  ---//
						//--- o pr�ximo seja exibido.							---//
						//---------------------------------------------------------//
						if (this.individualRankPosition[rankCounter] == finalRankPosition && this.nickDone && this.scoreDone) {
							this.maxRankToShow = rankCounter + 1;
						}
					}
					
					//---------------------------------------------------------//
					//--- Reseto o actualScorePosition e somo 1 espa�o �    ---//
					//--- altura.											---//
					//---------------------------------------------------------//
					actualScorePosition = finalRankPosition;	
					height += this.imageUtil.getScaledHeight(HEIGHT_SPACE);
					
					//---------------------------------------------------------//
					//--- Se o contador de exibi��o for maior que o m�ximo  ---//
					//--- que me proponho a mostrar, coibo o incremento.	---//
					//--- Assim, o n�mero permanece no limite do que desejo ---//
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

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#updateGraphics(boolean, java.lang.Integer, java.lang.Integer)
	 */
	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 	= pwidth;
		this.PHEIGHT 	= pheight;
		this.imageUtil.updateCanvasProperties(pwidth, pheight, currentAspectRatio);
		
		//---------------------------------------------------------//
		//--- Inicializa com a posi��o inicial.           		---//
		//---------------------------------------------------------//
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

	/* (non-Javadoc)
	 * @see br.com.animator.gameScore.GameScore#resetCounters()
	 */
	public void resetCounters() {
		this.hiScoreCounter = 0;
		this.maxRankToShow							= 0;
		this.maxNickToShow							= 0;
		this.maxScoreToShow							= 0;
		this.nickDone 								= false;
		this.scoreDone 								= false;
		
		//---------------------------------------------------------//
		//--- Inicializa com a posi��o inicial.           		---//
		//---------------------------------------------------------//
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
}