package scenes;

import engine.ecs.*;
import engine.ecs.components.MouseControls;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Camera;
import engine.util.AssetPool;
import engine.util.GameDataDebug;
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
            if (gameObjects.size() > 0) {
                this.activeGameObject = gameObjects.get(0);
            }
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

        for (GameObject g : gameObjects) {
            SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
            // Check if the game object has a sprite renderer
            if (spr != null) {
                // Check if the sprite renderer has a texture
                if (spr.getTexture() != null) {
                    // Set the texture to the texture that is found in the asset pool
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        devSceneGameObject.update(dt);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
    }

    @Override
    public void render() {
        this.renderer.render();
    }

    @Override
    public void imgui() {
        GameDataDebug.imgui();
        // GameManager.get().getResourceManager().imgui();
    }
}
