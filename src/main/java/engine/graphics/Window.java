package engine.graphics;

import engine.listeners.KeyListener;
import engine.listeners.MouseListener;
import game.Player;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
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

    //    private static int monitorWidth = 2560;
//    private static int monitorHeight = 1440;
    private static int monitorWidth = 500;
    private static int monitorHeight = 500;

    private static Window window = null;

    private static Scene currentScene = null;

    private Window() {
        this.width = monitorWidth;
        this.height = monitorHeight;
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

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

        Window.changeScene(0);
    }

    public void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = -1.0f;

        Player.get().init();

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwSetWindowTitle(glfwWindow, title + " @ " + Math.round(1 / dt) + " FPS");

            // Poll events
            glfwPollEvents();

//            Player.get().update(dt);

            // TODO renders
            glClearColor(148.0f / 255.0f, 242.0f / 255.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0 -> {
                currentScene = new DevScene();
                currentScene.init();
                currentScene.start();
            }
            case 1 -> {
                currentScene = new HomeScene();
                currentScene.init();
                currentScene.start();
            }
            default -> {
                assert false : "Unknown scene: '" + newScene + "'";
            }
        }
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
}
