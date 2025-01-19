package TDA.ui.menus.inventory.itemList;

import TDA.entities.ecs.components.Inventory;
import TDA.entities.inventory.ItemStack;
import woareXengine.ui.components.UiComponent;

public class InventoryItemList extends UiComponent {
    private final ItemStack[] inventoryItems;

    public InventoryItemList(Inventory inventory) {
        this.inventoryItems = inventory.getInventoryItems();
    }

    @Override
    protected void init() {
        setTransform(0);
        setPadding(16);
        transform.setWidth(334);

        for (int i = 0; i < inventoryItems.length; i++) {
            add(new InventorySlot(i, 5));
        }
    }

    @Override
    protected void updateSelf() {

    }

    public ItemStack getItemStack(int index) {
        return inventoryItems[index];
    }
}
