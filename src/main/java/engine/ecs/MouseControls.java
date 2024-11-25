package engine.ecs;

import engine.graphics.Window;
import engine.listeners.MouseListener;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

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
            //  TODO: Center object on mouse
            holdingObject.transform.position.x = MouseListener.getOrthoX() - (holdingObject.transform.scale.x / 2);
            holdingObject.transform.position.y = MouseListener.getOrthoY() - (holdingObject.transform.scale.y / 2);

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
