package testGame;

import engine.ecs.GameObject;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Window;
import engine.listeners.MouseListener;
import engine.ui.MouseEventConsumer;
import org.joml.Vector4f;

public class Resource extends MouseEventConsumer {
    private String name;
    private int amount;
    private transient GameObject toolTipGo;

    public Resource(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public void onClick() {
        System.out.println("Clicked on " + this.name + " with id " + this.gameObject.getUid() + " and amount " + this.amount);
    }

    @Override
    public void onHover() {
        toolTipGo.transform.position.x = MouseListener.getOrthoX();
        toolTipGo.transform.position.y = MouseListener.getOrthoY();
    }

    @Override
    public void onEnter() {
        this.gameObject.getComponent(SpriteRenderer.class).setColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
        toolTipGo = ResourceTooltip.generateTooltip(this);
        Window.getScene().addGameObjectToScene(toolTipGo);
    }

    @Override
    public void onLeave() {
        this.gameObject.getComponent(SpriteRenderer.class).setColor(new Vector4f(1));
        Window.getScene().removeGameObjectFromScene(toolTipGo);
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        System.out.println("Resource " + this.name + " renamed to " + name);
        this.name = name;
    }

    public void setAmount(int amount) {
        System.out.println("Resource " + this.name + " amount changed to " + amount);
        this.amount = amount;
    }
}
