package woareXengine.io.userInputs;

import woareXengine.io.window.Window;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private final Window window;

    private final Set<Integer> buttonsDown = new HashSet<>();
    private final Set<Integer> buttonsClickedThisFrame = new HashSet<>();
    private final Set<Integer> buttonsReleasedThisFrame = new HashSet<>();

    private final ShortClickChecker[] shortClickCheckers;
    private final DoubleClickChecker doubleClickChecker;

    private float x, y;
    private float dx, dy;
    private float scroll;
    private float lastX, lastY;

    public Mouse(Window window) {
        this.window = window;
        this.shortClickCheckers = initShortClickCheckers(this);
        this.doubleClickChecker = new DoubleClickChecker(this);
        addMoveListener(window.getId());
        addClickListener(window.getId());
        addScrollListener(window.getId());
    }

    public void update() {
        for (ShortClickChecker checker : shortClickCheckers) {
            checker.update();
        }
        doubleClickChecker.update();
        buttonsClickedThisFrame.clear();
        buttonsReleasedThisFrame.clear();
        updateDeltas();
        this.scroll = 0;
    }

    private void updateDeltas() {
        this.dx = x - lastX;
        this.dy = y - lastY;
        this.lastX = x;
        this.lastY = y;
    }

    public boolean anyButtonClicked() {
        return !buttonsClickedThisFrame.isEmpty();
    }

    public boolean isButtonDown(MouseButton button) {
        return buttonsDown.contains(button.getId());
    }

    /**
     * @param button The button to check.
     * @return true if the button is held down for longer than a short click.
     */
    public boolean isButtonHeld(MouseButton button) {
        return shortClickCheckers[button.ordinal()].isLongHold();
    }

    public boolean isClickEvent(MouseButton button) {
        return buttonsClickedThisFrame.contains(button.getId());
    }

    public boolean isReleaseEvent(MouseButton button) {
        return buttonsReleasedThisFrame.contains(button.getId());
    }

    public boolean isShortClick(MouseButton button) {
        return shortClickCheckers[button.ordinal()].isShortClickEvent();
    }


    /** @return A number between 0-1 showing how far across the screen the cursor is (0 is left edge). */
    public float getX() {
        return x;
    }

    /** @return A number between 0-1 showing how far down the screen the cursor is (0 is bottom edge). */
    public float getY() {
        return 1 - y;
    }

    /** @return A whole number showing the x-coordinate of the mouse's current pixel location. */
    public int getScreenX() {
        return (int) (getX() * window.getPixelWidth());
    }

    /** @return A whole number showing the y-coordinate of the mouse's current pixel location. */
    public int getScreenY() {
        return (int) (getY() * window.getPixelHeight());
    }

    /** @return The change in x position since the last frame. Positive is right, using screen coordinates (0,0 top left, 1,1 bottom right). */
    public float getDx() {
        return dx;
    }

    /** @return The change in y position since the last frame. Positive is down, using screen coordinates (0,0 top left, 1,1 bottom right). */
    public float getDy() {
        return dy;
    }


    public float getScroll() {
        return scroll;
    }

    public boolean isDoubleClick() {
        return doubleClickChecker.isDoubleClickEvent();
    }


    private void addMoveListener(long windowId) {
        glfwSetCursorPosCallback(windowId, (currentWindow, xPos, yPos) -> {
            this.x = (float) (xPos / window.getScreenCoordWidth());
            this.y = (float) (yPos / window.getScreenCoordHeight());
        });
    }

    private void addClickListener(long windowId) {
        glfwSetMouseButtonCallback(windowId, (window, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                buttonsClickedThisFrame.add(button);
                buttonsDown.add(button);
            } else if (action == GLFW_RELEASE) {
                buttonsReleasedThisFrame.add(button);
                buttonsDown.remove(button);
            }
        });
    }

    private void addScrollListener(long windowId) {
        glfwSetScrollCallback(windowId, (window, scrollX, scrollY) -> {
            this.scroll = (float) scrollY;
        });
    }


    private static ShortClickChecker[] initShortClickCheckers(Mouse mouse) {
        ShortClickChecker[] checkers = new ShortClickChecker[MouseButton.values().length];
        for (MouseButton button : MouseButton.values()) {
            checkers[button.ordinal()] = new ShortClickChecker(mouse, button);
        }
        return checkers;
    }

}
