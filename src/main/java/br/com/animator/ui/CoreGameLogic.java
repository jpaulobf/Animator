package br.com.animator.ui;

import java.awt.Graphics2D;

import br.com.animator.game.Game;

public interface CoreGameLogic {

	public void draw(Graphics2D g2d);

	public void update(long frametime);

	public void resetCounters();

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);

	public boolean finished();

	public void handleInput(Game game, int keyCode, boolean isAltDown);
}
