package TDA.scene.prefabs.homeScene;

import TDA.entities.inventory.items.ItemStack;
import TDA.entities.player.PlayerPrefab;
import TDA.entities.resources.items.types.StoneItem;
import TDA.entities.resources.items.types.TreeItem;
import TDA.entities.resources.nodes.types.Metal;
import TDA.entities.resources.nodes.types.Stone;
import TDA.entities.resources.nodes.types.Tree;
import TDA.entities.storage.entities.BarrelStorage;
import TDA.main.world.World;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.scene.Scene;
import TDA.scene.systems.InventoryManager;
import woareXengine.util.Logger;

public class HomeScene extends Scene {

    public World world;

    public HomeScene() {
        super(new TDARenderSystem(), new HomeCamera());
        Logger.success("Home Scene initialized");
    }

    @Override
    protected void createEntities() {
        addEntity(PlayerPrefab.getPlayer());
        PlayerPrefab.getInventory().addItem(new ItemStack(new StoneItem(), 30));
        PlayerPrefab.getInventory().addItem(new ItemStack(new TreeItem(), 10));

        addEntity(Tree.create(1300, 300));
        addEntity(Stone.create(1000, 300));
        addEntity(Metal.create(1600, 300));

        addEntity(BarrelStorage.create(1000, 100));

        world = new World();
        world.init();

        Logger.success("Created entities and world for Home Scene");
    }

    @Override
    protected void addSystems() {
        addSystem(new InventoryManager());

        Logger.success("Created systems for Home Scene");
    }
}
