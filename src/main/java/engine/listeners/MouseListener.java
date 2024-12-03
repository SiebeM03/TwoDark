package engine.listeners;

import engine.graphics.Camera;
import engine.graphics.Window;
import engine.util.Settings;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {

    /**
     * The current position of the mouse cursor.
     */
    private static double xPos, yPos;

    /**
     * The position of the mouse cursor in the previous frame.
     */
    private static double lastX, lastY;

    /**
     * The scroll wheel's offset.
     */
    private static double scrollX, scrollY;

    /**
     * An array of booleans representing the state of each mouse button.
     */
    private static boolean mouseButtonPressed[] = new boolean[9];

    /**
     * The number of mouse buttons currently pressed.
     */
    private static int mouseButtonsDown = 0;

    private static Vector2f gameViewPortPos = new Vector2f();
    private static Vector2f gameViewPortSize = new Vector2f();

    public static void setupCallbacks() {
        long window = Window.getGlfwWindow();

        glfwSetCursorPosCallback(window, MouseListener::mousePosCallback);

        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
    }

    public static void mousePosCallback(long window, double newXPos, double newYPos) {
        lastX = xPos;
        lastY = yPos;
        xPos = newXPos;
        yPos = newYPos;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            mouseButtonsDown++;

            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            mouseButtonsDown--;

            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        scrollX = (float) xOffset;
        scrollY = (float) yOffset;
        clearMouseInput();
    }

    /**
     * @param button The mouse button to check (use GLFW constants).
     * @return true if the mouse button is currently pressed, false otherwise.
     */
    public static boolean mouseButtonDown(int button) {
        if (button < mouseButtonPressed.length) {
            return mouseButtonPressed[button];
        } else {
            return false;
        }
    }

    /**
     * This method should be called once per frame.
     *
     * <p>Resets scroll values and sets {@link #lastX} and {@link #lastY} to the mouse position of the previous frame.</p>
     */
    public static void clearMouseInput() {
        scrollX = 0;
        scrollY = 0;
        lastX = xPos;
        lastY = yPos;
    }


    /**
     * @return The x position of the mouse cursor on the monitor.
     */
    public static float getX() {
        return (float) xPos;
    }

    /**
     * @return The y position of the mouse cursor on the monitor.
     */
    public static float getY() {
        return (float) yPos;
    }


    /**
     * @return The change in x position of the mouse cursor from the previous frame.
     */
    public static float getDx() {
        return (float) (lastX - xPos);
    }

    /**
     * @return The change in y position of the mouse cursor from the previous frame.
     */
    public static float getDy() {
        return (float) (lastY - yPos);
    }


    /**
     * @return The scroll offset in the x direction.
     */
    public static float getScrollX() {
        return (float) scrollX;
    }

    /**
     * @return The scroll offset in the y direction.
     */
    public static float getScrollY() {
        return (float) scrollY;
    }


    /**
     * @return The x position of the mouse cursor in the screen (game view port or window).
     */
    public static float getScreenX() {
        float currentX = getX() - gameViewPortPos.x;
        currentX = (currentX / (float) gameViewPortSize.x) * Settings.MONITOR_WIDTH;

        return currentX;
    }

    /**
     * @return The y position of the mouse cursor in the screen (game view port or window).
     */
    public static float getScreenY() {
        float currentY = getY() - gameViewPortPos.y;
        currentY = Settings.MONITOR_HEIGHT - ((currentY / (float) gameViewPortSize.y) * Settings.MONITOR_HEIGHT);

        return currentY;
    }


    /**
     * @return The x position of the mouse cursor in the orthographic view (world coordinates).
     */
    public static float getOrthoX() {
        float currentX = getX() - gameViewPortPos.x;
        currentX = (currentX / (float) gameViewPortSize.x) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);

        Camera camera = Window.getScene().camera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseView().mul(camera.getInverseProjection(), viewProjection);
        tmp.mul(viewProjection);
        currentX = tmp.x;

        return currentX;
    }

    /**
     * @return The y position of the mouse cursor in the orthographic view (world coordinates).
     */
    public static float getOrthoY() {
        float currentY = getY() - gameViewPortPos.y;
        currentY = -((currentY / (float) gameViewPortSize.y) * 2.0f - 1.0f);
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);

        Camera camera = Window.getScene().camera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseView().mul(camera.getInverseProjection(), viewProjection);
        tmp.mul(viewProjection);
        currentY = tmp.y;

        return currentY;
    }


    /**
     * Sets the position of the game view port. This is used to convert the mouse position to the orthographic view.
     *
     * @param pos The position of the game view port.
     */
    public static void setGameViewPortPos(Vector2f pos) {
        gameViewPortPos.set(pos);
    }

    /**
     * Sets the size of the game view port. This is used to convert the mouse position to the orthographic view.
     *
     * @param size The size of the game view port.
     */
    public static void setGameViewPortSize(Vector2f size) {
        gameViewPortSize.set(size);
    }
}
