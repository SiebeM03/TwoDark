package scenes;

import engine.ecs.*;
import engine.ecs.components.MouseControls;
import engine.graphics.Camera;
import engine.util.AssetPool;
import game.GameManager;
import game.resources.ResourceManager;
import org.joml.Vector2f;

public class DevScene extends Scene {

    private SpriteSheet sprites;
    GameObject devSceneGameObject = new GameObject("DevScene", new Transform(new Vector2f(), new Vector2f()), 0);

    ResourceManager resourceManager;

    @Override
    public void init() {
        devSceneGameObject.addComponent(new MouseControls());

        loadResources();
        this.camera = new Camera(new Vector2f());
        this.sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/resourceObjectSprites.png");
        this.resourceManager = GameManager.get().getResourceManager();

        if (levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        this.resourceManager.addResourceGameObjectsToScene();
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
        devSceneGameObject.update(dt);

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
