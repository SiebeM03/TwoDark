package engine.ui;

import engine.ecs.Transform;
import org.joml.Vector2f;

public interface UIComponent {
    void update();

    void setTransform(Transform transform);

    Vector2f getAbsolutePosition();

    void setParent(UIComponent parent);

    void addChild(UIComponent child);

    void removeChild(UIComponent child);

    void setNoInteraction();
}
