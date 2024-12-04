package testGame;

import engine.ecs.GameObject;
import engine.ecs.Transform;
import engine.ecs.components.SpriteRenderer;
import engine.listeners.MouseListener;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class ResourceTooltip {

    public static GameObject generateTooltip(Resource resource) {
        GameObject go = new GameObject(resource.getName() + "_Tooltip", new Transform(new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY()), new Vector2f(50, 50)), 999);
        go.addComponent(new SpriteRenderer().setColor(new Vector4f(1.0f, 1.0f, 0.2f, 1)));
        return go;
    }
}
