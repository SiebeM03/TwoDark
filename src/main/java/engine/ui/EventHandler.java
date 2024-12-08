package engine.ui;

import engine.graphics.Window;
import engine.listeners.MouseListener;
import org.lwjgl.glfw.GLFW;

public class EventHandler {

    private final UIElement uiElement;
    private boolean wasMouseOnThis;
    private MouseEventConsumer consumer;

    public EventHandler(UIElement uiElement) {
        this.uiElement = uiElement;
        this.consumer = uiElement.getConsumer();
    }

    public void update() {
        MouseEventConsumer elementConsumer = uiElement.getConsumer();
        if (elementConsumer == null) return;    // No consumer, no events
        if (this.consumer == null) {    // If the consumer is null and the element's consumer isn't, set it to the element's consumer
            this.consumer = elementConsumer;
        }

        boolean isMouseOnThis = isMouseOnThis();

        if (isMouseOnThis && !wasMouseOnThis) {
            // If the mouse is on this element and wasn't before, enter
            uiElement.getConsumer().onEnter();
        } else if (!isMouseOnThis && wasMouseOnThis) {
            // If the mouse isn't on this element and was before, leave
            uiElement.getConsumer().onLeave();
        }
        if (isMouseOnThis) {
            if (MouseListener.mouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
                uiElement.getConsumer().onClick();
            } else {
                uiElement.getConsumer().onHover();
            }
        }
        wasMouseOnThis = isMouseOnThis();
    }

    private boolean isMouseOnThis() {
        return Window.getPickingTexture().readPixel((int) MouseListener.getScreenX(), (int) MouseListener.getScreenY()) == uiElement.gameObject.getUid();
    }


    public enum Event {
        MOUSE_ENTER,
        MOUSE_LEAVE,
        MOUSE_HOVER,
        MOUSE_CLICK
    }
}
