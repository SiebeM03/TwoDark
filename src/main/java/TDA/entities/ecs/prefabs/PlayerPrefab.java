package TDA.entities.ecs.prefabs;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.*;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Logger;
import woareXengine.util.Transform;

public class PlayerPrefab {
    private static Entity player = null;
    private static Hotbar hotbar = new Hotbar();
    private static Inventory inventory = new Inventory(40);

    public static Entity getPlayer() {
        if (player == null) {
            player = createPlayer(0, 0);
        }
        return player;
    }

    public static Entity createPlayer(float x, float y) {
        if (GameManager.currentScene == null) {
            Logger.error("No scene to create player in!");
            return null;
        }
        return createPlayer(x, y, GameManager.currentScene.camera);
    }

    public static Entity createPlayer(float x, float y, Camera camera) {
        if (player != null) {
            Logger.error("Player already exists!");
            return null;
        }

        player = new Entity(new Transform(new Vector2f(x, y), new Vector2f(200, 200)))
                         .addComponent(new QuadComponent(Assets.getTexture("src/assets/images/character.png"), Layer.FOREGROUND))
                         .addComponent(new Collider((float) 350 / 600, (float) 110 / 600, (float) 220 / 600, (float) 230 / 600))
                         .addComponent(inventory)
                         .addComponent(hotbar)
                         .addComponent(new CharacterController())
                         .addComponent(new CameraFollow(camera))
                         .addComponent(new PlayerActions());

        return player;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static Hotbar getHotbar() {
        return hotbar;
    }
}
