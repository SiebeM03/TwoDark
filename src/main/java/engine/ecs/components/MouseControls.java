package engine.ecs.components;

import engine.ecs.Component;
import engine.ecs.GameObject;
import engine.graphics.Window;
import engine.listeners.MouseListener;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

/**
 * A class for dragging and dropping GameObjects from an ImGui window into the scene.
 */
public class MouseControls extends Component {
    GameObject holdingObject = null;

    public void pickupObject(GameObject go) {
        this.holdingObject = go;
        Window.getScene().addGameObjectToScene(go);
    }

    public void place() {
        this.holdingObject = null;
    }

    @Override
    public void update(float dt) {
        if (holdingObject != null) {
            holdingObject.transform.position.x = MouseListener.getOrthoX() - (holdingObject.transform.scale.x / 2);
            holdingObject.transform.position.y = MouseListener.getOrthoY() - (holdingObject.transform.scale.y / 2);

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
