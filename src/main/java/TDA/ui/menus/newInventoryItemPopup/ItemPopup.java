package TDA.ui.menus.newInventoryItemPopup;

import TDA.entities.inventory.items.ItemStack;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;

public class ItemPopup extends UiBlock {
    private final float displayDuration = 3;
    private float timer = 0;

    protected final ItemStack itemStack;
    private UiBlock icon;
    private Text amount;

    public ItemPopup(ItemStack itemStack) {
        this.itemStack = itemStack;
        icon = new UiBlock(Color.WHITE);
        icon.texture = itemStack.item.getTexture();
        add(icon, new UiConstraints(
                new PixelConstraint(0),
                new CenterConstraint(),
                new PixelConstraint(30),
                new PixelConstraint(30)
        ));
        amount = Assets.getDefaultFont().createText(itemStack.amount + "", 0.75f);
        add(amount, new UiConstraints(
                new PixelConstraint(35),
                new CenterConstraint(),
                new PixelConstraint((int) amount.calculateWidth()),
                new PixelConstraint((int) amount.calculateHeight())
        ));
    }

    @Override
    public void updateSelf() {
        timer += Engine.getDelta();
    }

    protected boolean shouldRemove() {
        return timer >= displayDuration;
    }

    protected void updateValue(int value) {
        amount.textString = Integer.parseInt(amount.textString) + value + "";
        timer = 0;
    }

    protected float getWidth() {
        return 35 + amount.calculateWidth();
    }
}
