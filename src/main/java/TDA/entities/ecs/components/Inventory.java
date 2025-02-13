package TDA.entities.ecs.components;


import TDA.entities.ecs.Component;
import TDA.entities.inventory.ItemStack;
import woareXengine.util.Logger;

import java.util.Arrays;

public class Inventory extends Component {
    public final ItemStack[] inventoryItems;

    public Inventory(int size) {
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
        boolean added = false;

        if (itemStack.item.isStackable()) {
            added = addToExistingStack(itemStack);
        }

        if (!added) {
            added = addToNewStack(itemStack);
        }

        if (!added) {
            Logger.error("Inventory is full, could not add item: " + itemStack.item.getClass().getSimpleName());
        }
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
