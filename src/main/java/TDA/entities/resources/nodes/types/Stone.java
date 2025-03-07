package TDA.entities.resources.nodes.types;

import TDA.entities.components.interactions.ColliderComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.main.Entity;
import TDA.entities.resources.nodes.harvesting.HarvestableComp;
import TDA.entities.resources.items.types.StoneItem;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

/**
 * Represents a stone resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Stone extends HarvestableComp<Stone, StoneItem> {

    public Stone() {
        super(Stone.class, StoneItem.class);
    }

    public static Entity create(float x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(150, 150)))
                       .addComponent(new QuadComp(Assets.getTexture("src/assets/images/seperateImages/stone2.png"), Layer.FOREGROUND))
                       .addComponent(new ColliderComp(300f / 600f, 110f / 600f, 110f / 600f, 80f / 600f))
                       .addComponent(new Stone());
    }
}