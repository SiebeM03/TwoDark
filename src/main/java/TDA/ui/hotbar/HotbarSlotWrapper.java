package TDA.ui.hotbar;

import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.entities.inventory.ItemStack;
import TDA.ui.menus.inventory.itemList.InventorySlot;
import woareXengine.ui.components.UiComponent;

public class HotbarSlotWrapper extends UiComponent {
    private final ItemStack[] hotbarItems;

    public HotbarSlotWrapper() {
        this.hotbarItems = PlayerPrefab.getHotbar().getHotbarItems();
    }

    @Override
    protected void init() {
        setTransform(4);

        for (int i = 0; i < hotbarItems.length; i++) {
            add(new InventorySlot(i, 10));
        }
    }

    @Override
    protected void updateSelf() {

    }

    public ItemStack getItemStack(int index) {
        return hotbarItems[index];
    }

    private InventorySlot getSlot(int index) {
        for (UiComponent c : children) {
            if (!(c instanceof InventorySlot)) continue;
            InventorySlot i = (InventorySlot) c;

            if (i.getIndex() == index) {
                return i;
            }
        }
        return null;
    }
}
