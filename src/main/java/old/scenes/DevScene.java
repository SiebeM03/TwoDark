package old.scenes;

import old.engine.ecs.GameObject;
import old.engine.ecs.SpriteSheet;
import old.engine.ecs.Transform;
import old.engine.ecs.components.MouseControls;
import old.engine.ecs.components.SpriteRenderer;
import old.engine.graphics.Camera;
import old.engine.ui.fonts.FontLoader;
import old.engine.util.AssetPool;
import old.engine.util.Layer;
import old.engine.util.Settings;
import old.guis.mainView.ResourceObject;
import old.guis.topBar.ResourceCounterUI;
import old.guis.topBar.TopBar;
import old.testGame.resources.types.Metal;
import old.testGame.resources.types.Stone;
import old.testGame.resources.types.Wood;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;


public class DevScene extends Scene {

    GameObject devSceneGameObject = new GameObject("DevScene", new Transform(new Vector2f(), new Vector2f()), Layer.NO_RENDER);
    List<ResourceCounterUI> resourceCounters = new ArrayList<>();

    @Override
    public void init() {
        devSceneGameObject.addComponent(new MouseControls());
        this.camera = new Camera(new Vector2f());
        FontLoader.loadFonts();
        loadResources();

        new ResourceObject(Wood.class, (float) Settings.PROJECTION_WIDTH / 2 - 400, 100);
        new ResourceObject(Stone.class, (float) Settings.PROJECTION_WIDTH / 2 - 100, 100);
        new ResourceObject(Metal.class, (float) Settings.PROJECTION_WIDTH / 2 + 200, 100);

        addUIComponent(new TopBar());

        if (levelLoaded) {
            if (gameObjects.size() > 0) {
                this.activeGameObject = gameObjects.get(0);
            }
            return;
        }
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


//        for (ResourceCounterUI ui : resourceCounters) {
//            ui.update();
//        }


//        test.setPosition(new Vector2f(MouseListener.getScreenX(), MouseListener.getScreenY()));
    }

    @Override
    public void imgui() {

    }
}
