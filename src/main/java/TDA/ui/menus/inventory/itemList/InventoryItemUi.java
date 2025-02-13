package TDA.ui.menus.inventory.itemList;

import TDA.entities.inventory.ItemStack;
import TDA.entities.player.Player;
import TDA.entities.resources.drops.TreeDrop;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;

import java.util.Objects;

public class InventoryItemUi extends UiComponent {
    public Text amountText = null;

    UiBlock block = new UiBlock();

    public InventoryItemUi() {
    }

    @Override
    protected void init() {
        setTransform(0);

        if (getItemStack() != null) {
            texture = getItemStack().item.getTexture();
            color = Color.WHITE;

            if (amountText == null) {
                amountText = Assets.getFont("src/assets/fonts/rounded.fnt").createText(getItemStack().amount + "", 0.5f);
                add(amountText);
                updateTextTransform();
            }
        }
    }

    @Override
    protected void updateSelf() {
        if (getItemStack() == null) return;

        if (!amountText.textString.equals(getItemStack().amount + "")) {
            amountText.textString = getItemStack().amount + "";
            updateTextTransform();
        }

        if (isMouseOver()) {
            if (Engine.mouse().getScroll() > 0) {
                getItemStack().amount++;
            }
            if (Engine.mouse().getScroll() < 0) {
                getItemStack().amount--;
            }
        }
    }

    private void updateTextTransform() {
        amountText.transform.setWidth(amountText.calculateWidth());
        amountText.transform.setHeight(amountText.calculateHeight());
        amountText.transform.setX(parent.transform.getWidth() - amountText.calculateWidth() - 4);
        amountText.transform.setY(4);
    }

    public ItemStack getItemStack() {
        if (parent instanceof InventorySlot) {
            return ((InventorySlot) parent).getItemStack();
        }
        return null;
    }
}
