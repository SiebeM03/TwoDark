package TDA.entities.components.resources.nodes.types;

import TDA.entities.components.resources.nodes.HarvestableComp;
import TDA.entities.components.resources.items.types.StoneItem;

/**
 * Represents a stone resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Stone extends HarvestableComp<Stone, StoneItem> {

    public Stone() {
        super(Stone.class, StoneItem.class);
    }
}