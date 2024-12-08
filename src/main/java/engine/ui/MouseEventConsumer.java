package engine.ui;

import engine.ecs.Component;
import engine.ecs.components.SpriteRenderer;
import engine.util.Engine;
import org.joml.Vector4f;

/**
 * Abstract class for handling mouse events on a UI element.
 * This class provides default behavior for handling hover, click, enter, and leave events,
 * along with a click delay mechanism to control interaction timing.
 */
public abstract class MouseEventConsumer extends Component {

    /** The current color of the UI element. */
    protected Vector4f color = new Vector4f(1, 1, 1, 1);

    /** The default color of the UI element. */
    public Vector4f defaultColor = color;

    /** The color of the UI element when it is hovered over. */
    public Vector4f hoverColor = new Vector4f(0.5f, 0.5f, 0.5f, 1);

    /** The required delay between consecutive clicks in seconds. */
    protected float clickDelay;

    /** The current timer for tracking the click delay. */
    protected float clickDelayTimer;


    public abstract void onClick();

    public abstract void onHover();

    public abstract void onEnter();

    public abstract void onLeave();

    /**
     * Updates the click delay timer based on the elapsed time since the last frame.
     * <p>
     * If the object cannot currently be clicked, this method increments the timer
     * and marks the associated {@link SpriteRenderer} as dirty (to update the visual cooldown state).
     */
    protected void updateClickDelayTimer() {
        if (!canClick()) {
            clickDelayTimer += Engine.deltaTime();
            gameObject.getComponent(SpriteRenderer.class).setDirty();
        }
    }

    /**
     * Reset the click delay timer to 0.0f
     */
    protected void resetClickDelayTimer() {
        clickDelayTimer = 0.0f;
    }

    /**
     * Checks whether the object can currently be clicked.
     *
     * @return true if the click delay has elapsed, false otherwise
     */
    protected boolean canClick() {
        return clickDelayTimer >= clickDelay;
    }

    public float clickDelay() {
        return clickDelay;
    }

    public float clickDelayTimer() {
        return clickDelayTimer;
    }

    /**
     * Sets the click delay and initializes the timer to the specified value.
     * <p>
     * This method must be called in the constructor of subclasses to avoid
     * unintended behavior where the click delay defaults to zero.
     *
     * @param delay the delay in seconds to set for click interactions
     */
    protected void setClickDelay(float delay) {
        clickDelay = delay;
        clickDelayTimer = delay;
    }
}
