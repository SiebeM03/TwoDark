package scenes;

import engine.ecs.*;
import engine.graphics.Camera;
import engine.util.AssetPool;
import imgui.ImGui;
import org.joml.Vector2f;

public class DevScene extends Scene {

    private SpriteSheet sprites;

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());
        if (levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        sprites = AssetPool.getSpriteSheet("assets/images/testSprites.png");

        GameObject obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(100, 100), new Vector2f(128, 128)),
                2);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        Sprite obj1Sprite = new Sprite();
        obj1Sprite.setTexture(AssetPool.getTexture("assets/images/tile.png"));
        obj1SpriteRenderer.setSprite(obj1Sprite);
        obj1.addComponent(obj1SpriteRenderer);
        obj1.addComponent(new RigidBody());
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(400, 100), new Vector2f(128, 128)),
                0
        );
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/tile.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);
    }

    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.getTexture("assets/images/tile.png");

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

    @Override
    public void imgui() {
        ImGui.begin("Test window");
        ImGui.text("Some random text");
        ImGui.end();
    }
}
