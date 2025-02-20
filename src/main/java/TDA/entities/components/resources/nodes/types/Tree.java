package TDA.entities.components.resources.nodes.types;

import TDA.entities.components.resources.nodes.HarvestableComp;
import TDA.entities.components.resources.items.types.TreeItem;

/**
 * Represents a tree resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Tree extends HarvestableComp<Tree, TreeItem> {

    public Tree() {
        super(Tree.class, TreeItem.class);
    }
}