package TDA.entities.ecs.components;


import TDA.entities.ecs.Component;
import TDA.entities.inventory.ItemStack;
import woareXengine.util.Logger;

import java.util.Arrays;

public class Inventory extends Component {
    private final ItemStack[] inventoryItems;

    public Inventory(int size) {
        this.inventoryItems = new ItemStack[size];
    }


    public void addItem(ItemStack item) {
        for (int i = 0; i < inventoryItems.length; i++) {
            if (inventoryItems[i] != null && inventoryItems[i].item.getClass().equals(item.item.getClass())) {
                inventoryItems[i].amount += item.amount;
                Logger.debug("Inventory: " + Arrays.toString(inventoryItems));
                return;
            }
        }
        for (int i = 0; i < inventoryItems.length; i++) {
            if (inventoryItems[i] == null) {
                inventoryItems[i] = item;
                Logger.debug("Inventory: " + Arrays.toString(inventoryItems));
                return;
            }
        }
    }

    public ItemStack[] getInventoryItems() {
        return inventoryItems;
    }


}
