package scenes;

import engine.ecs.*;
import engine.graphics.Camera;
import engine.util.AssetPool;
import org.joml.Vector2f;

public class DevScene extends Scene {

    private GameObject obj1;
    private SpriteSheet sprites;

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpriteSheet("assets/images/testSprites.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(128, 128)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(128, 128)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(3)));
        this.addGameObjectToScene(obj2);
    }

    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/testSprites.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/testSprites.png"), 64, 64, 4, 0));
    }


    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;

    @Override
    public void update(float dt) {
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if (spriteIndex > 3) {
                spriteIndex = 0;
            }
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
