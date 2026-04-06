package br.com.game.animator.window.renderer;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import br.com.game.animator.window.Window;

/**
 * Java2D Renderer implementation using native Java 2D graphics.
 * This is the traditional rendering approach using BufferStrategy.
 */
public class Java2DRenderer implements Renderer {

    private Window window;
    private boolean ready = false;

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
        if (!ready || window == null) {
            return;
        }

        try {
            BufferStrategy strategy = window.getBufferStrategy();
            if (strategy == null) {
                return;
            }

            // Get graphics from strategy
            Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
            if (g2d == null) {
                return;
            }

            // Dispose of the graphics object
            g2d.dispose();

            // Show the buffer
            if (!strategy.contentsLost()) {
                strategy.show();
            } else {
                System.out.println("BufferStrategy contents lost");
            }

            // Sync with system toolkit
            Toolkit.getDefaultToolkit().sync();
        } catch (Exception e) {
            System.err.println("Java2DRenderer error during rendering: " + e.getMessage());
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
