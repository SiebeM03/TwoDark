package TDA.ui.states;

import TDA.entities.storage.StorageComp;
import TDA.main.GameManager;
import TDA.scene.systems.InventoryManager;
import TDA.ui.menus.inventory.PlayerInventoryUi;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import woareXengine.ui.constraints.ConstraintUtils;

public class InventoryUiState extends UiState {
    private PlayerInventoryUi playerInventoryUi = new PlayerInventoryUi();

    protected void toggleMouseAndKeyboard(boolean isOpening) {
        GameManager.gameControls.windowControls.enableInput(!isOpening);
        GameManager.gameControls.playerControls.enableInput(!isOpening);

        if (!isOpening && playerInventoryUi.storageInventoryItemList != null) {
            playerInventoryUi.rightSegment.children.forEach(this::remove);
            InventoryManager.getFromCurrentScene().setExternalInventory(null);
        }
    }

    @Override
    protected void init() {
        add(playerInventoryUi, ConstraintUtils.margin(100, 100));
        enableState(false);
    }

    @Override
    protected void updateSelf() {

    }

    public void showStorage(StorageComp storage) {
        playerInventoryUi.showStorageInventory(new InventoryItemList(storage.getInventory()), storage.storageName);
        InventoryManager.getFromCurrentScene().setExternalInventory(storage.getInventory());
        enableState(true);
    }
}
