package TDA.ui.menus.inventory.itemList;

import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.entities.inventory.InventoryManager;
import TDA.entities.inventory.ItemStack;
import TDA.main.GameManager;
import TDA.ui.hotbar.HotbarSlotWrapper;
import woareXengine.ui.common.UiBorderedBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.text.basics.Font;
import woareXengine.ui.text.basics.Text;

public class InventorySlot extends UiBorderedBlock {
    public static final int SLOT_WIDTH = 54;
    public static final int SLOT_HEIGHT = 54;
    public static final int SLOT_SPACING = 8;

    private boolean selected = false;

    private int index;

    public InventorySlot(int index) {
        this.index = index;
    }

    @Override
    protected void init() {
        super.init();

        if (getItemStack() != null) {
            add(new InventoryItemUi(), ConstraintUtils.fill());
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

        // If a new item is found that is not yet added to the UI, add it
        if (getItemStack() != null && getInventoryItem() == null && !InventoryManager.getFromCurrentScene().isHolding()) {
            System.out.println(index + " has a new item");
            add(new InventoryItemUi(), ConstraintUtils.fill());
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
