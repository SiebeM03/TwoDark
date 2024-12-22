package guis.mainView;

import engine.ecs.GameObject;
import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Window;
import engine.ui.EventConsumer;
import engine.util.AssetPool;
import engine.util.Layer;
import org.joml.Vector2f;
import testGame.resources.Resource;
import testGame.resources.ResourceManager;


public class ResourceObject extends EventConsumer {
    private static final int SIZE = 200;

    private Resource resource;
    private GameObject resourceObject;

    public ResourceObject(Class<? extends Resource> clazz, float x, float y) {
        resource = ResourceManager.getResource(clazz);

        resourceObject = new GameObject(clazz.getSimpleName(),
                new Transform(new Vector2f(x, y), new Vector2f(SIZE, SIZE)),
                Layer.INTERACTION);
        resourceObject.addComponent(new SpriteRenderer().setSprite(
                new Sprite().setTexture(AssetPool.getTexture(resource.getTexturePath()))
        ));
        resourceObject.eventConsumer = this;
        this.gameObject = resourceObject;

        setHasCooldownAnimation();
        setClickDelay(resource.getClickDelay());

        Window.getScene().addGameObjectToScene(resourceObject);
    }


    @Override
    protected void onClick() {
        if (!canClick()) return;
        resource.harvest();
        resetClickDelayTimer();
    }

    @Override
    protected void onHover() {

    }

    @Override
    protected void onEnter() {
        gameObject.getComponent(SpriteRenderer.class).setColor(this.hoverColor);
    }

    @Override
    protected void onLeave() {
        gameObject.getComponent(SpriteRenderer.class).setColor(this.defaultColor);
    }
}
