package woareXengine.io.userInputs;

import woareXengine.mainEngine.Engine;

/**
 * A wrapper for {@link Mouse} and {@link Keyboard} that can enable both inputs independently
 */
public class InputController {
    protected final Mouse mouse;
    protected boolean isMouseEnabled = true;

    protected final Keyboard keyboard;
    protected boolean isKeyboardEnabled = true;

    public InputController() {
        this.mouse = Engine.mouse();
        this.keyboard = Engine.keyboard();

        if (mouse == null || keyboard == null) {
            System.err.println("InputController: Mouse or Keyboard is null");
        }
    }

    public void enableMouseUse(boolean enable) {
        isMouseEnabled = enable;
    }

    public void enableKeyboardUse(boolean enable) {
        isKeyboardEnabled = enable;
    }


}
