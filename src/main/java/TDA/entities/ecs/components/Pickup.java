package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.inventory.ItemStack;
import TDA.entities.resources.Resource;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Logger;
import woareXengine.util.MathUtils;

public class Pickup extends Component {
    public final static int PULL_RANGE = 100;
    public final static int PULL_SPEED = 1000;
    public final static int PICKUP_RANGE = 20;

    private final Resource item;
    private final int amount;

    public Pickup(Resource item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), PULL_RANGE);

        for (Entity inventoryEntity : GameManager.currentScene.getEntitiesWithComponents(Inventory.class)) {
            if (MathUtils.dist(inventoryEntity.transform.getCenter(), entity.transform.getCenter()) <= PULL_RANGE) {
                Vector2f delta = inventoryEntity.transform.getCenter().sub(entity.transform.getCenter(), new Vector2f());
                Vector2f normalizedDelta = delta.div(delta.absolute(new Vector2f()));

                entity.transform.addX(normalizedDelta.x * PULL_SPEED * Engine.getDelta());
                entity.transform.addY(normalizedDelta.y * PULL_SPEED * Engine.getDelta());

                if (MathUtils.dist(inventoryEntity.transform.getCenter(), entity.transform.getCenter()) <= PICKUP_RANGE) {
                    Logger.debug("Picked up item");
                    inventoryEntity.getComponent(Inventory.class).addItem(new ItemStack(item, amount));
                    entity.destroy();
                }
            }
        }
    }
}
