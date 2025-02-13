package TDA.entities.ecs.prefabs;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.Collider;
import TDA.entities.ecs.components.QuadComponent;
import TDA.entities.resources.types.Metal;
import TDA.entities.resources.types.Stone;
import TDA.entities.resources.types.Tree;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class ResourceNodePrefabs {
    public static Entity createTree(float  x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(400, 400)))
                .addComponent(new QuadComponent(Assets.getTexture("src/assets/images/seperateImages/tree2.png"), Layer.FOREGROUND))
                .addComponent(new Collider(470f / 600f, 70f / 600f, 270f / 600f, 240f / 600f))
                .addComponent(new Tree());
    }

    public static Entity createStone(float x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(150, 150)))
                .addComponent(new QuadComponent(Assets.getTexture("src/assets/images/seperateImages/stone2.png"), Layer.FOREGROUND))
                .addComponent(new Collider(300f / 600f, 110f / 600f, 110f / 600f, 80f / 600f))
                .addComponent(new Stone());
    }

    public static Entity createMetal(float x, float y) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(150, 150)))
                .addComponent(new QuadComponent(Assets.getTexture("src/assets/images/seperateImages/metal2.png"), Layer.FOREGROUND))
                .addComponent(new Collider(250f / 600f, 120f / 600f, 50f / 600f, 50f / 600f))
                .addComponent(new Metal());
    }
}
