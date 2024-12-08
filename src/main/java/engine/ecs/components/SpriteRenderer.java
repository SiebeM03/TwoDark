package engine.ecs.components;


import engine.ecs.Component;
import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.graphics.renderer.Texture;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;


    @Override
    public void start() {
        this.lastTransform = this.gameObject.transform.copy();
    }

    @Override
    public void update() {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

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

    public void setDirty() {
        this.isDirty = true;
    }

    public void setClean() {
        this.isDirty = false;
    }

    public SpriteRenderer setTexture(Texture texture) {
        this.sprite.setTexture(texture);
        return this;
    }
}
