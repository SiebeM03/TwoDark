package scenes;

import engine.ecs.*;
import engine.ecs.components.MouseControls;
import engine.ecs.components.RigidBody;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Camera;
import engine.util.AssetPool;
import game.GameManager;
import game.resources.Resource;
import game.resources.ResourceManager;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;

public class DevScene extends Scene {

    private SpriteSheet sprites;
    MouseControls mouseControls = new MouseControls();

    ResourceManager resourceManager;

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());
        this.sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/resourceObjectSprites.png");
        this.resourceManager = GameManager.get().getResourceManager();
        resourceManager.addResourceGameObjectsToScene();

        if (levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        GameObject obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(100, 100), new Vector2f(128, 128)),
                2);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        Sprite obj1Sprite = new Sprite();
        obj1Sprite.setTexture(AssetPool.getTexture("assets/images/tile.png"));
        obj1SpriteRenderer.setSprite(obj1Sprite);
        obj1.addComponent(obj1SpriteRenderer);
        obj1.addComponent(new RigidBody());
        // this.addGameObjectToScene(obj1);
        // this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(400, 100), new Vector2f(128, 128)),
                0
        );
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/tile.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        // this.addGameObjectToScene(obj2);
    }

    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/resourceObjectSprites.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/spritesheets/resourceObjectSprites.png"),
                        200, 200, 3, 0));

        AssetPool.getTexture("assets/images/tile.png");
        AssetPool.getTexture("assets/images/seperateImages/tree2.png");
        AssetPool.getTexture("assets/images/seperateImages/stone2.png");
        AssetPool.getTexture("assets/images/seperateImages/metal2.png");
    }

    @Override
    public void update(float dt) {
        mouseControls.update(dt);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        if (resourceManager == null) return;
        resourceManager.imgui();
    }
}
