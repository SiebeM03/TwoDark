package TDA.entities.components.interactions;

import TDA.entities.main.Component;
import TDA.entities.main.Entity;
import TDA.entities.components.inventory.InventoryComp;
import TDA.entities.components.inventory.items.ItemStack;
import TDA.entities.components.resources.items.ItemDropComp;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Logger;
import woareXengine.util.MathUtils;

public class ItemPickupComp extends Component {
    public final static int PULL_RANGE = 100;
    public final static int PULL_SPEED = 1000;
    public final static int PICKUP_RANGE = 20;

    private final ItemDropComp item;
    private final int amount;

    public ItemPickupComp(ItemDropComp item, int amount) {
        this.item = item;
        this.amount = amount;
        Logger.debug("Pickup " + item + " with amount " + amount);
    }

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), PULL_RANGE);

        for (Entity inventoryEntity : GameManager.currentScene.getEntitiesWithComponents(InventoryComp.class)) {
            if (MathUtils.dist(inventoryEntity.transform.getCenter(), entity.transform.getCenter()) <= PULL_RANGE) {
                Vector2f delta = inventoryEntity.transform.getCenter().sub(entity.transform.getCenter(), new Vector2f());
                Vector2f normalizedDelta = delta.div(delta.absolute(new Vector2f()));

                entity.transform.addX(normalizedDelta.x * PULL_SPEED * Engine.getDelta());
                entity.transform.addY(normalizedDelta.y * PULL_SPEED * Engine.getDelta());

                if (MathUtils.dist(inventoryEntity.transform.getCenter(), entity.transform.getCenter()) <= PICKUP_RANGE) {
                    Logger.debug("Picked up item");
                    inventoryEntity.getComponent(InventoryComp.class).addItem(new ItemStack(item, amount));
                    entity.destroy();
                }
            }
        }
    }
}
