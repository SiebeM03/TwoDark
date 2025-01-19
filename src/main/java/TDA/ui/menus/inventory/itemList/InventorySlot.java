package TDA.ui.menus.inventory.itemList;

import TDA.entities.inventory.InventoryManager;
import TDA.entities.inventory.ItemStack;
import TDA.entities.player.Player;
import TDA.main.GameManager;
import TDA.ui.hotbar.HotbarSlotWrapper;
import woareXengine.ui.common.UiBorderedBlock;
import woareXengine.ui.components.UiComponent;

public class InventorySlot extends UiBorderedBlock {
    private final int COLS;
    private final int SPACING = 8;
    private float WIDTH = 54;
    private float HEIGHT = 54;

    private boolean selected = false;

    private int index;

    public InventorySlot(int index, int numCols) {
        this.index = index;
        COLS = numCols;
    }

    @Override
    protected void init() {
        int column = this.index % COLS;
        int row = this.index / COLS;

        super.init();

        transform.setX(column * (SPACING + WIDTH) + parent.getPadding());
        transform.setY(parent.transform.getHeight() - HEIGHT - row * (HEIGHT + SPACING) - parent.getPadding());
        transform.setWidth(WIDTH);
        transform.setHeight(HEIGHT);

        if (getItemStack() != null) {
            add(new InventoryItemUi());
        }

        if (parent instanceof HotbarSlotWrapper) {
            selected = index == Player.hotbar.getSelectedIndex();
        }
    }

    @Override
    protected void updateSelf() {
        super.updateSelf();

        setBorderWidth(selected || isMouseOver() ? 4 : 2);

        if (isMouseOver() && GameManager.gameControls.inventoryControls.isClicked()) {
            if (InventoryManager.getFromCurrentScene().getHolding() == null) {
                InventoryManager.getFromCurrentScene().setHolding(getInventoryItem());
            } else {
                InventoryManager.getFromCurrentScene().swapSlot(this);
            }
        }
    }

    public InventoryItemUi getInventoryItem() {
        for (UiComponent c : children) {
            if (c instanceof InventoryItemUi) {
                return (InventoryItemUi) c;
            }
        }
        return null;
    }

    public ItemStack getItemStack() {
        if (parent instanceof InventoryItemList) {
            return ((InventoryItemList) parent).getItemStack(index);
        }
        if (parent instanceof HotbarSlotWrapper) {
            return ((HotbarSlotWrapper) parent).getItemStack(index);
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
