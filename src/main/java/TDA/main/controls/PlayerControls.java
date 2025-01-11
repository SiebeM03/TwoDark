package TDA.main.controls;

import woareXengine.io.userInputs.InputController;
import woareXengine.io.userInputs.MouseButton;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerControls extends InputController {
    protected PlayerControls() {
        super();
    }


    public boolean isJumpPressed() {
        return isKeyboardEnabled && keyboard.keyPressEvent(GLFW_KEY_SPACE);
    }

    public boolean isMoveLeftHeld() {
        return isKeyboardEnabled && keyboard.isKeyDown(GLFW_KEY_A);
    }

    public boolean isMoveRightHeld() {
        return isKeyboardEnabled && keyboard.isKeyDown(GLFW_KEY_D);
    }

    public boolean isMoveUpHeld() {
        return isKeyboardEnabled && keyboard.isKeyDown(GLFW_KEY_W);
    }

    public boolean isMoveDownHeld() {
        return isKeyboardEnabled && keyboard.isKeyDown(GLFW_KEY_S);
    }

    public boolean isSprintHeld() {
        return isKeyboardEnabled && keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT);
    }

    public boolean isHarvestPressed() {
        return isMouseEnabled && mouse.isClickEvent(MouseButton.LEFT);
    }
}
