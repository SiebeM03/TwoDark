package TDA.main.controls;

import woareXengine.io.userInputs.InputController;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

public class DeveloperControls extends InputController {
    protected DeveloperControls() {
        super();
    }

    public boolean isDevModeToggled() {
        return isKeyboardEnabled && keyboard.keyPressEvent(GLFW_KEY_P);
    }
}
