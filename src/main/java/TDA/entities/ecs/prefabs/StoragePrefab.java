package TDA.entities.ecs.prefabs;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.Collider;
import TDA.entities.ecs.components.QuadComponent;
import TDA.entities.ecs.components.Storage;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class StoragePrefab {
    public static Entity createBarrel(float x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(64, 96)))
                       .addComponent(new QuadComponent(Assets.getTexture("src/assets/images/pixelArt/barrel_closed.png"), Layer.FOREGROUND))
                       .addComponent(new Collider(32 / 48f, 1 / 48f, 3 / 32f, 3 / 32f))
                       .addComponent(new Storage(10));
    }
}
