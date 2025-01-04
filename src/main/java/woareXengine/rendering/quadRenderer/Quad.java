package woareXengine.rendering.quadRenderer;

import org.joml.Vector2f;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.rendering.renderData.Primitive;
import woareXengine.rendering.renderData.RenderObject;
import woareXengine.util.Color;

public class Quad extends RenderObject {
    public float width;
    public float height;
    public Color color = new Color(Color.WHITE);
    public Vector2f position = new Vector2f();

    public Texture texture = null;
    public Vector2f[] textureCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1),
    };

    public Quad(float width, float height, Vector2f position, int zIndex) {
        new Quad(width, height, new Color(Color.WHITE), position, zIndex);
    }

    public Quad(float width, float height, Color color, Vector2f position, int zIndex) {
        Quad.primitive = Primitive.QUAD;
        this.zIndex = zIndex;

        this.width = width;
        this.height = height;
        this.color.set(color.toVec4());
        this.position.set(position);
    }

}
