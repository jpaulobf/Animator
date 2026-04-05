package br.com.game.animator.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import br.com.game.animator.game.core.AbstractGame;
import br.com.game.animator.game.factory.CoreGameFactory;
import br.com.game.animator.game.gameData.enumerators.ScreenMode;
import br.com.game.animator.game.gameUI.CoreGameLogic;
import br.com.game.animator.game.gameUI.menu.GameExitMenu;
import br.com.game.animator.game.gameUI.menu.GameExitMenuImpl;
import br.com.game.animator.game.state.GameStateMachine;
import br.com.game.animator.window.Window;

/**
 * Class responsable for managing the game menu, including the developer logo, intro, high score presentation, 
 * main menu, options menu, and exit menu. It handles user input to navigate through these screens and updates the game settings accordingly.
 */
public class Game extends AbstractGame {

    private CoreGameLogic currentCoreGame;
    private GameStateMachine gameStateMachine;
    private Window gameWindow;
    private GameExitMenu gameExitMenu = null; 

    /**
     * Constructor for the Game class.
     */
    public Game() {
        //do nothing
    }
    
    /**
     * Initializes the game menu by creating instances of the various screens 
     * and options, and setting up their properties based on the game window's 
     * dimensions and aspect ratio.
     */
    public void init() {
        this.gameWindow = this.getGameWindow();
        this.gameStateMachine = new GameStateMachine();
        this.currentCoreGame = CoreGameFactory.getInstance(this.gameStateMachine, this.gameWindow);
        this.gameExitMenu = new GameExitMenuImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
    }

    /**
     * Updates the game menu based on the current state and the elapsed time since the last frame.
     */
    @Override
    public void update(long frametime) {
        if (!this.loading) {

            if (this.currentCoreGame.finished()) {
                this.currentCoreGame.resetCounters();
                this.gameStateMachine.gotoNextState();
                this.currentCoreGame = CoreGameFactory.getInstance(this.gameStateMachine, this.gameWindow);
            } else {
                this.currentCoreGame.update(frametime);
            }

		} else {
			this.currentCoreGame.update(frametime);
		}
    }

    /**
     * Renders the game menu based on the current state and the elapsed time since the last frame.
     * @param delta The time elapsed since the last frame.
     */
    @Override
    public void render(long delta) {
		this.graphics2D = (Graphics2D) gameWindow.getBufferStrategy().getDrawGraphics();

		if (this.graphics2D == null) {
			return;
		}

		if (!gameWindow.isFullScreen()) {
			this.graphics2D.translate(0, gameWindow.getInsets().top);
		}

		this.graphics2D.setColor(Color.BLACK);
		this.graphics2D.fillRect(0, 0, gameWindow.getPanelWidth(), gameWindow.getPanelHeight());

        //draw the current screen based on the game state machine
		this.currentCoreGame.draw(this.graphics2D);

        if (this.isToShowFPS) {
            if (this.graphics2D != null) {
                this.graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                this.graphics2D.setColor(Color.RED);
                this.graphics2D.drawString("Média de FPS / UPS: " + (int) (gameEngine.averageFPS) + " / " + (int) (gameEngine.averageUPS), 10, 20);
            }
        }

		if (this.gameExitMenu.isShowingExitMenu()) {
			this.gameExitMenu.draw(this.graphics2D);
		}
    }

    /**
     * Updates the game settings based on the current state of the game window.
     */
    public void updateGameSettings(boolean isFullScreen, Integer pWIDTH, Integer pHEIGHT) {     
		this.loading();

        {
            Integer currentAspectRatio = gameWindow.getCurrentAspectRatio();
            this.currentCoreGame.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);

            if (this.gameExitMenu != null) {
                this.gameExitMenu.updateGraphics(isFullScreen, pWIDTH, pHEIGHT, currentAspectRatio);
            }
        }

		this.loadingDone();
    }

    /**
     * Handles key press events to navigate through the game menu and update settings based on user input. 
     * It checks the current state of the menu and performs actions accordingly, such as navigating through options, 
     * applying changes, or showing/hiding the exit menu.
     */
    @Override
    public void keyPressed(int keyCode, boolean isAltDown) {
        if (!gameExitMenu.isShowingExitMenu()) {

            if (isAltDown && (keyCode == KeyEvent.VK_F4)) {
                if (!this.gameStateMachine.isInIntroDev() && 
                    !this.gameStateMachine.isInOptions()) {
                    if (gameExitMenu != null) {
                        gameExitMenu.showExitMenu();
                    }
                }
            } else if (keyCode != KeyEvent.VK_ALT && this.gameStateMachine.isInIntro()) {
                this.gameStateMachine.gotoMainMenu();
                this.currentCoreGame = CoreGameFactory.getInstance(this.gameStateMachine, this.gameWindow);
            } else if (isAltDown && (keyCode == KeyEvent.VK_ENTER)) {
                if (!gameWindow.isFullScreen()) {
                    pauseGame();

                    try {
                        gameWindow.switchToFullScreen();
                        CoreGameFactory.configureGameGraphics(ScreenMode.FULLSCREEN);

                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null,
                                "Nao foi possível inicializar em FullScreen.\n" +
                                        "Tente alterar o modo de video em Game-Options.",
                                "Erro ao alterar o modo de tela",
                                JOptionPane.ERROR_MESSAGE);

                        gameWindow.backToWindow();
                        CoreGameFactory.configureGameGraphics(ScreenMode.WINDOWED);
                    }
                    resumeGame();
                } else {
                    pauseGame();

                    gameWindow.backToWindow();
                    CoreGameFactory.configureGameGraphics(ScreenMode.WINDOWED);

                    resumeGame();
                }
            } else if (!this.gameStateMachine.isInIntro() && 
                       !this.gameStateMachine.isInOptions() && 
                      (keyCode == KeyEvent.VK_P) || (keyCode == KeyEvent.VK_PAUSE)) {
                if (!isPaused) {
                    pauseGame();
                } else {
                    resumeGame();
                }

            } else {
                //handle input for the current screen based on the game state machine
                this.currentCoreGame.handleInput(this, keyCode, isAltDown);
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

    /**
	 * loading - Set the loading flag to true, indicating that the game is currently
	 * loading resources or performing some initialization tasks.
	 */
	public void loading() {
        this.gameStateMachine.setLoadingState();
        this.currentCoreGame = CoreGameFactory.getInstance(this.gameStateMachine, this.gameWindow);
		this.loading = true;
	}

	/**
	 * loadingDone - Set the loading flag to false, indicating that the game has
	 * finished loading resources or initialization tasks.
	 */
	public void loadingDone() {
        this.gameStateMachine.unloadState();
        this.currentCoreGame = CoreGameFactory.getInstance(this.gameStateMachine, this.gameWindow);
		this.loading = false;
	}

    public void showExitMenu() {
        if (this.gameExitMenu != null) {
            this.gameExitMenu.showExitMenu();
        }
    }

    public void gotoMainOption() {
        this.gameStateMachine.gotoMainOption();
        this.currentCoreGame = CoreGameFactory.getInstance(gameStateMachine, gameWindow);
    }

    public void gotoMainMenu() {
        this.gameStateMachine.gotoMainMenu();
        this.currentCoreGame = CoreGameFactory.getInstance(gameStateMachine, gameWindow);
    }

    public void gotoGameOptions() {
        this.gameStateMachine.gotoGameOptions();
        this.currentCoreGame = CoreGameFactory.getInstance(gameStateMachine, gameWindow);
    }

    public void gotoSFXConfigMenu() {
        this.gameStateMachine.gotoSFXConfigMenu();
        this.currentCoreGame = CoreGameFactory.getInstance(gameStateMachine, gameWindow);
    }

    public void gotoGFXConfigMenu() {
        this.gameStateMachine.gotoGFXConfigMenu();
        this.currentCoreGame = CoreGameFactory.getInstance(gameStateMachine, gameWindow);
    }
}