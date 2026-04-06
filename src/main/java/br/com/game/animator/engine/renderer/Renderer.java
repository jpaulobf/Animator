package br.com.game.animator.engine.renderer;

import java.awt.Graphics2D;
import br.com.game.animator.window.Window;

/**
 * Renderer interface that defines the contract for different rendering strategies.
 * Implementations can use Java2D (native) or LWJGL (OpenGL) for rendering.
 */
public interface Renderer {

    /**
     * Initialize the renderer with the game window.
     * This should be called during game initialization.
     * 
     * @param window The game window used for rendering
     */
    void init(Window window);

    /**
     * Render the current frame.
     * This method should handle all rendering operations for the current frame.
     * 
     * @param graphics2D The Graphics2D object (may be null for some implementations like LWJGL)
     */
    void render(Graphics2D graphics2D);

    /**
     * Dispose of renderer resources.
     * This should be called when the game is shutting down.
     */
    void dispose();

    /**
     * Get the name/type of this renderer.
     * 
     * @return The renderer type name (e.g., "native", "lwjgl")
     */
    String getRendererType();

    /**
     * Check if the renderer is ready to render.
     * 
     * @return true if the renderer is initialized and ready to render
     */
    boolean isReady();
}
