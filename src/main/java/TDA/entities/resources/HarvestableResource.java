package TDA.entities.resources;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.ClickableEntity;
import TDA.entities.ecs.components.Pickup;
import TDA.entities.ecs.components.QuadComponent;
import TDA.entities.ecs.Component;
import TDA.entities.resources.tools.Tool;
import TDA.main.GameManager;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Layer;
import woareXengine.util.Logger;
import woareXengine.util.MathUtils;
import woareXengine.util.Transform;

/**
 * Represents a resource that can be harvested.
 *
 * @param <T> - The type of the resource that can be harvested.
 * @param <R> - The type of the drop.
 */
public abstract class HarvestableResource<T extends HarvestableResource<T, R>, R extends DropResource> extends ClickableEntity {

    /** Health of the resource, decreases when harvested. The resource gets destroyed when health reaches 0 **/
    private int health = 5;
    /** Actual resource node that is spawned across the world **/
    private final Class<T> resourceType;
    /** The item that drops when the resource is harvested **/
    private final Class<R> drop;

    public HarvestableResource(Class<T> resourceType, Class<R> drop) {
        this.resourceType = resourceType;
        this.drop = drop;
    }

    /**
     * Harvests the resource, decreases the health of the resource and spawns the {@link #drop}.
     * @param tool The tool equipped by the player, damage applied to the resource scales depending on what tool is used. If null, the resource will be harvested with a damage of 1.
     */
    public void harvest(final @Nullable Tool tool) {
        if (tool != null) {
            Logger.debug("Harvested " + this.getClass().getSimpleName() + " with " + tool.getClass().getSimpleName() + "!");
        } else {
            Logger.debug("Harvested " + this.getClass().getSimpleName() + "!");
        }
        spawnItem();

        final int damage = tool == null ? 1 : tool.getDamage(resourceType);

        health -= damage;

        if (health <= 0) {
            entity.destroy();
        }
    }

    protected void spawnItem() {
        try {
            final R dropClassInstance = drop.getDeclaredConstructor().newInstance();
            final Entity item = new Entity(
                    new Transform(
                            new Vector2f(
                                    MathUtils.randomInRange(entity.transform.getX(), entity.transform.getX() + entity.transform.getWidth()),
                                    entity.transform.getCenter().y - 150
                            ),
                            new Vector2f(50, 50)
                    )
            )
                    .addComponent(new QuadComponent(dropClassInstance.getTexture(), Layer.UI))
                    .addComponent(new Pickup(dropClassInstance, Math.round(MathUtils.randomInRange(1, 5))));

            GameManager.currentScene.addEntity(item);
        } catch (Exception e) {
            Logger.error("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Class<R> getDrop() {
        return drop;
    }

    public abstract Texture getTexture();
}
