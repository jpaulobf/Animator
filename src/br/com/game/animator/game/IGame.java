package br.com.game.animator.game;

public interface IGame {

    public void update(long frametime);

    public void render(long delta);

    public void paintScreen();

    public void updateGameSettings(boolean isFullScreen, Integer pWIDTH, Integer pHEIGHT);

	public void pauseGame();

	public void loading();

	public void loadingDone();

    public void resumeGame();

	public void stopGame();

    public void keyPressed(int keyCode, boolean isAltDown);
}
