package woareXengine.io.userInputs;

import static org.lwjgl.glfw.GLFW.*;

public enum MouseButton {

    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT);

    private final int glfwId;

    private MouseButton(int id) {
        this.glfwId = id;
    }

    protected int getId() {
        return glfwId;
    }

}