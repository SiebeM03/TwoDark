package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.inventory.ItemStack;
import TDA.entities.resources.drops.StoneDrop;
import TDA.entities.resources.drops.TreeDrop;
import TDA.entities.resources.tools.Axe;
import TDA.entities.resources.tools.Pickaxe;
import TDA.main.GameManager;

import java.util.Arrays;

public class Hotbar extends Component {
    public ItemStack[] inventoryItems = new ItemStack[10];
    private int selected = 0;

    @Override
    public void init() {
        inventoryItems[0] = new ItemStack(new Pickaxe(), 1);
        inventoryItems[1] = new ItemStack(new Axe(), 1);
        inventoryItems[2] = new ItemStack(new TreeDrop(), 120);
        inventoryItems[4] = new ItemStack(new StoneDrop(), 40);
    }

    @Override
    public void update() {
        for (int i = 0; i < inventoryItems.length; i++) {
            ItemStack itemStack = inventoryItems[i];
            if (itemStack == null) continue;

            if (itemStack.amount == 0) {
                inventoryItems[i] = null;
            }
        }

        int buttonPressed = GameManager.gameControls.inventoryControls.isHotbarItemSelected();
        if (buttonPressed == -1) return;

        selected = buttonPressed;
    }

    public ItemStack[] getHotbarItems() {
        return inventoryItems;
    }

    public ItemStack getSelectedItem() {
        return inventoryItems[selected];
    }

    public int getSelectedIndex() {
        return selected;
    }
}
