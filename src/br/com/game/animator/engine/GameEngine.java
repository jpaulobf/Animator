package br.com.game.animator.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.com.game.animator.game.gameData.GameGraphics;
import br.com.game.animator.game.gameData.GameGraphicsImpl;
import br.com.game.animator.game.gameData.GameOptions;
import br.com.game.animator.game.gameData.GameOptionsImpl;
import br.com.game.animator.game.gameData.GameSoundOptions;
import br.com.game.animator.game.gameData.GameSoundOptionsImpl;
import br.com.game.animator.game.gameData.enumerators.ScreenMode;
import br.com.game.animator.game.gameScore.GameScorePresentation;
import br.com.game.animator.game.gameScore.GameScorePresentationImpl;
import br.com.game.animator.game.gameUI.DeveloperAdvertise;
import br.com.game.animator.game.gameUI.DeveloperAdvertiseImpl;
import br.com.game.animator.game.gameUI.GameExitMenu;
import br.com.game.animator.game.gameUI.GameExitMenuImpl;
import br.com.game.animator.game.gameUI.GameGraphicsScreen;
import br.com.game.animator.game.gameUI.GameGraphicsScreenImpl;
import br.com.game.animator.game.gameUI.GameIntro;
import br.com.game.animator.game.gameUI.GameIntroImpl;
import br.com.game.animator.game.gameUI.GameMainMenu;
import br.com.game.animator.game.gameUI.GameMainMenuImpl;
import br.com.game.animator.game.gameUI.GameMainOptionScreen;
import br.com.game.animator.game.gameUI.GameMainOptionScreenImpl;
import br.com.game.animator.game.gameUI.GameOptionScreen;
import br.com.game.animator.game.gameUI.GameOptionScreenImpl;
import br.com.game.animator.game.gameUI.GameSoundOptionScreen;
import br.com.game.animator.game.gameUI.GameSoundOptionScreenImpl;
import br.com.game.animator.game.gameUI.Loading;
import br.com.game.animator.game.gameUI.LoadingImpl;
import br.com.game.animator.input.InputHandler;
import br.com.game.animator.util.GlobalProperties;

/**
 * @author João Paulo Faria
 */
public class GameEngine extends JFrame implements WindowListener, Runnable {

	private static final boolean FULLSCREEN = true;
	private static final boolean WINDOWED = false;
	private volatile boolean finishedOff = false;
	public volatile boolean fullScreen = FULLSCREEN;
	private Integer CURRENT_WINDOW_WIDTH = null;
	private Integer CURRENT_WINDOW_HEIGHT = null;
	private static final int NUM_BUFFERS = 2;
	private static final int TRIPLE_BUFFERS = 3;
	private GraphicsDevice graphicsDevice = null;
	private Integer PWIDTH = null;
	private Integer PHEIGHT = null;

	// ------------------------------------------------------------------------//
	public static final Integer FPS = 60;
	private static final Integer MAX_FRAME_SKIPS = 5;
	private final long PERIOD = 1000000000L / FPS;
	private Thread animator = null;
	public volatile boolean running = false;
	public volatile boolean gameOver = false;
	public volatile boolean isPaused = false;
	private volatile boolean isToShowFPS = true;
	private volatile boolean loading = false;

	// ------------------------------------------------------------------------//
	public volatile boolean isDevLogoScreen = true;
	public volatile boolean isSubIntroScreen = false;
	public volatile boolean isIntroScreen = false;
	public volatile boolean isShowHighScoreScreen = false;
	public volatile boolean isMainMenuScreen = false;
	public volatile boolean isInGameScreen = false;
	private volatile boolean isInGameOptionScreen = false;
	public volatile boolean isInMainOptionScreen = false;
	public volatile boolean isInOptionGameScreen = false;
	private volatile boolean isInOptionKeyJoyScreen = false;
	private volatile boolean isInOptionKeyScreen = false;
	private volatile boolean isInOptionJoyScreen = false;
	public volatile boolean isInOptionSoundScreen = false;
	public volatile boolean isInOptionGFXScreen = false;
	private volatile boolean isInOptionTestGFXScreen = false;
	private volatile boolean isInOptionProfileScreen = false;
	private Loading gameLoading = null;
	public DeveloperAdvertise developerAdvertise = null;
	public GameGraphics gameGraphics = null;
	public GameIntro gameIntro = null;
	public GameScorePresentation gameScore = null;
	public GameMainMenu gameMainMenu = null;
	public GameExitMenu gameExitMenu = null;
	public GameMainOptionScreen gameMainOptionScreen = null;
	public GameOptionScreen gameOptionScreen = null;
	public GameSoundOptionScreen gameSoundOptionScreen = null;
	public GameOptions gameOptions = null;
	public GameSoundOptions gameSoundOptions = null;
	public GameGraphicsScreen gameGraphicsScreen = null;

	// ------------------------------------------------------------------------//
	private static long MAX_STATS_INTERVAL = 1000000000L;
	private static long FIRST_STATS_INTERVAL = 2000000000L;
	private long statsInterval = 0L;
	private long prevStatsTime = 0L;
	private long totalElapsedTime = 0L;
	private long frameCount = 0;
	private long lastFrameCount = 0L;
	private double fpsStore[] = null;
	private long statsCount = 0;
	private double averageFPS = 0.0;
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[] = null;
	private double averageUPS = 0.0;
	private DecimalFormat df = new DecimalFormat("0.##"); // 2 dp
	private boolean storeStats = true;
	private boolean startStoreStats = false;
	private Graphics2D graphics2D = null;

	// ------------------------------------------------------------------------//
	private DisplayMode currentDisplayMode = null;
	private volatile boolean tripleBuffering = false;
	private Integer currentAspectRatio = null;
	private InputHandler inputHandler = null;

	/**
	 * Construtor
	 */
	public GameEngine() {
		super("The Game Engine");

		System.setProperty("sun.java2d.translaccel", "true");
		System.setProperty("sun.java2d.ddforcevram", "true");
		System.setProperty("sun.java2d.opengl", "true");

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

		this.currentAspectRatio = this.getCurrentAspectRatio();
		this.defineCurrentGameWindow();

		this.inputHandler = new InputHandler(this);

		if (this.fullScreen) {
			try {
				super.dispose();
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.setIgnoreRepaint(true);
			super.setResizable(false);

			this.setFullScreen();
			this.addKeyListener(inputHandler);
			this.addMouseListener(inputHandler);
			this.addMouseMotionListener(inputHandler);

			this.PWIDTH = this.getBounds().width;
			this.PHEIGHT = this.getBounds().height;

		} else {
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/game-icon.png")));

			this.setPreferredSize(new Dimension(CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT));
			this.setLocation(
					(int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (CURRENT_WINDOW_WIDTH / 2)),
					(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (CURRENT_WINDOW_HEIGHT / 2)
							- 20));
			this.pack();

			super.addWindowListener(this);
			this.addKeyListener(inputHandler);
			this.addMouseListener(inputHandler);
			this.addMouseMotionListener(inputHandler);
			this.setResizable(false);
			this.setVisible(true);
			this.PWIDTH = CURRENT_WINDOW_WIDTH;
			this.PHEIGHT = CURRENT_WINDOW_HEIGHT;
		}

		Thread t = null;
		try {
			t = new Thread(new Runnable() {
				public void run() {
					if (graphicsDevice.getDefaultConfiguration().getBufferCapabilities().isMultiBufferAvailable()) {
						createBufferStrategy(TRIPLE_BUFFERS);
						System.out.println("Triple buffering active");
					} else {
						createBufferStrategy(NUM_BUFFERS);
						System.err.println("Triple buffering not supported by the GPU");
						System.out.println("Double buffering active");
					}
				}
			});
			t.start();
		} catch (Exception e) {
			System.out.println("Error while creating buffer strategy");
			System.exit(0);
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setFocusable(true);
		super.requestFocus();

		this.fpsStore = new double[10];
		this.upsStore = new double[10];

		// ---------------------------------------------------------//
		Integer currentAspectRatio = this.getCurrentAspectRatio();
		this.gameOptions = new GameOptionsImpl();
		this.gameLoading = new LoadingImpl(PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameSoundOptions = new GameSoundOptionsImpl();
		this.gameGraphics = new GameGraphicsImpl(this.fullScreen, this.tripleBuffering);
		this.developerAdvertise = new DeveloperAdvertiseImpl(PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameIntro = new GameIntroImpl(PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameScore = new GameScorePresentationImpl(PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameMainMenu = new GameMainMenuImpl(PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameExitMenu = new GameExitMenuImpl(PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameMainOptionScreen = new GameMainOptionScreenImpl(PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameOptionScreen = new GameOptionScreenImpl(this.gameOptions, PWIDTH, PHEIGHT, currentAspectRatio);
		this.gameSoundOptionScreen = new GameSoundOptionScreenImpl(this.gameSoundOptions, PWIDTH, PHEIGHT,
				currentAspectRatio);
		this.gameGraphicsScreen = new GameGraphicsScreenImpl(this.gameGraphics, PWIDTH, PHEIGHT, currentAspectRatio);

		// ---------------------------------------------------------//
		this.currentDisplayMode = this.getGraphicsConfiguration().getDevice().getDisplayMode();
		this.hideMouseCursor();
		this.startGame();
	}

	/**
	 * Start the game.
	 */
	protected void startGame() {
		if (this.animator == null || !this.running) {
			this.animator = new Thread(this);
			this.animator.start();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		this.running = true;
		long beforeTime = System.nanoTime();
		long overSleepTime = 0L;
		this.prevStatsTime = beforeTime;

		// ---------------------------------------------------------//
		while (this.running) {
			this.gameUpdate();
			try {
				this.gameRender();
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.paintScreen();

			long afterTime = System.nanoTime();
			long timeDiff = afterTime - beforeTime;
			long sleepTime = (PERIOD - timeDiff) - overSleepTime;

			if (sleepTime > 0) {
				// Espera Híbrida: Sleep para economizar CPU + Busy-Wait para precisão
				long sleepMs = sleepTime / 1000000L;
				if (sleepMs > 2) {
					try {
						// Dormimos 2ms a menos do que o necessário para garantir que não passamos do
						// ponto
						Thread.sleep(sleepMs - 2);
					} catch (InterruptedException e) {
					}
				}

				// Busy-wait (espera ativa) para os nanossegundos finais
				while (System.nanoTime() - beforeTime < PERIOD) {
					// Aguarda o tempo exato passar
				}

				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else {
				long excess = -sleepTime;
				overSleepTime = 0L;

				int skips = 0;
				while ((excess > PERIOD) && (skips < MAX_FRAME_SKIPS)) {
					excess -= PERIOD;
					this.gameUpdate();
					skips++;
				}
				this.framesSkipped += skips;
			}

			beforeTime = System.nanoTime();

			if (this.storeStats) {
				this.storeStats();
			}
		}

		this.printStats();
		System.exit(0);
	}

	/**
	 * paintScreen - Paint the buffer to the screen.
	 */
	private void paintScreen() {
		try {
			BufferStrategy strategy = getBufferStrategy();
			this.graphics2D = (Graphics2D) strategy.getDrawGraphics();
			this.graphics2D.dispose();
			if (!strategy.contentsLost()) {
				strategy.show();
			} else {
				System.out.println("Contents Lost");
			}
			Toolkit.getDefaultToolkit().sync();
		} catch (Exception e) {
			this.running = false;
		}
	}

	// -------------------------------------------------------------------------------------------------//
	// --- CONTROLE DO JOGO ---//
	// -------------------------------------------------------------------------------------------------//
	/**
	 * gameUpdate - Pausa o Jogo
	 */
	private void gameUpdate() {
		if (!this.loading) {
			// ---------------------------------------------------------//
			// --- O jogo inicia com a dev logo. ---//
			// ---------------------------------------------------------//
			if (this.isDevLogoScreen) {
				if (this.developerAdvertise.developerLogoPresentationFinished()) {
					this.isDevLogoScreen = false;
					this.isSubIntroScreen = true;
					this.developerAdvertise.resetCounters();
				} else {
					this.developerAdvertise.update();
				}
				// ---------------------------------------------------------//
				// --- Parte ent�o para a sub-intro. ---//
				// ---------------------------------------------------------//
			} else if (this.isSubIntroScreen) {
				if (this.gameIntro.subIntroFinished()) {
					this.isSubIntroScreen = false;
					this.isIntroScreen = true;
					this.gameIntro.resetCounters();
				} else {
					this.gameIntro.updateSubIntro();
				}
				// ---------------------------------------------------------//
				// --- Se nenhuma tecla for pressionada, exibe a abertura --//
				// ---------------------------------------------------------//
			} else if (this.isIntroScreen) {
				if (this.gameIntro.introFinished()) {
					this.isIntroScreen = false;
					this.isShowHighScoreScreen = true;
					this.gameIntro.resetCounters();
				} else {
					this.gameIntro.updateIntro();
				}
				// ---------------------------------------------------------//
				// --- Se nenhuma tecla for pressionada, exibe o hiscore ---//
				// ---------------------------------------------------------//
			} else if (this.isShowHighScoreScreen) {
				if (this.gameScore.hiScorePresentationFinished()) {
					this.isShowHighScoreScreen = false;
					this.isSubIntroScreen = true;
					this.gameScore.resetCounters();
				} else {
					this.gameScore.update();
				}
			} else if (this.isMainMenuScreen) {
				if (this.gameMainMenu.mainMenuFinished()) {
					// this.isMainMenuScreen = false;
					// this.isIntroScreen = true;
					// this.gameMainMenu.resetCounters();
				} else {
					this.gameMainMenu.update();
				}
			} /*
				 * else if (this.isInGameScreen) {
				 * if (!this.isPaused && !this.gameOver) {
				 * //TODO:
				 * }
				 * } else if (this.isInGameOptionScreen) {
				 * //TODO:
				 * } else if (this.isInMainOptionScreen) {
				 * this.gameMainOptionScreen.update();
				 * } else if (this.isInOptionGameScreen) {
				 * this.gameOptionScreen.update();
				 * } else if (this.isInOptionSoundScreen) {
				 * this.gameSoundOptionScreen.update();
				 * } else if (this.isInOptionKeyJoyScreen) {
				 * 
				 * } else if (this.isInOptionKeyScreen) {
				 * 
				 * } else if (this.isInOptionJoyScreen) {
				 * 
				 * } else if (this.isInOptionGFXScreen) {
				 * this.gameGraphicsScreen.update();
				 * } else if (this.isInOptionTestGFXScreen) {
				 * 
				 * } else if (this.isInOptionProfileScreen) {
				 * 
				 * }
				 */
		} else {

			this.gameLoading.update();

		}
	}

	/**
	 * gameRender - Renderiza o Jogo
	 */
	private void gameRender() {
		// ---------------------------------------------------------//
		// --- Em FullScreen, faz uso do BufferStrategy. ---//
		// ---------------------------------------------------------//
		this.graphics2D = (Graphics2D) getBufferStrategy().getDrawGraphics();

		// ---------------------------------------------------------//
		// --- Se houver erro na cria��o do elemento Graphics, ---//
		// --- interrompe para nova tentativa. ---//
		// ---------------------------------------------------------//
		if (this.graphics2D == null) {
			System.out.println("o Elemento 'Graphics' (dbg) est� nulo");
			return;
		}

		if (!this.fullScreen) {
			this.graphics2D.translate(0, super.getInsets().top);
		}

		// ---------------------------------------------------------//
		// --- Limpa cada quadro. ---//
		// ---------------------------------------------------------//
		this.graphics2D.setColor(Color.WHITE);
		this.graphics2D.fillRect(0, 0, PWIDTH, PHEIGHT);

		// ---------------------------------------------------------//
		// --- Se for 'game-over' ---//
		// ---------------------------------------------------------//
		if (this.loading) {

			this.gameLoading.draw(this.graphics2D);

		} else if (this.gameOver) {

			// ---------------------------------------------------------//
			// --- Se for game over, mostra a mensagem. ---//
			// ---------------------------------------------------------//
			// this.gameOverMessage(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar a 'title screen' ---//
			// ---------------------------------------------------------//
		} else if (this.isDevLogoScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.developerAdvertise.draw(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar a 'sub intro' ---//
			// ---------------------------------------------------------//
		} else if (this.isSubIntroScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameIntro.drawSubIntro(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar a 'introdu��o' ---//
			// ---------------------------------------------------------//
		} else if (this.isIntroScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameIntro.drawIntro(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar os 'hiscores' ---//
			// ---------------------------------------------------------//
		} else if (this.isShowHighScoreScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameScore.drawHiScores(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar o 'Main Menu' ---//
			// ---------------------------------------------------------//
		} else if (this.isMainMenuScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameMainMenu.drawMainMenu(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar o 'Main Game Options' ---//
			// ---------------------------------------------------------//
		} else if (this.isInMainOptionScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameMainOptionScreen.drawMainOptionScreen(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar o 'Game Options' ---//
			// ---------------------------------------------------------//
		} else if (this.isInOptionGameScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameOptionScreen.drawOptionScreen(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar o 'Game Sound Options' ---//
			// ---------------------------------------------------------//
		} else if (this.isInOptionSoundScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameSoundOptionScreen.drawSoundOptionScreen(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Se for para mostrar o 'Game Graphics Options' ---//
			// ---------------------------------------------------------//
		} else if (this.isInOptionGFXScreen) {

			// ---------------------------------------------------------//
			// --- Mostra a propaganda do developer. ---//
			// ---------------------------------------------------------//
			this.gameGraphicsScreen.drawGraphicsOptionScreen(this.graphics2D);

			// ---------------------------------------------------------//
			// --- Jogo Rodando. ---//
			// ---------------------------------------------------------//
		} else {

			// ---------------------------------------------------------//
			// --- Se for solicitada a exibi��o de FPSs. ---//
			// ---------------------------------------------------------//
			if (this.isToShowFPS) {
				// ---------------------------------------------------------//
				// --- Mostra os FPSs na tela. ---//
				// ---------------------------------------------------------//
				// this.showFPSValueInScreen();
			}

			// ---------------------------------------------------------//
			// --- Se estiver em tela cheia, completa os elementos. ---//
			// ---------------------------------------------------------//
			if (this.fullScreen) {
			}

			// ---------------------------------------------------------//
			// --- Retorna a cor do foreground para preto. ---//
			// ---------------------------------------------------------//
			this.graphics2D.setColor(Color.BLACK);
		}

		// ---------------------------------------------------------//
		// --- Mostrar o menu de sair acima de qualquer tela. ---//
		// ---------------------------------------------------------//
		if (this.gameExitMenu.isShowingExitMenu()) {
			this.gameExitMenu.drawExitMenu(this.graphics2D);
		}

		if (this.graphics2D != null) {
			this.graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			this.graphics2D.setColor(Color.RED);
			this.graphics2D.drawString("M�dia de FPS / UPS: " + (int) (this.averageFPS) + " / " +
					(int) (this.averageUPS), 10, 20);
		}
	}

	/**
	 * controlKeysPressed - Controla o Pressionar das teclas.
	 */
	protected void controlKeysPressed(Component component) {
		component.addKeyListener(new KeyAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();

				if (!gameExitMenu.isShowingExitMenu()) {
					// ---------------------------------------------------------//
					// --- Bot�es ESC, Q, END e Ctrl+C saem da aplica��o. ---//
					// ---------------------------------------------------------//
					if (isMainMenuScreen && gameMainMenu != null) {
						if (keyCode == KeyEvent.VK_ENTER) {
							if (gameMainMenu.isExitSelected()) {
								gameExitMenu.showExitMenu();
							} else if (gameMainMenu.isOptionSelected()) {
								isMainMenuScreen = false;
								isInMainOptionScreen = true;
							}
						} else if (keyCode == KeyEvent.VK_UP) {
							gameMainMenu.previousGameOption();
						} else if (keyCode == KeyEvent.VK_DOWN) {
							gameMainMenu.nextGameOption();
						}

					} else if (isInMainOptionScreen && gameMainOptionScreen != null) {

						if (keyCode == KeyEvent.VK_ENTER) {
							if (gameMainOptionScreen.isToBackToMainMenu()) {
								gameMainMenu.resetCounters();
								gameMainOptionScreen.resetCounters();
								isInMainOptionScreen = false;
								isMainMenuScreen = true;
							} else if (gameMainOptionScreen.isToGoToGameOptions()) {
								gameMainOptionScreen.resetCounters();
								isInMainOptionScreen = false;
								isInOptionGameScreen = true;
							} else if (gameMainOptionScreen.isToConfigSFX()) {
								gameMainOptionScreen.resetCounters();
								isInMainOptionScreen = false;
								isInOptionSoundScreen = true;
							} else if (gameMainOptionScreen.isToConfigGFX()) {
								gameMainOptionScreen.resetCounters();
								isInMainOptionScreen = false;
								isInOptionGFXScreen = true;
								gameGraphicsScreen.resetMustApplyForChanges();
							}
						} else if (keyCode == KeyEvent.VK_UP) {
							gameMainOptionScreen.previousGameOption();
						} else if (keyCode == KeyEvent.VK_DOWN) {
							gameMainOptionScreen.nextGameOption();
						}

					} else if (isInOptionGameScreen && gameOptionScreen != null) {

						if (keyCode == KeyEvent.VK_ENTER) {
							if (gameOptionScreen.isToBackToMainOption()) {
								gameOptionScreen.resetCounters();
								gameMainOptionScreen.resetCounters();
								isInOptionGameScreen = false;
								isInMainOptionScreen = true;
							}
						} else if (keyCode == KeyEvent.VK_UP) {

							gameOptionScreen.previousOption();

						} else if (keyCode == KeyEvent.VK_DOWN) {

							gameOptionScreen.nextOption();

						} else if (keyCode == KeyEvent.VK_LEFT) {

							if (gameOptionScreen.isOverEnableSubtitles()) {
								gameOptionScreen.enableSubtitles();
							} else if (gameOptionScreen.isOverGameDifficulty()) {
								gameOptionScreen.setPreviousDifficulty();
							} else if (gameOptionScreen.isOverRestsSelection()) {
								gameOptionScreen.subRest();
							} else if (gameOptionScreen.isOverExtraLifeSelection()) {
								gameOptionScreen.setPreviousExtraLifeAtPoints();
							} else if (gameOptionScreen.isOverContinuesSelection()) {
								gameOptionScreen.subContinues();
							}

						} else if (keyCode == KeyEvent.VK_RIGHT) {

							if (gameOptionScreen.isOverEnableSubtitles()) {
								gameOptionScreen.disableSubtitles();
							} else if (gameOptionScreen.isOverGameDifficulty()) {
								gameOptionScreen.setNextDifficulty();
							} else if (gameOptionScreen.isOverRestsSelection()) {
								gameOptionScreen.addRest();
							} else if (gameOptionScreen.isOverExtraLifeSelection()) {
								gameOptionScreen.setNextExtraLifeAtPoints();
							} else if (gameOptionScreen.isOverContinuesSelection()) {
								gameOptionScreen.addContinues();
							}

						}

					} else if (isInOptionSoundScreen && gameSoundOptionScreen != null) {

						if (keyCode == KeyEvent.VK_ENTER) {
							if (gameSoundOptionScreen.isToBackToMainOption()) {
								gameSoundOptionScreen.resetCounters();
								gameMainOptionScreen.resetCounters();
								isInOptionSoundScreen = false;
								isInMainOptionScreen = true;
							}
						} else if (keyCode == KeyEvent.VK_UP) {

							gameSoundOptionScreen.previousOption();

						} else if (keyCode == KeyEvent.VK_DOWN) {

							gameSoundOptionScreen.nextOption();

						} else if (keyCode == KeyEvent.VK_LEFT) {

							if (gameSoundOptionScreen.isOverEnableMusic()) {
								gameSoundOptionScreen.setMusicEnable();
							} else if (gameSoundOptionScreen.isOverEnableSFX()) {
								gameSoundOptionScreen.setSFXEnable();
							} else if (gameSoundOptionScreen.isOverMusicVolume()) {
								gameSoundOptionScreen.decreaseMusicVolume();
							} else if (gameSoundOptionScreen.isOverSFXVolume()) {
								gameSoundOptionScreen.decreaseSFXVolume();
							}

						} else if (keyCode == KeyEvent.VK_RIGHT) {

							if (gameSoundOptionScreen.isOverEnableMusic()) {
								gameSoundOptionScreen.setMusicDisable();
							} else if (gameSoundOptionScreen.isOverEnableSFX()) {
								gameSoundOptionScreen.setSFXDisable();
							} else if (gameSoundOptionScreen.isOverMusicVolume()) {
								gameSoundOptionScreen.increaseMusicVolume();
							} else if (gameSoundOptionScreen.isOverSFXVolume()) {
								gameSoundOptionScreen.increaseSFXVolume();
							}
						}

					} else if (isInOptionGFXScreen && gameGraphicsScreen != null) {

						if (keyCode == KeyEvent.VK_ENTER) {
							if (gameGraphicsScreen.isToBackToMainOption()) {
								gameGraphicsScreen.resetCounters();
								gameGraphicsScreen.cancelChanges();
								gameMainOptionScreen.resetCounters();
								isInOptionGFXScreen = false;
								isInMainOptionScreen = true;

							} else if (gameGraphicsScreen.isToApply()) {
								// TODO:
							}
						} else if (keyCode == KeyEvent.VK_UP) {

							gameGraphicsScreen.previousOption();

						} else if (keyCode == KeyEvent.VK_DOWN) {

							gameGraphicsScreen.nextOption();

						} else if (keyCode == KeyEvent.VK_LEFT) {

							if (gameGraphicsScreen.isOverEnableTripleBuffering()) {
								gameGraphicsScreen.enableTripleBuffer();
							} else if (gameGraphicsScreen.isOverScreenMode()) {
								gameGraphicsScreen.previousScreenMode();
							} else if (gameGraphicsScreen.isOverDeepColor()) {
								gameGraphicsScreen.previousScreenDeepColor();
							}

						} else if (keyCode == KeyEvent.VK_RIGHT) {

							if (gameGraphicsScreen.isOverEnableTripleBuffering()) {
								gameGraphicsScreen.disableTripleBuffer();
							} else if (gameGraphicsScreen.isOverScreenMode()) {
								gameGraphicsScreen.nextScreenMode();
							} else if (gameGraphicsScreen.isOverDeepColor()) {
								gameGraphicsScreen.nextScreenDeepColor();
							}
						}
					}

					// ---------------------------------------------------------//
					// --- Bot�es ALT+ENTER alternam o modo de tela. ---//
					// ---------------------------------------------------------//
					if (e.isAltDown() && (keyCode == KeyEvent.VK_ENTER)) {
						if (!fullScreen) {
							// ---------------------------------------------------------//
							// --- Pausa o jogo para o jogador n�o se prejudicar. ---//
							// ---------------------------------------------------------//
							pauseGame();

							try {
								// ---------------------------------------------------------//
								// --- Tenta colocar a janela em fullscreen. ---//
								// ---------------------------------------------------------//
								switchToFullScreen();
								gameGraphics.setScreenMode(ScreenMode.FULLSCREEN);

							} catch (Exception exception) {

								// ---------------------------------------------------------//
								// --- Mensagem de erro para falha em alternar ao modo ---//
								// --- fullscreen. ---//
								// ---------------------------------------------------------//
								JOptionPane.showMessageDialog(null,
										"N�o foi poss�vel inicializar a aplica��o em FullScreen.\n" +
												"Tente alterar o modo de video em Game-Options.",
										"Erro ao alterar o modo de tela",
										JOptionPane.ERROR_MESSAGE);

								// ---------------------------------------------------------//
								// --- Retorna a tela em modo janela. ---//
								// ---------------------------------------------------------//
								backToWindow();
								gameGraphics.setScreenMode(ScreenMode.WINDOWED);
							}

							// ---------------------------------------------------------//
							// --- Retorna o jogo. ---//
							// ---------------------------------------------------------//
							resumeGame();
						} else {

							// ---------------------------------------------------------//
							// --- Pausa o jogo para o jogador n�o se prejudicar. ---//
							// ---------------------------------------------------------//
							pauseGame();

							// ---------------------------------------------------------//
							// --- Retorna a tela em modo janela. ---//
							// ---------------------------------------------------------//
							backToWindow();
							gameGraphics.setScreenMode(ScreenMode.WINDOWED);

							// ---------------------------------------------------------//
							// --- Retorna o jogo. ---//
							// ---------------------------------------------------------//
							resumeGame();
						}
						// ---------------------------------------------------------//
						// --- Bot�es P e Pause, pausam o jogo. ---//
						// ---------------------------------------------------------//
					} else if ((keyCode == KeyEvent.VK_P) ||
							(keyCode == KeyEvent.VK_PAUSE) &&
									isInGameScreen) {
						if (!isPaused) {
							pauseGame();
						} else {
							resumeGame();
						}

						// ---------------------------------------------------------//
						// --- Caso contr�rio, qualquer outra tecla ser� avaliada --//
						// ---------------------------------------------------------//
					} else if (e.isAltDown() && (keyCode == KeyEvent.VK_F4)) {
						if (!isDevLogoScreen &&
								!isInMainOptionScreen &&
								!isInOptionGameScreen &&
								!isInOptionSoundScreen &&
								!isInOptionGFXScreen) {
							if (gameExitMenu != null) {
								gameExitMenu.showExitMenu();
							}
						}

						// ---------------------------------------------------------//
						// --- Caso contr�rio, qualquer outra tecla ser� avaliada --//
						// ---------------------------------------------------------//
					} else if (keyCode != KeyEvent.VK_ALT) {
						if (isSubIntroScreen || isIntroScreen || isShowHighScoreScreen) {
							isSubIntroScreen = false;
							isIntroScreen = false;
							isShowHighScoreScreen = false;
							isMainMenuScreen = true;
						}
					}
				} else {
					if (keyCode == KeyEvent.VK_LEFT) {
						gameExitMenu.previousGameOption();
					} else if (keyCode == KeyEvent.VK_RIGHT) {
						gameExitMenu.nextGameOption();
					}

					if (keyCode == KeyEvent.VK_ENTER) {
						if (gameExitMenu.isToExit()) {
							stopGame();
						} else {
							gameExitMenu.hideExitMenu();
						}
					}
				}
			}
		});
	}

	/**
	 * Esconde o cursor do mouse
	 */
	private void hideMouseCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getBestCursorSize(1, 1);
		BufferedImage cursorImg = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = cursorImg.createGraphics();
		g2d.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		g2d.clearRect(0, 0, dim.width, dim.height);
		g2d.dispose();
		this.setCursor(toolkit.createCustomCursor(cursorImg, new Point(0, 0), "hiddenCursor"));
	}

	/**
	 * pauseGame - Pausa o Jogo
	 */
	public void pauseGame() {
		this.isPaused = true;
	}

	/**
	 * loading - Carrega...
	 */
	public void loading() {
		this.loading = true;
	}

	/**
	 * loadingDone - Conclui o carregamento...
	 */
	public void loadingDone() {
		this.loading = false;
	}

	/**
	 * resumeGame - Retorna o Jogo
	 */
	public void resumeGame() {
		this.isPaused = false;
	}

	/**
	 * stopGame - Encerra o Jogo
	 */
	public void stopGame() {
		this.running = false;
	}
	// -------------------------------------------------------------------------------------------------//
	// --- FIM CONTROLE DO JOGO ---//
	// -------------------------------------------------------------------------------------------------//

	// -------------------------------------------------------------------------------------------------//
	// -------------------------------------------------------------------------------------------------//
	// --- OS M�TODOS ABAIXO N�O PRECISAM SER MODIFICADOS PARA CONSTRU��O DO JOGO
	// ---//
	// -------------------------------------------------------------------------------------------------//
	// -------------------------------------------------------------------------------------------------//

	// -------------------------------------------------------------------------------------------------//
	// --- M�TODOS PARA ALTERNAR O JOGO PARA FULL-SCREEN ---//
	// -------------------------------------------------------------------------------------------------//
	/**
	 * Atualiza a tela para fullscreen
	 * 
	 * @param pWIDTH
	 * @param pHEIGHT
	 */
	public void switchWindowFullScreenMode(boolean isFullScreen, Integer pWIDTH, Integer pHEIGHT) {
		this.fullScreen = isFullScreen;
		this.PWIDTH = pWIDTH;
		this.PHEIGHT = pHEIGHT;

		// ---------------------------------------------------------//
		// --- Atualiza os elementos da classes de suporte. ---//
		// ---------------------------------------------------------//
		this.loading();

		Integer currentAspectRatio = this.getCurrentAspectRatio();
		if (this.gameLoading != null) {
			this.gameLoading.updateCanvasProperties(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.developerAdvertise != null) {
			this.developerAdvertise.updateCanvasProperties(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameIntro != null) {
			this.gameIntro.updateCanvasProperties(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
			this.gameIntro.updateCanvasPropertiesSubIntro(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameScore != null) {
			this.gameScore.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameMainMenu != null) {
			this.gameMainMenu.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameMainOptionScreen != null) {
			this.gameMainOptionScreen.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameOptionScreen != null) {
			this.gameOptionScreen.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameSoundOptionScreen != null) {
			this.gameSoundOptionScreen.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameGraphicsScreen != null) {
			this.gameGraphicsScreen.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}
		if (this.gameExitMenu != null) {
			this.gameExitMenu.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
		}

		this.loadingDone();
	}

	/**
	 * Coloca a janela em FullScreen
	 */
	public void setFullScreen() {
		// ---------------------------------------------------------//
		// --- Altera o par�metro global para FULLSCREEN. ---//
		// ---------------------------------------------------------//
		this.fullScreen = FULLSCREEN;

		// ---------------------------------------------------------//
		// --- Verifica se FullScreen � suportado, do contr�rio, ---//
		// --- encerra. ---//
		// ---------------------------------------------------------//
		if (!this.graphicsDevice.isFullScreenSupported()) {
			System.out.println("Full-screen exclusive mode not supported");
			super.setUndecorated(false);
			this.fullScreen = WINDOWED;
		}

		// ---------------------------------------------------------//
		// --- Coloca a tela em FullScreen. ---//
		// ---------------------------------------------------------//
		this.graphicsDevice.setFullScreenWindow(this);
	}

	/**
	 * Alterna para o modo FullScreen
	 */
	public void switchToFullScreen() {
		// ---------------------------------------------------------//
		// --- Configura a tela para FullScreen. ---//
		// ---------------------------------------------------------//
		this.setFullScreen();

		// ---------------------------------------------------------//
		// --- Altera as configura��es de refer�ncia do panel. ---//
		// ---------------------------------------------------------//
		this.switchWindowFullScreenMode(FULLSCREEN,
				this.getBounds().width,
				this.getBounds().height);
	}
	// -------------------------------------------------------------------------------------------------//
	// --- FIM FULL-SCREEN ---//
	// -------------------------------------------------------------------------------------------------//

	// -------------------------------------------------------------------------------------------------//
	// --- M�TODOS PARA JOGO EM JANELA ---//
	// -------------------------------------------------------------------------------------------------//
	/**
	 * Retorna para Modo Janela
	 */
	public void backToWindow() {
		// ---------------------------------------------------------//
		// --- Altera as configura��es de refer�ncia do panel. ---//
		// ---------------------------------------------------------//
		this.switchWindowFullScreenMode(WINDOWED,
				CURRENT_WINDOW_WIDTH,
				CURRENT_WINDOW_HEIGHT);

		// ---------------------------------------------------------//
		// --- Monta a GUI e a janela. ---//
		// ---------------------------------------------------------//
		this.restoreScreen();

		// ---------------------------------------------------------//
		// --- Centraliza a janela no meio da tela. ---//
		// ---------------------------------------------------------//
		this.setPreferredSize(new Dimension(CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT));
		this.setLocation(
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (CURRENT_WINDOW_WIDTH / 2)),
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (CURRENT_WINDOW_HEIGHT / 2)
						- 20));

		// ---------------------------------------------------------//
		// --- Adiciona aos Listeners de Janela, esta janela. ---//
		// ---------------------------------------------------------//
		super.addWindowListener(this);

		// ---------------------------------------------------------//
		// --- Retorna os elementos da janela e impede o ---//
		// --- redimensionamento. ---//
		// ---------------------------------------------------------//
		try {
			// super.dispose();
			super.setUndecorated(false);
		} catch (Exception e) {
		}
		super.setIgnoreRepaint(false);

		// ---------------------------------------------------------//
		// --- Coibe o componente de ser redimension�vel e o ---//
		// --- torna vis�vel. ---//
		// ---------------------------------------------------------//
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Restaura a Tela
	 */
	private void restoreScreen() {
		this.fullScreen = WINDOWED;
		Window window = this.graphicsDevice.getFullScreenWindow();
		try {
			if (window != null) {
			}
			this.graphicsDevice.setFullScreenWindow(null);
		} catch (IllegalArgumentException ex) {
		} catch (Exception e) {
		}
	}
	// -------------------------------------------------------------------------------------------------//
	// --- FIM JANELA ---//
	// -------------------------------------------------------------------------------------------------//

	// -------------------------------------------------------------------------------------------------//
	// --- DESTRUTOR ---//
	// -------------------------------------------------------------------------------------------------//
	/**
	 * Encerra o programa
	 */
	public void finishOff() {
		if (!this.finishedOff && this.fullScreen) {
			this.finishedOff = true;

			// ---------------------------------------------------------//
			// --- Restaura a tela com a forma original. ---//
			// ---------------------------------------------------------//
			this.restoreScreen();
			this.graphicsDevice = null;
			this.animator = null;
			this.graphics2D = null;

			System.exit(0);
		}
	}
	// -------------------------------------------------------------------------------------------------//
	// --- FIM DESTRUTOR ---//
	// -------------------------------------------------------------------------------------------------//

	// -------------------------------------------------------------------------------------------------//
	// --- INFORMA��ES RESOLU��O ---//
	// -------------------------------------------------------------------------------------------------//
	/**
	 * Recupera as Resolu��es de Tela dispon�veis no Sistema
	 */
	public DisplayMode[] getAvailableScreenResolutions() {
		return (this.getGraphicsConfiguration().getDevice().getDisplayModes());
	}

	/**
	 * Atualiza o DisplayMode
	 */
	public void updateDisplayMode() {
		this.currentAspectRatio = this.getCurrentAspectRatio();
		this.currentDisplayMode = this.getGraphicsConfiguration().getDevice().getDisplayMode();
	}

	/**
	 * Verifica se � poss�vel (caso execute em janela) operar com tamanho X2
	 * 
	 * @return
	 */
	private boolean isScreenX2Available() {
		return (this.getGraphicsConfiguration()
				.getDevice()
				.getDisplayMode()
				.getWidth() >= GlobalProperties.WINDOW_WIDTH_X2_4_3);
	}

	/**
	 * Verifica se � poss�vel (caso execute em janela) operar com tamanho X3
	 * 
	 * @return
	 */
	private boolean isScreenX3Available() {
		return (this.getGraphicsConfiguration()
				.getDevice()
				.getDisplayMode()
				.getWidth() >= GlobalProperties.WINDOW_WIDTH_X3_4_3);
	}

	/**
	 * Verifica se � poss�vel (caso execute em janela) operar com tamanho X3
	 * 
	 * @return
	 */
	private boolean isScreenX4Available() {
		return (this.getGraphicsConfiguration()
				.getDevice()
				.getDisplayMode()
				.getWidth() >= GlobalProperties.WINDOW_WIDTH_X4_4_3);
	}

	/**
	 * Recupera o Aspect Ratio atual da tela.
	 * 
	 * @return
	 */
	public Integer getCurrentAspectRatio() {
		DisplayMode cdm = this.getGraphicsConfiguration().getDevice().getDisplayMode();
		int width = cdm.getWidth();
		int height = cdm.getHeight();
		double ratio = (double) width / (double) height;

		if (ratio == ((double) 4 / (double) 3)) {
			return (GlobalProperties.ASPECT_RATIO_4_3);
		} else if (ratio == ((double) 16 / (double) 10)) {
			return (GlobalProperties.ASPECT_RATIO_16_10);
		} else {
			return (GlobalProperties.ASPECT_RATIO_16_9);
		}
	}

	/**
	 * Com base no Aspect Ratio, define o tamanho da tela (para games em janela).
	 */
	private void defineCurrentGameWindow() {
		if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_4_3) {
			if (this.isScreenX4Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_4_3;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_4_3;
			} else if (this.isScreenX3Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_4_3;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_4_3;
			} else if (this.isScreenX2Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_4_3;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_4_3;
			} else {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_4_3;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_4_3;
			}
		} else if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_16_10) {
			if (this.isScreenX4Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_16_10;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_16_10;
			} else if (this.isScreenX3Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_16_10;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_16_10;
			} else if (this.isScreenX2Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_16_10;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_16_10;
			} else {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_16_10;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_16_10;
			}
		} else {
			if (this.isScreenX4Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_16_9;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_16_9;
			} else if (this.isScreenX3Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_16_9;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_16_9;
			} else if (this.isScreenX2Available()) {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_16_9;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_16_9;
			} else {
				CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_16_9;
				CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_16_9;
			}
		}
	}

	/**
	 * Define o tamanho da janela para 1X em 16X9
	 */
	public void setWindowSizeX1_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_16_9;
	}

	/**
	 * Define o tamanho da janela para 2X em 16X9
	 */
	public void setWindowSizeX2_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_16_9;
	}

	/**
	 * Define o tamanho da janela para 3X em 16X9
	 */
	public void setWindowSizeX3_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_16_9;
	}

	/**
	 * Define o tamanho da janela para 4X em 16X9
	 */
	public void setWindowSizeX4_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_16_9;
	}

	/**
	 * Define o tamanho da janela para 1X em 4X3
	 */
	public void setWindowSizeX1_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_4_3;
	}

	/**
	 * Define o tamanho da janela para 2X em 4X3
	 */
	public void setWindowSizeX2_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_4_3;
	}

	/**
	 * Define o tamanho da janela para 3X em 4X3
	 */
	public void setWindowSizeX3_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_4_3;
	}

	/**
	 * Define o tamanho da janela para 4X em 4X3
	 */
	public void setWindowSizeX4_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_4_3;
	}

	/**
	 * Define o tamanho da janela para 1X em 16X10
	 */
	public void setWindowSizeX1_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_16_10;
	}

	/**
	 * Define o tamanho da janela para 2X em 16X10
	 */
	public void setWindowSizeX2_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_16_10;
	}

	/**
	 * Define o tamanho da janela para 3X em 16X10
	 */
	public void setWindowSizeX3_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_16_10;
	}

	/**
	 * Define o tamanho da janela para 4X em 16X10
	 */
	public void setWindowSizeX4_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_16_10;
	}

	/**
	 * @return
	 */
	public DisplayMode getCurrentDisplayModes() {
		return (this.currentDisplayMode);
	}
	// -------------------------------------------------------------------------------------------------//
	// --- FIM INFORMA��ES RESOLU��O ---//
	// -------------------------------------------------------------------------------------------------//

	// -------------------------------------------------------------------------------------------------//
	// --- EVENTOS DA JANELA ---//
	// -------------------------------------------------------------------------------------------------//
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {
		this.resumeGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0) {
		this.stopGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0) {
		this.pauseGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0) {
		this.resumeGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0) {
		this.pauseGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0) {
	}
	// -------------------------------------------------------------------------------------------------//
	// --- FIM EVENTOS DA JANELA ---//
	// -------------------------------------------------------------------------------------------------//

	/**
	 * Store the game statistics in the Stats Store to calculate the average FPS and UPS.
	 */
	private void storeStats() {
		this.frameCount++;
		long timeNow = System.nanoTime();
		long realElapsedTime = timeNow - this.prevStatsTime;

		// Define se usamos o intervalo inicial (2s) ou o normal (1s)
		long triggerInterval = startStoreStats ? MAX_STATS_INTERVAL : FIRST_STATS_INTERVAL;

		if (realElapsedTime >= triggerInterval) {
			this.startStoreStats = true;

			// Calcula quantos quadros ocorreram APENAS neste último intervalo
			long framesInInterval = this.frameCount - this.lastFrameCount;
			this.lastFrameCount = this.frameCount;

			// FPS e UPS baseados no tempo real decorrido no intervalo
			double actualFPS = ((double) framesInInterval / realElapsedTime) * 1000000000L;
			double actualUPS = ((double) (framesInInterval + this.framesSkipped) / realElapsedTime) * 1000000000L;

			this.totalFramesSkipped += this.framesSkipped;

			// Armazena no buffer circular de médias (tamanho definido na inicialização, ex: 10)
			int numSamples = this.fpsStore.length;
			int index = (int) (this.statsCount % numSamples);
			this.fpsStore[index] = actualFPS;
			this.upsStore[index] = actualUPS;
			this.statsCount++;

			// Calcula a média móvel das últimas amostras
			double totalFPS = 0.0;
			double totalUPS = 0.0;
			int activeSamples = (int) Math.min(this.statsCount, numSamples);
			
			for (int i = 0; i < activeSamples; i++) {
				totalFPS += this.fpsStore[i];
				totalUPS += this.upsStore[i];
			}

			this.averageFPS = totalFPS / activeSamples;
			this.averageUPS = totalUPS / activeSamples;

			// Reseta dados do intervalo
			this.framesSkipped = 0;
			this.prevStatsTime = timeNow;
		}
	}

	/**
	 * printStats
	 */
	private void printStats() {
		System.out.println("Frame Count/Loss: " + this.frameCount + " / " + this.totalFramesSkipped);
		System.out.println("Average FPS: " + this.df.format(this.averageFPS));
		System.out.println("Average UPS: " + this.df.format(this.averageUPS));
	}
}