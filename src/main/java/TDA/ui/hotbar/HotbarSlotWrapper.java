package TDA.ui.hotbar;

import TDA.entities.ecs.components.Hotbar;
import TDA.entities.player.Player;
import TDA.ui.menus.inventory.InventorySlot;
import woareXengine.ui.components.UiComponent;

public class HotbarSlotWrapper extends UiComponent {


    @Override
    protected void init() {
        setTransform(4);

        Hotbar hotbar = Player.hotbar;
        for (int i = 0; i < hotbar.getHotbarItems().length; i++) {
            add(new InventorySlot(hotbar.getHotbarItems(), i, 10));
        }
    }

    @Override
    protected void updateSelf() {
        for (UiComponent c : children) {
            if (!(c instanceof InventorySlot)) continue;
            InventorySlot i = (InventorySlot) c;

            if (Player.hotbar.getSelectedItem() != null && Player.hotbar.getSelectedItem().equals(i.itemUi.getItemStack())) {
                i.setSelected(true);
            } else {
                i.setSelected(false);
            }
        }
    }
}
