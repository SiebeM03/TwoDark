package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.inventory.ItemStack;
import TDA.entities.resources.drops.StoneDrop;
import TDA.entities.resources.drops.TreeDrop;
import TDA.entities.resources.tools.Axe;
import TDA.entities.resources.tools.Pickaxe;
import TDA.entities.resources.types.Stone;
import TDA.entities.resources.types.Tree;
import TDA.main.GameManager;

public class Hotbar extends Component {
    public ItemStack[] hotbar = new ItemStack[10];
    private int selected = 0;

    @Override
    public void init() {
        hotbar[0] = new ItemStack(new Pickaxe(), 1);
        hotbar[1] = new ItemStack(new Axe(), 1);
        hotbar[2] = new ItemStack(new TreeDrop(), 20);
        hotbar[4] = new ItemStack(new StoneDrop(), 40);
    }

    @Override
    public void update() {
        int buttonPressed = GameManager.gameControls.inventoryControls.isHotbarItemSelected();
        if (buttonPressed == -1) return;

        selected = buttonPressed;
    }

    public void updateAtSlot(int index, ItemStack newStack) {
        if (hotbar[index] != null) return;
        hotbar[index] = newStack;
    }


    public ItemStack[] getHotbarItems() {
        return hotbar;
    }

    public ItemStack getSelectedItem() {
        return hotbar[selected];
    }

    public int getSelectedIndex() {
        return selected;
    }
}
