package TDA.entities.ecs;

import TDA.entities.ecs.components.QuadComponent;
import TDA.main.GameManager;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Id;
import woareXengine.util.Transform;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private final Id id = new Id();
    private boolean updateWhenOutOfView = false;
    private boolean isDead = false;

    private List<Component> components = new ArrayList<>();

    public Transform transform;

    public Entity(Transform transform, Component... components) {
        this.transform = transform;
        for (Component component : components) {
            addComponent(component);
        }
    }

    public void update() {
        if (updateWhenOutOfView || GameManager.currentScene.camera.isOnScreen(transform)) {
            for (Component component : components) {
                component.update();
            }
        }
    }

    public Entity addComponent(Component component) {
        components.add(component);
        component.entity = this;
        component.init();
        return this;
    }

    public <T> void removeComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                components.remove(component);
            }
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                return componentClass.cast(component);
            }
        }
        return null;
    }

    public void destroy() {
        for (Component component : components) {
            component.destroy();
        }
        GameManager.currentScene.entityManager.entities.remove(this);
    }
}
