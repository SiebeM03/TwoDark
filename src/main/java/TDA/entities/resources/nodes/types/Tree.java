package TDA.entities.resources.nodes.types;

import TDA.entities.components.interactions.ColliderComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.main.Entity;
import TDA.entities.resources.nodes.harvesting.HarvestableComp;
import TDA.entities.resources.items.types.TreeItem;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

/**
 * Represents a tree resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Tree extends HarvestableComp<Tree, TreeItem> {

    public Tree() {
        super(Tree.class, TreeItem.class);
    }

    public static Entity create(float  x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(400, 400)))
                       .addComponent(new QuadComp(Assets.getTexture("src/assets/images/seperateImages/tree2.png"), Layer.FOREGROUND))
                       .addComponent(new ColliderComp(470f / 600f, 70f / 600f, 270f / 600f, 240f / 600f))
                       .addComponent(new Tree());
    }
}