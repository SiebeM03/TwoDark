package scenes;

import engine.ecs.*;
import engine.graphics.Camera;
import engine.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class DevScene extends Scene {

    private SpriteSheet sprites;

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpriteSheet("assets/images/testSprites.png");

        GameObject obj3 = new GameObject("Object 3",
                new Transform(new Vector2f(150, 120), new Vector2f(128, 128)),
                -1);
        obj3.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        obj3.getComponent(SpriteRenderer.class).setColor(new Vector4f(0, 1, 0, 0.7f));
        this.addGameObjectToScene(obj3);

        GameObject obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(100, 100), new Vector2f(128, 128)),
                2);
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        obj1.getComponent(SpriteRenderer.class).setColor(new Vector4f(1, 0, 0, 0.8f));
        this.addGameObjectToScene(obj1);


        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(400, 100), new Vector2f(128, 128)),
                0
        );
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(3)));
        this.addGameObjectToScene(obj2);
    }

    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/testSprites.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/testSprites.png"), 64, 64, 4, 0));
    }


    @Override
    public void update(float dt) {

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
