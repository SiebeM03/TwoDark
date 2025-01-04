package game.scene;

import game.rendering.SceneRenderSystem;
import woareXengine.mainEngine.gameObjects.Camera;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    public final Camera camera;

    public final SceneRenderSystem renderer;
    // TODO add entity manager

    private final Map<Integer, SceneSystem> sceneSystems = new HashMap<>();

    public Scene(SceneRenderSystem renderSystem, Camera camera) {
        this.renderer = renderSystem;
        this.camera = camera;
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
