package TDA.entities.resources;

import TDA.entities.ecs.Component;
import TDA.entities.inventory.Inventory;
import TDA.entities.inventory.InventoryItem;
import org.joml.Vector2f;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Color;

public abstract class Resource extends Component {

    public void harvest() {
        Inventory.get().addItem(new InventoryItem(this, 10));
    }

    @Override
    public void update() {
        DebugDraw.addBox2D(entity.transform.getCenter(), new Vector2f(3, 3), 0, Color.RED);
    }
}
