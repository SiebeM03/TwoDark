package engine.ui;


import engine.ecs.GameObject;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Window;
import engine.listeners.MouseListener;
import engine.util.Color;
import engine.util.Engine;
import engine.util.JMath;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public abstract class EventConsumer {
    protected abstract void onClick();

    protected abstract void onHover();

    protected abstract void onEnter();

    protected abstract void onLeave();

    private boolean wasMouseOnThis;

    public GameObject gameObject;
    public UIComponent uiComponent;


    /** The current color of the UI element. */
    protected Color color = Color.WHITE;

    /** The default color of the UI element. */
    public Color defaultColor = color;

    /** The color of the UI element when it is hovered over. */
    public Color hoverColor = new Color(0.8f, 0.8f, 0.8f, 1);

    /** The required delay between consecutive clicks in seconds. */
    protected float clickDelay;

    /** The current timer for tracking the click delay. */
    protected float clickDelayTimer;

    private boolean hasCooldownAnimation = false;

    public void update() {
        boolean isMouseOnThis = isMouseOnThis();

        if (isMouseOnThis && !wasMouseOnThis) {
            // If the mouse is on this element and wasn't before, enter
            onEnter();
        } else if (!isMouseOnThis && wasMouseOnThis) {
            // If the mouse isn't on this element and was before, leave
            onLeave();
        }
        if (isMouseOnThis) {
            if (MouseListener.mouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
                onClick();
            } else {
                onHover();
            }
        }
        updateClickDelayTimer();
        wasMouseOnThis = isMouseOnThis();
    }


    /**
     * Updates the click delay timer based on the elapsed time since the last frame.
     * <p>
     * If the object cannot currently be clicked, this method increments the timer
     * and marks the associated {@link SpriteRenderer} as dirty (to update the visual cooldown state).
     */
    private void updateClickDelayTimer() {
        if (!canClick()) {
            clickDelayTimer += Engine.deltaTime();
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

    public void setHasCooldownAnimation() {
        this.hasCooldownAnimation = true;
    }

    public boolean hasCooldownAnimation() {
        return hasCooldownAnimation;
    }

    /**
     * Checks if the mouse is currently positioned over the UI element.
     *
     * @return true if the mouse is on the element, false otherwise
     */
    private boolean isMouseOnThis() {
        if (gameObject != null) {
            int hoveredUid = Window.readPixel((int) MouseListener.getScreenX(), (int) MouseListener.getScreenY());
            return hoveredUid == gameObject.getUid();
        }
        if (uiComponent != null) {
            return JMath.inRect(new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY()), uiComponent.getAbsolutePosition().x(), uiComponent.getAbsolutePosition().y(), uiComponent.getTransform().scale.x, uiComponent.getTransform().scale.y);
        }
        return false;
    }
}
