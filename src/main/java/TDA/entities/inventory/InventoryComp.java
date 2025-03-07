package TDA.entities.inventory;


import TDA.entities.main.Component;
import TDA.entities.inventory.items.ItemStack;
import TDA.ui.TDAUi;
import woareXengine.util.Logger;

public class InventoryComp extends Component {
    public final ItemStack[] inventoryItems;

    public InventoryComp(int size) {
        this.inventoryItems = new ItemStack[size];
    }

    @Override
    public void update() {
        for (int i = 0; i < inventoryItems.length; i++) {
            ItemStack itemStack = inventoryItems[i];
            if (itemStack == null) continue;

            if (itemStack.amount == 0) {
                inventoryItems[i] = null;
            }
        }
    }

    public void addItem(ItemStack itemStack) {
        System.out.println("Added");
        boolean added = false;

        if (itemStack.item.isStackable()) {
            added = addToExistingStack(itemStack);
        }

        if (!added) {
            added = addToNewStack(itemStack);
        }

        if (!added) {
            Logger.error("Inventory is full, could not add item: " + itemStack.item.getClass().getSimpleName());
            return;
        }

        TDAUi.get().gameUi.mainGame.itemAddedToInventory(itemStack);
    }

    private boolean addToExistingStack(ItemStack itemStack) {
        for (int i = 0; i < inventoryItems.length; i++) {
            if (inventoryItems[i] != null && inventoryItems[i].item.getClass().equals(itemStack.item.getClass())) {
                inventoryItems[i].amount += itemStack.amount;
                return true;
            }
        }
        return false;
    }

    private boolean addToNewStack(ItemStack itemStack) {
        for (int i = 0; i < inventoryItems.length; i++) {
            if (inventoryItems[i] == null) {
                inventoryItems[i] = itemStack;
                return true;
            }
        }
        return false;
    }
}
