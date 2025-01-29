package woareXengine.rendering.quadRenderer;

import org.joml.Vector2f;
import woareXengine.rendering.renderData.Primitive;
import woareXengine.rendering.renderData.RenderObject;
import woareXengine.util.Color;
import woareXengine.util.Transform;

public class Quad extends RenderObject {
    public Color color = new Color(Color.WHITE);

    public Transform transform;

    // TODO feels wrong that Quad knows about entity data / implementation
    private int entityID;

    public Quad(float width, float height, Vector2f position, int zIndex) {
        this(width, height, Color.WHITE, position, zIndex);
    }

    public Quad(float width, float height, Color color, Vector2f position, int zIndex) {
        Quad.primitive = Primitive.QUAD;
        this.zIndex = zIndex;

        this.transform = new Transform(position, new Vector2f(width, height));

        this.color.set(color.toVec4());
    }

    public void horizontalFlip() {
        this.textureCoords = new Vector2f[]{
                textureCoords[3],
                textureCoords[2],
                textureCoords[1],
                textureCoords[0]
        };
    }

    public void setEntityID(int id) {
        this.entityID = id;
    }

    public int getEntityID() {
        return entityID;
    }
}
