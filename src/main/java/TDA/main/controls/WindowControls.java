package TDA.main.controls;

import woareXengine.io.userInputs.InputController;
import woareXengine.util.Logger;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F5;

public class WindowControls extends InputController {
    protected WindowControls() {
        super();
    }

    public boolean isEscapeKeyPressed() {
        return isKeyboardEnabled && keyboard.keyPressEvent(GLFW_KEY_ESCAPE);
    }

    public boolean isDisplayModeSwitchPressed() {
        return isKeyboardEnabled && keyboard.keyPressEvent(GLFW_KEY_F5);
    }
}
