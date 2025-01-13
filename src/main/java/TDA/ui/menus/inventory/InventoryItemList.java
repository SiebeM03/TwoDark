package TDA.ui.menus.inventory;

import TDA.entities.ecs.components.Inventory;
import TDA.entities.inventory.ItemStack;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiComponent;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class InventoryItemList extends UiComponent {
    private final ItemStack[] inventoryItems;

    protected InventoryItemList(Inventory inventory) {
        this.inventoryItems = inventory.getInventoryItems();
    }

    @Override
    protected void init() {
        setTransform(16);
        transform.setWidth(252);

        for (int i = 0; i < inventoryItems.length; i++) {
            add(new InventorySlot(inventoryItems, i, 5));
        }
    }

    @Override
    protected void updateSelf() {
        if (Engine.keyboard().isKeyDown(GLFW_KEY_LEFT)) {
            transform.setWidth(transform.getWidth() - 1);
        }
        if (Engine.keyboard().isKeyDown(GLFW_KEY_RIGHT)) {
            transform.setWidth(transform.getWidth() + 1);
        }
    }
}
