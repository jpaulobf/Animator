package br.com.game.animator.game.gameData;

import java.util.Map;

import br.com.game.animator.game.gameData.enumerators.DeepColor;
import br.com.game.animator.game.gameData.enumerators.ScreenMode;

/**
 * @author João Paulo Faria
 */
public interface GameGraphics {

	/**
	 * @return
	 * Recupera o tamanho da tela ativo.
	 */
	public Integer getScreenWidth();
	
	/**
	 * @return
	 * Recupera a altura da tela ativa.
	 */
	public Integer getScreenHeight();
	
	/**
	 * @return
	 * Recupera a profundidade de cores da tela ativo.
	 */
	public DeepColor getScreenDeepColor();
	
	/**
	 * @return
	 * Verifica se o Triple Buffer est� ativo.
	 */
	public boolean getTripleBufferEnabled();
	
	/**
	 * @return
	 * Recupera o Modo da Tela (Fullscreen/Window)
	 */
	public ScreenMode getScreenMode();
	
	/**
	 * @param screenWidth
	 * Define o tamanho da tela.
	 */
	public void setScreenWidth(Integer screenWidth);
	
	/**
	 * @param screenHeight
	 * Define a altura da tela.
	 */
	public void setScreenHeight(Integer screenHeight);
	
	/**
	 * @param deepColor
	 * Define a quantidades de cores da tela.
	 */
	public void setScreenDeepColor(DeepColor deepColor);
	
	/**
	 * @param enableTripleBuffer
	 * Habilita ou n�o o triple-buffer.
	 */
	public void setTripleBufferEnabled(boolean enableTripleBuffer);
	
	/**
	 * @param screenMode
	 * Define o modo da tela (Fullscreen/Window)
	 */
	public void setScreenMode(ScreenMode screenMode);
	
	/**
	 * @return
	 * Recupera a lista de resolu��es dispon�veis.
	 */
	public Map<String, GameGraphics> getAvailableScreenResolutions();
	
	/**
	 * @param screenResolutionsAvailable
	 * Armazena a lista de resolu��es dispon�veis.
	 */
	public void setAvailableScreenResolutions(Map<String, GameGraphics> availableScreenResolutions);
	
	/**
	 * Clona as propriedades simples (n�o listas)
	 */
	public void cloneProperties(GameGraphics gameGraphics);
	
}