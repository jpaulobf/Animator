package br.com.game.animator.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
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

/**
 * Class responsable for managing the game menu, including the developer logo, intro, high score presentation, 
 * main menu, options menu, and exit menu. It handles user input to navigate through these screens and updates the game settings accordingly.
 */
public class Game extends AbstractGame {

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

   	// -------------------------------------------------------//
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

    public Game() {
        //do nothing
    }
    
    public void init() {
        this.gameOptions = new GameOptionsImpl();
		this.gameLoading = new LoadingImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameSoundOptions = new GameSoundOptionsImpl();
		this.gameGraphics = new GameGraphicsImpl(gameWindow.isFullScreen(), gameWindow.isTripleBuffering());
		this.developerAdvertise = new DeveloperAdvertiseImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameIntro = new GameIntroImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameScore = new GameScorePresentationImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameMainMenu = new GameMainMenuImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameExitMenu = new GameExitMenuImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameMainOptionScreen = new GameMainOptionScreenImpl(gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameOptionScreen = new GameOptionScreenImpl(this.gameOptions, gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameSoundOptionScreen = new GameSoundOptionScreenImpl(this.gameSoundOptions, gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
		this.gameGraphicsScreen = new GameGraphicsScreenImpl(this.gameGraphics, gameWindow.getPanelWidth(), gameWindow.getPanelHeight(), gameWindow.getCurrentAspectRatio());
    }

    /**
     * Updates the game menu based on the elapsed frame time.
     * @param frametime The time elapsed since the last frame update.
     */
    @Override
    public void update(long frametime) {
        if (!this.loading) {
			if (this.isDevLogoScreen) {
				if (this.developerAdvertise.developerLogoPresentationFinished()) {
					this.isDevLogoScreen = false;
					this.isSubIntroScreen = true;
					this.developerAdvertise.resetCounters();
				} else {
					this.developerAdvertise.update();
				}

			} else if (this.isSubIntroScreen) {
				if (this.gameIntro.subIntroFinished()) {
					this.isSubIntroScreen = false;
					this.isIntroScreen = true;
					this.gameIntro.resetCounters();
				} else {
					this.gameIntro.updateSubIntro();
				}

			} else if (this.isIntroScreen) {
				if (this.gameIntro.introFinished()) {
					this.isIntroScreen = false;
					this.isShowHighScoreScreen = true;
					this.gameIntro.resetCounters();
				} else {
					this.gameIntro.updateIntro();
				}

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
			} else if (this.isInGameScreen) {
				 if (!this.isPaused && !this.gameOver) {
				    //todo
				 }
			} else if (this.isInGameOptionScreen) {
			    //todo
            } else if (this.isInMainOptionScreen) {
                this.gameMainOptionScreen.update();
            } else if (this.isInOptionGameScreen) {
                this.gameOptionScreen.update();
            } else if (this.isInOptionSoundScreen) {
                this.gameSoundOptionScreen.update();
            } else if (this.isInOptionKeyJoyScreen) {
                //todo
            } else if (this.isInOptionKeyScreen) {
				//todo
            } else if (this.isInOptionJoyScreen) {
			    //todo
            } else if (this.isInOptionGFXScreen) {
				this.gameGraphicsScreen.update();
            } else if (this.isInOptionTestGFXScreen) {
                //todo
            } else if (this.isInOptionProfileScreen) {
                //todo
            }
		} else {
			this.gameLoading.update();
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

		this.graphics2D.setColor(Color.WHITE);
		this.graphics2D.fillRect(0, 0, gameWindow.getPanelWidth(), gameWindow.getPanelHeight());

		if (this.loading) {
			this.gameLoading.draw(this.graphics2D);

		} else if (this.gameOver) {
            //todo
		} else if (this.isDevLogoScreen) {
			this.developerAdvertise.draw(this.graphics2D);

		} else if (this.isSubIntroScreen) {
			this.gameIntro.drawSubIntro(this.graphics2D);

		} else if (this.isIntroScreen) {
			this.gameIntro.drawIntro(this.graphics2D);

		} else if (this.isShowHighScoreScreen) {
			this.gameScore.drawHiScores(this.graphics2D);

		} else if (this.isMainMenuScreen) {
			this.gameMainMenu.drawMainMenu(this.graphics2D);

		} else if (this.isInMainOptionScreen) {
			this.gameMainOptionScreen.drawMainOptionScreen(this.graphics2D);

		} else if (this.isInOptionGameScreen) {
			this.gameOptionScreen.drawOptionScreen(this.graphics2D);

		} else if (this.isInOptionSoundScreen) {
			this.gameSoundOptionScreen.drawSoundOptionScreen(this.graphics2D);

		} else if (this.isInOptionGFXScreen) {
			this.gameGraphicsScreen.drawGraphicsOptionScreen(this.graphics2D);

		} else {
			if (gameWindow.isFullScreen()) {
			}
			this.graphics2D.setColor(Color.BLACK);
		}

        if (this.isToShowFPS) {
            if (this.graphics2D != null) {
                this.graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                this.graphics2D.setColor(Color.RED);
                this.graphics2D.drawString("Média de FPS / UPS: " + (int) (gameEngine.averageFPS) + " / " + (int) (gameEngine.averageUPS), 10, 20);
            }
        }

		if (this.gameExitMenu.isShowingExitMenu()) {
			this.gameExitMenu.drawExitMenu(this.graphics2D);
		}
    }

    /**
     * Updates the game settings based on the provided parameters, such as full screen mode, width, and height. 
     * It also updates the canvas properties of all relevant game screens to reflect the new settings.
     */
    public void updateGameSettings(boolean isFullScreen, Integer pWIDTH, Integer pHEIGHT) {
        
		this.loading();

		Integer currentAspectRatio = gameWindow.getCurrentAspectRatio();
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
     * Handles key press events to navigate through the game menu and update settings based on user input. 
     * It checks the current state of the menu and performs actions accordingly, such as navigating through options, 
     * applying changes, or showing/hiding the exit menu.
     */
    @Override
    public void keyPressed(int keyCode, boolean isAltDown) {
        if (!gameExitMenu.isShowingExitMenu()) {
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
                        // todo
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

            if (isAltDown && (keyCode == KeyEvent.VK_ENTER)) {
                if (!gameWindow.isFullScreen()) {
                    pauseGame();

                    try {
                        gameWindow.switchToFullScreen();
                        gameGraphics.setScreenMode(ScreenMode.FULLSCREEN);

                    } catch (Exception exception) {

                        JOptionPane.showMessageDialog(null,
                                "Nao foi possível inicializar em FullScreen.\n" +
                                        "Tente alterar o modo de video em Game-Options.",
                                "Erro ao alterar o modo de tela",
                                JOptionPane.ERROR_MESSAGE);

                        gameWindow.backToWindow();
                        gameGraphics.setScreenMode(ScreenMode.WINDOWED);
                    }
                    resumeGame();
                } else {
                    pauseGame();

                    gameWindow.backToWindow();
                    gameGraphics.setScreenMode(ScreenMode.WINDOWED);

                    resumeGame();
                }
            } else if ((keyCode == KeyEvent.VK_P) ||
                    (keyCode == KeyEvent.VK_PAUSE) &&
                            isInGameScreen) {
                if (!isPaused) {
                    pauseGame();
                } else {
                    resumeGame();
                }

            } else if (isAltDown && (keyCode == KeyEvent.VK_F4)) {
                if (!isDevLogoScreen &&
                        !isInMainOptionScreen &&
                        !isInOptionGameScreen &&
                        !isInOptionSoundScreen &&
                        !isInOptionGFXScreen) {
                    if (gameExitMenu != null) {
                        gameExitMenu.showExitMenu();
                    }
                }

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
}