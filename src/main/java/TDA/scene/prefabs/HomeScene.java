package TDA.scene.prefabs;

import TDA.entities.dinos.Dino;
import TDA.entities.inventory.InventoryManager;
import TDA.entities.inventory.ItemStack;
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
        Player.inventory.addItem(new ItemStack(new Stone(), 100));
        Player.inventory.addItem(new ItemStack(new Tree(), 100));

        scene.addEntity(ResourceFactory.createResourceOfType(Tree.class));
        scene.addEntity(ResourceFactory.createResourceOfType(Stone.class));
        scene.addEntity(ResourceFactory.createResourceOfType(Metal.class));
        scene.addEntity(Dino.createEntity());


        scene.addSystem(new InventoryManager());

        Logger.success("Home Scene initialized");
        return scene;
    }
}
