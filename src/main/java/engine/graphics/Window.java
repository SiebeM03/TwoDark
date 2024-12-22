package engine.graphics;

import engine.graphics.debug.DebugDraw;
import engine.graphics.renderer.Framebuffer;
import engine.listeners.KeyListener;
import engine.listeners.MouseListener;
import engine.ui.fonts.FontLoader;
import engine.util.Engine;
import engine.util.ImGuiLayer;
import engine.util.Settings;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import scenes.DevScene;
import scenes.HomeScene;
import scenes.Scene;
import scenes.SceneLoader;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The main game window class responsible for initializing and managing the rendering context,
 * input listeners, and game scenes. It encapsulates the lifecycle of the application
 * including initialization, rendering, and cleanup.
 */
public class Window {

    /** Width of the game window in pixels. */
    private int width;

    /** Height of the game window in pixels. */
    private int height;

    /** Title displayed in the window's title bar. */
    private String title;

    /** Handle to the GLFW window instance. */
    private long glfwWindow;

    /** Layer responsible for rendering the ImGui user interface. */
    private ImGuiLayer imGuiLayer;

    /** Singleton instance of the window. */
    private static Window window = null;

    private static Framebuffer framebuffer;

    /** The currently active scene. */
    private static Scene currentScene = null;

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes default window settings.
     */
    private Window() {
        this.width = Settings.MONITOR_WIDTH;
        this.height = Settings.MONITOR_HEIGHT;
        this.title = "Idle Ark";
    }

    /**
     * Returns the singleton instance of the Window class.
     *
     * @return the Window instance
     */
    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    /**
     * Starts the main application loop, initializing and managing
     * resources and rendering until the window is closed.
     */
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Initializes GLFW, OpenGL, and window-specific settings.
     * Also prepares development resources if development mode is enabled.
     */
    public void init() {
        // Set up an error callback: where GLFW will show errors
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize  GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUSED, GLFW_TRUE);


        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }
//        setupMonitor();

        MouseListener.setupCallbacks();
        KeyListener.setupCallbacks();
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        framebuffer = new Framebuffer(this.width, this.height);

        if (Settings.DEVELOPMENT_MODE) {
            this.imGuiLayer = new ImGuiLayer(glfwWindow);
            this.imGuiLayer.initImGui();
            glViewport(0, 0, this.width, this.height);
        }
        FontLoader.loadFonts();

        Window.changeScene(0);
    }

    /**
     * Configures the game window to use the secondary monitor in development mode.
     * <p>
     * This method is <b>development only</b>.
     */
    private void setupMonitor() {
        PointerBuffer monitors = glfwGetMonitors();
        assert monitors != null : "Error: No monitors found";

        // Select the secondary monitor (last monitor in the list)
        long monitor = monitors.get(monitors.limit() - 1);

        // Get the video mode of the selected monitor
        GLFWVidMode mode = glfwGetVideoMode(monitor);
        assert mode != null : "Error: Video mode is null";

        int[] x = new int[1];
        int[] y = new int[1];
        glfwGetMonitorPos(monitor, x, y);

        glfwSetWindowPos(glfwWindow, x[0], y[0]);
        glfwSetWindowSize(glfwWindow, mode.width(), mode.height());
        glfwFocusWindow(glfwWindow);
    }

    /**
     * Executes the main rendering loop, updating the scene and rendering
     * frames until the window is closed.
     */
    public void loop() {
        double frameBeginTime = glfwGetTime();
        double frameEndTime = glfwGetTime();

        while (!glfwWindowShouldClose(glfwWindow)) {
            frameEndTime = glfwGetTime();
            Engine.updateDeltaTime((float) (frameEndTime - frameBeginTime));
            frameBeginTime = frameEndTime;

            // Poll events
            glfwPollEvents();

            // Render actual textures
            DebugDraw.beginFrame();

            if (currentScene.isRunning()) {
                DebugDraw.draw();
                currentScene.update();
                currentScene.updateGameObjects();
                currentScene.render();
                currentScene.processPendingModifications();
                currentScene.updateUI();
            }

            if (Settings.DEVELOPMENT_MODE) {
                this.imGuiLayer.update(currentScene);
            }

            glfwSwapBuffers(glfwWindow);

            // Close on escape press
            if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(glfwWindow, true);
            }

            get().getFPS();
        }

        SceneLoader.saveScene(currentScene);
    }

    /**
     * Changes the active scene to the specified scene ID.
     *
     * <ul>
     *     <li>Loads the scene ({@link SceneLoader#loadScene(Scene)})</li>
     *     <li>Initializes the scene ({@link Scene#init()})</li>
     *     <li>Starts the scene ({@link Scene#start()})</li>
     * </ul>
     *
     * @param newScene the ID of the new scene (0: {@link DevScene}, 1: {@link HomeScene})
     */
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0 -> currentScene = new DevScene();
            case 1 -> currentScene = new HomeScene();
            default -> {
                assert false : "Unknown scene: '" + newScene + "'";
            }
        }

        SceneLoader.loadScene(currentScene);
        currentScene.init();
        currentScene.start();
    }


    public static long getGlfwWindow() {
        return get().glfwWindow;
    }

    public static int getWidth() {
        return get().width;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public static Scene getScene() {
        return currentScene;
    }

    public static Framebuffer getFramebuffer() {
        return framebuffer;
    }

    public static int readPixel(int x, int y) {
        return currentScene.pickingRenderer().readPixel(x, y);
    }


    public static float getTargetAspectRatio() {
        return Settings.TARGET_ASPECT_RATIO;
    }

    /**
     * Updates the window's title to display the current frames per second (FPS).
     */
    public void getFPS() {
        float fps = 1 / Engine.deltaTime();
        glfwSetWindowTitle(glfwWindow, title + " @ " + (int) fps + " FPS");
    }

    public boolean loadFromFiles() {
        return true;
    }
}
