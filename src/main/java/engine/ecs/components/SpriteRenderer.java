package engine.ecs.components;


import engine.ecs.Component;
import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.graphics.renderer.Texture;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * A component responsible for rendering a {@link Sprite} with a specified color and texture.
 * <p>
 * This component tracks changes to the associated {@link Transform} and updates the rendering state accordingly.
 */
public class SpriteRenderer extends Component {
    /**
     * The color of the sprite. Default is white (1, 1, 1, 1).
     */
    private Vector4f color = new Vector4f(1, 1, 1, 1);

    /**
     * The sprite being rendered.
     */
    private Sprite sprite = new Sprite();

    /**
     * Stores the last known {@link Transform} state to detect changes.
     */
    private transient Transform lastTransform;

    /**
     * Indicates if the rendering state requires updating.
     */
    private transient boolean isDirty = true;


    /**
     * Initializes the component and saves the initial transform state.
     */
    @Override
    public void start() {
        this.lastTransform = this.gameObject.transform.copy();
    }

    /**
     * Checks for changes to the transform and marks the component as dirty if any are detected.
     */
    @Override
    public void update() {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    /**
     * Displays an ImGui interface for adjusting the sprite's color.
     * <p>
     * Updates the color and marks the component as dirty if changes are made.
     */
    @Override
    public void imgui() {
        float[] imColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
            this.isDirty = true;
        }
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public SpriteRenderer setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
        return this;
    }

    public SpriteRenderer setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color.set(color);
            this.isDirty = true;
        }
        return this;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    /**
     * Marks the component's state as dirty, forcing a rendering update.
     */
    public void setDirty() {
        this.isDirty = true;
    }

    /**
     * Marks the component's state as clean, indicating no pending updates.
     */
    public void setClean() {
        this.isDirty = false;
    }

    public SpriteRenderer setTexture(Texture texture) {
        this.sprite.setTexture(texture);
        return this;
    }
}
