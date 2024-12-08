package engine.ui;

import engine.graphics.Window;
import engine.listeners.MouseListener;
import org.lwjgl.glfw.GLFW;

/**
 * Handles mouse events for a specific {@link UIElement}, such as hover, click, and enter/leave events.
 */
public class EventHandler {

    /** The UI element this event handler is associated with. */
    private final UIElement uiElement;

    /** Tracks whether the mouse was on this UI element during the last update. */
    private boolean wasMouseOnThis;

    /** The consumer responsible for handling mouse events for the UI element. */
    private MouseEventConsumer consumer;

    public EventHandler(UIElement uiElement) {
        this.uiElement = uiElement;
        this.consumer = uiElement.getConsumer();
    }

    /**
     * Updates the event handler state and processes mouse interactions with the associated UI element.
     *
     * <p>Handles the following mouse events:
     * <ul>
     *     <li>{@code onEnter()} - triggered when the mouse enters the UI element.</li>
     *     <li>{@code onLeave()} - triggered when the mouse leaves the UI element.</li>
     *     <li>{@code onHover()} - triggered when the mouse hovers over the UI element.</li>
     *     <li>{@code onClick()} - triggered when the UI element is clicked.</li>
     * </ul>
     */
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

    /**
     * Checks if the mouse is currently positioned over the UI element.
     *
     * @return true if the mouse is on the element, false otherwise
     */
    private boolean isMouseOnThis() {
        return Window.getPickingTexture().readPixel((int) MouseListener.getScreenX(), (int) MouseListener.getScreenY()) == uiElement.gameObject.getUid();
    }


    /**
     * Enum representing the types of events that can be triggered on a UI element.
     */
    public enum Event {
        /** Event triggered when the mouse enters the UI element. */
        MOUSE_ENTER,

        /** Event triggered when the mouse leaves the UI element. */
        MOUSE_LEAVE,

        /** Event triggered when the mouse hovers over the UI element. */
        MOUSE_HOVER,

        /** Event triggered when the UI element is clicked. */
        MOUSE_CLICK
    }
}
