package br.com.animator.game.ui.options;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import br.com.animator.core.game.IGame;
import br.com.animator.game.Game;
import br.com.animator.game.data.GameOptions;
import br.com.animator.game.data.enumerators.GameDifficulty;
import br.com.animator.input.GameAction;
import br.com.animator.ui.options.GameOptionScreen;
import br.com.animator.util.ImageUtil;

/**
 * author: Joao Paulo Faria
 */
public class GameOptionScreenImpl implements GameOptionScreen {
	
	//--- Properties
	private Integer optionGameCounter								= 0;
	private Integer PWIDTH											= null;
	private Integer PHEIGHT											= null;
	protected ImageUtil imageUtil									= null;
	private Integer optionSelected									= 0;
	private GameOptions parentGameOption							= null;
	private BufferedImage backgroundGameOptions						= null;
	private BufferedImage gameOptionsLogo							= null;
	private BufferedImage highlightButton							= null;
	private BufferedImage labelGameDifficulty						= null;
	private BufferedImage labelRests								= null;
	private BufferedImage labelExtraLifeAt							= null;
	private BufferedImage labelContinues							= null;
	private BufferedImage labelEnableSubtitles						= null;
	private BufferedImage labelBack									= null;
	private BufferedImage buttonArrowLeft							= null;
	private BufferedImage buttonArrowRight							= null;
	private BufferedImage buttonArrowLeftWhite						= null;
	private BufferedImage buttonArrowRightWhite						= null;
	private BufferedImage checked									= null;
	private BufferedImage unchecked									= null;
	private BufferedImage labelYes									= null;
	private BufferedImage labelNo									= null;
	private BufferedImage imageGameDifficulty						= null;
	private BufferedImage imageDifficultyEasy						= null;
	private BufferedImage imageDifficultyNormal						= null;
	private BufferedImage imageDifficultyHard						= null;
	private BufferedImage imageGameRestsStrip						= null;
	private BufferedImage [] imageGameRests							= null;
	private BufferedImage imageGameContinuesStrip					= null;
	private BufferedImage [] imageGameContinues						= null;
	private BufferedImage imageGameExtraLifeStrip					= null;
	private BufferedImage [] imageGameExtraLife						= null;
	private final static Integer LOGO_GAME_OPTIONS_POSITION_X 		= 919;
	private final static Integer LOGO_GAME_OPTIONS_POSITION_Y 		= 42;
	private final static Integer LABEL_DIFFICULTY_POSITION_X 		= 90;
	private final static Integer LABEL_DIFFICULTY_POSITION_Y 		= 299;
	private final static Integer LABEL_RESTS_POSITION_X 			= 90;
	private final static Integer LABEL_RESTS_POSITION_Y 			= 423;
	private final static Integer LABEL_EXTRALIFE_POSITION_X 		= 90;
	private final static Integer LABEL_EXTRALIFE_POSITION_Y 		= 540;
	private final static Integer LABEL_CONTINUES_POSITION_X 		= 90;
	private final static Integer LABEL_CONTINUES_POSITION_Y 		= 659;
	private final static Integer LABEL_SUBTITLES_POSITION_X 		= 90;
	private final static Integer LABEL_SUBTITLES_POSITION_Y 		= 775;
	private final static Integer LABEL_BACK_POSITION_X 				= 90;
	private final static Integer LABEL_BACK_POSITION_Y 				= 893;
	private final static Integer LABEL_YES_POSITION_X 				= 592;
	private final static Integer LABEL_YES_POSITION_Y 				= 782;
	private final static Integer LABEL_NO_POSITION_X 				= 769;
	private final static Integer LABEL_NO_POSITION_Y 				= 782;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_X 		= 0;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_1 		= 277;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_2 		= 397;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_3 		= 517;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_4 		= 635;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_5 		= 754;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_6 		= 872;
	private final static Integer ARROWL_DIFFICULTY_POSITION_X 		= 577;
	private final static Integer ARROWL_DIFFICULTY_POSITION_Y 		= 318;
	private final static Integer ARROWR_DIFFICULTY_POSITION_X 		= 794;
	private final static Integer ARROWR_DIFFICULTY_POSITION_Y 		= 318;
	private final static Integer ARROWL_RESTS_POSITION_X 			= 577;
	private final static Integer ARROWL_RESTS_POSITION_Y 			= 439;
	private final static Integer ARROWR_RESTS_POSITION_X 			= 744;
	private final static Integer ARROWR_RESTS_POSITION_Y 			= 439;
	private final static Integer ARROWL_EXTRALIFE_POSITION_X 		= 577;
	private final static Integer ARROWL_EXTRALIFE_POSITION_Y 		= 559;
	private final static Integer ARROWR_EXTRALIFE_POSITION_X 		= 953;
	private final static Integer ARROWR_EXTRALIFE_POSITION_Y 		= 559;
	private final static Integer ARROWL_CONTINUES_POSITION_X 		= 577;
	private final static Integer ARROWL_CONTINUES_POSITION_Y 		= 676;
	private final static Integer ARROWR_CONTINUES_POSITION_X 		= 744;
	private final static Integer ARROWR_CONTINUES_POSITION_Y 		= 676;
	private final static Integer W_ARROWL_DIFFICULTY_POSITION_X 	= 572;
	private final static Integer W_ARROWL_DIFFICULTY_POSITION_Y 	= 315;
	private final static Integer W_ARROWR_DIFFICULTY_POSITION_X 	= 790;
	private final static Integer W_ARROWR_DIFFICULTY_POSITION_Y 	= 315;
	private final static Integer W_ARROWL_RESTS_POSITION_X 			= 572;
	private final static Integer W_ARROWL_RESTS_POSITION_Y 			= 436;
	private final static Integer W_ARROWR_RESTS_POSITION_X 			= 740;
	private final static Integer W_ARROWR_RESTS_POSITION_Y 			= 436;
	private final static Integer W_ARROWL_EXTRALIFE_POSITION_X 		= 572;
	private final static Integer W_ARROWL_EXTRALIFE_POSITION_Y 		= 556;
	private final static Integer W_ARROWR_EXTRALIFE_POSITION_X 		= 949;
	private final static Integer W_ARROWR_EXTRALIFE_POSITION_Y 		= 556;
	private final static Integer W_ARROWL_CONTINUES_POSITION_X 		= 572;
	private final static Integer W_ARROWL_CONTINUES_POSITION_Y 		= 673;
	private final static Integer W_ARROWR_CONTINUES_POSITION_X 		= 740;
	private final static Integer W_ARROWR_CONTINUES_POSITION_Y 		= 673;
	private final static Integer BUTTON_CHECK_YES_POSITION_X 		= 582;
	private final static Integer BUTTON_CHECK_YES_POSITION_Y 		= 780;
	private final static Integer BUTTON_CHECK_NO_POSITION_X 		= 758;
	private final static Integer BUTTON_CHECK_NO_POSITION_Y 		= 780;
	private final static Integer VALUE_DIFFICULTY_POSITION_X 		= 638;
	private final static Integer VALUE_DIFFICULTY_B_POSITION_X 		= 658;
	private final static Integer VALUE_DIFFICULTY_POSITION_Y 		= 309;
	private final static Integer VALUE_RESTS_POSITION_X				= 649;
	private final static Integer VALUE_RESTS_POSITION_Y				= 432;
	private final static Integer VALUE_CONTINUES_POSITION_X			= 644;
	private final static Integer VALUE_CONTINUES_POSITION_Y			= 671;
	private final static Integer VALUE_EXTRALIFE_POSITION_X			= 650;
	private final static Integer VALUE_EXTRALIFE_POSITION_Y			= 549;
	private final static Integer VALUE_EXTRALIFE_NEVER_POSITION_X	= 713;
	private final static Integer VALUE_EXTRALIFE_NEVER_POSITION_Y	= 549;
	private Integer counterArrowLeftDifficulty 						= 0;
	private Integer counterArrowRightDifficulty 					= 0;
	private Integer counterArrowLeftRest 							= 0;
	private Integer counterArrowRightRest 							= 0;
	private Integer counterArrowLeftContinue 						= 0;
	private Integer counterArrowRightContinue 						= 0;
	private Integer counterArrowLeftExtraLife 						= 0;
	private Integer counterArrowRightExtraLife 						= 0;
	
	/**
	 * Constructor
	 * @param parentGameOption
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public GameOptionScreenImpl(GameOptions parentGameOption, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		//--- Atualiza o tamanho do canvas.
		this.PWIDTH 						= pwidth;
		this.PHEIGHT 						= pheight;
		this.imageUtil 						= new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.parentGameOption				= parentGameOption;
		this.backgroundGameOptions			= this.imageUtil.loadImage("GameOptionsConfig.1");
		this.gameOptionsLogo				= this.imageUtil.loadImage("GameOptionsConfig.2");
		this.highlightButton				= this.imageUtil.loadImage("GameOptionsConfig.3");
		this.labelGameDifficulty			= this.imageUtil.loadImage("GameOptionsConfig.4");
		this.labelRests						= this.imageUtil.loadImage("GameOptionsConfig.5");
		this.labelExtraLifeAt				= this.imageUtil.loadImage("GameOptionsConfig.6");
		this.labelContinues					= this.imageUtil.loadImage("GameOptionsConfig.7");
		this.labelEnableSubtitles			= this.imageUtil.loadImage("GameOptionsConfig.8");
		this.labelBack						= this.imageUtil.loadImage("GameOptionsConfig.9");
		this.buttonArrowLeft				= this.imageUtil.loadImage("GameOptionsConfig.10");
		this.buttonArrowRight				= this.imageUtil.loadImage("GameOptionsConfig.11");
		this.buttonArrowLeftWhite			= this.imageUtil.loadImage("GameOptionsConfig.12");
		this.buttonArrowRightWhite			= this.imageUtil.loadImage("GameOptionsConfig.13");
		this.checked						= this.imageUtil.loadImage("GameOptionsConfig.14");
		this.unchecked						= this.imageUtil.loadImage("GameOptionsConfig.15");
		this.labelYes						= this.imageUtil.loadImage("GameOptionsConfig.16");
		this.labelNo						= this.imageUtil.loadImage("GameOptionsConfig.17");
		this.imageGameDifficulty			= this.imageUtil.loadImage("GameOptionsConfig.18");
		this.imageGameRestsStrip			= this.imageUtil.loadImage("GameOptionsConfig.19");
		this.imageGameContinuesStrip		= this.imageUtil.loadImage("GameOptionsConfig.20");
		this.imageGameExtraLifeStrip		= this.imageUtil.loadImage("GameOptionsConfig.21");

		this.imageDifficultyEasy			= this.imageUtil.copyImage(this.imageGameDifficulty, 0, 0, 68, 33);
		this.imageDifficultyNormal			= this.imageUtil.copyImage(this.imageGameDifficulty, 78, 0, 114, 33);
		this.imageDifficultyHard			= this.imageUtil.copyImage(this.imageGameDifficulty, 205, 0, 72, 33);
		
		this.imageGameRests					= new BufferedImage[6];
		this.imageGameRests[0]				= this.imageUtil.copyImage(this.imageGameRestsStrip, 0, 0, 37, 26);
		this.imageGameRests[1]				= this.imageUtil.copyImage(this.imageGameRestsStrip, 70, 0, 36, 26);
		this.imageGameRests[2]				= this.imageUtil.copyImage(this.imageGameRestsStrip, 139, 0, 37, 26);
		this.imageGameRests[3]				= this.imageUtil.copyImage(this.imageGameRestsStrip, 209, 0, 37, 26);
		this.imageGameRests[4]				= this.imageUtil.copyImage(this.imageGameRestsStrip, 278, 0, 37, 26);
		this.imageGameRests[5]				= this.imageUtil.copyImage(this.imageGameRestsStrip, 348, 0, 37, 26);
		
		this.imageGameContinues				= new BufferedImage[7];
		this.imageGameContinues[0]			= this.imageUtil.copyImage(this.imageGameContinuesStrip, 0, 0, 46, 26);
		this.imageGameContinues[1]			= this.imageUtil.copyImage(this.imageGameContinuesStrip, 52, 0, 38, 26);
		this.imageGameContinues[2]			= this.imageUtil.copyImage(this.imageGameContinuesStrip, 96, 0, 37, 26);
		this.imageGameContinues[3]			= this.imageUtil.copyImage(this.imageGameContinuesStrip, 139, 0, 37, 26);
		this.imageGameContinues[4]			= this.imageUtil.copyImage(this.imageGameContinuesStrip, 182, 0, 37, 26);
		this.imageGameContinues[5]			= this.imageUtil.copyImage(this.imageGameContinuesStrip, 225, 0, 37, 26);
		this.imageGameContinues[6]			= this.imageUtil.copyImage(this.imageGameContinuesStrip, 268, 0, 37, 26);
		
		this.imageGameExtraLife				= new BufferedImage[3];
		this.imageGameExtraLife[0]			= this.imageUtil.copyImage(this.imageGameExtraLifeStrip, 0, 0, 246, 33);
		this.imageGameExtraLife[1]			= this.imageUtil.copyImage(this.imageGameExtraLifeStrip, 302, 0, 246, 33);
		this.imageGameExtraLife[2]			= this.imageUtil.copyImage(this.imageGameExtraLifeStrip, 616, 0, 88, 33);
	}
	
	public void update(long frametime) {
		this.optionGameCounter++;
	}

	public void draw(Graphics2D g2d) {
		
		//---------------------------------------------------------//
		//--- Desenha a tela de plano de fundos.                 --//
		//---------------------------------------------------------//
		g2d.drawImage(this.backgroundGameOptions, 
					  0,
					  0, 
					  this.imageUtil.getScaledWidth(this.backgroundGameOptions.getWidth()),
					  this.imageUtil.getScaledHeight(this.backgroundGameOptions.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a logo na tela de plano de fundos.         --//
		//---------------------------------------------------------//
		g2d.drawImage(this.gameOptionsLogo, 
					  this.imageUtil.getScaledWidthScaledWithInformed4x3(LOGO_GAME_OPTIONS_POSITION_X, 450),
					  this.imageUtil.getScaledHeight(LOGO_GAME_OPTIONS_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.gameOptionsLogo.getWidth()),
					  this.imageUtil.getScaledHeight(this.gameOptionsLogo.getHeight()),
					  null);
		
		int selector = 0;
		if (this.optionSelected == 0) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_1;
		} else if (this.optionSelected == 1) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_2;
		} else if (this.optionSelected == 2) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_3;
		} else if (this.optionSelected == 3) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_4;
		} else if (this.optionSelected == 4) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_5;
		} else if (this.optionSelected == 5) {
			selector = BUTTON_HIGHLIGHT_POSITION_Y_6;
		}
		
		g2d.drawImage(this.highlightButton, 
					  this.imageUtil.getScaledWidth(BUTTON_HIGHLIGHT_POSITION_X),
					  this.imageUtil.getScaledHeight(selector),
					  this.imageUtil.getScaledWidth(this.highlightButton.getWidth()),
					  this.imageUtil.getScaledHeight(this.highlightButton.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do difficulty.         			---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelGameDifficulty, 
					  this.imageUtil.getScaledWidth(LABEL_DIFFICULTY_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_DIFFICULTY_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelGameDifficulty.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelGameDifficulty.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do Rests.         				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelRests, 
					  this.imageUtil.getScaledWidth(LABEL_RESTS_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_RESTS_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelRests.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelRests.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do Extra Life.       				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelExtraLifeAt, 
					  this.imageUtil.getScaledWidth(LABEL_EXTRALIFE_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_EXTRALIFE_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelExtraLifeAt.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelExtraLifeAt.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label dos Continues.       				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelContinues, 
					  this.imageUtil.getScaledWidth(LABEL_CONTINUES_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_CONTINUES_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelContinues.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelContinues.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do Subtitle.       				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelEnableSubtitles, 
					  this.imageUtil.getScaledWidth(LABEL_SUBTITLES_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_SUBTITLES_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelEnableSubtitles.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelEnableSubtitles.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do Back.       					---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelBack, 
					  this.imageUtil.getScaledWidth(LABEL_BACK_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_BACK_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelBack.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelBack.getHeight()),
					  null);
		
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda W do seletor de dificuldade ---//
		//---------------------------------------------------------//
		if (this.counterArrowLeftDifficulty > 0) {
			g2d.drawImage(this.buttonArrowLeftWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWL_DIFFICULTY_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWL_DIFFICULTY_POSITION_Y),
						  this.buttonArrowLeftWhite.getWidth(),
						  this.buttonArrowLeftWhite.getHeight(),
						  null);
			this.counterArrowLeftDifficulty--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda do seletor de dificuldade.  ---//
		//---------------------------------------------------------//
		BufferedImage temp = null;
		if (this.parentGameOption.getGameDifficulty() == GameDifficulty.EASY) {
			temp = imageUtil.getGrayscaleImage(this.buttonArrowLeft);
		} else {
			temp = this.buttonArrowLeft;
		}
		g2d.drawImage(temp, 
					  this.imageUtil.getScaledWidth(ARROWL_DIFFICULTY_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWL_DIFFICULTY_POSITION_Y),
					  this.buttonArrowLeft.getWidth(),
					  this.buttonArrowLeft.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha seta direita W do seletor de dificuldade  ---//
		//---------------------------------------------------------//
		if (this.counterArrowRightDifficulty > 0) {
			g2d.drawImage(this.buttonArrowRightWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWR_DIFFICULTY_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWR_DIFFICULTY_POSITION_Y),
						  this.buttonArrowRightWhite.getWidth(),
						  this.buttonArrowRightWhite.getHeight(),
						  null);
			this.counterArrowRightDifficulty--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta direita do seletor de dificuldade.   ---//
		//---------------------------------------------------------//
		if (this.parentGameOption.getGameDifficulty() == GameDifficulty.HARD) {
			temp = imageUtil.getGrayscaleImage(this.buttonArrowRight);
		} else {
			temp = this.buttonArrowRight;
		}
		g2d.drawImage(temp, 
					  this.imageUtil.getScaledWidth(ARROWR_DIFFICULTY_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWR_DIFFICULTY_POSITION_Y),
					  this.buttonArrowRight.getWidth(),
					  this.buttonArrowRight.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda W do seletor de vidas.		---//
		//---------------------------------------------------------//
		if (this.counterArrowLeftRest > 0) {
			g2d.drawImage(this.buttonArrowLeftWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWL_RESTS_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWL_RESTS_POSITION_Y),
						  this.buttonArrowLeftWhite.getWidth(),
						  this.buttonArrowLeftWhite.getHeight(),
						  null);
			this.counterArrowLeftRest--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda do seletor de vidas.		---//
		//---------------------------------------------------------//
		if (this.parentGameOption.getGameRestNumber() == 4) {
			temp = imageUtil.getGrayscaleImage(this.buttonArrowLeft);
		} else {
			temp = this.buttonArrowLeft;
		}
		g2d.drawImage(temp, 
					  this.imageUtil.getScaledWidth(ARROWL_RESTS_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWL_RESTS_POSITION_Y),
					  this.buttonArrowLeft.getWidth(),
					  this.buttonArrowLeft.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha seta direita W do seletor de vidas.  		---//
		//---------------------------------------------------------//
		if (this.counterArrowRightRest > 0) {
			g2d.drawImage(this.buttonArrowRightWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWR_RESTS_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWR_RESTS_POSITION_Y),
						  this.buttonArrowRightWhite.getWidth(),
						  this.buttonArrowRightWhite.getHeight(),
						  null);
			this.counterArrowRightRest--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta direita do seletor de vidas.  		---//
		//---------------------------------------------------------//
		if (this.parentGameOption.getGameRestNumber() == 9) {
			temp = imageUtil.getGrayscaleImage(this.buttonArrowRight);
		} else {
			temp = this.buttonArrowRight;
		}
		g2d.drawImage(temp, 
					  this.imageUtil.getScaledWidth(ARROWR_RESTS_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWR_RESTS_POSITION_Y),
					  this.buttonArrowRight.getWidth(),
					  this.buttonArrowRight.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda W do seletor de extra-life.	---//
		//---------------------------------------------------------//
		if (this.counterArrowLeftExtraLife > 0) {
			g2d.drawImage(this.buttonArrowLeftWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWL_EXTRALIFE_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWL_EXTRALIFE_POSITION_Y),
						  this.buttonArrowLeftWhite.getWidth(),
						  this.buttonArrowLeftWhite.getHeight(),
						  null);
			this.counterArrowLeftExtraLife--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda do seletor de extra-life.	---//
		//---------------------------------------------------------//
		g2d.drawImage(this.buttonArrowLeft, 
					  this.imageUtil.getScaledWidth(ARROWL_EXTRALIFE_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWL_EXTRALIFE_POSITION_Y),
					  this.buttonArrowLeft.getWidth(),
					  this.buttonArrowLeft.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha seta direita W do seletor de extra-life.	---//
		//---------------------------------------------------------//
		if (this.counterArrowRightExtraLife > 0) {
			g2d.drawImage(this.buttonArrowRightWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWR_EXTRALIFE_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWR_EXTRALIFE_POSITION_Y),
						  this.buttonArrowRightWhite.getWidth(),
						  this.buttonArrowRightWhite.getHeight(),
						  null);
			this.counterArrowRightExtraLife--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta direita do seletor de extra-life.	---//
		//---------------------------------------------------------//
		g2d.drawImage(this.buttonArrowRight, 
					  this.imageUtil.getScaledWidth(ARROWR_EXTRALIFE_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWR_EXTRALIFE_POSITION_Y),
					  this.buttonArrowRight.getWidth(),
					  this.buttonArrowRight.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda W do seletor de continue.	---//
		//---------------------------------------------------------//
		if (this.counterArrowLeftContinue > 0) {
			g2d.drawImage(this.buttonArrowLeftWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWL_CONTINUES_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWL_CONTINUES_POSITION_Y),
						  this.buttonArrowLeftWhite.getWidth(),
						  this.buttonArrowLeftWhite.getHeight(),
						  null);
			this.counterArrowLeftContinue--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta esquerda do seletor de continue.		---//
		//---------------------------------------------------------//
		if (this.parentGameOption.getGameContinuesNumber() == 4) {
			temp = imageUtil.getGrayscaleImage(this.buttonArrowLeft);
		} else {
			temp = this.buttonArrowLeft;
		}
		g2d.drawImage(temp, 
					  this.imageUtil.getScaledWidth(ARROWL_CONTINUES_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWL_CONTINUES_POSITION_Y),
					  this.buttonArrowLeft.getWidth(),
					  this.buttonArrowLeft.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha seta direita W do seletor de continue.	---//
		//---------------------------------------------------------//
		if (this.counterArrowRightContinue > 0) {
			g2d.drawImage(this.buttonArrowRightWhite, 
						  this.imageUtil.getScaledWidth(W_ARROWR_CONTINUES_POSITION_X),
						  this.imageUtil.getScaledHeight(W_ARROWR_CONTINUES_POSITION_Y),
						  this.buttonArrowRightWhite.getWidth(),
						  this.buttonArrowRightWhite.getHeight(),
						  null);
			this.counterArrowRightContinue--;
		}
		
		//---------------------------------------------------------//
		//--- Desenha seta direita do seletor de continue.		---//
		//---------------------------------------------------------//
		if (this.parentGameOption.getGameContinuesNumber() == -1) {
			temp = imageUtil.getGrayscaleImage(this.buttonArrowRight);
		} else {
			temp = this.buttonArrowRight;
		}
		g2d.drawImage(temp, 
					  this.imageUtil.getScaledWidth(ARROWR_CONTINUES_POSITION_X),
					  this.imageUtil.getScaledHeight(ARROWR_CONTINUES_POSITION_Y),
					  this.buttonArrowRight.getWidth(),
					  this.buttonArrowRight.getHeight(),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha os checkbox da legenda.					---//
		//---------------------------------------------------------//
		if (this.parentGameOption.getSubtitlesEnabled()) {
			g2d.drawImage(this.checked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_YES_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_YES_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.checked.getWidth()),
						  this.imageUtil.getScaledHeight(this.checked.getHeight()),
						  null);
			
			g2d.drawImage(this.unchecked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_NO_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_NO_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
						  this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
						  null);
		} else {
			g2d.drawImage(this.unchecked, 
					  	  this.imageUtil.getScaledWidth(BUTTON_CHECK_YES_POSITION_X),
					  	  this.imageUtil.getScaledHeight(BUTTON_CHECK_YES_POSITION_Y),
					  	  this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
					  	  this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
					  	  null);
		
			g2d.drawImage(this.checked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_NO_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_NO_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.checked.getWidth()),
						  this.imageUtil.getScaledHeight(this.checked.getHeight()),
						  null);
		}
		
		//---------------------------------------------------------//
		//--- Desenha a label Sim (para subtitles).				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelYes, 
					  this.imageUtil.getScaledWidth(LABEL_YES_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_YES_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelYes.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelYes.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label Não (para subtitles).				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelNo, 
					  this.imageUtil.getScaledWidth(LABEL_NO_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_NO_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelNo.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelNo.getHeight()),
					  null);
		
		
		BufferedImage image = null;
		int x1 = VALUE_DIFFICULTY_B_POSITION_X;
		if (this.parentGameOption.getGameDifficulty() == GameDifficulty.EASY) {
			image = this.imageDifficultyEasy;
		} else if (this.parentGameOption.getGameDifficulty() == GameDifficulty.NORMAL) {
			x1 = VALUE_DIFFICULTY_POSITION_X;
			image = this.imageDifficultyNormal;
		} else if (this.parentGameOption.getGameDifficulty() == GameDifficulty.HARD) {
			image = this.imageDifficultyHard;
		}
		
		//---------------------------------------------------------//
		//--- Desenha o value para dificuldade.					---//
		//---------------------------------------------------------//
		g2d.drawImage(image, 
					  this.imageUtil.getScaledWidth(x1),
					  this.imageUtil.getScaledHeight(VALUE_DIFFICULTY_POSITION_Y),
					  this.imageUtil.getScaledWidth(image.getWidth()),
					  this.imageUtil.getScaledHeight(image.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha o value para o n�mero de vidas.			---//
		//---------------------------------------------------------//
		image = this.imageGameRests[this.parentGameOption.getGameRestNumber() - 4];
		g2d.drawImage(image, 
					  this.imageUtil.getScaledWidth(VALUE_RESTS_POSITION_X),
					  this.imageUtil.getScaledHeight(VALUE_RESTS_POSITION_Y),
					  this.imageUtil.getScaledWidth(image.getWidth()),
					  this.imageUtil.getScaledHeight(image.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha o value para o n�mero de continues.		---//
		//---------------------------------------------------------//
		if (this.parentGameOption.getGameContinuesNumber() != -1) {
			image = this.imageGameContinues[this.parentGameOption.getGameContinuesNumber() - 4 + 1];
		} else {
			image = this.imageGameContinues[0];
		}
		g2d.drawImage(image, 
					  this.imageUtil.getScaledWidth(VALUE_CONTINUES_POSITION_X),
					  this.imageUtil.getScaledHeight(VALUE_CONTINUES_POSITION_Y),
					  this.imageUtil.getScaledWidth(image.getWidth()),
					  this.imageUtil.getScaledHeight(image.getHeight()),
					  null);
		
		
		//---------------------------------------------------------//
		//--- Desenha o value para os pontos de extra-life.		---//
		//---------------------------------------------------------//
		int x = VALUE_EXTRALIFE_POSITION_X;
		int y = VALUE_EXTRALIFE_POSITION_Y;
		if (this.parentGameOption.getExtraLifeAtEachHowMuchPoints() == 100) {
			image = this.imageGameExtraLife[0];
		} else if (this.parentGameOption.getExtraLifeAtEachHowMuchPoints() == 200) {
			image = this.imageGameExtraLife[1];
		} else {
			image = this.imageGameExtraLife[2];
			x = VALUE_EXTRALIFE_NEVER_POSITION_X;
			y = VALUE_EXTRALIFE_NEVER_POSITION_Y;
		}
		g2d.drawImage(image, 
					  this.imageUtil.getScaledWidth(x),
					  this.imageUtil.getScaledHeight(y),
					  this.imageUtil.getScaledWidth(image.getWidth()),
					  this.imageUtil.getScaledHeight(image.getHeight()),
					  null);
	}

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH 	= pwidth;
		this.PHEIGHT 	= pheight;
		this.imageUtil.updateCanvasProperties(this.PWIDTH, this.PHEIGHT, currentAspectRatio);
	}

	public void resetCounters() {
		this.optionGameCounter 	= 0;
		this.optionSelected 	= 0;
	}

	public void nextOption() {
		this.optionSelected = (this.optionSelected + 1) % 6;
	}

	public void previousOption() {
		this.optionSelected = (this.optionSelected - 1);
		if (this.optionSelected < 0) {
			this.optionSelected = 5;	
		}
	}

	public boolean isOverGameDifficulty() {
		return (this.optionSelected == 0);
	}

	public boolean isOverRestsSelection() {
		return (this.optionSelected == 1);
	}

	public boolean isOverExtraLifeSelection() {
		return (this.optionSelected == 2);
	}

	public boolean isOverContinuesSelection() {
		return (this.optionSelected == 3);
	}

	public boolean isOverEnableSubtitles() {
		return (this.optionSelected == 4);
	}

	public boolean isToBackToMainOption() {
		return (this.optionSelected == 5);
	}

	public void setNextDifficulty() {
		if (this.parentGameOption.getGameDifficulty() == GameDifficulty.EASY) {
			this.parentGameOption.setGameDifficulty(GameDifficulty.NORMAL);
			this.counterArrowRightDifficulty = 6;
		} else if (this.parentGameOption.getGameDifficulty() == GameDifficulty.NORMAL) {
			this.parentGameOption.setGameDifficulty(GameDifficulty.HARD);
			this.counterArrowRightDifficulty = 6;
		}
	}

	public void setPreviousDifficulty() {
		if (this.parentGameOption.getGameDifficulty() == GameDifficulty.HARD) {
			this.parentGameOption.setGameDifficulty(GameDifficulty.NORMAL);
			this.counterArrowLeftDifficulty = 6;
		} else if (this.parentGameOption.getGameDifficulty() == GameDifficulty.NORMAL) {
			this.parentGameOption.setGameDifficulty(GameDifficulty.EASY);
			this.counterArrowLeftDifficulty = 6;
		}
	}

	public void addRest() {
		if (this.parentGameOption.getGameRestNumber() < 9) {
			this.parentGameOption.setGameRestNumber(this.parentGameOption.getGameRestNumber() + 1);
			this.counterArrowRightRest = 6;
		}
	}

	public void subRest() {
		if (this.parentGameOption.getGameRestNumber() > 4) {
			this.parentGameOption.setGameRestNumber(this.parentGameOption.getGameRestNumber() - 1);
			this.counterArrowLeftRest = 6;
		}
	}

	public void setNextExtraLifeAtPoints() {
		if (this.parentGameOption.getExtraLifeAtEachHowMuchPoints() == 100) {
			this.parentGameOption.setExtraLifeAtEachHowMuchPoints(200);
			this.counterArrowRightExtraLife = 6;
		} else if (this.parentGameOption.getExtraLifeAtEachHowMuchPoints() == 200) {
			this.parentGameOption.setExtraLifeAtEachHowMuchPoints(0);
			this.counterArrowRightExtraLife = 6;
		} else {
			this.parentGameOption.setExtraLifeAtEachHowMuchPoints(100);
			this.counterArrowRightExtraLife = 6;
		}
	}

	public void setPreviousExtraLifeAtPoints() {
		if (this.parentGameOption.getExtraLifeAtEachHowMuchPoints() == 0) {
			this.parentGameOption.setExtraLifeAtEachHowMuchPoints(200);
			this.counterArrowLeftExtraLife = 6;
		} else if (this.parentGameOption.getExtraLifeAtEachHowMuchPoints() == 200) {
			this.parentGameOption.setExtraLifeAtEachHowMuchPoints(100);
			this.counterArrowLeftExtraLife = 6;
		} else {
			this.parentGameOption.setExtraLifeAtEachHowMuchPoints(0);
			this.counterArrowLeftExtraLife = 6;
		}
	}

	public void addContinues() {
		if (this.parentGameOption.getGameContinuesNumber() < 9 && this.parentGameOption.getGameContinuesNumber() != -1) {
			this.parentGameOption.setGameContinuesNumber(this.parentGameOption.getGameContinuesNumber() + 1);
			this.counterArrowRightContinue = 6;
		} else if (this.parentGameOption.getGameContinuesNumber() == -1) {
		} else {
			this.parentGameOption.setGameContinuesNumber(-1);
			this.counterArrowRightContinue = 6;
		}
	}

	public void subContinues() {
		if (this.parentGameOption.getGameContinuesNumber() > 4) {
			this.parentGameOption.setGameContinuesNumber(this.parentGameOption.getGameContinuesNumber() - 1);
			this.counterArrowLeftContinue = 6;
		} else if (this.parentGameOption.getGameContinuesNumber() == -1) {
			this.parentGameOption.setGameContinuesNumber(9);
			this.counterArrowLeftContinue = 6;
		}
	}

	public void enableSubtitles() {
		this.parentGameOption.setSubtitlesEnabled(true);
	}

	public void disableSubtitles() {
		this.parentGameOption.setSubtitlesEnabled(false);
	}

	@Override
	public boolean finished() {
		return false;
	}

	@Override
	public void handleInput(IGame game, GameAction action) {
		if (action == GameAction.START || action == GameAction.BUTTON_1) {
			if (this.isToBackToMainOption()) {
				this.resetCounters();
				((Game)game).gotoMainOption();
			}
		} else if (action == GameAction.UP) {
			this.previousOption();

		} else if (action == GameAction.DOWN) {
			this.nextOption();

		} else if (action == GameAction.LEFT) {
			if (this.isOverEnableSubtitles()) {
				this.enableSubtitles();
			} else if (this.isOverGameDifficulty()) {
				this.setPreviousDifficulty();
			} else if (this.isOverRestsSelection()) {
				this.subRest();
			} else if (this.isOverExtraLifeSelection()) {
				this.setPreviousExtraLifeAtPoints();
			} else if (this.isOverContinuesSelection()) {
				this.subContinues();
			}

		} else if (action == GameAction.RIGHT) {
			if (this.isOverEnableSubtitles()) {
				this.disableSubtitles();
			} else if (this.isOverGameDifficulty()) {
				this.setNextDifficulty();
			} else if (this.isOverRestsSelection()) {
				this.addRest();
			} else if (this.isOverExtraLifeSelection()) {
				this.setNextExtraLifeAtPoints();
			} else if (this.isOverContinuesSelection()) {
				this.addContinues();
			}
		}
	}
}