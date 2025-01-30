package TDA.entities.resources.types;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.drops.MetalDrop;

/**
 * Represents a metal resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Metal extends HarvestableResource<Metal, MetalDrop> {

    public Metal() {
        super(Metal.class, MetalDrop.class);
    }
}
