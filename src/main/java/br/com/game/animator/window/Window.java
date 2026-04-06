package br.com.game.animator.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import br.com.game.animator.game.core.IGame;
import br.com.game.animator.util.GlobalProperties;

/**
 * Window - Class responsible for creating and managing the game window,
 * handling fullscreen mode, and processing user input.
 * This class extends JFrame and implements WindowListener to handle window
 * events such as activation, deactivation, and closing.
 * It also manages the buffer strategy for rendering the game graphics and
 * provides methods for switching between fullscreen and windowed modes.
 * The Window class interacts with the IGame interface to update the game
 * settings when switching between fullscreen and windowed modes, and to handle
 * key presses for game controls.
 */
public class Window extends JFrame implements WindowListener, KeyListener, MouseListener, MouseMotionListener {

	// --- Constants ---//
	private static final int NUM_BUFFERS = 2;
	private static final int TRIPLE_BUFFERS = 3;
	private static final boolean FULLSCREEN = true;
	private static final boolean WINDOWED = false;

	// --- Properties ---//
	private volatile Integer CURRENT_WINDOW_WIDTH = null;
	private volatile Integer CURRENT_WINDOW_HEIGHT = null;
	private final Object dimensionLock = new Object();
	private volatile boolean fullScreen = FULLSCREEN;
	private volatile boolean isTransitioning = false;
	private volatile FullscreenType fullscreenType = FullscreenType.BORDERLESS_FULLSCREEN;
	private GraphicsDevice graphicsDevice = null;
	private volatile Integer panelWidth = null;
	private volatile Integer panelHeight = null;
	private DisplayMode currentDisplayMode = null;
	private volatile boolean tripleBuffering = false;
	private Integer currentAspectRatio = null;
	private IGame game = null;

	// --- Window Configuration Map ---//
	private static final Map<Integer, Map<WindowScale, WindowDimensions>> WINDOW_CONFIGS = createWindowConfigurations();

	private static Map<Integer, Map<WindowScale, WindowDimensions>> createWindowConfigurations() {
		Map<Integer, Map<WindowScale, WindowDimensions>> configs = new HashMap<>();

		// 16:9 aspect ratio
		Map<WindowScale, WindowDimensions> ratio169 = new HashMap<>();
		ratio169.put(WindowScale.X1,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X1_16_9, GlobalProperties.WINDOW_HEIGHT_X1_16_9));
		ratio169.put(WindowScale.X2,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X2_16_9, GlobalProperties.WINDOW_HEIGHT_X2_16_9));
		ratio169.put(WindowScale.X3,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X3_16_9, GlobalProperties.WINDOW_HEIGHT_X3_16_9));
		ratio169.put(WindowScale.X4,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X4_16_9, GlobalProperties.WINDOW_HEIGHT_X4_16_9));
		configs.put(GlobalProperties.ASPECT_RATIO_16_9, ratio169);

		// 4:3 aspect ratio
		Map<WindowScale, WindowDimensions> ratio43 = new HashMap<>();
		ratio43.put(WindowScale.X1,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X1_4_3, GlobalProperties.WINDOW_HEIGHT_X1_4_3));
		ratio43.put(WindowScale.X2,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X2_4_3, GlobalProperties.WINDOW_HEIGHT_X2_4_3));
		ratio43.put(WindowScale.X3,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X3_4_3, GlobalProperties.WINDOW_HEIGHT_X3_4_3));
		ratio43.put(WindowScale.X4,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X4_4_3, GlobalProperties.WINDOW_HEIGHT_X4_4_3));
		configs.put(GlobalProperties.ASPECT_RATIO_4_3, ratio43);

		// 16:10 aspect ratio
		Map<WindowScale, WindowDimensions> ratio1610 = new HashMap<>();
		ratio1610.put(WindowScale.X1,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X1_16_10, GlobalProperties.WINDOW_HEIGHT_X1_16_10));
		ratio1610.put(WindowScale.X2,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X2_16_10, GlobalProperties.WINDOW_HEIGHT_X2_16_10));
		ratio1610.put(WindowScale.X3,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X3_16_10, GlobalProperties.WINDOW_HEIGHT_X3_16_10));
		ratio1610.put(WindowScale.X4,
				new WindowDimensions(GlobalProperties.WINDOW_WIDTH_X4_16_10, GlobalProperties.WINDOW_HEIGHT_X4_16_10));
		configs.put(GlobalProperties.ASPECT_RATIO_16_10, ratio1610);

		return configs;
	}

	/**
	 * Window Construtor
	 */
	public Window(IGame game) {
		super("The Game Engine");

		this.game = game;

		// --- config rendering ---//
		System.setProperty("sun.java2d.translaccel", "true");
		System.setProperty("sun.java2d.ddforcevram", "true");
		System.setProperty("sun.java2d.opengl", "true");

		// --- Graphics Device ---//
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

		// --- Initialize window ---//
		this.currentAspectRatio = this.getCurrentAspectRatio();
		this.defineCurrentGameWindow();

		// --- Define window fullscreen strategy ---//
		if (this.fullScreen) {
			try {
				super.dispose();
			} catch (Exception e) {
				/* do nothing */}

			super.setIgnoreRepaint(true);
			super.setResizable(false);

			// --- Set the window to fullscreen ---//
			this.setFullScreen();
			this.addKeyListener(this);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);

			this.panelWidth = this.getBounds().width;
			this.panelHeight = this.getBounds().height;

		} else {
			this.setIconImage(
					Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/game-icon.png")));

			this.setPreferredSize(new Dimension(CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT));
			this.setLocation(
					(int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (CURRENT_WINDOW_WIDTH / 2)),
					(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (CURRENT_WINDOW_HEIGHT / 2)
							- 20));
			this.pack();

			super.addWindowListener(this);
			this.addKeyListener(this);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.setResizable(false);
			this.setVisible(true);
			this.panelWidth = CURRENT_WINDOW_WIDTH;
			this.panelHeight = CURRENT_WINDOW_HEIGHT;
		}

		// --- Buffer Strategy ---//
		this.initializeBufferStrategy();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setFocusable(true);
		super.requestFocus();

		// --- Finalize window ---//
		this.getCurrentAspectRatio();
		this.currentDisplayMode = this.getGraphicsConfiguration().getDevice().getDisplayMode();
		this.hideMouseCursor();
	}

	public DisplayMode[] getAvailableScreenResolutions() {
		return (this.getGraphicsConfiguration().getDevice().getDisplayModes());
	}

	public void updateDisplayMode() {
		this.currentAspectRatio = this.getCurrentAspectRatio();
		this.currentDisplayMode = this.getGraphicsConfiguration().getDevice().getDisplayMode();
	}

	private boolean isScreenScaleAvailable(WindowScale scale) {
		int requiredWidth = switch (scale) {
			case X2 -> GlobalProperties.WINDOW_WIDTH_X2_4_3;
			case X3 -> GlobalProperties.WINDOW_WIDTH_X3_4_3;
			case X4 -> GlobalProperties.WINDOW_WIDTH_X4_4_3;
			default -> 0;
		};
		return this.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth() >= requiredWidth;
	}

	private WindowScale getMaxAvailableScale() {
		if (isScreenScaleAvailable(WindowScale.X4))
			return WindowScale.X4;
		if (isScreenScaleAvailable(WindowScale.X3))
			return WindowScale.X3;
		if (isScreenScaleAvailable(WindowScale.X2))
			return WindowScale.X2;
		return WindowScale.X1;
	}

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

	private void defineCurrentGameWindow() {
		WindowScale scale = getMaxAvailableScale();
		setWindowSize(this.currentAspectRatio, scale);
	}

	/**
	 * Sets the window size based on aspect ratio and scale factor.
	 * 
	 * @param aspectRatio The aspect ratio (from GlobalProperties)
	 * @param scale       The scale factor (X1, X2, X3, X4)
	 */
	public void setWindowSize(Integer aspectRatio, WindowScale scale) {
		Map<WindowScale, WindowDimensions> ratioConfig = WINDOW_CONFIGS.getOrDefault(aspectRatio,
				WINDOW_CONFIGS.get(GlobalProperties.ASPECT_RATIO_16_9));

		WindowDimensions dims = ratioConfig.getOrDefault(scale,
				ratioConfig.get(WindowScale.X1));

		synchronized (dimensionLock) {
			this.CURRENT_WINDOW_WIDTH = dims.width;
			this.CURRENT_WINDOW_HEIGHT = dims.height;
		}
	}

	public DisplayMode getCurrentDisplayModes() {
		return (this.currentDisplayMode);
	}

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
	 * Initializes the buffer strategy in a background thread with proper timeout
	 * handling.
	 */
	public void initializeBufferStrategy() {
		try {
			boolean multiBufferAvailable = graphicsDevice.getDefaultConfiguration()
					.getBufferCapabilities().isMultiBufferAvailable();

			int buffers = multiBufferAvailable ? TRIPLE_BUFFERS : NUM_BUFFERS;
			this.createBufferStrategy(buffers);

			String bufferType = multiBufferAvailable ? "Triple" : "Double";
			System.out.println(bufferType + " buffering active");
		} catch (Exception e) {
			System.err.println("Error creating buffer strategy: " + e.getMessage());
		}
	}

	private void restoreScreen() {
		this.fullScreen = WINDOWED;
		java.awt.Window window = this.graphicsDevice.getFullScreenWindow();
		try {
			if (window != null) {
				this.graphicsDevice.setFullScreenWindow(null);
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Failed to restore screen from fullscreen: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error restoring screen: " + e.getMessage());
		}
	}

	public void switchWindowFullScreenMode(boolean isFullScreen, Integer pWIDTH, Integer pHEIGHT) {
		this.fullScreen = isFullScreen;
		this.panelWidth = pWIDTH;
		this.panelHeight = pHEIGHT;
		this.game.updateGameSettings(isFullScreen, pWIDTH, pHEIGHT);
	}

	public void setFullScreen() {
		this.fullScreen = FULLSCREEN;
		this.isTransitioning = true;

		if (fullscreenType == FullscreenType.EXCLUSIVE_FULLSCREEN) {
			if (this.graphicsDevice.isFullScreenSupported()) {
				this.graphicsDevice.setFullScreenWindow(this);
				this.initializeBufferStrategy();
				this.isTransitioning = false;
				return;
			}
			System.err.println("Exclusive Fullscreen not supported, falling back to Borderless");
			this.fullscreenType = FullscreenType.BORDERLESS_FULLSCREEN;
		}

		if (fullscreenType == FullscreenType.BORDERLESS_FULLSCREEN) {
			if (this.graphicsDevice.getFullScreenWindow() != null) {
				this.graphicsDevice.setFullScreenWindow(null);
			}

			if (this.isDisplayable()) {
				this.dispose();
			}

			try {
				this.setUndecorated(true);
			} catch (Exception e) {
				System.err.println("Error setting undecorated: " + e.getMessage());
			}

			this.setResizable(false);

			java.awt.Rectangle screenBounds = this.graphicsDevice.getDefaultConfiguration().getBounds();
			this.setBounds(screenBounds);
			this.setVisible(true);
			this.initializeBufferStrategy();
		}
		this.isTransitioning = false;
	}

	public void switchToFullScreen() {
		this.setFullScreen();

		this.switchWindowFullScreenMode(FULLSCREEN,
				this.getBounds().width,
				this.getBounds().height);
	}

	public void backToWindow() {
		this.isTransitioning = true;
		this.switchWindowFullScreenMode(WINDOWED, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);

		this.restoreScreen();

		if (this.isDisplayable()) {
			this.dispose();
		}

		try {
			this.setUndecorated(false);
		} catch (Exception e) {
			System.err.println("Error restoring window decorations: " + e.getMessage());
		}

		this.setPreferredSize(new Dimension(CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT));
		this.setResizable(false);
		this.pack();
		this.setLocation(
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (CURRENT_WINDOW_WIDTH / 2)),
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (CURRENT_WINDOW_HEIGHT / 2)
						- 20));

		super.addWindowListener(this);
		super.setIgnoreRepaint(false);
		this.setVisible(true);
		this.initializeBufferStrategy();
		this.isTransitioning = false;
	}

	// --- Window Listener ---//
	public void windowActivated(WindowEvent arg0) {
		game.resumeGame();
	}

	public void windowClosing(WindowEvent arg0) {
		game.stopGame();
	}

	public void windowDeactivated(WindowEvent arg0) {
		game.pauseGame();
	}

	public void windowDeiconified(WindowEvent arg0) {
		game.resumeGame();
	}

	public void windowIconified(WindowEvent arg0) {
		game.pauseGame();
	}

	public void windowOpened(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	// --- Accessors (Thread-safe) ---//
	/**
	 * Checks if the window is ready for rendering.
	 * Call this in your Game Loop to prevent IllegalStateException during transitions.
	 * 
	 * @return true if a BufferStrategy exists and the window is visible
	 */
	public boolean isReadyToRender() {
		try {
			return !isTransitioning && isDisplayable() && isShowing() && getBufferStrategy() != null;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer getPanelWidth() {
		synchronized (dimensionLock) {
			return panelWidth;
		}
	}

	public Integer getPanelHeight() {
		synchronized (dimensionLock) {
			return panelHeight;
		}
	}

	/**
	 * Gets the current window dimensions in a thread-safe manner.
	 * 
	 * @return Dimension object with current width and height
	 */
	public Dimension getWindowDimensions() {
		synchronized (dimensionLock) {
			return new Dimension(CURRENT_WINDOW_WIDTH != null ? CURRENT_WINDOW_WIDTH : 0,
					CURRENT_WINDOW_HEIGHT != null ? CURRENT_WINDOW_HEIGHT : 0);
		}
	}

	public boolean isTripleBuffering() {
		return tripleBuffering;
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	// --- Key Listener ---//
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e.getKeyCode(), e.isAltDown());
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// --- Mouse Listener ---//
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse Click X: " + e.getX() + " Y: " + e.getY());
	}

	// Required interface implementations (empty if unused)
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}