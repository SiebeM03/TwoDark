package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.main.GameManager;
import TDA.entities.ecs.components.Inventory.InventoryItem;
import woareXengine.util.Logger;

public class Hotbar extends Component {
    private InventoryItem[] hotbar = new InventoryItem[10];
    private int selected = 0;

    @Override
    public void init() {
    }

    @Override
    public void update() {
        int buttonPressed = GameManager.gameControls.inventoryControls.isHotbarItemSelected();
        if (buttonPressed != -1) {
            Logger.debug("Hotbar item selected: " + GameManager.gameControls.inventoryControls.isHotbarItemSelected());
            selected = buttonPressed;
        }
    }

    public InventoryItem getSelectedItem() {
        return hotbar[selected];
    }
}
