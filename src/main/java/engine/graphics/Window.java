package engine.graphics;

import engine.editor.PickingTexture;
import engine.graphics.debug.DebugDraw;
import engine.graphics.renderer.Framebuffer;
import engine.graphics.renderer.Renderer;
import engine.listeners.KeyListener;
import engine.listeners.MouseListener;
import engine.util.AssetPool;
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

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imGuiLayer;
    private Framebuffer framebuffer;
    private PickingTexture pickingTexture;

    private static Window window = null;

    private static Scene currentScene = null;

    private Window() {
        this.width = Settings.MONITOR_WIDTH;
        this.height = Settings.MONITOR_HEIGHT;
        this.title = "Idle Ark";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

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
//        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
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

        if (Settings.DEVELOPMENT_MODE) {
            this.imGuiLayer = new ImGuiLayer(glfwWindow);
            this.imGuiLayer.initImGui();

            this.framebuffer = new Framebuffer(this.width, this.height);
            this.pickingTexture = new PickingTexture(this.width, this.height);
            glViewport(0, 0, this.width, this.height);
        }

        Window.changeScene(0);
    }


    /**
     * Sets up the game window on the secondary monitor.
     *
     * <p>This method is <b>development only</b>.</p>
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

    public void loop() {
        double frameBeginTime = glfwGetTime();
        double frameEndTime = glfwGetTime();

        Shader defaultShader = AssetPool.getShader("assets/shaders/default.glsl");

        while (!glfwWindowShouldClose(glfwWindow)) {
            frameEndTime = glfwGetTime();
            Engine.updateDeltaTime((float) (frameEndTime - frameBeginTime));
            frameBeginTime = frameEndTime;

            // Poll events
            glfwPollEvents();

            // Render picking texture
            pickingTexture.render(this.width, this.height, currentScene);

            // Render actual textures
            DebugDraw.beginFrame();

            if (Settings.DEVELOPMENT_MODE) {
                this.framebuffer.bind();
            }

            glClearColor(12.0f / 255.0f, 122.0f / 255.0f, 138.0f / 255.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (currentScene.isRunning()) {
                DebugDraw.draw();
                Renderer.bindShader(defaultShader);
                currentScene.update();
                currentScene.render();
                currentScene.endFrame();
            }


            // Render picking texture
            pickingTexture.render(this.width, this.height, currentScene);

            if (Settings.DEVELOPMENT_MODE) {
                this.framebuffer.unbind();
                this.imGuiLayer.update(currentScene);
            }

            glfwSwapBuffers(glfwWindow);

            // Close on escape press
            if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(glfwWindow, true);
            }

            get().getFPS();
        }

        currentScene.saveExit();
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0 -> currentScene = new DevScene();
            case 1 -> currentScene = new HomeScene();
            default -> {
                assert false : "Unknown scene: '" + newScene + "'";
            }
        }

        currentScene.load();
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
        return get().framebuffer;
    }

    public static float getTargetAspectRatio() {
        return Settings.TARGET_ASPECT_RATIO;
    }

    public static PickingTexture getPickingTexture() {
        return get().pickingTexture;
    }

    public float getFPS() {
        float fps = 1 / Engine.deltaTime();
        glfwSetWindowTitle(glfwWindow, title + " @ " + (int) fps + " FPS");
        return fps;
    }
}
