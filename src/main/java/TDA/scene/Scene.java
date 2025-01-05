package TDA.scene;

import TDA.entities.player.Player;
import TDA.entities.resources.Resource;
import TDA.entities.resources.types.Metal;
import TDA.entities.resources.types.Stone;
import TDA.entities.resources.types.Tree;
import TDA.rendering.SceneRenderSystem;
import org.joml.Vector2f;
import woareXengine.mainEngine.gameObjects.Camera;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    public final Camera camera;
    public final Player player;

    public final SceneRenderSystem renderer;
    // TODO add entity manager

    private final Map<Integer, SceneSystem> sceneSystems = new HashMap<>();


    public Scene(SceneRenderSystem renderSystem, Camera camera) {
        this.renderer = renderSystem;
        this.camera = camera;

        this.player = new Player(camera);
        renderer.registerPlayer(player);

        Resource stone = new Stone(new Vector2f(1000, 300));
        renderer.registerResource(stone);

        Resource tree = new Tree(new Vector2f(1300, 300));
        renderer.registerResource(tree);

        Resource metal = new Metal(new Vector2f(1600, 300));
        renderer.registerResource(metal);
    }

    public void update() {
        player.update();
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
