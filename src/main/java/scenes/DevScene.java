package scenes;

import engine.ecs.GameObject;
import engine.ecs.Sprite;
import engine.ecs.SpriteSheet;
import engine.ecs.Transform;
import engine.ecs.components.MouseControls;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Camera;
import engine.listeners.MouseListener;
import engine.ui.Text;
import engine.ui.UIElement;
import engine.ui.fonts.Font;
import engine.util.AssetPool;
import engine.util.Layer;
import engine.util.Settings;
import org.joml.Vector2f;
import org.joml.Vector4f;
import testGame.resources.types.Metal;
import testGame.resources.types.Stone;
import testGame.resources.types.Wood;
import testGame.tools.types.Axe;
import testGame.tools.types.Pickaxe;

public class DevScene extends Scene {

    GameObject devSceneGameObject = new GameObject("DevScene", new Transform(new Vector2f(), new Vector2f()), Layer.NO_RENDER);

    Text test;

    @Override
    public void init() {
        devSceneGameObject.addComponent(new MouseControls());
        this.camera = new Camera(new Vector2f());

        loadResources();

        GameObject axeGo = new GameObject("Axe", new Transform(new Vector2f(Settings.PROJECTION_WIDTH - 175, Settings.PROJECTION_HEIGHT - 105), new Vector2f(60, 60)), Layer.NO_INTERACTION);
        axeGo.addComponent(new SpriteRenderer()
                                   .setSprite(new Sprite()
                                                      .setTexture(AssetPool.getTexture("src/assets/images/seperateImages/axe.png"))
                                   ));
        addGameObjectToScene(axeGo);

        GameObject pickaxeGo = new GameObject("Pickaxe", new Transform(new Vector2f(Settings.PROJECTION_WIDTH - 100, Settings.PROJECTION_HEIGHT - 100), new Vector2f(50, 50)), Layer.NO_INTERACTION);
        pickaxeGo.addComponent(new SpriteRenderer()
                                       .setSprite(new Sprite()
                                                          .setTexture(AssetPool.getTexture("src/assets/images/seperateImages/pickaxe.png"))
                                       ));
        addGameObjectToScene(pickaxeGo);

        if (levelLoaded) {
            if (gameObjects.size() > 0) {
                this.activeGameObject = gameObjects.get(0);
            }
            return;
        }


        // Create resource game objects
        GameObject treeGo = new GameObject("Tree", new Transform(new Vector2f((float) Settings.PROJECTION_WIDTH / 2 - 400, 100), new Vector2f(200, 200)), Layer.INTERACTION);
        treeGo.addComponent(new SpriteRenderer()
                                    .setSprite(new Sprite()
                                                       .setTexture(AssetPool.getTexture("src/assets/images/seperateImages/tree2.png"))
                                    ));
        treeGo.addComponent(new Wood());
        treeGo.addComponent(new UIElement());
        addGameObjectToScene(treeGo);

        GameObject stoneGo = new GameObject("Stone", new Transform(new Vector2f((float) Settings.PROJECTION_WIDTH / 2 - 100, 100), new Vector2f(200, 200)), Layer.INTERACTION);
        stoneGo.addComponent(new SpriteRenderer()
                                     .setSprite(new Sprite()
                                                        .setTexture(AssetPool.getTexture("src/assets/images/seperateImages/stone2.png"))
                                     ));
        stoneGo.addComponent(new Stone());
        stoneGo.addComponent(new UIElement());
        addGameObjectToScene(stoneGo);

        GameObject metalGo = new GameObject("Metal", new Transform(new Vector2f((float) Settings.PROJECTION_WIDTH / 2 + 200, 100), new Vector2f(200, 200)), Layer.INTERACTION);
        metalGo.addComponent(new SpriteRenderer()
                                     .setSprite(new Sprite()
                                                        .setTexture(AssetPool.getTexture("src/assets/images/seperateImages/metal2.png"))
                                     ));
        metalGo.addComponent(new Metal());
        metalGo.addComponent(new UIElement());
        addGameObjectToScene(metalGo);

        GameObject pickaxeGoBg = new GameObject("PickaxeBg", new Transform(new Vector2f(Settings.PROJECTION_WIDTH - 105, Settings.PROJECTION_HEIGHT - 105), new Vector2f(60, 60)), Layer.INTERACTION);
        pickaxeGoBg.addComponent(new SpriteRenderer()
                                         .setSprite(new Sprite()
                                                            .setTexture(AssetPool.getTexture("src/assets/images/tile.png"))
                                         ));
        pickaxeGoBg.addComponent(new Pickaxe());
        pickaxeGoBg.addComponent(new UIElement());
        addGameObjectToScene(pickaxeGoBg);

        GameObject axeGoBg = new GameObject("AxeBg", new Transform(new Vector2f(Settings.PROJECTION_WIDTH - 175, Settings.PROJECTION_HEIGHT - 105), new Vector2f(60, 60)), Layer.INTERACTION);
        axeGoBg.addComponent(new SpriteRenderer()
                                     .setSprite(new Sprite()
                                                        .setTexture(AssetPool.getTexture("src/assets/images/tile.png"))
                                     ));
        axeGoBg.addComponent(new Axe());
        axeGoBg.addComponent(new UIElement());
        addGameObjectToScene(axeGoBg);
    }

    public void loadResources() {
        AssetPool.getShader("src/assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("src/assets/images/spritesheets/resourceObjectSprites.png",
                new SpriteSheet(AssetPool.getTexture("src/assets/images/spritesheets/resourceObjectSprites.png"),
                        200, 200, 3, 0));

        AssetPool.getTexture("src/assets/images/seperateImages/stone2.png");
        AssetPool.getTexture("src/assets/images/seperateImages/tree2.png");
        AssetPool.getTexture("src/assets/images/seperateImages/metal2.png");
        AssetPool.getTexture("src/assets/images/tile.png");
        AssetPool.getTexture("src/assets/images/seperateImages/pickaxe.png");
        AssetPool.getTexture("src/assets/images/seperateImages/axe.png");

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
    public void update() {
        devSceneGameObject.update();

//        test.setPosition(new Vector2f(MouseListener.getScreenX(), MouseListener.getScreenY()));
    }

    @Override
    public void imgui() {

    }
}
