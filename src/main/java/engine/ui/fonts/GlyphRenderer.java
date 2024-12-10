package engine.ui.fonts;

import engine.ecs.Transform;
import engine.graphics.renderer.Texture;
import engine.ui.Text;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GlyphRenderer {
    private Vector4f color;

    private Glyph glyph;
    private char character;
    private Text parentText;

    private Transform localTransform;
    private Transform lastTransform = new Transform();

    public GlyphRenderer(Transform transform, Glyph glyph, Text parentText, char c, Vector4f color) {
        this.localTransform = transform;
        this.glyph = glyph;
        this.color = color;
        this.parentText = parentText;
        this.character = c;
    }

    public void update() {
        if (!this.lastTransform.equals(this.localTransform)) {
            this.localTransform.copy(this.lastTransform);
        }
    }

    public void updatePosition(Vector2f delta) {
        localTransform.addX(delta.x());
        localTransform.addY(delta.y());
    }

    /**
     * @return type Texture of the sprite if applicable.
     */
    public Texture getTexture() {
        return glyph.getTexture();
    }


    /**
     * @return Vector2f array of the UV coordinates of the sprite if applicable.
     */
    public Vector2f[] getTexCoords() {
        return glyph.getUV();
    }

    /**
     * @return a Vector4f containing the normalized (0-1) color values (R, G, B, and A)
     */
    public Vector4f getColor() {
        return color;
    }

    public Transform getLocalTransform() {
        return localTransform;
    }

    /**
     * Change the color by passing a Vector4f
     *
     * @param color vector, values should be in the range of 0-1
     */
    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color = color;
        }
    }
}
