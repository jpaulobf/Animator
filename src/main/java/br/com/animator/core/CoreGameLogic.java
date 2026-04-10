package br.com.animator.core;

import java.awt.Graphics2D;
import br.com.animator.input.GameAction;

public interface CoreGameLogic {

	public void draw(Graphics2D g2d);

	public void update(long frametime);

	public void resetCounters();

	public void updateGraphics(boolean fullScreen, Integer pwidth, Integer pheight, Integer currentAspectRatio);

	public boolean finished();

	public void handleInput(IGame game, GameAction action);
}
