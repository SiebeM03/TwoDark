package TDA.entities.components.inventory.items;

/**
 * This class represents a stack of an item in the inventory. It contains an item and the amount of that item. If the item is not stackable, the amount will always be 1.
 */
public class ItemStack {
    public InventoryObject item;
    // TODO add getter and setter for amount to prevent negative amounts and amounts above the stack limit (for non-stackable items), maybe consider using a stack limit instead of a boolean for stackable
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
        return item.getClass().getSimpleName() + " x" + amount;
    }
}