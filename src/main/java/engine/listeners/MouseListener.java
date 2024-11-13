package engine.listeners;

import engine.graphics.Window;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;

    private boolean mouseButtonPressed[] = new boolean[9];
    private int mouseButtonsDown = 0;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    public static void setupCallbacks() {
        long window = Window.getGlfwWindow();

        glfwSetCursorPosCallback(window, (w, xPos, yPos) -> {
            get().lastX = get().xPos;
            get().lastY = get().yPos;
            get().xPos = xPos;
            get().yPos = yPos;
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                get().mouseButtonsDown++;

                if (button < get().mouseButtonPressed.length) {
                    get().mouseButtonPressed[button] = true;
                }
            } else if (action == GLFW_RELEASE) {
                get().mouseButtonsDown--;

                if (button < get().mouseButtonPressed.length) {
                    get().mouseButtonPressed[button] = false;
                }
            }
        });

        glfwSetScrollCallback(window, (w, xOffset, yOffset) -> {
            get().scrollX = (float) xOffset;
            get().scrollY = (float) yOffset;
        });
    }

    public static void clearMouseInput() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }
}
