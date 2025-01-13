package TDA.ui.menus.inventory;

import TDA.entities.ecs.components.Inventory;
import woareXengine.ui.components.UiComponent;
import woareXengine.util.Color;

public class InventoryUi extends UiComponent {

    public Inventory inventory;

    public InventoryUi(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    protected void init() {
        setTransform(100);
        color = new Color("#0061a6");
        color.setAlpha(0.7f);
        add(new InventoryItemList(inventory));
    }

    @Override
    protected void updateSelf() {

    }
}
