package br.com.game.animator.game.gameUI.options;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import br.com.game.animator.game.Game;
import br.com.game.animator.game.gameData.GameSoundOptions;
import br.com.game.animator.util.ImageUtil;

/**
 * @author João Paulo Faria
 */
public class GameSoundOptionScreenImpl implements GameSoundOptionScreen {
	
	//--- Properties
	private Integer soundOptionGameCounter							= 0;
	private Integer PWIDTH											= null;
	private Integer PHEIGHT											= null;
	protected ImageUtil imageUtil									= null;
	private Integer optionSelected									= 0;
	private GameSoundOptions parentGameSoundOption					= null;
	private BufferedImage backgroundGameOptions						= null;
	private BufferedImage gameOptionsLogo							= null;
	private BufferedImage highlightButton							= null;
	private BufferedImage labelEnableMusic							= null;
	private BufferedImage labelEnableSFX							= null;
	private BufferedImage labelMusicVolume							= null;
	private BufferedImage labelSFXVolume							= null;
	private BufferedImage labelBack									= null;
	private BufferedImage checked									= null;
	private BufferedImage unchecked									= null;
	private BufferedImage labelYes									= null;
	private BufferedImage labelNo									= null;
	private BufferedImage [] imagesVolumes							= null;
	
	private final static Integer BUTTON_HIGHLIGHT_POSITION_X 		= 0;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_1 		= 317;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_2 		= 437;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_3 		= 555;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_4 		= 674;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_5 		= 791;
	private final static Integer LOGO_SOUND_OPTIONS_POSITION_X 		= 1008;
	private final static Integer LOGO_SOUND_OPTIONS_POSITION_Y 		= 31;
	private final static Integer LABEL_ENABLE_MUSIC_POSITION_X 		= 82;
	private final static Integer LABEL_ENABLE_MUSIC_POSITION_Y 		= 341;
	private final static Integer LABEL_ENABLE_SFX_POSITION_X 		= 82;
	private final static Integer LABEL_ENABLE_SFX_POSITION_Y 		= 459;
	private final static Integer LABEL_MUSIC_VOL_POSITION_X 		= 82;
	private final static Integer LABEL_MUSIC_VOL_POSITION_Y 		= 577;
	private final static Integer LABEL_SFX_VOL_POSITION_X 			= 82;
	private final static Integer LABEL_SFX_VOL_POSITION_Y 			= 696;
	private final static Integer LABEL_BACK_POSITION_X 				= 82;
	private final static Integer LABEL_BACK_POSITION_Y 				= 813;
	private final static Integer LABEL_YES_MUSIC_POSITION_X 		= 762 - 48;
	private final static Integer LABEL_YES_MUSIC_POSITION_Y 		= 352;
	private final static Integer LABEL_NO_MUSIC_POSITION_X 			= 939 - 54;
	private final static Integer LABEL_NO_MUSIC_POSITION_Y 			= 352;
	private final static Integer LABEL_YES_SFX_POSITION_X 			= 762 - 48;
	private final static Integer LABEL_YES_SFX_POSITION_Y 			= 472;
	private final static Integer LABEL_NO_SFX_POSITION_X 			= 939 - 54;
	private final static Integer LABEL_NO_SFX_POSITION_Y 			= 472;
	private final static Integer BUTTON_CHECK_MUSIC_YES_POSITION_X 	= 702;
	private final static Integer BUTTON_CHECK_MUSIC_YES_POSITION_Y 	= 348;
	private final static Integer BUTTON_CHECK_MUSIC_NO_POSITION_X 	= 878;
	private final static Integer BUTTON_CHECK_MUSIC_NO_POSITION_Y 	= 349;
	private final static Integer BUTTON_CHECK_SFX_YES_POSITION_X 	= 702;
	private final static Integer BUTTON_CHECK_SFX_YES_POSITION_Y 	= 468;
	private final static Integer BUTTON_CHECK_SFX_NO_POSITION_X 	= 878;
	private final static Integer BUTTON_CHECK_SFX_NO_POSITION_Y 	= 469;
	private final static Integer COMMAND_MUSIC_VOL_POSITION_X 		= 703;
	private final static Integer COMMAND_MUSIC_VOL_POSITION_Y 		= 572;
	private final static Integer COMMAND_SFX_VOL_POSITION_X 		= 703;
	private final static Integer COMMAND_SFX_VOL_POSITION_Y 		= 692;
	
	/**
	 * Constructor
	 * @param parentGameSoundOption
	 * @param pwidth
	 * @param pheight
	 * @param currentAspectRatio
	 */
	public GameSoundOptionScreenImpl(GameSoundOptions parentGameSoundOption, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		//--- Atualiza o tamanho do canvas
		this.PWIDTH 						= pwidth;
		this.PHEIGHT 						= pheight;
		this.imageUtil 						= new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.parentGameSoundOption			= parentGameSoundOption;
		this.backgroundGameOptions			= this.imageUtil.loadImage("/res/images/bg_option_screen4.png");
		this.gameOptionsLogo				= this.imageUtil.loadImage("/res/images/logo_sound_config.png");
		this.highlightButton				= this.imageUtil.loadImage("/res/images/optg_gameoption_selector.png");
		this.labelEnableMusic				= this.imageUtil.loadImage("/res/images/sound_enable_music_label.png");
		this.labelEnableSFX					= this.imageUtil.loadImage("/res/images/sound_enable_sfx_label.png");
		this.labelMusicVolume				= this.imageUtil.loadImage("/res/images/sound_music_volume_label.png");
		this.labelSFXVolume					= this.imageUtil.loadImage("/res/images/sound_sfx_volume_label.png");
		this.labelBack						= this.imageUtil.loadImage("/res/images/optg_back.png");
		this.checked						= this.imageUtil.loadImage("/res/images/optg_check.png");
		this.unchecked						= this.imageUtil.loadImage("/res/images/optg_uncheck.png");
		this.labelYes						= this.imageUtil.loadImage("/res/images/optg_yes.png");
		this.labelNo						= this.imageUtil.loadImage("/res/images/optg_no.png");
		this.imagesVolumes					= new BufferedImage[10];
		this.imagesVolumes[0]				= this.imageUtil.loadImage("/res/images/volume_0.png");
		this.imagesVolumes[1]				= this.imageUtil.loadImage("/res/images/volume_1.png");
		this.imagesVolumes[2]				= this.imageUtil.loadImage("/res/images/volume_2.png");
		this.imagesVolumes[3]				= this.imageUtil.loadImage("/res/images/volume_3.png");
		this.imagesVolumes[4]				= this.imageUtil.loadImage("/res/images/volume_4.png");
		this.imagesVolumes[5]				= this.imageUtil.loadImage("/res/images/volume_5.png");
		this.imagesVolumes[6]				= this.imageUtil.loadImage("/res/images/volume_6.png");
		this.imagesVolumes[7]				= this.imageUtil.loadImage("/res/images/volume_7.png");
		this.imagesVolumes[8]				= this.imageUtil.loadImage("/res/images/volume_8.png");
		this.imagesVolumes[9]				= this.imageUtil.loadImage("/res/images/volume_9.png");
	}
	
	public void update(long frametime) {
		this.soundOptionGameCounter++;
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
					  this.imageUtil.getScaledWidthScaledWithInformed4x3(LOGO_SOUND_OPTIONS_POSITION_X, 450),
					  this.imageUtil.getScaledHeight(LOGO_SOUND_OPTIONS_POSITION_Y),
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
		}
		
		g2d.drawImage(this.highlightButton, 
					  this.imageUtil.getScaledWidth(BUTTON_HIGHLIGHT_POSITION_X),
					  this.imageUtil.getScaledHeight(selector),
					  this.imageUtil.getScaledWidth(this.highlightButton.getWidth()),
					  this.imageUtil.getScaledHeight(this.highlightButton.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do Controle de Músicas.  			---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelEnableMusic, 
					  this.imageUtil.getScaledWidth(LABEL_ENABLE_MUSIC_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_ENABLE_MUSIC_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelEnableMusic.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelEnableMusic.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do Controle de SFX.  				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelEnableSFX, 
					  this.imageUtil.getScaledWidth(LABEL_ENABLE_SFX_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_ENABLE_SFX_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelEnableSFX.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelEnableSFX.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label  do volume das Músicas.			---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelMusicVolume, 
					  this.imageUtil.getScaledWidth(LABEL_MUSIC_VOL_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_MUSIC_VOL_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelMusicVolume.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelMusicVolume.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label do volume do SFX.       			---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelSFXVolume, 
					  this.imageUtil.getScaledWidth(LABEL_SFX_VOL_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_SFX_VOL_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelSFXVolume.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelSFXVolume.getHeight()),
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
		//--- Desenha os checkbox da música.					---//
		//---------------------------------------------------------//
		if (this.parentGameSoundOption.getMusicEnabled()) {
			g2d.drawImage(this.checked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_MUSIC_YES_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_MUSIC_YES_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.checked.getWidth()),
						  this.imageUtil.getScaledHeight(this.checked.getHeight()),
						  null);
			
			g2d.drawImage(this.unchecked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_MUSIC_NO_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_MUSIC_NO_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
						  this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
						  null);
		} else {
			g2d.drawImage(this.unchecked, 
					  	  this.imageUtil.getScaledWidth(BUTTON_CHECK_MUSIC_YES_POSITION_X),
					  	  this.imageUtil.getScaledHeight(BUTTON_CHECK_MUSIC_YES_POSITION_Y),
					  	  this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
					  	  this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
					  	  null);
		
			g2d.drawImage(this.checked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_MUSIC_NO_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_MUSIC_NO_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.checked.getWidth()),
						  this.imageUtil.getScaledHeight(this.checked.getHeight()),
						  null);
		}
		
		//---------------------------------------------------------//
		//--- Desenha os checkbox dos SFX.						---//
		//---------------------------------------------------------//
		if (this.parentGameSoundOption.getSFXEnabled()) {
			g2d.drawImage(this.checked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_SFX_YES_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_SFX_YES_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.checked.getWidth()),
						  this.imageUtil.getScaledHeight(this.checked.getHeight()),
						  null);
			
			g2d.drawImage(this.unchecked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_SFX_NO_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_SFX_NO_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
						  this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
						  null);
		} else {
			g2d.drawImage(this.unchecked, 
					  	  this.imageUtil.getScaledWidth(BUTTON_CHECK_SFX_YES_POSITION_X),
					  	  this.imageUtil.getScaledHeight(BUTTON_CHECK_SFX_YES_POSITION_Y),
					  	  this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
					  	  this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
					  	  null);
		
			g2d.drawImage(this.checked, 
						  this.imageUtil.getScaledWidth(BUTTON_CHECK_SFX_NO_POSITION_X),
						  this.imageUtil.getScaledHeight(BUTTON_CHECK_SFX_NO_POSITION_Y),
						  this.imageUtil.getScaledWidth(this.checked.getWidth()),
						  this.imageUtil.getScaledHeight(this.checked.getHeight()),
						  null);
		}
		
		//---------------------------------------------------------//
		//--- Desenha a label Sim (para músicas).				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelYes, 
					  this.imageUtil.getScaledWidth(LABEL_YES_MUSIC_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_YES_MUSIC_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelYes.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelYes.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label Não (para músicas).				---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelNo, 
					  this.imageUtil.getScaledWidth(LABEL_NO_MUSIC_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_NO_MUSIC_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelNo.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelNo.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label Sim (para SFX).					---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelYes, 
					  this.imageUtil.getScaledWidth(LABEL_YES_SFX_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_YES_SFX_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelYes.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelYes.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha a label Não (para SFX).					---//
		//---------------------------------------------------------//
		g2d.drawImage(this.labelNo, 
					  this.imageUtil.getScaledWidth(LABEL_NO_SFX_POSITION_X),
					  this.imageUtil.getScaledHeight(LABEL_NO_SFX_POSITION_Y),
					  this.imageUtil.getScaledWidth(this.labelNo.getWidth()),
					  this.imageUtil.getScaledHeight(this.labelNo.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha o volume das músicas.						---//
		//---------------------------------------------------------//
		BufferedImage image = this.imagesVolumes[this.parentGameSoundOption.getMusicVolume()];
		g2d.drawImage(image, 
					  this.imageUtil.getScaledWidth(COMMAND_MUSIC_VOL_POSITION_X),
					  this.imageUtil.getScaledHeight(COMMAND_MUSIC_VOL_POSITION_Y),
					  this.imageUtil.getScaledWidth(image.getWidth()),
					  this.imageUtil.getScaledHeight(image.getHeight()),
					  null);
		
		//---------------------------------------------------------//
		//--- Desenha o volume das músicas.						---//
		//---------------------------------------------------------//
		image = this.imagesVolumes[this.parentGameSoundOption.getSFXVolume()];
		g2d.drawImage(image, 
					  this.imageUtil.getScaledWidth(COMMAND_SFX_VOL_POSITION_X),
					  this.imageUtil.getScaledHeight(COMMAND_SFX_VOL_POSITION_Y),
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
		this.soundOptionGameCounter = 0;
		this.optionSelected 		= 0;
	}

	public void nextOption() {
		this.optionSelected = (this.optionSelected + 1) % 5;
		
		if (!this.parentGameSoundOption.getMusicEnabled() && !this.parentGameSoundOption.getSFXEnabled() && 
			(this.optionSelected == 3 || this.optionSelected == 2)) {
			this.optionSelected = 4;
		} else if (!this.parentGameSoundOption.getMusicEnabled() && this.optionSelected == 2) {
			this.optionSelected = 3;
		} else if (!this.parentGameSoundOption.getSFXEnabled() && this.optionSelected == 3) {
			this.optionSelected = 4;
		}
		
	}

	public void previousOption() {
		this.optionSelected = (this.optionSelected - 1);
		if (this.optionSelected < 0) {
			this.optionSelected = 4;	
		}
		
		if (!this.parentGameSoundOption.getMusicEnabled() && !this.parentGameSoundOption.getSFXEnabled() && 
			(this.optionSelected == 3 || this.optionSelected == 2)) {
			this.optionSelected = 1;
		} else if (!this.parentGameSoundOption.getMusicEnabled() && this.optionSelected == 2) {
			this.optionSelected = 1;
		} else if (!this.parentGameSoundOption.getSFXEnabled() && this.optionSelected == 3) {
			this.optionSelected = 2;
		}
	}

	public boolean isToBackToMainOption() {
		return (this.optionSelected == 4);
	}

	public boolean isOverEnableMusic() {
		return (this.optionSelected == 0);
	}

	public boolean isOverEnableSFX() {
		return (this.optionSelected == 1);
	}

	public boolean isOverMusicVolume() {
		return (this.optionSelected == 2);
	}

	public boolean isOverSFXVolume() {
		return (this.optionSelected == 3);
	}

	public void setMusicEnable() {
		this.parentGameSoundOption.setMusicEnabled(true);
		this.parentGameSoundOption.setMusicVolume(3);
	}

	public void setMusicDisable() {
		this.parentGameSoundOption.setMusicEnabled(false);
		this.parentGameSoundOption.setMusicVolume(0);
	}

	public void setSFXEnable() {
		this.parentGameSoundOption.setSFXEnabled(true);
		this.parentGameSoundOption.setSFXVolume(3);
	}

	public void setSFXDisable() {
		this.parentGameSoundOption.setSFXEnabled(false);
		this.parentGameSoundOption.setSFXVolume(0);
	}

	public void increaseMusicVolume() {
		if (this.parentGameSoundOption.getMusicVolume() < 9) {
			this.parentGameSoundOption.setMusicVolume(this.parentGameSoundOption.getMusicVolume() + 1);
		}
	}

	public void decreaseMusicVolume() {
		if (this.parentGameSoundOption.getMusicVolume() > 1) {
			this.parentGameSoundOption.setMusicVolume(this.parentGameSoundOption.getMusicVolume() - 1);
		}
	}

	public void increaseSFXVolume() {
		if (this.parentGameSoundOption.getSFXVolume() < 9) {
			this.parentGameSoundOption.setSFXVolume(this.parentGameSoundOption.getSFXVolume() + 1);
		}
	}

	public void decreaseSFXVolume() {
		if (this.parentGameSoundOption.getSFXVolume() > 1) {
			this.parentGameSoundOption.setSFXVolume(this.parentGameSoundOption.getSFXVolume() - 1);
		}
	}

	@Override
	public boolean finished() {
		return false;
	}

	@Override
	public void handleInput(Game game, int keyCode, boolean isAltDown) {
		if (keyCode == KeyEvent.VK_ENTER) {
			if (this.isToBackToMainOption()) {
				this.resetCounters();
				game.gotoMainOption();
			}
		} else if (keyCode == KeyEvent.VK_UP) {
			this.previousOption();

		} else if (keyCode == KeyEvent.VK_DOWN) {
			this.nextOption();

		} else if (keyCode == KeyEvent.VK_LEFT) {
			if (this.isOverEnableMusic()) {
				this.setMusicEnable();
			} else if (this.isOverEnableSFX()) {
				this.setSFXEnable();
			} else if (this.isOverMusicVolume()) {
				this.decreaseMusicVolume();
			} else if (this.isOverSFXVolume()) {
				this.decreaseSFXVolume();
			}

		} else if (keyCode == KeyEvent.VK_RIGHT) {

			if (this.isOverEnableMusic()) {
				this.setMusicDisable();
			} else if (this.isOverEnableSFX()) {
				this.setSFXDisable();
			} else if (this.isOverMusicVolume()) {
				this.increaseMusicVolume();
			} else if (this.isOverSFXVolume()) {
				this.increaseSFXVolume();
			}
		}
	}
}