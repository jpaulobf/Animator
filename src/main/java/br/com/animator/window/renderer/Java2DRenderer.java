package br.com.animator.window.renderer;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import br.com.animator.window.Window;

/**
 * Java2D Renderer implementation using native Java 2D graphics.
 * This is the traditional rendering approach using BufferStrategy.
 */
public class Java2DRenderer implements Renderer {

    private Window window;
    private boolean ready = false;
    protected Graphics2D graphics2D = null;

    @Override
    public void init(Window window) {
        this.window = window;
        this.ready = window != null && window.getBufferStrategy() != null;
        if (ready) {
            System.out.println("Java2DRenderer initialized successfully");
        } else {
            System.err.println("Java2DRenderer failed to initialize: window or BufferStrategy is null");
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        try {
            BufferStrategy strategy = window.getBufferStrategy();
			this.graphics2D = (Graphics2D) strategy.getDrawGraphics();
			this.graphics2D.dispose();
			if (!strategy.contentsLost()) {
				strategy.show();
			} else {
				System.out.println("Contents Lost");
			}
			Toolkit.getDefaultToolkit().sync();
        } catch (Exception e) {
            ready = false;
        }
    }

    @Override
    public void dispose() {
        System.out.println("Java2DRenderer disposed");
        ready = false;
    }

    @Override
    public String getRendererType() {
        return "native";
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    /**
     * Get the current BufferStrategy from the window.
     * Useful for external rendering operations.
     * 
     * @return The BufferStrategy, or null if not available
     */
    public BufferStrategy getBufferStrategy() {
        if (window != null) {
            return window.getBufferStrategy();
        }
        return null;
    }
}
