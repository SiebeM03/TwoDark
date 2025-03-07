package TDA.ui.menus.newInventoryItemPopup;

import TDA.entities.inventory.items.ItemStack;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;

import java.util.ArrayList;
import java.util.List;

public class ItemPopupContainer extends UiComponent {
    private List<ItemPopup> popups = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected void updateSelf() {
        for (int i = 0; i < popups.size(); i++) {
            ItemPopup popup = popups.get(i);

            popup.getConstraints().getY().setPixelValue((int) getYOffset(i));
            popup.getConstraints().apply();

            if (popup.shouldRemove()) {
                remove(popup);
                popups.remove(popup);
                i--;
            }
        }
    }

    public void createPopup(ItemStack itemStack) {
        ItemPopup popup = null;
        for (ItemPopup p : popups) {
            if (itemStack.item.getClass().equals(p.itemStack.item.getClass())) {
                popup = p;
                break;
            }
        }

        if (popup == null) {
            popup = new ItemPopup(itemStack);
            popups.add(popup);
            add(popup, new UiConstraints(
                    new PixelConstraint(10),
                    new PixelConstraint((int) getYOffset(popups.size())),
                    new PixelConstraint(100),
                    new PixelConstraint(30)
            ));
        } else {
            popup.updateValue(itemStack.amount);
        }
    }

    private float getYOffset(int index) {
        int popupSize = 40;

        float containerHeight = transform.getHeight();
        float totalHeight = popups.size() * popupSize;

        return (containerHeight - totalHeight) / 2 + index * popupSize;
    }
}
