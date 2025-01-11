package TDA.entities.inventory;

public class InventoryItem {
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
