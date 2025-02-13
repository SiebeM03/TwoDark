package TDA.entities.resources.types;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.drops.TreeDrop;

/**
 * Represents a tree resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Tree extends HarvestableResource<Tree, TreeDrop> {

    public Tree() {
        super(Tree.class, TreeDrop.class);
    }
}