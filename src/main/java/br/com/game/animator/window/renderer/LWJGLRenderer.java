package br.com.game.animator.window.renderer;

import java.awt.Graphics2D;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import br.com.game.animator.window.Window;

/**
 * LWJGL Renderer implementation using OpenGL through LWJGL.
 * This renderer uses OpenGL for rendering via GLFW window context.
 * 
 * NOTE: This implementation creates an OpenGL context using GLFW.
 * The Window parameter is used for dimension information but not directly integrated.
 */
public class LWJGLRenderer implements Renderer {

    private long glfwWindow = 0;
    private Window window;
    private boolean ready = false;
    private boolean contextInitialized = false;
    private static final int RENDER_WIDTH = 1024;
    private static final int RENDER_HEIGHT = 768;

    @Override
    public void init(Window window) {
        this.window = window;
        System.out.println("LWJGLRenderer initialization started (window creation deferred to render thread)");
        
        try {
            // Setup error callback (safe to do here)
            GLFWErrorCallback.createPrint(System.err).set();

            // Initialize GLFW (safe to do here)
            if (!GLFW.glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
            }

            this.ready = true;
            System.out.println("GLFW initialized");

        } catch (Exception e) {
            System.err.println("LWJGLRenderer failed during init: " + e.getMessage());
            e.printStackTrace();
            this.ready = false;
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (!ready || glfwWindow == 0) {
            // Initialize OpenGL context and window on first render (must be in rendering thread)
            if (!contextInitialized && ready) {
                try {
                    initializeGLFWWindow();
                    contextInitialized = true;
                } catch (Exception e) {
                    System.err.println("LWJGLRenderer failed to initialize window: " + e.getMessage());
                    e.printStackTrace();
                    this.ready = false;
                    return;
                }
            }
            
            if (!ready || glfwWindow == 0) {
                return;
            }
        }

        try {
            // Check if window should close
            if (GLFW.glfwWindowShouldClose(glfwWindow)) {
                this.ready = false;
                return;
            }

            // Clear the framebuffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Render game objects
            renderTestTriangle();

            // Swap buffers
            GLFW.glfwSwapBuffers(glfwWindow);

            // Poll events
            GLFW.glfwPollEvents();

        } catch (Exception e) {
            System.err.println("LWJGLRenderer error during rendering: " + e.getMessage());
            e.printStackTrace();
            this.ready = false;
        }
    }

    /**
     * Initialize GLFW window and OpenGL context (must be called from rendering thread).
     */
    private void initializeGLFWWindow() {
        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        // Use Compatibility Profile to allow legacy functions like glBegin/glEnd
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_COMPAT_PROFILE);

        // Get window dimensions from the passed Window object
        int width = RENDER_WIDTH;
        int height = RENDER_HEIGHT;
        if (this.window != null) {
            width = this.window.getPanelWidth();
            height = this.window.getPanelHeight();
        }

        // Create the GLFW window
        glfwWindow = GLFW.glfwCreateWindow(width, height, "Animator Engine - LWJGL", 0, 0);
        if (glfwWindow == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup resize callback
        GLFW.glfwSetFramebufferSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            GL11.glViewport(0, 0, newWidth, newHeight);
        });

        // Make the OpenGL context current (must be before glfwSwapInterval)
        GLFW.glfwMakeContextCurrent(glfwWindow);
        
        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Make the window visible
        GLFW.glfwShowWindow(glfwWindow);

        // Focus the window to bring it to front
        GLFW.glfwFocusWindow(glfwWindow);

        // Initialize OpenGL capabilities
        GL.createCapabilities();

        // Setup OpenGL viewport
        GL11.glViewport(0, 0, width, height);

        // Configure OpenGL settings
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        System.out.println("LWJGLRenderer window and context initialized successfully");
        System.out.println("  Window size: " + width + "x" + height);
        System.out.println("  OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("  Renderer: " + GL11.glGetString(GL11.GL_RENDERER));
        System.out.println("  Vendor: " + GL11.glGetString(GL11.GL_VENDOR));
    }

    /**
     * Renders a simple test triangle using immediate mode (for testing purposes).
     * This should be replaced with proper shader-based rendering in production.
     */
    private void renderTestTriangle() {
        // Immediate mode rendering (deprecated but useful for testing)
        GL11.glBegin(GL11.GL_TRIANGLES);
        
        // Red vertex
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex2f(-0.5f, -0.5f);
        
        // Green vertex
        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex2f(0.5f, -0.5f);
        
        // Blue vertex
        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex2f(0.0f, 0.5f);
        
        GL11.glEnd();
    }

    /**
     * Clean up LWJGL resources.
     */
    private void cleanup() {
        // Destroy the window if valid
        if (glfwWindow != 0) {
            GLFW.glfwDestroyWindow(glfwWindow);
            glfwWindow = 0;
        }
        
        // Terminate GLFW and free error callback
        GLFW.glfwTerminate();
        GLFWErrorCallback callback = GLFW.glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    @Override
    public void dispose() {
        try {
            cleanup();
            System.out.println("LWJGLRenderer disposed");
        } catch (Exception e) {
            System.err.println("LWJGLRenderer error during disposal: " + e.getMessage());
            e.printStackTrace();
        }
        ready = false;
    }

    @Override
    public String getRendererType() {
        return "opengl";
    }

    @Override
    public boolean isReady() {
        // We are "ready" to start rendering as soon as init() finishes, 
        // even if the glfwWindow is created lazily on the first render call.
        return ready;
    }

    /**
     * Get the GLFW window handle.
     * Useful for advanced rendering operations.
     * 
     * @return The GLFW window ID
     */
    public long getGlfwWindow() {
        return glfwWindow;
    }
}
