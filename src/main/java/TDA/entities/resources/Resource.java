package TDA.entities.resources;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.Pickup;
import TDA.entities.ecs.components.QuadComponent;
import TDA.entities.inventory.CollectableItem;
import TDA.entities.ecs.Component;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Layer;
import woareXengine.util.Logger;
import woareXengine.util.MathUtils;
import woareXengine.util.Transform;

public abstract class Resource<T extends Resource<T>> extends Component implements CollectableItem {
    private int health = 5;
    private final Class<T> resourceType;

    public Resource(Class<T> resourceType) {
        this.resourceType = resourceType;
    }

    public void harvest() {
        Logger.debug("Harvested " + this.getClass().getSimpleName() + "!");
        spawnItem();
        health--;

        if (health <= 0) {
            entity.destroy();
        }
    }

    protected void spawnItem() {
        try {
            final T resourceInstance = resourceType.getDeclaredConstructor().newInstance();
            final Entity item = new Entity(
                    new Transform(
                            new Vector2f(
                                    MathUtils.randomInRange(entity.transform.getX(), entity.transform.getX() + entity.transform.getWidth()),
                                    entity.transform.getCenter().y - 150
                            ),
                            new Vector2f(50, 50)
                    )
            )
                    .addComponent(new QuadComponent(getTexture(), Layer.UI))
                    .addComponent(new Pickup(resourceInstance, Math.round(MathUtils.randomInRange(1, 5))));

            GameManager.currentScene.addEntity(item);
        } catch (Exception e) {
            Logger.error("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public abstract Texture getTexture();
}
