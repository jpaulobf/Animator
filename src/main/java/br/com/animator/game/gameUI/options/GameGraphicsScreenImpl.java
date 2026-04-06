package br.com.animator.game.gameUI.options;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import br.com.animator.game.Game;
import br.com.animator.game.gameData.GameGraphics;
import br.com.animator.game.gameData.GameGraphicsImpl;
import br.com.animator.game.gameData.enumerators.DeepColor;
import br.com.animator.game.gameData.enumerators.ScreenMode;
import br.com.animator.util.ImageUtil;

/**
 * Game Graphics Screen Implementation.
 */
public class GameGraphicsScreenImpl implements GameGraphicsScreen {

	// Properties
	private Integer graphicsOptionGameCounter = 0;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;
	protected ImageUtil imageUtil = null;
	private Integer optionSelected = 0;
	private GameGraphics parentGameGraphicsOption = null;
	private GameGraphics originalGameGraphicsOption = null;
	private BufferedImage backgroundGameOptions = null;
	private BufferedImage gameOptionsLogo = null;
	private BufferedImage highlightButton = null;
	private BufferedImage labelScreenResolution = null;
	private BufferedImage labelEnableTripleBuffer = null;
	private BufferedImage labelScreenMode = null;
	private BufferedImage labelDeepColor = null;
	private BufferedImage labelApply = null;
	private BufferedImage labelCancel = null;
	private BufferedImage checked = null;
	private BufferedImage unchecked = null;
	private BufferedImage labelYes = null;
	private BufferedImage labelNo = null;
	private BufferedImage buttonArrowLeft = null;
	private BufferedImage buttonArrowRight = null;
	private BufferedImage buttonArrowLeftWhite = null;
	private BufferedImage buttonArrowRightWhite = null;
	private BufferedImage screenResolutionStrip = null;
	private BufferedImage screenResolutionValue = null;
	private BufferedImage screenModeStrip = null;
	private BufferedImage[] screenModeValues = null;
	private BufferedImage deepColorStrip = null;
	private BufferedImage[] deepColorValues = null;
	private BufferedImage mustApplyForChanges = null;
	private volatile boolean showMustApply = false;

	private final static Integer LOGO_GAME_GRAPHICS_POSITION_X = 956;
	private final static Integer LOGO_GAME_GRAPHICS_POSITION_Y = 28;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_X = 0;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_1 = 255;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_2 = 358;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_3 = 464;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_4 = 566;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_5 = 827;
	private final static Integer BUTTON_HIGHLIGHT_POSITION_Y_6 = 927;
	private final static Integer LABEL_SCREENRES_POSITION_X = 60;
	private final static Integer LABEL_SCREENRES_POSITION_Y = 278;
	private final static Integer LABEL_ENABLEBUFFER_POSITION_X = 60;
	private final static Integer LABEL_ENABLEBUFFER_POSITION_Y = 380;
	private final static Integer LABEL_SCREENMODE_POSITION_X = 60;
	private final static Integer LABEL_SCREENMODE_POSITION_Y = 488;
	private final static Integer LABEL_DEEPCOLOR_POSITION_X = 60;
	private final static Integer LABEL_DEEPCOLOR_POSITION_Y = 589;
	private final static Integer LABEL_APPLY_POSITION_X = 60;
	private final static Integer LABEL_APPLY_POSITION_Y = 849;
	private final static Integer LABEL_CANCEL_POSITION_X = 60;
	private final static Integer LABEL_CANCEL_POSITION_Y = 949;
	// private final static Integer VALUE_SCREENRES_POSITION_X = 0;
	// private final static Integer VALUE_SCREENRES_POSITION_Y = 0;
	private final static Integer LABEL_YES_BUFFER_POSITION_X = 802 - 48;
	private final static Integer LABEL_YES_BUFFER_POSITION_Y = 388;
	private final static Integer LABEL_NO_BUFFER_POSITION_X = 979 - 54;
	private final static Integer LABEL_NO_BUFFER_POSITION_Y = 388;
	private final static Integer BUTTON_CHECK_YES_BUFFER_POSITION_X = 742;
	private final static Integer BUTTON_CHECK_YES_BUFFER_POSITION_Y = 388;
	private final static Integer BUTTON_CHECK_NO_BUFFER_POSITION_X = 918;
	private final static Integer BUTTON_CHECK_NO_BUFFER_POSITION_Y = 388;
	private final static Integer VALUE_SCREENMODE_POSITION_X = 805;
	private final static Integer VALUE_SCREENMODE_POSITION_Y = 495;
	private final static Integer VALUE_DEEPCOLOR_POSITION_X = 798;
	private final static Integer VALUE_DEEPCOLOR_POSITION_Y = 597;
	private final static Integer W_ARROWL_SCREENRES_POSITION_X = 743;
	private final static Integer W_ARROWL_SCREENRES_POSITION_Y = 293;
	private final static Integer W_ARROWR_SCREENRES_POSITION_X = 1130;
	private final static Integer W_ARROWR_SCREENRES_POSITION_Y = 293;
	private final static Integer ARROWL_SCREENRES_POSITION_X = 748;
	private final static Integer ARROWL_SCREENRES_POSITION_Y = 296;
	private final static Integer ARROWR_SCREENRES_POSITION_X = 1134;
	private final static Integer ARROWR_SCREENRES_POSITION_Y = 296;
	private final static Integer W_ARROWL_SCREENMODE_POSITION_X = 743;
	private final static Integer W_ARROWL_SCREENMODE_POSITION_Y = 503;
	private final static Integer W_ARROWR_SCREENMODE_POSITION_X = 1000;
	private final static Integer W_ARROWR_SCREENMODE_POSITION_Y = 503;
	private final static Integer ARROWL_SCREENMODE_POSITION_X = 748;
	private final static Integer ARROWL_SCREENMODE_POSITION_Y = 506;
	private final static Integer ARROWR_SCREENMODE_POSITION_X = 1004;
	private final static Integer ARROWR_SCREENMODE_POSITION_Y = 506;
	private final static Integer W_ARROWL_DEEPCOLOR_POSITION_X = 743;
	private final static Integer W_ARROWL_DEEPCOLOR_POSITION_Y = 604;
	private final static Integer W_ARROWR_DEEPCOLOR_POSITION_X = 1130;
	private final static Integer W_ARROWR_DEEPCOLOR_POSITION_Y = 604;
	private final static Integer ARROWL_DEEPCOLOR_POSITION_X = 748;
	private final static Integer ARROWL_DEEPCOLOR_POSITION_Y = 607;
	private final static Integer ARROWR_DEEPCOLOR_POSITION_X = 1134;
	private final static Integer ARROWR_DEEPCOLOR_POSITION_Y = 607;
	private final static Integer LABEL_MUST_APPLY_POSITION_X = 225;
	private final static Integer LABEL_MUST_APPLY_POSITION_Y = 869;
	private Integer counterArrowLeftScreenRes = 0;
	private Integer counterArrowRightScreenRes = 0;
	private Integer counterArrowLeftScreenMode = 0;
	private Integer counterArrowRightScreenMode = 0;
	private Integer counterArrowLeftDeepColor = 0;
	private Integer counterArrowRightDeepColor = 0;

	/**
	 * Constructor
	 * 
	 * @param parentGameGraphicsOption
	 * @param pwidth
	 * @param pheight
	 */
	public GameGraphicsScreenImpl(GameGraphics parentGameGraphicsOption, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		// --- Atualiza o tamanho do canvas.
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil = new ImageUtil(pwidth, pheight, currentAspectRatio);
		this.parentGameGraphicsOption = parentGameGraphicsOption;

		// --- Guardo um clone do GameGraphics 
		this.originalGameGraphicsOption = new GameGraphicsImpl(parentGameGraphicsOption);

		// --- Carrega as imagens.
		this.backgroundGameOptions = this.imageUtil.loadImage("/images/bg_option_screen5.png");
		this.gameOptionsLogo = this.imageUtil.loadImage("/images/logo_graphics_config.png");
		this.highlightButton = this.imageUtil.loadImage("/images/optg_gameoption_selector.png");
		this.labelScreenResolution = this.imageUtil.loadImage("/images/gfx_screen_res_label.png");
		this.labelEnableTripleBuffer = this.imageUtil.loadImage("/images/gfx_enable_triple_label.png");
		this.labelScreenMode = this.imageUtil.loadImage("/images/gfx_screen_mode_label.png");
		this.labelDeepColor = this.imageUtil.loadImage("/images/gfx_deep_color_label.png");
		this.labelApply = this.imageUtil.loadImage("/images/gfx_apply_label.png");
		this.labelCancel = this.imageUtil.loadImage("/images/gfx_cancel_label.png");
		this.screenResolutionStrip = this.imageUtil.loadImage("/images/gfx_number_value.png");
		this.screenModeStrip = this.imageUtil.loadImage("/images/gfx_screen_mode_value.png");
		this.deepColorStrip = this.imageUtil.loadImage("/images/gfx_deep_colors_value.png");
		this.checked = this.imageUtil.loadImage("/images/optg_check.png");
		this.unchecked = this.imageUtil.loadImage("/images/optg_uncheck.png");
		this.labelYes = this.imageUtil.loadImage("/images/optg_yes.png");
		this.labelNo = this.imageUtil.loadImage("/images/optg_no.png");
		this.buttonArrowLeft = this.imageUtil.loadImage("/images/bt_arrow_left.png");
		this.buttonArrowRight = this.imageUtil.loadImage("/images/bt_arrow_right.png");
		this.buttonArrowLeftWhite = this.imageUtil.loadImage("/images/bt_arrow_left_white.png");
		this.buttonArrowRightWhite = this.imageUtil.loadImage("/images/bt_arrow_right_white.png");
		this.screenModeValues = new BufferedImage[2];
		this.screenModeValues[0] = this.imageUtil.copyImage(this.screenModeStrip, 0, 0, 153, 27);
		this.screenModeValues[1] = this.imageUtil.copyImage(this.screenModeStrip, 181, 0, 164, 27);
		this.deepColorValues = new BufferedImage[2];
		this.deepColorValues[0] = this.imageUtil.copyImage(this.deepColorStrip, 0, 0, 299, 33);
		this.deepColorValues[1] = this.imageUtil.copyImage(this.deepColorStrip, 337, 0, 260, 33);
		this.mustApplyForChanges = this.imageUtil.loadImage("/images/gfx_must_apply.png");

		// TODO: VERIFICAR SE DESEJA MONTAR LISTA DE IMAGEM DE RESOLUCOES...

	}

	public void update(long frametime) {
		this.graphicsOptionGameCounter++;
	}


	public void draw(Graphics2D g2d) {
		// ---------------------------------------------------------//
		// --- Desenha a tela de plano de fundos. --//
		// ---------------------------------------------------------//
		g2d.drawImage(this.backgroundGameOptions,
				0,
				0,
				this.imageUtil.getScaledWidth(this.backgroundGameOptions.getWidth()),
				this.imageUtil.getScaledHeight(this.backgroundGameOptions.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a logo na tela de plano de fundos. --//
		// ---------------------------------------------------------//
		g2d.drawImage(this.gameOptionsLogo,
				this.imageUtil.getScaledWidthScaledWithInformed4x3(LOGO_GAME_GRAPHICS_POSITION_X, 480),
				this.imageUtil.getScaledHeight(LOGO_GAME_GRAPHICS_POSITION_Y),
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

		// ---------------------------------------------------------//
		// --- Desenha a label do Screen Resolution. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelScreenResolution,
				this.imageUtil.getScaledWidth(LABEL_SCREENRES_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_SCREENRES_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelScreenResolution.getWidth()),
				this.imageUtil.getScaledHeight(this.labelScreenResolution.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a label do Triple Buffering. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelEnableTripleBuffer,
				this.imageUtil.getScaledWidth(LABEL_ENABLEBUFFER_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_ENABLEBUFFER_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelEnableTripleBuffer.getWidth()),
				this.imageUtil.getScaledHeight(this.labelEnableTripleBuffer.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a label do Screen Mode. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelScreenMode,
				this.imageUtil.getScaledWidth(LABEL_SCREENMODE_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_SCREENMODE_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelScreenMode.getWidth()),
				this.imageUtil.getScaledHeight(this.labelScreenMode.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a label do Deep Color. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelDeepColor,
				this.imageUtil.getScaledWidth(LABEL_DEEPCOLOR_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_DEEPCOLOR_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelDeepColor.getWidth()),
				this.imageUtil.getScaledHeight(this.labelDeepColor.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a label do Apply. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelApply,
				this.imageUtil.getScaledWidth(LABEL_APPLY_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_APPLY_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelApply.getWidth()),
				this.imageUtil.getScaledHeight(this.labelApply.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a label do Cancel. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelCancel,
				this.imageUtil.getScaledWidth(LABEL_CANCEL_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_CANCEL_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelCancel.getWidth()),
				this.imageUtil.getScaledHeight(this.labelCancel.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha seta esquerda W do seletor de resolu��o ---//
		// ---------------------------------------------------------//
		if (this.counterArrowLeftScreenRes > 0) {
			g2d.drawImage(this.buttonArrowLeftWhite,
					this.imageUtil.getScaledWidth(W_ARROWL_SCREENRES_POSITION_X),
					this.imageUtil.getScaledHeight(W_ARROWL_SCREENRES_POSITION_Y),
					this.buttonArrowLeftWhite.getWidth(),
					this.buttonArrowLeftWhite.getHeight(),
					null);
			this.counterArrowLeftScreenRes--;
		}

		// ---------------------------------------------------------//
		// --- Desenha seta direita W do seletor de resolu��o ---//
		// ---------------------------------------------------------//
		if (this.counterArrowRightScreenRes > 0) {
			g2d.drawImage(this.buttonArrowRightWhite,
					this.imageUtil.getScaledWidth(W_ARROWR_SCREENRES_POSITION_X),
					this.imageUtil.getScaledHeight(W_ARROWR_SCREENRES_POSITION_Y),
					this.buttonArrowRightWhite.getWidth(),
					this.buttonArrowRightWhite.getHeight(),
					null);
			this.counterArrowRightScreenRes--;
		}

		// ---------------------------------------------------------//
		// --- Desenha seta esquerda do seletor de resolu��o ---//
		// ---------------------------------------------------------//
		BufferedImage image = null;
		if (this.isFirstResolutionFromList()) {
			image = this.imageUtil.getGrayscaleImage(this.buttonArrowLeft);
		} else {
			image = this.buttonArrowLeft;
		}
		g2d.drawImage(image,
				this.imageUtil.getScaledWidth(ARROWL_SCREENRES_POSITION_X),
				this.imageUtil.getScaledHeight(ARROWL_SCREENRES_POSITION_Y),
				this.buttonArrowLeft.getWidth(),
				this.buttonArrowLeft.getHeight(),
				null);

		// ---------------------------------------------------------//
		// --- Desenha seta direita do seletor de resolu��o ---//
		// ---------------------------------------------------------//
		if (this.isLastResolutionFromList()) {
			image = this.imageUtil.getGrayscaleImage(this.buttonArrowRight);
		} else {
			image = this.buttonArrowRight;
		}
		g2d.drawImage(image,
				this.imageUtil.getScaledWidth(ARROWR_SCREENRES_POSITION_X),
				this.imageUtil.getScaledHeight(ARROWR_SCREENRES_POSITION_Y),
				this.buttonArrowRight.getWidth(),
				this.buttonArrowRight.getHeight(),
				null);

		// ---------------------------------------------------------//
		// --- Desenha seta esquerda W do seletor de modo de tela --//
		// ---------------------------------------------------------//
		if (this.counterArrowLeftScreenMode > 0) {
			g2d.drawImage(this.buttonArrowLeftWhite,
					this.imageUtil.getScaledWidth(W_ARROWL_SCREENMODE_POSITION_X),
					this.imageUtil.getScaledHeight(W_ARROWL_SCREENMODE_POSITION_Y),
					this.buttonArrowLeftWhite.getWidth(),
					this.buttonArrowLeftWhite.getHeight(),
					null);
			this.counterArrowLeftScreenMode--;
		}

		// ---------------------------------------------------------//
		// --- Desenha seta direita W do seletor de modo de tela ---//
		// ---------------------------------------------------------//
		if (this.counterArrowRightScreenMode > 0) {
			g2d.drawImage(this.buttonArrowRightWhite,
					this.imageUtil.getScaledWidth(W_ARROWR_SCREENMODE_POSITION_X),
					this.imageUtil.getScaledHeight(W_ARROWR_SCREENMODE_POSITION_Y),
					this.buttonArrowRightWhite.getWidth(),
					this.buttonArrowRightWhite.getHeight(),
					null);
			this.counterArrowRightScreenMode--;
		}

		// ---------------------------------------------------------//
		// --- Desenha seta esquerda do seletor do modo de tela ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.buttonArrowLeft,
				this.imageUtil.getScaledWidth(ARROWL_SCREENMODE_POSITION_X),
				this.imageUtil.getScaledHeight(ARROWL_SCREENMODE_POSITION_Y),
				this.buttonArrowLeft.getWidth(),
				this.buttonArrowLeft.getHeight(),
				null);

		// ---------------------------------------------------------//
		// --- Desenha seta direita do seletor do modo de tela. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.buttonArrowRight,
				this.imageUtil.getScaledWidth(ARROWR_SCREENMODE_POSITION_X),
				this.imageUtil.getScaledHeight(ARROWR_SCREENMODE_POSITION_Y),
				this.buttonArrowRight.getWidth(),
				this.buttonArrowRight.getHeight(),
				null);

		// ---------------------------------------------------------//
		// --- Desenha seta esquerda W do seletor de cor ---//
		// ---------------------------------------------------------//
		if (this.counterArrowLeftDeepColor > 0) {
			g2d.drawImage(this.buttonArrowLeftWhite,
					this.imageUtil.getScaledWidth(W_ARROWL_DEEPCOLOR_POSITION_X),
					this.imageUtil.getScaledHeight(W_ARROWL_DEEPCOLOR_POSITION_Y),
					this.buttonArrowLeftWhite.getWidth(),
					this.buttonArrowLeftWhite.getHeight(),
					null);
			this.counterArrowLeftDeepColor--;
		}

		// ---------------------------------------------------------//
		// --- Desenha seta direita W do seletor de cor ---//
		// ---------------------------------------------------------//
		if (this.counterArrowRightDeepColor > 0) {
			g2d.drawImage(this.buttonArrowRightWhite,
					this.imageUtil.getScaledWidth(W_ARROWR_DEEPCOLOR_POSITION_X),
					this.imageUtil.getScaledHeight(W_ARROWR_DEEPCOLOR_POSITION_Y),
					this.buttonArrowRightWhite.getWidth(),
					this.buttonArrowRightWhite.getHeight(),
					null);
			this.counterArrowRightDeepColor--;
		}

		// ---------------------------------------------------------//
		// --- Desenha seta esquerda do seletor de cor. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.buttonArrowLeft,
				this.imageUtil.getScaledWidth(ARROWL_DEEPCOLOR_POSITION_X),
				this.imageUtil.getScaledHeight(ARROWL_DEEPCOLOR_POSITION_Y),
				this.buttonArrowLeft.getWidth(),
				this.buttonArrowLeft.getHeight(),
				null);

		// ---------------------------------------------------------//
		// --- Desenha seta direita do seletor de cor. ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.buttonArrowRight,
				this.imageUtil.getScaledWidth(ARROWR_DEEPCOLOR_POSITION_X),
				this.imageUtil.getScaledHeight(ARROWR_DEEPCOLOR_POSITION_Y),
				this.buttonArrowRight.getWidth(),
				this.buttonArrowRight.getHeight(),
				null);

		// ---------------------------------------------------------//
		// --- Desenha o valor selecionado do deep color. ---//
		// ---------------------------------------------------------//
		if (this.parentGameGraphicsOption.getScreenMode() == ScreenMode.FULLSCREEN) {
			image = this.screenModeValues[0];
		} else {
			image = this.screenModeValues[1];
		}
		g2d.drawImage(image,
				this.imageUtil.getScaledWidth(VALUE_SCREENMODE_POSITION_X),
				this.imageUtil.getScaledHeight(VALUE_SCREENMODE_POSITION_Y),
				this.imageUtil.getScaledWidth(image.getWidth()),
				this.imageUtil.getScaledHeight(image.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha o valor selecionado do deep color. ---//
		// ---------------------------------------------------------//
		image = (this.parentGameGraphicsOption.getScreenDeepColor() == null || 
				 this.parentGameGraphicsOption.getScreenDeepColor() == DeepColor.TRUE_COLOR_32_BITS)?this.deepColorValues[0]:this.deepColorValues[1];
		g2d.drawImage(image,
				this.imageUtil.getScaledWidth(VALUE_DEEPCOLOR_POSITION_X),
				this.imageUtil.getScaledHeight(VALUE_DEEPCOLOR_POSITION_Y),
				this.imageUtil.getScaledWidth(image.getWidth()),
				this.imageUtil.getScaledHeight(image.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a label Sim (para triple buffering). ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelYes,
				this.imageUtil.getScaledWidth(LABEL_YES_BUFFER_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_YES_BUFFER_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelYes.getWidth()),
				this.imageUtil.getScaledHeight(this.labelYes.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha a label N�o (para triple buffering). ---//
		// ---------------------------------------------------------//
		g2d.drawImage(this.labelNo,
				this.imageUtil.getScaledWidth(LABEL_NO_BUFFER_POSITION_X),
				this.imageUtil.getScaledHeight(LABEL_NO_BUFFER_POSITION_Y),
				this.imageUtil.getScaledWidth(this.labelNo.getWidth()),
				this.imageUtil.getScaledHeight(this.labelNo.getHeight()),
				null);

		// ---------------------------------------------------------//
		// --- Desenha os checkbox do Triple Buffering. ---//
		// ---------------------------------------------------------//
		if (this.parentGameGraphicsOption.getTripleBufferEnabled()) {
			g2d.drawImage(this.checked,
					this.imageUtil.getScaledWidth(BUTTON_CHECK_YES_BUFFER_POSITION_X),
					this.imageUtil.getScaledHeight(BUTTON_CHECK_YES_BUFFER_POSITION_Y),
					this.imageUtil.getScaledWidth(this.checked.getWidth()),
					this.imageUtil.getScaledHeight(this.checked.getHeight()),
					null);

			g2d.drawImage(this.unchecked,
					this.imageUtil.getScaledWidth(BUTTON_CHECK_NO_BUFFER_POSITION_X),
					this.imageUtil.getScaledHeight(BUTTON_CHECK_NO_BUFFER_POSITION_Y),
					this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
					this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
					null);
		} else {
			g2d.drawImage(this.unchecked,
					this.imageUtil.getScaledWidth(BUTTON_CHECK_YES_BUFFER_POSITION_X),
					this.imageUtil.getScaledHeight(BUTTON_CHECK_YES_BUFFER_POSITION_Y),
					this.imageUtil.getScaledWidth(this.unchecked.getWidth()),
					this.imageUtil.getScaledHeight(this.unchecked.getHeight()),
					null);

			g2d.drawImage(this.checked,
					this.imageUtil.getScaledWidth(BUTTON_CHECK_NO_BUFFER_POSITION_X),
					this.imageUtil.getScaledHeight(BUTTON_CHECK_NO_BUFFER_POSITION_Y),
					this.imageUtil.getScaledWidth(this.checked.getWidth()),
					this.imageUtil.getScaledHeight(this.checked.getHeight()),
					null);
		}

		if (this.showMustApply) {
			g2d.drawImage(this.mustApplyForChanges,
					this.imageUtil.getScaledWidth(LABEL_MUST_APPLY_POSITION_X),
					this.imageUtil.getScaledHeight(LABEL_MUST_APPLY_POSITION_Y),
					this.imageUtil.getScaledWidth(this.mustApplyForChanges.getWidth()),
					this.imageUtil.getScaledHeight(this.mustApplyForChanges.getHeight()),
					null);
		}
	}

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio) {
		this.PWIDTH = pwidth;
		this.PHEIGHT = pheight;
		this.imageUtil.updateCanvasProperties(this.PWIDTH, this.PHEIGHT, currentAspectRatio);
	}

	public void resetCounters() {
		this.graphicsOptionGameCounter = 0;
		this.optionSelected = 0;
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

	public boolean isOverScreenResolution() {
		return (this.optionSelected == 0);
	}

	public boolean isOverEnableTripleBuffering() {
		return (this.optionSelected == 1);
	}

	public boolean isOverScreenMode() {
		return (this.optionSelected == 2);
	}

	public boolean isOverDeepColor() {
		return (this.optionSelected == 3);
	}

	public boolean isToApply() {
		return (this.optionSelected == 4);
	}

	public boolean isToBackToMainOption() {
		return (this.optionSelected == 5);
	}

	public void nextScreenResolution() {
		if (!this.isLastResolutionFromList()) {

			// Verifica da lista de resolu��es, qual a pr�xima

			// Armazena a nova resolu��o no objeto pai
			this.parentGameGraphicsOption.setScreenWidth(0);
			this.parentGameGraphicsOption.setScreenHeight(0);

			// Solicita e altera a imagem da resolu��o
			this.screenResolutionValue = this.createScreenResolutionValueImage();

			this.counterArrowRightScreenRes = 6;

			this.showMustApply = true;
		}
	}

	public void previousScreenResolution() {
		if (!this.isFirstResolutionFromList()) {

			// Recupera e guarda a resolu��o atual

			// Verifica da lista de resolu��es, qual a pr�xima

			// Solicita e altera a imagem da resolu��o
			this.screenResolutionValue = this.createScreenResolutionValueImage();

			this.counterArrowLeftScreenRes = 6;

			this.showMustApply = true;
		}
	}

	public void enableTripleBuffer() {
		this.parentGameGraphicsOption.setTripleBufferEnabled(true);
		this.showMustApply = true;
	}

	public void disableTripleBuffer() {
		this.parentGameGraphicsOption.setTripleBufferEnabled(false);
		this.showMustApply = true;
	}

	public void nextScreenMode() {
		if (this.parentGameGraphicsOption.getScreenMode() == ScreenMode.FULLSCREEN) {
			this.parentGameGraphicsOption.setScreenMode(ScreenMode.WINDOWED);
		} else {
			this.parentGameGraphicsOption.setScreenMode(ScreenMode.FULLSCREEN);
		}
		this.counterArrowRightScreenMode = 6;
		this.showMustApply = true;
	}

	public void previousScreenMode() {
		if (this.parentGameGraphicsOption.getScreenMode() == ScreenMode.FULLSCREEN) {
			this.parentGameGraphicsOption.setScreenMode(ScreenMode.WINDOWED);
		} else {
			this.parentGameGraphicsOption.setScreenMode(ScreenMode.FULLSCREEN);
		}
		this.counterArrowLeftScreenMode = 6;
		this.showMustApply = true;
	}

	public void nextScreenDeepColor() {
		if (this.parentGameGraphicsOption.getScreenDeepColor() == DeepColor.TRUE_COLOR_32_BITS) {
			this.parentGameGraphicsOption.setScreenDeepColor(DeepColor.HI_COLOR_16_BITS);
		} else {
			this.parentGameGraphicsOption.setScreenDeepColor(DeepColor.TRUE_COLOR_32_BITS);
		}
		this.counterArrowRightDeepColor = 6;
		this.showMustApply = true;
	}

	public void previousScreenDeepColor() {
		if (this.parentGameGraphicsOption.getScreenDeepColor() == DeepColor.TRUE_COLOR_32_BITS) {
			this.parentGameGraphicsOption.setScreenDeepColor(DeepColor.HI_COLOR_16_BITS);
		} else {
			this.parentGameGraphicsOption.setScreenDeepColor(DeepColor.TRUE_COLOR_32_BITS);
		}
		this.counterArrowLeftDeepColor = 6;
		this.showMustApply = true;
	}

	public void applyChanges() {

	}

	public void resetMustApplyForChanges() {
		this.showMustApply = false;
	}

	private BufferedImage createScreenResolutionValueImage() {
		return (null);
	}

	private boolean isLastResolutionFromList() {
		return (false);
	}

	private boolean isFirstResolutionFromList() {
		return (false);
	}

	public void cancelChanges() {
		this.parentGameGraphicsOption.cloneProperties(this.originalGameGraphicsOption);
	}

	public BufferedImage getScreenResolutionStrip() {
		return screenResolutionStrip;
	}

	public BufferedImage getScreenResolutionValue() {
		return screenResolutionValue;
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
				this.cancelChanges();
				game.gotoMainOption();

			} else if (this.isToApply()) {
				// todo
			}
		} else if (keyCode == KeyEvent.VK_UP) {
			this.previousOption();

		} else if (keyCode == KeyEvent.VK_DOWN) {
			this.nextOption();

		} else if (keyCode == KeyEvent.VK_LEFT) {
			if (this.isOverEnableTripleBuffering()) {
				this.enableTripleBuffer();
			} else if (this.isOverScreenMode()) {
				this.previousScreenMode();
			} else if (this.isOverDeepColor()) {
				this.previousScreenDeepColor();
			}

		} else if (keyCode == KeyEvent.VK_RIGHT) {
			if (this.isOverEnableTripleBuffering()) {
				this.disableTripleBuffer();
			} else if (this.isOverScreenMode()) {
				this.nextScreenMode();
			} else if (this.isOverDeepColor()) {
				this.nextScreenDeepColor();
			}
		}
	}
}