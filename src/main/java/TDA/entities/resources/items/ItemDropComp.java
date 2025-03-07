package TDA.entities.resources.items;

import TDA.entities.main.Component;
import TDA.entities.inventory.items.InventoryObject;

public abstract class ItemDropComp extends Component implements InventoryObject {

    @Override
    public boolean isStackable() {
        return true;
    }
}
