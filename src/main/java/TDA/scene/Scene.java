package TDA.scene;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.ecs.EntityManager;
import TDA.entities.ecs.components.Collider;
import TDA.rendering.SceneRenderSystem;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.util.Id;

import java.util.*;

public class Scene {

    public final Camera camera;

    public final SceneRenderSystem renderer;
    public final EntityManager entityManager;


    private Map<Id, SceneSystem> sceneSystems = new LinkedHashMap<>();


    public Scene(SceneRenderSystem renderSystem, Camera camera) {
        this.renderer = renderSystem;
        this.camera = camera;

        this.entityManager = new EntityManager();
    }

    public void update() {
        for (SceneSystem system : sceneSystems.values()) {
            system.update();
        }
        entityManager.update();
    }

    public void addEntity(Entity entity) {
        entityManager.entities.add(entity);
    }

    public void addSystem(SceneSystem system) {
        sceneSystems.put(system.getId(), system);
    }

    public SceneSystem getSystem(Id id) {
        return sceneSystems.get(id);
    }

    public void render() {
        // TODO add pre-render updates for SceneSystem if needed
        renderer.render(this);
    }

    public void cleanUp() {
        for (SceneSystem system : sceneSystems.values()) {
            system.cleanUp();
        }
        renderer.cleanUp();
    }

    @SafeVarargs
    public final List<Entity> getEntitiesWithComponents(Class<? extends Component>... component) {
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : entityManager.entities) {
            boolean hasAllComponents = true;
            for (Class<? extends Component> c : component) {
                if (entity.getComponent(c) == null) {
                    hasAllComponents = false;
                    break;
                }
            }
            if (hasAllComponents) {
                entities.add(entity);
            }
        }
        return entities;
    }
}
