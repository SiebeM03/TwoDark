package TDA.scene.prefabs;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.gameObjects.Camera;

public class HomeCamera extends Camera {

    public HomeCamera() {
        super(0.0f, 100.0f);
        setPosition(0, 0);
    }

    @Override
    public float getMouseWorldX() {
        float currentX = Engine.mouse().getX() * 2.0f - 1.0f;
        return transformScreenToWorld(currentX, 0).x;
    }

    @Override
    public float getMouseWorldY() {
        float currentY = -(Engine.mouse().getY() * 2.0f - 1.0f);
        return transformScreenToWorld(0, currentY).y;
    }

}
