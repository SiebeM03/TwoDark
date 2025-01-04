package old.guis.mainView;

import old.engine.ecs.GameObject;
import old.engine.ecs.Sprite;
import old.engine.ecs.Transform;
import old.engine.ecs.components.SpriteRenderer;
import old.engine.graphics.Window;
import old.engine.ui.EventConsumer;
import old.engine.util.AssetPool;
import old.engine.util.Layer;
import org.joml.Vector2f;
import old.testGame.resources.Resource;
import old.testGame.resources.ResourceManager;


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
