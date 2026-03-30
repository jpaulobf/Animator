package br.com.game.animator.game.gameUI.intro;

import java.awt.Graphics2D;

import br.com.game.animator.game.gameUI.CoreGameLogic;

/**
 * @author João Paulo Faria
 */
public interface GameIntro extends CoreGameLogic {
	
	public final static Float MAX_COUNTER_SUBINTRO_VALUE = 900f;

	public boolean subIntroFinished();
	
	public void updateSubIntro();
	
	public void drawSubIntro(Graphics2D g2d);

	public void updateCanvasPropertiesSubIntro(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);
}