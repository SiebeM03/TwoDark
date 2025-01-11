package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.resources.types.Metal;
import TDA.entities.resources.types.Stone;
import woareXengine.util.Logger;

import java.util.Arrays;

public class Inventory extends Component {
    private final InventoryItem[] inventoryItems;

    public Inventory(int size) {
        this.inventoryItems = new InventoryItem[size];
        inventoryItems[3] = new InventoryItem(new Stone(), 10);
        inventoryItems[10] = new InventoryItem(new Metal(), 100);
    }


    public void addItem(InventoryItem item) {
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

    public static class InventoryItem {
        public Object item;
        public int amount;

        public InventoryItem(Object item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "{" +
                           "item=" + item.getClass().getSimpleName() +
                           ", amount=" + amount +
                           '}';
        }
    }
}
