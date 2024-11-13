package scenes;

import engine.ecs.*;
import engine.graphics.Camera;
import engine.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class DevScene extends Scene {

    public DevScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());

        SpriteSheet sprites = AssetPool.getSpriteSheet("assets/images/testSprites.png");

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(128, 128)));
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

    @Override
    public void update(float dt) {
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
