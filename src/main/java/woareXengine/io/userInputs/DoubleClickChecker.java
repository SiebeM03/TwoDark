package woareXengine.io.userInputs;

import woareXengine.mainEngine.Engine;

public class DoubleClickChecker {

    private static final float TIME_THRESHOLD = 0.3f;

    private final Mouse mouse;

    private float timeSinceLastClick = 0;
    private boolean hasMovedSinceLastClick = true;

    DoubleClickChecker(Mouse mouse) {
        this.mouse = mouse;
    }

    void update() {
        if (mouse.isClickEvent(MouseButton.LEFT)) {
            timeSinceLastClick = 0;
            hasMovedSinceLastClick = false;
        } else {
            timeSinceLastClick += Engine.getDelta();
            hasMovedSinceLastClick |= (mouse.getDx() != 0 || mouse.getDy() != 0);
        }
    }

    boolean isDoubleClickEvent() {
        return mouse.isClickEvent(MouseButton.LEFT) && timeSinceLastClick < TIME_THRESHOLD && !hasMovedSinceLastClick;
    }
}
