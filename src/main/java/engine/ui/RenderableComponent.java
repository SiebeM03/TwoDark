package engine.ui;

import engine.ecs.Sprite;
import engine.listeners.MouseListener;
import engine.util.Color;
import engine.util.JMath;
import org.joml.Vector2f;

import static engine.util.Color.WHITE;

public class RenderableComponent extends BaseComponent {
    public Sprite sprite;

    public Color color = WHITE;
    public Color defaultColor = this.color;
    public Color hoverColor = new Color(0.8f, 0.8f, 0.8f, 1.0f);

    protected boolean hovering = false;

    public RenderableComponent(String name) {
        new RenderableComponent(name, WHITE, new Sprite());
    }

    public RenderableComponent(String name, Color color) {
        this.name = name;
        setColor(color);
        this.defaultColor = this.color;
    }

    public RenderableComponent(String name, Sprite sprite) {
        this.name = name;
        this.sprite = sprite;
    }

    public RenderableComponent(String name, Color color, Sprite sprite) {
        this.name = name;
        this.setColor(color);
        this.defaultColor = this.color;
        this.sprite = sprite;
    }

    @Override
    public void update() {
        super.update();
        this.hovering = JMath.inRect(new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY()), getAbsolutePosition().x(), getAbsolutePosition().y(), transform.scale.x, transform.scale.y);
        if (this.hovering) {
            setColor(hoverColor);
        } else {
            setColor(defaultColor);
        }
    }

    public void setColor(Color color) {
        if (!this.color.equals(color)) {
            this.color = color;
        }
    }

    public void setNoInteraction() {
        this.hoverColor = this.defaultColor;
    }
}
