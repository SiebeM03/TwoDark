package TDA.ui.menus.inventory;

import TDA.entities.ecs.components.Inventory;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import woareXengine.ui.components.UiComponent;
import woareXengine.util.Color;

public class PlayerInventoryUi extends UiComponent {

    public Inventory inventory;

    public PlayerInventoryUi(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    protected void init() {
        setTransform(100);
        color = new Color("#0061a6");
        color.setAlpha(0.7f);

        UiComponent inventory1 = new InventoryItemList(inventory);
        add(inventory1);
    }

    @Override
    protected void updateSelf() {

    }
}
