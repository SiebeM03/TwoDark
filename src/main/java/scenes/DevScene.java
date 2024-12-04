package scenes;

import engine.ecs.*;
import engine.ecs.components.MouseControls;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Camera;
import engine.ui.MouseEventConsumer;
import engine.ui.UIElement;
import engine.util.AssetPool;
import org.joml.Vector2f;
import testGame.Resource;

public class DevScene extends Scene {

    private SpriteSheet sprites;
    GameObject devSceneGameObject = new GameObject("DevScene", new Transform(new Vector2f(), new Vector2f()), 0);

    @Override
    public void init() {
        devSceneGameObject.addComponent(new MouseControls());

        loadResources();
        this.camera = new Camera(new Vector2f());
        this.sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/resourceObjectSprites.png");

        if (levelLoaded) {
            if (gameObjects.size() > 0) {
                this.activeGameObject = gameObjects.get(0);
            }
            return;
        }
        System.out.println("Creating new game objects");

        // Create resource game objects
        GameObject treeGo = new GameObject("Tree", new Transform(new Vector2f(100, 100), new Vector2f(200, 200)), 1);
        treeGo.addComponent(new SpriteRenderer()
                                    .setSprite(new Sprite()
                                                       .setTexture(AssetPool.getTexture("assets/images/seperateImages/tree2.png"))
                                                       .setWidth(200)
                                                       .setHeight(200)
                                    ));
        Resource wood = new Resource("Wood", 100);
        treeGo.addComponent(wood);
        treeGo.addComponent(new UIElement());
        addGameObjectToScene(treeGo);

        GameObject stoneGo = new GameObject("Stone", new Transform(new Vector2f(400, 100), new Vector2f(500, 500)), 1);
        stoneGo.addComponent(new SpriteRenderer()
                                     .setSprite(new Sprite()
                                                        .setTexture(AssetPool.getTexture("assets/images/seperateImages/stone2.png"))
                                                        .setWidth(200)
                                                        .setHeight(200)
                                     ));
        Resource stone = new Resource("Stone", 100);
        stoneGo.addComponent(stone);
        stoneGo.addComponent(new UIElement());
        addGameObjectToScene(stoneGo);

        GameObject metalGo = new GameObject("Metal", new Transform(new Vector2f(700, 100), new Vector2f(200, 200)), 1);
        metalGo.addComponent(new SpriteRenderer()
                                     .setSprite(new Sprite()
                                                        .setTexture(AssetPool.getTexture("assets/images/seperateImages/metal2.png"))
                                                        .setWidth(200)
                                                        .setHeight(200)
                                     ));
        Resource metal = new Resource("Metal", 100);
        metalGo.addComponent(metal);
        metalGo.addComponent(new UIElement());
        addGameObjectToScene(metalGo);
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

    }
}
