package TDA.ui.menus.inventory;

import TDA.entities.components.inventory.InventoryComp;
import TDA.entities.prefabs.PlayerPrefab;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import TDA.ui.menus.inventory.itemList.InventorySlot;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.*;
import woareXengine.util.Color;

public class PlayerInventoryUi extends UiComponent {

    public InventoryComp inventory;
    public InventoryItemList storageInventoryItemList;

    public PlayerInventoryUi() {
        this.inventory = PlayerPrefab.getInventory();
    }

    @Override
    protected void init() {
        color = new Color("#0061a6");
        color.setAlpha(0.7f);

        InventoryItemList playerInventory = new InventoryItemList(inventory);
        playerInventory.isPlayerInventory(true);
        int width = InventoryItemList.COLS * (InventorySlot.SLOT_WIDTH + InventorySlot.SLOT_SPACING) - InventorySlot.SLOT_SPACING;
        add(playerInventory, ConstraintUtils.margin(16).setWidth(new PixelConstraint(width)));
    }

    @Override
    protected void updateSelf() {

    }

    @Override
    public void show(boolean show) {
        if (show && storageInventoryItemList != null) {
            int width = InventoryItemList.COLS * (InventorySlot.SLOT_WIDTH + InventorySlot.SLOT_SPACING) - InventorySlot.SLOT_SPACING;
            add(storageInventoryItemList, new UiConstraints(
                    new PositionConstraint(Position.RIGHT, 16),
                    new MarginConstraint(16),
                    new PixelConstraint(width),
                    new MarginConstraint(16)
            ));
        } else if (storageInventoryItemList != null) {
            remove(storageInventoryItemList);
            storageInventoryItemList = null;
        }
        super.show(show);
    }
}
