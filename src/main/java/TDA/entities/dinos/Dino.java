package TDA.entities.dinos;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.Collider;
import TDA.entities.ecs.components.QuadComponent;
import TDA.entities.ecs.components.WanderAI;
import org.joml.Vector2f;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Dino {

    public static Entity createEntity() {
        Entity dino = new Entity(new Transform(new Vector2f(100, 100), new Vector2f(128, 128)))
                              .addComponent(new QuadComponent("src/assets/images/dino.png", Layer.FOREGROUND))
                              .addComponent(new Collider(152 / 256f, 8 / 256f, 60 / 256f, 88 / 256f))
                              .addComponent(new WanderAI());
        return dino;
    }
}
