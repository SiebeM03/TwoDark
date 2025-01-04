package old.engine.ui.fonts;

import old.engine.ecs.Transform;
import old.engine.graphics.renderer.Texture;
import old.engine.ui.Text;
import old.engine.util.Color;

import org.joml.Vector2f;

public class GlyphRenderer {
    private Color color;

    private Glyph glyph;
    private char character;
    private Text parentText;

    private Transform localTransform;
    private Transform lastTransform = new Transform();

    public GlyphRenderer(Transform transform, Glyph glyph, Text parentText, char c, Color color) {
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
     * @return a Color containing the normalized (0-1) color values (R, G, B, and A)
     */
    public Color getColor() {
        return color;
    }

    public Transform getLocalTransform() {
        return localTransform;
    }

    /**
     * Change the color by passing a Color
     *
     * @param color values should be in the range of 0-1
     */
    public void setColor(Color color) {
        if (!this.color.equals(color)) {
            this.color = color;
        }
    }
}
