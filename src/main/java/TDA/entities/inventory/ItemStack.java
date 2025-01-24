package TDA.entities.inventory;

/**
 * This class represents a stack of an item in the inventory. It contains an item and the amount of that item. If the item is not stackable, the amount will always be 1.
 */
public class ItemStack {
    public InventoryObject item;
    public int amount;

    public ItemStack(InventoryObject item, int amount) {
        this.item = item;
        if (item.isStackable()) {
            this.amount = amount;
        } else {
            this.amount = 1;
        }
    }

    @Override
    public String toString() {
        return "{" +
                       "item=" + item.getClass().getSimpleName() +
                       ", amount=" + amount +
                       '}';
    }
}