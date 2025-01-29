package TDA.entities.resources;

import TDA.entities.ecs.Component;
import TDA.entities.inventory.InventoryObject;

public abstract class DropResource extends Component implements InventoryObject {

    @Override
    public boolean isStackable() {
        return true;
    }
}
