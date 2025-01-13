package TDA.entities.player;

import TDA.entities.ecs.Entity;
import TDA.entities.ecs.components.*;
import TDA.entities.inventory.ItemStack;
import TDA.entities.resources.types.Stone;
import TDA.entities.resources.types.Tree;
import org.joml.Vector2f;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Player {
    public static Inventory inventory = new Inventory(40);
    public static Hotbar hotbar = new Hotbar();

    public static Entity createEntity(Camera camera) {
        Entity player = new Entity(new Transform(new Vector2f(1000, 500), new Vector2f(200, 200)))
                                .addComponent(new CharacterController())
                                .addComponent(new CameraFollow(camera))
                                .addComponent(new QuadComponent(Assets.getTexture("src/assets/images/character.png"), Layer.FOREGROUND))
                                .addComponent(new Collider((float) 350 / 600, (float) 110 / 600, (float) 220 / 600, (float) 230 / 600))
                                .addComponent(Player.inventory)
                                .addComponent(new Harvester())
                                .addComponent(Player.hotbar);
        return player;
    }
}
