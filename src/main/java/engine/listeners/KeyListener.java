package engine.listeners;

import engine.graphics.Window;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private static KeyListener instance;
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {}

    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static void setupCallbacks() {
        long window = Window.getGlfwWindow();

        glfwSetKeyCallback(window, KeyListener::keyCallback);
    }
    public static void keyCallback(long window, int keycode, int scancode, int action, int mods) {
        switch (action) {
            case GLFW_PRESS -> get().keyPressed[keycode] = true;
            case GLFW_RELEASE -> get().keyPressed[keycode] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}
