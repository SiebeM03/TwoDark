package TDA.ui.states;

import TDA.entities.inventory.items.ItemStack;
import TDA.entities.player.PlayerPrefab;
import TDA.ui.hotbar.HotbarUi;
import TDA.ui.menus.inventory.itemList.InventorySlot;
import TDA.ui.menus.newInventoryItemPopup.ItemPopupContainer;
import woareXengine.ui.constraints.*;

public class MainGameUiState extends UiState {

    private HotbarUi hotbarUi = new HotbarUi();
    private ItemPopupContainer newItemContainer = new ItemPopupContainer();


    @Override
    protected void toggleMouseAndKeyboard(boolean isOpening) {

    }

    @Override
    protected void init() {
        int hotbarSize = PlayerPrefab.getHotbar().getHotbarItems().length;
        add(hotbarUi, new UiConstraints(
                new CenterConstraint(),
                new PixelConstraint(0),
                new PixelConstraint((InventorySlot.SLOT_WIDTH + InventorySlot.SLOT_SPACING) * hotbarSize),
                new PixelConstraint(InventorySlot.SLOT_HEIGHT + InventorySlot.SLOT_SPACING)
        ));

        add(newItemContainer, ConstraintUtils.fill());
    }

    @Override
    protected void updateSelf() {

    }

    public void itemAddedToInventory(ItemStack itemStack) {
        newItemContainer.createPopup(itemStack);
    }
}
