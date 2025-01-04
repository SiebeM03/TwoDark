package old.engine.ecs.components;


import old.engine.ecs.Component;
import old.engine.ecs.Sprite;
import old.engine.ecs.Transform;
import old.engine.graphics.renderer.Texture;
import old.engine.util.Color;
import imgui.ImGui;
import org.joml.Vector2f;

/**
 * A component responsible for rendering a {@link Sprite} with a specified color and texture.
 * <p>
 * This component tracks changes to the associated {@link Transform} and updates the rendering state accordingly.
 */
public class SpriteRenderer extends Component {
    /**
     * The color of the sprite. Default is white (1, 1, 1, 1).
     */
    private Color color = Color.WHITE;

    /**
     * The sprite being rendered.
     */
    private Sprite sprite = new Sprite();

    /**
     * Stores the last known {@link Transform} state to detect changes.
     */
    private transient Transform lastTransform;


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
        }
    }

    /**
     * Displays an ImGui interface for adjusting the sprite's color.
     * <p>
     * Updates the color and marks the component as dirty if changes are made.
     */
    @Override
    public void imgui() {
        float[] imColor = {color.r(), color.g(), color.b(), color.a()};
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
        }
    }

    public Color getColor() {
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
        return this;
    }

    public SpriteRenderer setColor(Color color) {
        if (!this.color.equals(color)) {
            this.color = color;
        }
        return this;
    }

    public SpriteRenderer setTexture(Texture texture) {
        this.sprite.setTexture(texture);
        return this;
    }
}
