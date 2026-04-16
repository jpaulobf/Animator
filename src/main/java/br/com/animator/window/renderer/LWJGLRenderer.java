package br.com.animator.window.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.event.KeyEvent;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import br.com.animator.window.Window;

/**
 * LWJGL Renderer implementation using OpenGL through LWJGL.
 * This renderer uses OpenGL for rendering via GLFW window context.
 * 
 * NOTE: This implementation creates an OpenGL context using GLFW.
 * The Window parameter is used for dimension information but not directly
 * integrated.
 */
public class LWJGLRenderer implements Renderer {

    private long glfwWindow = 0;
    private Window window;
    private boolean ready = false;
    private boolean contextInitialized = false;
    private int width;
    private int height;
    private int textureId = -1;
    private ByteBuffer pixelBuffer;
    private IntBuffer viewBuffer;
    private int pboId = -1;

    @Override
    public void init(Window window) {
        this.window = window;
        System.out.println("LWJGLRenderer initialization started (window creation deferred to render thread)");

        this.width = window.getPanelWidth();
        this.height = window.getPanelHeight();

        // Pre-allocate pixel buffer to avoid GC and black screen due to slowness
        this.pixelBuffer = ByteBuffer.allocateDirect(width * height * 4);
        this.pixelBuffer.order(ByteOrder.nativeOrder());
        this.viewBuffer = pixelBuffer.asIntBuffer();

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
        if (!ready)
            return;

        if (!contextInitialized) {
            try {
                initializeGLFWWindow();
                contextInitialized = true;
            } catch (Exception e) {
                System.err.println("Failed to initialize GLFW Window on Render Thread: " + e.getMessage());
                this.ready = false;
                return;
            }
        }

        if (glfwWindow == 0)
            return;

        try {
            // Check if window should close
            if (glfwWindow != 0 && GLFW.glfwWindowShouldClose(glfwWindow)) {
                window.getGame().stopGame();
                cleanup();
                System.exit(0);
                return;
            }

            // Clear the framebuffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, (double) width, (double) height, 0.0, -1.0, 1.0);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();

            renderJava2DBuffer();

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

    private void renderJava2DBuffer() {
        BufferedImage image = window.getGame().getMainBuffer();
        if (image == null)
            return;

        if (textureId == -1) {
            textureId = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

            // Basic texture settings
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA,
                    GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

            // Initialize PBO for asynchronous upload
            pboId = GL15.glGenBuffers();
            GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pboId);
            GL15.glBufferData(GL21.GL_PIXEL_UNPACK_BUFFER, (long) width * height * 4, GL15.GL_STREAM_DRAW);
            GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
        }

        // 1. Prepare data
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        viewBuffer.clear();
        viewBuffer.put(pixels);
        pixelBuffer.rewind();

        // 2. Upload via PBO (Asynchronous)
        GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pboId);

        // Orphaning: tell the driver we no longer need the previous content, avoiding
        // waits
        GL15.glBufferData(GL21.GL_PIXEL_UNPACK_BUFFER, (long) width * height * 4, GL15.GL_STREAM_DRAW);
        GL15.glBufferSubData(GL21.GL_PIXEL_UNPACK_BUFFER, 0, pixelBuffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        // When a PBO is bound, the last argument is the offset (0) and not the pointer
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, width, height, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, 0);
        GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);

        // Draw a square covering the entire screen with the texture
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST); // Disable depth for 2D blitting
        GL11.glDisable(GL11.GL_LIGHTING); // Ensure that lights do not darken the screen
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glBegin(GL11.GL_QUADS);

        // Mapping adjustment: Binding the top of the texture (0,0) to the top of the
        // screen (0,0)
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(width, 0);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, height);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Initialize GLFW window and OpenGL context (must be called from rendering
     * thread).
     */
    private void initializeGLFWWindow() {
        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUSED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        // Use Compatibility Profile to allow legacy functions like glBegin/glEnd
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_COMPAT_PROFILE);

        if (this.window != null) {
            this.width = this.window.getPanelWidth();
            this.height = this.window.getPanelHeight();
        }

        long monitor = 0;
        if (this.window != null && this.window.isFullScreen()) {
            monitor = GLFW.glfwGetPrimaryMonitor();
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(monitor);
            if (vidmode != null) {
                this.width = vidmode.width();
                this.height = vidmode.height();
            }
        }

        // Create the GLFW window
        glfwWindow = GLFW.glfwCreateWindow(width, height, "Animator Engine - LWJGL", monitor, 0);
        if (glfwWindow == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        if (monitor == 0) {
            // Centraliza a janela se não for fullscreen
            centerWindow(glfwWindow, width, height);
        }

        // Setup resize callback
        GLFW.glfwSetFramebufferSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            GL11.glViewport(0, 0, newWidth, newHeight);
        });

        // Setup Key Callback to forward events to the game
        GLFW.glfwSetKeyCallback(glfwWindow, (windowHandle, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
                int awtKeyCode = mapGlfwToAwtKey(key);
                boolean isAltDown = (mods & GLFW.GLFW_MOD_ALT) != 0;
                window.getGame().processKey(awtKeyCode, isAltDown);
            }
        });

        // Make the OpenGL context current (must be before glfwSwapInterval)
        GLFW.glfwMakeContextCurrent(glfwWindow);

        // 0 = Disable V-Sync (Maximum FPS), 1 = Enable V-Sync (Stable)
        GLFW.glfwSwapInterval(0);

        // Make the window visible
        GLFW.glfwShowWindow(glfwWindow);

        // Detect Window Scale (DPI)
        float[] xscale = new float[1];
        float[] yscale = new float[1];
        GLFW.glfwGetWindowContentScale(glfwWindow, xscale, yscale);
        System.out.println("  Detected Monitor Scale: " + xscale[0] + "x (DPI Awareness)");

        // Focus the window to bring it to front
        GLFW.glfwFocusWindow(glfwWindow);

        // ESSENTIAL: Load function pointers for the current thread (AWT)
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
     * Maps GLFW key codes to AWT key codes used by the game logic.
     */
    private int mapGlfwToAwtKey(int glfwKey) {
        return switch (glfwKey) {
            case GLFW.GLFW_KEY_LEFT -> KeyEvent.VK_LEFT;
            case GLFW.GLFW_KEY_RIGHT -> KeyEvent.VK_RIGHT;
            case GLFW.GLFW_KEY_UP -> KeyEvent.VK_UP;
            case GLFW.GLFW_KEY_DOWN -> KeyEvent.VK_DOWN;
            case GLFW.GLFW_KEY_ENTER -> KeyEvent.VK_ENTER;
            case GLFW.GLFW_KEY_P -> KeyEvent.VK_P;
            case GLFW.GLFW_KEY_PAUSE -> KeyEvent.VK_PAUSE;
            case GLFW.GLFW_KEY_F4 -> KeyEvent.VK_F4;
            case GLFW.GLFW_KEY_LEFT_ALT, GLFW.GLFW_KEY_RIGHT_ALT -> KeyEvent.VK_ALT;
            case GLFW.GLFW_KEY_ESCAPE -> KeyEvent.VK_ESCAPE;
            case GLFW.GLFW_KEY_SPACE -> KeyEvent.VK_SPACE;
            case GLFW.GLFW_KEY_A -> KeyEvent.VK_A;
            case GLFW.GLFW_KEY_B -> KeyEvent.VK_B;
            case GLFW.GLFW_KEY_C -> KeyEvent.VK_C;
            // Add more mappings as needed for your game
            default -> glfwKey;
        };
    }

    private void centerWindow(long window, int w, int h) {
        long monitor = GLFW.glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(monitor);
        if (vidmode != null) {
            GLFW.glfwSetWindowPos(window,
                    (vidmode.width() - w) / 2,
                    (vidmode.height() - h) / 2);
        }
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

        if (pboId != -1) {
            GL15.glDeleteBuffers(pboId);
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

    @Override
    public boolean isNative() {
        return false;
    }
}
