package TDA.scene.prefabs;

import TDA.entities.player.Player;
import TDA.entities.resources.ResourceFactory;
import TDA.entities.resources.types.Metal;
import TDA.entities.resources.types.Stone;
import TDA.entities.resources.types.Tree;
import TDA.rendering.SceneRenderSystem;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.scene.Scene;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.util.Logger;

public class HomeScene {

    public static Scene init() {
        Camera camera = new HomeCamera();
        Logger.info("Initializing Home Scene...");
        SceneRenderSystem renderSystem = TDARenderSystem.get();
        Scene scene = new Scene(renderSystem, camera);

        scene.addEntity(Player.createEntity(camera));

        scene.addEntity(ResourceFactory.createResourceOfType(Tree.class));
        scene.addEntity(ResourceFactory.createResourceOfType(Stone.class));
        scene.addEntity(ResourceFactory.createResourceOfType(Metal.class));

        Logger.success("Home Scene initialized");
        return scene;
    }
}
