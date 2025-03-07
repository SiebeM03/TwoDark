package TDA.entities.storage.entities;

import TDA.entities.components.interactions.ColliderComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.main.Entity;
import TDA.entities.storage.StorageComp;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class BarrelStorage {
    public static Entity create(float x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(64, 96)))
                       .addComponent(new QuadComp(Assets.getTexture("src/assets/images/pixelArt/barrel_closed.png"), Layer.FOREGROUND))
                       .addComponent(new ColliderComp(32 / 48f, 1 / 48f, 3 / 32f, 3 / 32f))
                       .addComponent(new StorageComp(10, "Barrel"));
    }
}
