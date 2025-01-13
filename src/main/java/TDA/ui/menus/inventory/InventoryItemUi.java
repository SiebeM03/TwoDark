package TDA.ui.menus.inventory;

import TDA.entities.inventory.ItemStack;
import woareXengine.ui.components.UiComponent;
import woareXengine.util.Color;

public class InventoryItemUi extends UiComponent {
    private ItemStack[] itemStacks;
    private int index;

    private boolean isHoverState = false;

    public InventoryItemUi(ItemStack[] itemStacks, int index) {
        this.itemStacks = itemStacks;
        this.index = index;
    }

    @Override
    protected void init() {
        setTransform(3);
    }

    @Override
    protected void updateSelf() {
        if (getItemStack() == null) return;
        if (texture == null) {
            color = new Color(Color.WHITE);
            texture = getItemStack().item.getTexture();
        }

        if (isMouseOver() && !isHoverState) {
//            setTransform(0);
            isHoverState = true;
        } else if (!isMouseOver() && isHoverState) {
//            setTransform(3);
            isHoverState = false;
        }
    }

    public ItemStack getItemStack() {
        return itemStacks[index];
    }
}
