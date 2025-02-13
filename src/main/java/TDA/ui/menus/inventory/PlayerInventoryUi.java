package TDA.ui.menus.inventory;

import TDA.entities.ecs.components.Inventory;
import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.entities.inventory.InventoryManager;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import woareXengine.ui.components.UiComponent;
import woareXengine.util.Color;
import woareXengine.util.Logger;

public class PlayerInventoryUi extends UiComponent {

    public Inventory inventory;
    public InventoryItemList storageInventoryItemList;

    public PlayerInventoryUi() {
        this.inventory = PlayerPrefab.getInventory();
    }

    @Override
    protected void init() {
        setTransform(100);
        color = new Color("#0061a6");
        color.setAlpha(0.7f);

        InventoryItemList playerInventory = new InventoryItemList(inventory);
        playerInventory.isPlayerInventory(true);
        add(playerInventory);
    }

    @Override
    protected void updateSelf() {

    }

    @Override
    public void show(boolean show) {
        if (show && storageInventoryItemList != null) {
            add(storageInventoryItemList);
        } else if (storageInventoryItemList != null) {
            remove(storageInventoryItemList);
            storageInventoryItemList = null;
        }
        super.show(show);
    }
}
