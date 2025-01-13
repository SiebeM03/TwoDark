package TDA.ui.menus.inventory;

import TDA.entities.inventory.ItemStack;
import woareXengine.ui.components.UiComponent;
import woareXengine.util.Color;

public class InventorySlot extends UiComponent {
    private final int COLS;
    private final int SPACING = 8;
    private float WIDTH = 54;
    private float HEIGHT = 54;

    public InventoryItemUi itemUi;

    private int column;
    private int row;

    public InventorySlot(ItemStack[] items, int index, int numCols) {
        COLS = numCols;

        itemUi = new InventoryItemUi(items, index);

        this.column = index % COLS;
        this.row = index / COLS;
    }

    @Override
    protected void init() {
        transform.setX(column * (SPACING + WIDTH));
        transform.setY(parent.transform.getHeight() - HEIGHT - row * (HEIGHT + SPACING));
        transform.setWidth(WIDTH);
        transform.setHeight(HEIGHT);

        color = new Color("#01487a");
        color.setAlpha(0.5f);
        add(itemUi);
    }

    @Override
    protected void updateSelf() {

    }

    public void setSelected(boolean selected) {
        if (selected) {
            color.setAlpha(1.0f);
        } else {
            color.setAlpha(0.5f);
        }
    }
}
