package TDA.entities.inventory;

public class ItemStack {
    public CollectableItem item;
    public int amount;

    public ItemStack(CollectableItem item, int amount) {
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