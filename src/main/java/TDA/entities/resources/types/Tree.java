package TDA.entities.resources.types;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.Pickup;
import TDA.entities.ecs.components.QuadComponent;
import TDA.entities.resources.Resource;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Tree extends Resource {
    @Override
    protected void spawnItem() {
        Entity item = new Entity(new Transform(new Vector2f((float) (entity.transform.getX() + (Math.random() * entity.transform.getWidth())), entity.transform.getCenter().y - 150), new Vector2f(50, 50)))
                              .addComponent(new QuadComponent("src/assets/images/seperateImages/tree2.png", Layer.UI))
                              .addComponent(new Pickup(new Tree()));

        GameManager.currentScene.addEntity(item);
    }
}