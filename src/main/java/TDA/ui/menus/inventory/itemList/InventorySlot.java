package TDA.ui.menus.inventory.itemList;

import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.entities.inventory.InventoryManager;
import TDA.entities.inventory.ItemStack;
import TDA.main.GameManager;
import TDA.ui.hotbar.HotbarSlotWrapper;
import woareXengine.ui.common.UiBorderedBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.text.basics.Font;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;

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
            selected = index == PlayerPrefab.getHotbar().getSelectedIndex();
        }
    }

    @Override
    protected void updateSelf() {
        super.updateSelf();
        selected = parent instanceof HotbarSlotWrapper && index == PlayerPrefab.getHotbar().getSelectedIndex();

        setBorderWidth(selected || isMouseOver() ? 4 : 2);
        if (isMouseOver()) {
            System.out.println(getInventoryItem());
        }

        // If a new item is found that is not yet added to the UI, add it
        if (getItemStack() != null && getInventoryItem() == null && !InventoryManager.getFromCurrentScene().isHolding()) {
            System.out.println(index + " has a new item");
            add(new InventoryItemUi());
        }

        // If an item's amount reaches 0, remove it from the UI
        if (getItemStack() == null && getInventoryItem() != null) {
            System.out.println(index + " removed item");
            InventoryItemUi itemUi = getInventoryItem();
            Text amountText = itemUi.amountText;
            Font.texts.get(amountText.font).remove(amountText);
            remove(getInventoryItem());
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
