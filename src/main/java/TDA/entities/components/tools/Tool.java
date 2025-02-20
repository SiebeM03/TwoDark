package TDA.entities.components.tools;

import TDA.entities.components.inventory.items.InventoryObject;
import TDA.entities.components.resources.nodes.HarvestableComp;

import java.util.Map;

public abstract class Tool implements InventoryObject {


    /** @return Map that keeps track of the damage done to a "HarvestableResource" (Stone, Tree,... etc) */
    protected abstract Map<Class<? extends HarvestableComp<?, ?>>, Integer> toolStats();

    public int getDamage(final Class<? extends HarvestableComp<?, ?>> targetMaterial) {
        return toolStats().getOrDefault(targetMaterial, 1);
    }

    @Override
    public boolean isStackable() {
        return false;
    }
}
