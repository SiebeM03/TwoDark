package TDA.entities.components.resources.nodes;

import TDA.entities.components.resources.items.ItemDropComp;
import TDA.entities.main.Entity;
import TDA.entities.components.interactions.ClickableComp;
import TDA.entities.components.interactions.ItemPickupComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.components.tools.Tool;
import TDA.main.GameManager;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
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
public abstract class HarvestableComp<T extends HarvestableComp<T, R>, R extends ItemDropComp> extends ClickableComp {
    /**
     * The ratio between the damage (determined by the used tool) and the amount of items that will be dropped
     */
    private static final float damageToDropRatio = 0.15f;

    /**
     * Health of the resource, decreases when harvested. The resource gets destroyed when health reaches 0
     **/
    private int health = 50;
    /**
     * Actual resource node that is spawned across the world
     **/
    private final Class<T> resourceType;
    /**
     * The item that drops when the resource is harvested
     **/
    private final Class<R> drop;

    public HarvestableComp(Class<T> resourceType, Class<R> drop) {
        this.resourceType = resourceType;
        this.drop = drop;
    }

    /**
     * Harvests the resource, decreases the health of the resource and spawns the {@link #drop}.
     *
     * @param tool The tool equipped by the player, damage applied to the resource scales depending on what tool is used. If null, the resource will be harvested with a damage of 1.
     */
    public void harvest(final @Nullable Tool tool) {
        if (tool != null) {
            Logger.debug("Harvested " + this.getClass().getSimpleName() + " with " + tool.getClass().getSimpleName() + "!");
        } else {
            Logger.debug("Harvested " + this.getClass().getSimpleName() + "!");
        }

        final int damage = tool == null ? 1 : tool.getDamage(resourceType);
        spawnItem(damage);

        health -= damage;

        if (health <= 0) {
            entity.destroy();
        }
    }

    private void spawnItem(int damage) {
        try {
            int spawnAmount = Math.round(MathUtils.randomInRange(damage * damageToDropRatio, 4 * damage * damageToDropRatio));
            if (spawnAmount <= 0) return;

            final R dropClassInstance = drop.getDeclaredConstructor().newInstance();
            final Entity item = new Entity(
                    new Transform(
                            new Vector2f(
                                    MathUtils.randomInRange(entity.transform.getX(), entity.transform.getX() + entity.transform.getWidth()),
                                    entity.transform.getCenter().y - 150
                            ),
                            new Vector2f(50, 50)
                    ))
                    .addComponent(new QuadComp(dropClassInstance.getTexture(), Layer.FOREGROUND))
                    .addComponent(new ItemPickupComp(dropClassInstance, spawnAmount));

            GameManager.currentScene.addEntity(item);
        } catch (Exception e) {
            Logger.error("(SpawnItem) Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Class<R> getDrop() {
        return drop;
    }
}
