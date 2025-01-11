package old.engine.ui;

import old.engine.ecs.Transform;
import org.joml.Vector2f;
import woareXengine.util.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseComponent implements UIComponent {
    public String name;
    public Transform transform;
    protected UIComponent parent = null;
    protected List<UIComponent> children = new ArrayList<>();

    public EventConsumer eventConsumer = null;

    @Override
    public void update() {
        for (UIComponent child : children) {
            child.update();
        }
        if (eventConsumer != null) {
            eventConsumer.update();
        }
    }

    @Override
    public void setEventConsumer(EventConsumer eventConsumer) {
        Logger.info("Setting event consumer for " + this.name + " to " + eventConsumer);
        this.eventConsumer = eventConsumer;
    }

    @Override
    public void setParent(UIComponent parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(UIComponent child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public void removeChild(UIComponent child) {
        children.remove(child);
        child.setParent(null);
    }

    public List<UIComponent> getChildren() {
        return children;
    }

    @Override
    public Vector2f getAbsolutePosition() {
        if (this.parent == null) return new Vector2f(transform.getX(), transform.getY());
        return new Vector2f(transform.getX(), transform.getY()).add(parent.getAbsolutePosition());
    }

    @Override
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public void setNoInteraction() {
    }
}
