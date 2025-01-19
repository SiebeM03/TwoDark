package TDA.ui.menus.inventory.itemList;

import TDA.entities.inventory.ItemStack;
import woareXengine.ui.components.UiComponent;
import woareXengine.util.Color;

public class InventoryItemUi extends UiComponent {

    public InventoryItemUi() {
    }

    @Override
    protected void init() {
        setTransform(3);

        if (getItemStack() != null) {
            texture = getItemStack().item.getTexture();
            color = Color.WHITE;
        }
    }

    @Override
    protected void updateSelf() {

    }

    public ItemStack getItemStack() {
        if (parent instanceof InventorySlot) {
            return ((InventorySlot) parent).getItemStack();
        }
        return null;
    }
}
