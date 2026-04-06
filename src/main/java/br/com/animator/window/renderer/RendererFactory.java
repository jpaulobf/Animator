package br.com.animator.window.renderer;

import br.com.animator.util.GameConfig;

/**
 * Factory for creating Renderer instances based on configuration.
 * Encapsulates the logic for selecting the appropriate renderer implementation.
 */
public class RendererFactory {

    /**
     * Creates and returns a Renderer instance based on the render mode specified in GameConfig.
     * 
     * @return A Renderer instance (Java2DRenderer or LWJGLRenderer)
     */
    public static Renderer createRenderer() {
        GameConfig config = GameConfig.getInstance();
        String renderMode = config.getRenderMode();

        switch (renderMode.toLowerCase()) {
            case "opengl":
                System.out.println("RendererFactory: Creating LWJGLRenderer (OpenGL)");
                return new LWJGLRenderer();

            case "native":
            default:
                System.out.println("RendererFactory: Creating Java2DRenderer (native)");
                return new Java2DRenderer();
        }
    }

    /**
     * Creates a renderer with a specific type.
     * Useful for testing or forcing a specific renderer.
     * 
     * @param rendererType The type of renderer to create ("native" or "opengl")
     * @return A Renderer instance
     */
    public static Renderer createRenderer(String rendererType) {
        if (rendererType == null) {
            return createRenderer();
        }

        switch (rendererType.toLowerCase()) {
            case "opengl":
                System.out.println("RendererFactory: Creating LWJGLRenderer (OpenGL)");
                return new LWJGLRenderer();

            case "native":
            default:
                System.out.println("RendererFactory: Creating Java2DRenderer (native)");
                return new Java2DRenderer();
        }
    }
}
