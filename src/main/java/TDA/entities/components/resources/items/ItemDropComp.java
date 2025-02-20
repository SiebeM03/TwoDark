package TDA.entities.components.resources.items;

import TDA.entities.main.Component;
import TDA.entities.components.inventory.items.InventoryObject;

public abstract class ItemDropComp extends Component implements InventoryObject {

    @Override
    public boolean isStackable() {
        return true;
    }
}
