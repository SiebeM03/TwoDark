package TDA.entities.resources.types;

import TDA.entities.player.controller.collisions.GameCollisionBox;
import TDA.entities.resources.Resource;
import org.joml.Vector2f;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Tree extends Resource {
    public Tree(Vector2f position) {
        this.transform = new Transform(position, new Vector2f(400, 400));

        this.renderObject = new Quad(transform.getWidth(), transform.getHeight(), position, Layer.FOREGROUND);
        this.renderObject.texture = Assets.getTexture("src/assets/images/seperateImages/tree2.png");

        this.collisionBox = new GameCollisionBox(transform, 470f / 600f, 70f / 600f, 270f / 600f, 240f / 600f);
    }
}