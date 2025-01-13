package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.inventory.ItemStack;
import TDA.entities.resources.types.Stone;
import TDA.entities.resources.types.Tree;
import TDA.main.GameManager;

public class Hotbar extends Component {
    public ItemStack[] hotbar = new ItemStack[10];
    private int selected = -1;

    @Override
    public void init() {
        hotbar[2] = new ItemStack(new Tree(), 20);
        hotbar[4] = new ItemStack(new Stone(), 40);
    }

    @Override
    public void update() {
        int buttonPressed = GameManager.gameControls.inventoryControls.isHotbarItemSelected();
        if (buttonPressed == -1) return;

        if (hotbar[buttonPressed] == null) return;
        if (buttonPressed == selected) {
            selected = -1;
        } else {
            selected = buttonPressed;
        }
    }

    public ItemStack[] getHotbarItems() {
        return hotbar;
    }

    public ItemStack getSelectedItem() {
        if (selected == -1) return null;
        return hotbar[selected];
    }

    public int getSelectedIndex() {
        return selected;
    }
}
