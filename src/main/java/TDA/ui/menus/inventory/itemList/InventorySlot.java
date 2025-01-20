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
        selected = parent instanceof HotbarSlotWrapper && index == Player.hotbar.getSelectedIndex();

        setBorderWidth(selected || isMouseOver() ? 4 : 2);

        if (getItemStack() != null && getInventoryItem() == null && !InventoryManager.getFromCurrentScene().isHolding()) {
            add(new InventoryItemUi());
        }

        handleHolding();
    }

    /**
     * Handles the placing/swapping of items between inventories. Also works across multiple inventories (e.g. PlayerInventory <-> Hotbar)
     * <ul>
     * <li>Slot is clicked while {@link InventoryManager} is holding an item:
     * <ul>
     *     <li>Clicked slot is empty? The item is placed on the clicked slot</li>
     *     <li>Clicked slot is not empty? Both items swap places</li>
     * </ul></li>
     * <li>Slot is clicked while {@link InventoryManager} is not holding an item:
     * <ul>
     *     <li>The item inside the slot is picked up</li>
     *     <li>If there is no item, nothing happens</li>
     * </ul></li></ul>
     */
    private void handleHolding() {
        if (isMouseOver() && GameManager.gameControls.inventoryControls.isClicked()) {
            if (InventoryManager.getFromCurrentScene().isHolding()) {
                InventoryManager.getFromCurrentScene().placeHolding(getIndex(), this);
            } else {
                if (getInventoryItem() == null) return;
                InventoryManager.getFromCurrentScene().setHolding(getIndex(), this);
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
