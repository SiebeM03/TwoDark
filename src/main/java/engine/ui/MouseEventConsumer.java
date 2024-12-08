package engine.ui;

import engine.ecs.Component;
import engine.ecs.components.SpriteRenderer;
import engine.util.Engine;
import org.joml.Vector4f;

public abstract class MouseEventConsumer extends Component {
    protected Vector4f color = new Vector4f(1, 1, 1, 1);
    public Vector4f defaultColor = color;
    public Vector4f hoverColor = new Vector4f(0.5f, 0.5f, 0.5f, 1);
    protected float clickDelay;
    protected float clickDelayTimer;

    public abstract void onClick();

    public abstract void onHover();

    public abstract void onEnter();

    public abstract void onLeave();

    /**
     * Add delta time to the click delay timer
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
     * Check if the object can be clicked
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
     * Set the click delay timer to a specific value, also sets the click delay to the same value to prevent a cooldown from being triggered
     *
     * @param delay the value to set the click delay timer to
     * @implNote This method needs to be called in the constructor of the class that extends this class, otherwise the click delay will be set to 0.0f
     */
    protected void setClickDelay(float delay) {
        clickDelay = delay;
        clickDelayTimer = delay;
    }
}
