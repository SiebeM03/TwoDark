package woareXengine.io.userInputs;

import woareXengine.mainEngine.Engine;

public class ShortClickChecker {
    private static final float MOVEMENT_FACTOR = 3.5f;
    private static final float THRESHOLD = 0.3f;

    private final Mouse mouse;
    private final MouseButton button;

    private float clickTime = 0;
    private float clickMovement = 0;

    ShortClickChecker(Mouse mouse, MouseButton button) {
        this.mouse = mouse;
        this.button = button;
    }

    void update() {
        if (mouse.isButtonDown(button)) {
            clickTime += Engine.getDelta();
            clickMovement += calculateMovement();
        } else {
            clickTime = 0;
            clickMovement = 0;
        }
    }

    boolean isLongHold() {
        return mouse.isButtonDown(button) && !isShortClick();
    }

    boolean isShortClickEvent() {
        return mouse.isReleaseEvent(button) && isShortClick();
    }

    private boolean isShortClick() {
        return clickTime + clickMovement * MOVEMENT_FACTOR < THRESHOLD;
    }

    private float calculateMovement() {
        float aspect = Engine.window().getAspectRatio();
        float xFactor = aspect > 1 ? aspect : 1;
        float yFactor = aspect > 1 ? 1 : 1 / aspect;
        float dx = mouse.getDx() * xFactor;
        float dy = mouse.getDy() * yFactor;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
