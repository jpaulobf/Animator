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
import javax.swing.JFrame;
import br.com.game.animator.game.IGame;
import br.com.game.animator.util.GlobalProperties;

/**
 * Window - Class responsible for creating and managing the game window, handling fullscreen mode, and processing user input.
 * This class extends JFrame and implements WindowListener to handle window events such as activation, deactivation, and closing.
 * It also manages the buffer strategy for rendering the game graphics and provides methods for switching between fullscreen and windowed modes.
 * The Window class interacts with the IGame interface to update the game settings when switching between fullscreen and windowed modes, and to handle key presses for game controls.
 */
public class Window extends JFrame implements WindowListener, KeyListener, MouseListener, MouseMotionListener {

    //--- Constants ---//
    private static final int NUM_BUFFERS = 2;
	private static final int TRIPLE_BUFFERS = 3;
    private static final boolean FULLSCREEN = true;
	private static final boolean WINDOWED = false;

    //--- Properties ---//
    private Integer CURRENT_WINDOW_WIDTH = null;
	private Integer CURRENT_WINDOW_HEIGHT = null;
	public volatile boolean fullScreen = FULLSCREEN;
    private GraphicsDevice graphicsDevice = null;
	private Integer panelWidth = null;
    private Integer panelHeight = null;
    private DisplayMode currentDisplayMode = null;
	private volatile boolean tripleBuffering = false;
    private Integer currentAspectRatio = null;
    private IGame game = null;

    /**
     * Window Construtor
     */
    public Window(IGame game) {
        super("The Game Engine");

        this.game = game;

        //--- config rendering ---//
		System.setProperty("sun.java2d.translaccel", "true");
		System.setProperty("sun.java2d.ddforcevram", "true");
		System.setProperty("sun.java2d.opengl", "true");

        //--- Graphics Device ---//
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        //--- Initialize window ---//
        this.currentAspectRatio = this.getCurrentAspectRatio();
		this.defineCurrentGameWindow();

        //--- Define window fullscreen strategy ---//
        if (this.fullScreen) {
			try {
				super.dispose();
			} catch (Exception e) {/*do nothing */}

			super.setIgnoreRepaint(true);
			super.setResizable(false);

            //--- Set the window to fullscreen ---//
			this.setFullScreen();
			this.addKeyListener(this);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);

			this.panelWidth = this.getBounds().width;
			this.panelHeight = this.getBounds().height;

		} else {
			this.setIconImage(
					Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/game-icon.png")));

			this.setPreferredSize(new Dimension(CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT));
			this.setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (CURRENT_WINDOW_WIDTH / 2)),
					         (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (CURRENT_WINDOW_HEIGHT / 2) - 20));
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

        //--- Buffer Strategy ---//
        {
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
            } catch (InterruptedException ex) {}
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setFocusable(true);
		super.requestFocus();

        //--- Finalize window ---//
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

	private boolean isScreenX2Available() {
		return (this.getGraphicsConfiguration()
				.getDevice()
				.getDisplayMode()
				.getWidth() >= GlobalProperties.WINDOW_WIDTH_X2_4_3);
	}

	private boolean isScreenX3Available() {
		return (this.getGraphicsConfiguration()
				.getDevice()
				.getDisplayMode()
				.getWidth() >= GlobalProperties.WINDOW_WIDTH_X3_4_3);
	}

	private boolean isScreenX4Available() {
		return (this.getGraphicsConfiguration()
				.getDevice()
				.getDisplayMode()
				.getWidth() >= GlobalProperties.WINDOW_WIDTH_X4_4_3);
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

	public void setWindowSizeX1_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_16_9;
	}

	public void setWindowSizeX2_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_16_9;
	}

	public void setWindowSizeX3_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_16_9;
	}

	public void setWindowSizeX4_16X9() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_16_9;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_16_9;
	}

	public void setWindowSizeX1_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_4_3;
	}

	public void setWindowSizeX2_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_4_3;
	}

	public void setWindowSizeX3_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_4_3;
	}

	public void setWindowSizeX4_4X3() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_4_3;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_4_3;
	}

	public void setWindowSizeX1_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X1_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X1_16_10;
	}

	public void setWindowSizeX2_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X2_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X2_16_10;
	}

	public void setWindowSizeX3_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X3_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X3_16_10;
	}

	public void setWindowSizeX4_16X10() {
		CURRENT_WINDOW_WIDTH = GlobalProperties.WINDOW_WIDTH_X4_16_10;
		CURRENT_WINDOW_HEIGHT = GlobalProperties.WINDOW_HEIGHT_X4_16_10;
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

    private void restoreScreen() {
		this.fullScreen = WINDOWED;
		java.awt.Window window = this.graphicsDevice.getFullScreenWindow();
		try {
			if (window != null) {
			}
			this.graphicsDevice.setFullScreenWindow(null);
		} catch (IllegalArgumentException ex) {
		} catch (Exception e) {
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

		if (!this.graphicsDevice.isFullScreenSupported()) {
			System.out.println("Full-screen exclusive mode not supported");
			super.setUndecorated(false);
			this.fullScreen = WINDOWED;
		}

		this.graphicsDevice.setFullScreenWindow(this);
	}

	public void switchToFullScreen() {
		this.setFullScreen();

		this.switchWindowFullScreenMode(FULLSCREEN,
				this.getBounds().width,
				this.getBounds().height);
	}

    public void backToWindow() {
		this.switchWindowFullScreenMode(WINDOWED, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);

		this.restoreScreen();

		this.setPreferredSize(new Dimension(CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT));
		this.setLocation(
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (CURRENT_WINDOW_WIDTH / 2)),
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (CURRENT_WINDOW_HEIGHT / 2)
						- 20));

		super.addWindowListener(this);

		try {
			super.setUndecorated(false);
		} catch (Exception e) {
		}
		super.setIgnoreRepaint(false);

		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

    //--- Window Listener ---//
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

    //--- Acessors ---//
    public Integer getPanelWidth() {
        return panelWidth;
    }

    public Integer getPanelHeight() {
        return panelHeight;
    }

    public boolean isTripleBuffering() {
        return tripleBuffering;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

	//--- Key Listener ---//
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

	//--- Mouse Listener ---//
	@Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Equivalente ao antigo testPress() da GameEngine
        System.out.println("Mouse Click X: " + e.getX() + " Y: " + e.getY());
    }

    // Implementações obrigatórias de interface (vazias se não usadas)
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}