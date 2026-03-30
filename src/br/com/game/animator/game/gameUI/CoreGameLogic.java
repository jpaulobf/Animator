package br.com.game.animator.game.gameUI;

import java.awt.Graphics2D;

public interface CoreGameLogic {

	public void draw(Graphics2D g2d);

	public void update(long frametime);

	public void resetCounters();

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);

	public boolean finished();
}
