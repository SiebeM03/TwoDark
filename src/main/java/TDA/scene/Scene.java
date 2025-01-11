package TDA.scene;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.EntityManager;
import TDA.rendering.SceneRenderSystem;
import woareXengine.mainEngine.gameObjects.Camera;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    public final Camera camera;

    public final SceneRenderSystem renderer;
    public final EntityManager entityManager;


    private final Map<Integer, SceneSystem> sceneSystems = new HashMap<>();


    public Scene(SceneRenderSystem renderSystem, Camera camera) {
        this.renderer = renderSystem;
        this.camera = camera;

        this.entityManager = new EntityManager();
    }

    public void update() {
        entityManager.update();
    }

    public void addEntity(Entity entity) {
        entityManager.entities.add(entity);
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
}
