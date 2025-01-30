package TDA.scene.prefabs;

import TDA.entities.dinos.Dino;
import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.entities.ecs.prefabs.ResourceNodePrefabs;
import TDA.entities.ecs.prefabs.StoragePrefab;
import TDA.entities.inventory.InventoryManager;
import TDA.entities.inventory.ItemStack;
import TDA.entities.resources.drops.StoneDrop;
import TDA.entities.resources.drops.TreeDrop;
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

        createEntities(scene);
        addSystems(scene);

        Logger.success("Home Scene initialized");
        return scene;
    }

    private static void createEntities(Scene scene) {
        scene.addEntity(PlayerPrefab.createPlayer(1000, 500, scene.camera));
        PlayerPrefab.getInventory().addItem(new ItemStack(new StoneDrop(), 30));
        PlayerPrefab.getInventory().addItem(new ItemStack(new TreeDrop(), 10));

        scene.addEntity(ResourceNodePrefabs.createTree(1300, 300));
        scene.addEntity(ResourceNodePrefabs.createStone(1000, 300));
        scene.addEntity(ResourceNodePrefabs.createMetal(1600, 300));
        scene.addEntity(Dino.createEntity());

        scene.addEntity(StoragePrefab.createBarrel(1000, 100));
    }

    private static void addSystems(Scene scene) {
        scene.addSystem(new InventoryManager());
    }
}
