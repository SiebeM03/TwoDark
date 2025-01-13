package woareXengine.rendering.renderData;

import org.joml.Vector2f;
import woareXengine.openglWrapper.textures.Texture;

public abstract class RenderObject {
    public static Primitive primitive;

    public Texture texture;

    public Vector2f[] textureCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1),
    };

    public int zIndex;
}
