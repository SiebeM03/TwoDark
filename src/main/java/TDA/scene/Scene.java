package TDA.scene;

import TDA.entities.main.Component;
import TDA.entities.main.Entity;
import TDA.entities.main.EntityManager;
import TDA.entities.components.interactions.ClickableComp;
import TDA.rendering.SceneRenderSystem;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.util.Id;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public Entity getClickableEntityAtMouse() {
        int pixelId = TDARenderSystem.get().renderer.getPickingRenderer().readPixel(
                Engine.mouse().getScreenX(),
                Engine.mouse().getScreenY()
        );
        return getEntitiesWithComponents(ClickableComp.class)
                       .stream()
                       .filter(e -> e.getId() == pixelId)
                       .findFirst()
                       .orElse(null);
    }
}
