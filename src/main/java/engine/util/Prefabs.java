package engine.util;

import engine.ecs.GameObject;
import engine.ecs.Sprite;
import engine.ecs.components.SpriteRenderer;
import engine.ecs.Transform;
import org.joml.Vector2f;

public class Prefabs {

    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject go = new GameObject("Sprite_Object_Prefab", new Transform(new Vector2f(), new Vector2f(sizeX, sizeY)), Layer.TOP);
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        go.addComponent(renderer);

        return go;
    }
}
