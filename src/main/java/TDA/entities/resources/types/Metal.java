package TDA.entities.resources.types;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.Pickup;
import TDA.entities.ecs.components.QuadComponent;
import TDA.entities.resources.Resource;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.MathUtils;
import woareXengine.util.Transform;

public class Metal extends Resource {
    @Override
    protected void spawnItem() {
        Entity item = new Entity(new Transform(new Vector2f(MathUtils.randomInRange(entity.transform.getX(), entity.transform.getX() + entity.transform.getWidth()), entity.transform.getCenter().y - 150), new Vector2f(50, 50)))
                              .addComponent(new QuadComponent(getTexture(), Layer.UI))
                              .addComponent(new Pickup(new Metal(), Math.round(MathUtils.randomInRange(1, 5))));

        GameManager.currentScene.addEntity(item);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/metal2.png");
    }
}
