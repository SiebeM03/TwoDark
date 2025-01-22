package TDA.entities.inventory;


public class ItemStack {
    public IResource item;
    public int amount;

    public ItemStack(IResource item, int amount) {
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