package TDA.entities.components.resources.nodes.types;

import TDA.entities.components.resources.nodes.HarvestableComp;
import TDA.entities.components.resources.items.types.MetalItem;

/**
 * Represents a metal resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Metal extends HarvestableComp<Metal, MetalItem> {

    public Metal() {
        super(Metal.class, MetalItem.class);
    }
}
