package testGame.resources;

import engine.ecs.components.SpriteRenderer;
import engine.ui.MouseEventConsumer;
import org.joml.Vector4f;

public abstract class Resource extends MouseEventConsumer {
    private String name;
    private float amount;
    private float amountPerClick = 1;

    protected Resource() {
        setHasCooldownAnimation();
        ResourceManager.addResource(this);
    }

    @Override
    public void onClick() {
        if (!canClick()) return;
        harvest();
        resetClickDelayTimer();
    }

    @Override
    public void onHover() {
    }

    @Override
    public void onEnter() {
        gameObject.getComponent(SpriteRenderer.class).setColor(new Vector4f(0.8f, 0.8f, 0.8f, 1));
    }

    @Override
    public void onLeave() {
        gameObject.getComponent(SpriteRenderer.class).setColor(new Vector4f(1, 1, 1, 1));
    }

    private void harvest() {
        this.setAmount(this.amount() + this.amountPerClick());

        System.out.println(this.name() + " has " + this.amount() + " resources");
    }

    public String name() {
        return name;
    }

    public float amount() {
        return amount;
    }

    public float amountPerClick() {
        return amountPerClick;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setAmountPerClick(float amountPerClick) {
        this.amountPerClick = amountPerClick;
    }
}
