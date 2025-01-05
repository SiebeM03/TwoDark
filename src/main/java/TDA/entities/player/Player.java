package TDA.entities.player;

import TDA.entities.player.controller.Deadzone;
import TDA.entities.player.controller.Movement;
import TDA.entities.player.controller.collisions.GameCollisionBox;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;
import woareXengine.util.collisions.CollisionBox;

import static TDA.entities.player.PlayerConfigs.DEADZONE;


public class Player {

    private Camera camera;
    public Transform transform;
    public Movement movement;

    public Quad renderObject;

    public CollisionBox collisionBox;

    public Player(Camera camera) {
        this.camera = camera;
        this.transform = new Transform(
                new Vector2f(1000, 500),
                new Vector2f(200, 200));
        this.movement = new Movement(this);
        this.collisionBox = new GameCollisionBox(transform, (float) 350 / 600, (float) 110 / 600, (float) 220 / 600, (float) 230 / 600);

        this.renderObject = new Quad(transform.getWidth(), transform.getHeight(), transform.getPosition(), Layer.FOREGROUND);
        this.renderObject.texture = Assets.getTexture("src/assets/images/character.png");

        Engine.window().addSizeChangeListener((width, height) -> {
            this.camera.setPosition(
                    transform.getX() + (transform.getWidth() / 2) - (width / 2f),
                    transform.getY() + (transform.getHeight() / 2) - (height / 2f));
        });

        this.camera.setPosition(
                transform.getX() + (transform.getWidth() / 2) - (Engine.window().getScreenCoordWidth() / 2f),
                transform.getY() + (transform.getHeight() / 2) - (Engine.window().getScreenCoordHeight() / 2f));
    }

    public void update() {
        this.movement.update(Engine.getDelta());

        this.renderObject.transform.setPosition(this.transform.getPosition());
        updateCameraPosition();
    }

    private void updateCameraPosition() {
        if (movement.isJumping) return;

        Vector2f playerCenter = new Vector2f(transform.getX() + (transform.getWidth() / 2), transform.getY() + (transform.getHeight() / 2));
        boolean[] isWithin = DEADZONE.isWithin(playerCenter);
        if (!isWithin[0]) {
            this.camera.setPosition(camera.getPosition().add(movement.addX, 0, new Vector2f()));
        }
        if (!isWithin[1]) {
            this.camera.setPosition(camera.getPosition().add(0, movement.addY, new Vector2f()));
        }
    }
}
