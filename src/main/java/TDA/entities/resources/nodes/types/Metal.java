package TDA.entities.resources.nodes.types;

import TDA.entities.components.interactions.ColliderComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.main.Entity;
import TDA.entities.resources.nodes.harvesting.HarvestableComp;
import TDA.entities.resources.items.types.MetalItem;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

/**
 * Represents a metal resource, extends HarvestableResource which in turn extends ClickableEntity.
 */
public class Metal extends HarvestableComp<Metal, MetalItem> {

    public Metal() {
        super(Metal.class, MetalItem.class);
    }

    public static Entity create(float x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(150, 150)))
                       .addComponent(new QuadComp(Assets.getTexture("src/assets/images/seperateImages/metal2.png"), Layer.FOREGROUND))
                       .addComponent(new ColliderComp(250f / 600f, 120f / 600f, 50f / 600f, 50f / 600f))
                       .addComponent(new Metal());
    }
}
