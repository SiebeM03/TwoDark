package woareXengine.rendering.renderData;

import woareXengine.openglWrapper.textures.Texture;

public abstract class RenderObject {
    public static Primitive primitive;

    public Texture texture;
    public int zIndex;
}
