package br.com.game.animator.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

import br.com.game.animator.engine.GameEngine;
import br.com.game.animator.game.gameData.enumerators.ScreenMode;

/**
 * InputHandler centraliza o tratamento de eventos de teclado e mouse.
 */
public class InputHandler extends KeyAdapter implements MouseListener, MouseMotionListener {

    private GameEngine engine;

    public InputHandler(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (engine.gameExitMenu == null) return;

        if (!engine.gameExitMenu.isShowingExitMenu()) {
            handleActiveGameInput(e, keyCode);
        } else {
            handleExitMenuInput(keyCode);
        }
    }

    private void handleActiveGameInput(KeyEvent e, int keyCode) {
        // Navegação no Menu Principal
        if (engine.isMainMenuScreen && engine.gameMainMenu != null) {
            if (keyCode == KeyEvent.VK_ENTER) {
                if (engine.gameMainMenu.isExitSelected()) {
                    engine.gameExitMenu.showExitMenu();
                } else if (engine.gameMainMenu.isOptionSelected()) {
                    engine.isMainMenuScreen = false;
                    engine.isInMainOptionScreen = true;
                }
            } else if (keyCode == KeyEvent.VK_UP) {
                engine.gameMainMenu.previousGameOption();
            } else if (keyCode == KeyEvent.VK_DOWN) {
                engine.gameMainMenu.nextGameOption();
            }
        } 
        // Opções Principais
        else if (engine.isInMainOptionScreen && engine.gameMainOptionScreen != null) {
            if (keyCode == KeyEvent.VK_ENTER) {
                if (engine.gameMainOptionScreen.isToBackToMainMenu()) {
                    engine.gameMainMenu.resetCounters();
                    engine.gameMainOptionScreen.resetCounters();
                    engine.isInMainOptionScreen = false;
                    engine.isMainMenuScreen = true;
                } else if (engine.gameMainOptionScreen.isToGoToGameOptions()) {
                    engine.gameMainOptionScreen.resetCounters();
                    engine.isInMainOptionScreen = false;
                    engine.isInOptionGameScreen = true;
                } else if (engine.gameMainOptionScreen.isToConfigSFX()) {
                    engine.gameMainOptionScreen.resetCounters();
                    engine.isInMainOptionScreen = false;
                    engine.isInOptionSoundScreen = true;
                } else if (engine.gameMainOptionScreen.isToConfigGFX()) {
                    engine.gameMainOptionScreen.resetCounters();
                    engine.isInMainOptionScreen = false;
                    engine.isInOptionGFXScreen = true;
                    engine.gameGraphicsScreen.resetMustApplyForChanges();
                }
            } else if (keyCode == KeyEvent.VK_UP) {
                engine.gameMainOptionScreen.previousGameOption();
            } else if (keyCode == KeyEvent.VK_DOWN) {
                engine.gameMainOptionScreen.nextGameOption();
            }
        }
        // Opções de Jogo
        else if (engine.isInOptionGameScreen && engine.gameOptionScreen != null) {
            handleGameOptionsInput(keyCode);
        }
        // Opções de Som
        else if (engine.isInOptionSoundScreen && engine.gameSoundOptionScreen != null) {
            handleSoundOptionsInput(keyCode);
        }
        // Opções Gráficas
        else if (engine.isInOptionGFXScreen && engine.gameGraphicsScreen != null) {
            handleGraphicsOptionsInput(keyCode);
        }

        // Teclas de Sistema
        handleSystemInput(e, keyCode);
    }

    private void handleGameOptionsInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            if (engine.gameOptionScreen.isToBackToMainOption()) {
                engine.gameOptionScreen.resetCounters();
                engine.gameMainOptionScreen.resetCounters();
                engine.isInOptionGameScreen = false;
                engine.isInMainOptionScreen = true;
            }
        } else if (keyCode == KeyEvent.VK_UP) {
            engine.gameOptionScreen.previousOption();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            engine.gameOptionScreen.nextOption();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (engine.gameOptionScreen.isOverEnableSubtitles()) engine.gameOptionScreen.enableSubtitles();
            else if (engine.gameOptionScreen.isOverGameDifficulty()) engine.gameOptionScreen.setPreviousDifficulty();
            else if (engine.gameOptionScreen.isOverRestsSelection()) engine.gameOptionScreen.subRest();
            else if (engine.gameOptionScreen.isOverExtraLifeSelection()) engine.gameOptionScreen.setPreviousExtraLifeAtPoints();
            else if (engine.gameOptionScreen.isOverContinuesSelection()) engine.gameOptionScreen.subContinues();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            if (engine.gameOptionScreen.isOverEnableSubtitles()) engine.gameOptionScreen.disableSubtitles();
            else if (engine.gameOptionScreen.isOverGameDifficulty()) engine.gameOptionScreen.setNextDifficulty();
            else if (engine.gameOptionScreen.isOverRestsSelection()) engine.gameOptionScreen.addRest();
            else if (engine.gameOptionScreen.isOverExtraLifeSelection()) engine.gameOptionScreen.setNextExtraLifeAtPoints();
            else if (engine.gameOptionScreen.isOverContinuesSelection()) engine.gameOptionScreen.addContinues();
        }
    }

    private void handleSoundOptionsInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            if (engine.gameSoundOptionScreen.isToBackToMainOption()) {
                engine.gameSoundOptionScreen.resetCounters();
                engine.gameMainOptionScreen.resetCounters();
                engine.isInOptionSoundScreen = false;
                engine.isInMainOptionScreen = true;
            }
        } else if (keyCode == KeyEvent.VK_UP) {
            engine.gameSoundOptionScreen.previousOption();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            engine.gameSoundOptionScreen.nextOption();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (engine.gameSoundOptionScreen.isOverEnableMusic()) engine.gameSoundOptionScreen.setMusicEnable();
            else if (engine.gameSoundOptionScreen.isOverEnableSFX()) engine.gameSoundOptionScreen.setSFXEnable();
            else if (engine.gameSoundOptionScreen.isOverMusicVolume()) engine.gameSoundOptionScreen.decreaseMusicVolume();
            else if (engine.gameSoundOptionScreen.isOverSFXVolume()) engine.gameSoundOptionScreen.decreaseSFXVolume();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            if (engine.gameSoundOptionScreen.isOverEnableMusic()) engine.gameSoundOptionScreen.setMusicDisable();
            else if (engine.gameSoundOptionScreen.isOverEnableSFX()) engine.gameSoundOptionScreen.setSFXDisable();
            else if (engine.gameSoundOptionScreen.isOverMusicVolume()) engine.gameSoundOptionScreen.increaseMusicVolume();
            else if (engine.gameSoundOptionScreen.isOverSFXVolume()) engine.gameSoundOptionScreen.increaseSFXVolume();
        }
    }

    private void handleGraphicsOptionsInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            if (engine.gameGraphicsScreen.isToBackToMainOption()) {
                engine.gameGraphicsScreen.resetCounters();
                engine.gameGraphicsScreen.cancelChanges();
                engine.gameMainOptionScreen.resetCounters();
                engine.isInOptionGFXScreen = false;
                engine.isInMainOptionScreen = true;
            }
        } else if (keyCode == KeyEvent.VK_UP) {
            engine.gameGraphicsScreen.previousOption();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            engine.gameGraphicsScreen.nextOption();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (engine.gameGraphicsScreen.isOverEnableTripleBuffering()) engine.gameGraphicsScreen.enableTripleBuffer();
            else if (engine.gameGraphicsScreen.isOverScreenMode()) engine.gameGraphicsScreen.previousScreenMode();
            else if (engine.gameGraphicsScreen.isOverDeepColor()) engine.gameGraphicsScreen.previousScreenDeepColor();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            if (engine.gameGraphicsScreen.isOverEnableTripleBuffering()) engine.gameGraphicsScreen.disableTripleBuffer();
            else if (engine.gameGraphicsScreen.isOverScreenMode()) engine.gameGraphicsScreen.nextScreenMode();
            else if (engine.gameGraphicsScreen.isOverDeepColor()) engine.gameGraphicsScreen.nextScreenDeepColor();
        }
    }

    private void handleSystemInput(KeyEvent e, int keyCode) {
        // Alt + Enter: Fullscreen toggle
        if (e.isAltDown() && (keyCode == KeyEvent.VK_ENTER)) {
            engine.pauseGame();
            if (!engine.fullScreen) {
                try {
                    engine.switchToFullScreen();
                    engine.gameGraphics.setScreenMode(ScreenMode.FULLSCREEN);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possível alternar para FullScreen.");
                    engine.backToWindow();
                }
            } else {
                engine.backToWindow();
                engine.gameGraphics.setScreenMode(ScreenMode.WINDOWED);
            }
            engine.resumeGame();
        }
        // Pause
        else if ((keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_PAUSE) && engine.isInGameScreen) {
            if (!engine.isPaused) engine.pauseGame(); else engine.resumeGame();
        }
        // Sair (Alt+F4)
        else if (e.isAltDown() && (keyCode == KeyEvent.VK_F4)) {
            if (!engine.isDevLogoScreen && !engine.isInMainOptionScreen && !engine.isInOptionGameScreen) {
                engine.gameExitMenu.showExitMenu();
            }
        }
        // Pular intro
        else if (keyCode != KeyEvent.VK_ALT) {
            if (engine.isSubIntroScreen || engine.isIntroScreen || engine.isShowHighScoreScreen) {
                engine.isSubIntroScreen = false;
                engine.isIntroScreen = false;
                engine.isShowHighScoreScreen = false;
                engine.isMainMenuScreen = true;
            }
        }
    }

    private void handleExitMenuInput(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) engine.gameExitMenu.previousGameOption();
        else if (keyCode == KeyEvent.VK_RIGHT) engine.gameExitMenu.nextGameOption();
        else if (keyCode == KeyEvent.VK_ENTER) {
            if (engine.gameExitMenu.isToExit()) engine.stopGame();
            else engine.gameExitMenu.hideExitMenu();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Equivalente ao antigo testMove() da GameEngine
        if (engine.running && engine.fullScreen) {
            // Lógica específica para movimento em tela cheia se necessário
        }
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
