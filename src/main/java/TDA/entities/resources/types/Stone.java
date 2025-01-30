package TDA.entities.resources.types;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.drops.StoneDrop;

/**
 * Represents a stone resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Stone extends HarvestableResource<Stone, StoneDrop> {

    public Stone() {
        super(Stone.class, StoneDrop.class);
    }
}