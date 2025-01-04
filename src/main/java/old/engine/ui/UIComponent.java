package old.engine.ui;

import old.engine.ecs.Transform;
import org.joml.Vector2f;

import java.util.List;

public interface UIComponent {
    void update();

    void setTransform(Transform transform);

    Transform getTransform();

    Vector2f getAbsolutePosition();

    void setParent(UIComponent parent);

    void addChild(UIComponent child);

    List<UIComponent> getChildren();

    void removeChild(UIComponent child);

    void setNoInteraction();

    void setEventConsumer(EventConsumer eventConsumer);
}
