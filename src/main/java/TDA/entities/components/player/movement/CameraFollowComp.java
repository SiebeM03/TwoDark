package TDA.entities.components.player.movement;

import TDA.entities.main.Component;
import TDA.scene.prefabs.HomeCamera.Deadzone;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.gameObjects.Camera;


public class CameraFollowComp extends Component {
    private final Camera camera;
    private final Deadzone deadzone;

    public CameraFollowComp(Camera camera) {
        this.camera = camera;
        this.deadzone = new Deadzone(0.2f, 0.4f);
        this.deadzone.camera = this.camera;
    }

    @Override
    public void init() {
        this.camera.setPosition(entity.transform.getCenter().sub(
                Engine.window().getScreenCoordWidth() / 2f,
                Engine.window().getScreenCoordHeight() / 2f,
                new Vector2f()));
        centerEntityOnCamera(Engine.window().getScreenCoordWidth(), Engine.window().getScreenCoordHeight());


        Engine.window().addSizeChangeListener(this::centerEntityOnCamera);
    }

    @Override
    public void update() {
        if (!entity.getComponent(CharacterControllerComp.class).isGrounded()) return;

        boolean[] isWithin = deadzone.isWithin(entity.transform.getCenter());
        if (!isWithin[0]) {
            camera.setPosition(camera.getPosition().add(entity.getComponent(CharacterControllerComp.class).addX, 0));
        }
        if (!isWithin[1]) {
            camera.setPosition(camera.getPosition().add(0, entity.getComponent(CharacterControllerComp.class).addY));
        }
    }

    private void centerEntityOnCamera(int screenWidth, int screenHeight) {
        this.camera.setPosition(
                entity.transform.getCenter().sub(screenWidth / 2f, screenHeight / 2f, new Vector2f())
        );
    }
}
