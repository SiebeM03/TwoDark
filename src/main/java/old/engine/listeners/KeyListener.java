package old.engine.listeners;

import old.engine.graphics.Window;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    /**
     * An array of booleans representing the state of each key on the keyboard.
     */
    private static boolean keyPressed[] = new boolean[350];


    public static void setupCallbacks() {
        long window = Window.getGlfwWindow();

        glfwSetKeyCallback(window, KeyListener::keyCallback);
    }

    public static void keyCallback(long window, int keycode, int scancode, int action, int mods) {
        if (keycode < keyPressed.length && keycode >= 0) {
            switch (action) {
                case GLFW_PRESS -> keyPressed[keycode] = true;
                case GLFW_RELEASE -> keyPressed[keycode] = false;
            }
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }
}
