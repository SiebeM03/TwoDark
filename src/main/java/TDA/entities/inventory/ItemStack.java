package TDA.entities.inventory;

import TDA.entities.resources.DropResource;

public class ItemStack {
    public IResource item;
    public int amount;

    public ItemStack(DropResource item, int amount) {
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