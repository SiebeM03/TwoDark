package old.engine.ecs.components;

import old.engine.ecs.Component;
import old.engine.ecs.GameObject;
import old.engine.graphics.Window;
import old.engine.listeners.MouseListener;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

/**
 * A class for dragging and dropping {@link GameObject}s from an ImGui window into the scene.
 * <p>
 * This component allows the user to pick up an object, move it based on mouse position, and place it in the scene.
 */
public class MouseControls extends Component {
    GameObject holdingObject = null;

    /**
     * Picks up the specified {@link GameObject} and adds it to the current scene.
     *
     * @param go the {@link GameObject} to pick up
     */
    public void pickupObject(GameObject go) {
        this.holdingObject = go;
        Window.getScene().addGameObjectToScene(go);
    }

    /**
     * Places the currently held {@link GameObject} at its current position and releases it.
     */
    public void place() {
        this.holdingObject = null;
    }

    /**
     * Updates the position of the held {@link GameObject} to follow the mouse cursor.
     */
    @Override
    public void update() {
        if (holdingObject != null) {
            holdingObject.transform.position.x = MouseListener.getOrthoX() - (holdingObject.transform.scale.x / 2);
            holdingObject.transform.position.y = MouseListener.getOrthoY() - (holdingObject.transform.scale.y / 2);

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
