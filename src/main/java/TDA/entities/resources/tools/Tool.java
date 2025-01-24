package TDA.entities.resources.tools;

import TDA.entities.inventory.InventoryObject;
import TDA.entities.resources.HarvestableResource;

import java.util.Map;

public abstract class Tool implements InventoryObject {


    // Map die bijhoudt hoeveel damage er gedaan wordt op een "HarvestableResource" (Stone, Tree,... etc)
    protected abstract Map<Class<? extends HarvestableResource<?, ?>>, Integer> toolStats();

    public int getDamage(final Class<? extends HarvestableResource<?, ?>> targetMaterial) {
        return toolStats().getOrDefault(targetMaterial, 1);
    }

    @Override
    public boolean isStackable() {
        return false;
    }
}
