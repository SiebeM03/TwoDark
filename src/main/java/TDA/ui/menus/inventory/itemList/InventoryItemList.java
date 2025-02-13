package TDA.ui.menus.inventory.itemList;

import TDA.entities.ecs.components.Inventory;
import TDA.entities.inventory.ItemStack;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;

import static TDA.ui.menus.inventory.itemList.InventorySlot.*;

public class InventoryItemList extends UiComponent {
    public static final int COLS = 5;

    private final ItemStack[] inventoryItems;
    private boolean isPlayerInventory = false;

    public InventoryItemList(Inventory inventory) {
        this.inventoryItems = inventory.inventoryItems;
    }

    @Override
    protected void init() {
        for (int i = 0; i < inventoryItems.length; i++) {
            int column = i % COLS;
            int row = i / COLS;

            add(new InventorySlot(i), new UiConstraints(
                    new PixelConstraint(column * (SLOT_WIDTH + SLOT_SPACING)),
                    new PixelConstraint(row * (SLOT_HEIGHT + SLOT_SPACING) + SLOT_HEIGHT, true),
                    new PixelConstraint(SLOT_WIDTH),
                    new PixelConstraint(SLOT_HEIGHT)
            ));
        }
    }

    @Override
    protected void updateSelf() {

    }

    public ItemStack getItemStack(int index) {
        return inventoryItems[index];
    }

    public void isPlayerInventory(boolean b) {
        isPlayerInventory = b;
    }

    public boolean isPlayerInventory() {
        return isPlayerInventory;
    }
}
