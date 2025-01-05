package TDA.entities.resources.types;

import TDA.entities.player.controller.collisions.GameCollisionBox;
import TDA.entities.resources.Resource;
import org.joml.Vector2f;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Stone extends Resource {
    public Stone(Vector2f position) {
        this.transform = new Transform(position, new Vector2f(150, 150));

        this.renderObject = new Quad(transform.getWidth(), transform.getHeight(), position, Layer.FOREGROUND);
        this.renderObject.texture = Assets.getTexture("src/assets/images/seperateImages/stone2.png");

        this.collisionBox = new GameCollisionBox(transform, 300f / 600f, 110f / 600f, 110f / 600f, 80f / 600f);
    }
}
