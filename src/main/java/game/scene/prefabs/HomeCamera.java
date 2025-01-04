package game.scene.prefabs;

import org.joml.Matrix4f;
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
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);

        Matrix4f viewProjection = new Matrix4f();
        Matrix4f inverseView = new Matrix4f();
        Matrix4f inverseProjection = new Matrix4f();
        getViewMatrix().invert(inverseView);
        getProjectionMatrix().invert(inverseProjection);
        inverseView.mul(inverseProjection, viewProjection);
        tmp.mul(viewProjection);
        currentX = tmp.x;

        return currentX;
    }

    @Override
    public float getMouseWorldY() {
        float currentY = -(Engine.mouse().getY() * 2.0f - 1.0f);
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);

        Matrix4f viewProjection = new Matrix4f();
        Matrix4f inverseView = new Matrix4f();
        Matrix4f inverseProjection = new Matrix4f();
        getViewMatrix().invert(inverseView);
        getProjectionMatrix().invert(inverseProjection);
        inverseView.mul(inverseProjection, viewProjection);
        tmp.mul(viewProjection);
        currentY = tmp.y;

        return currentY;
    }
}
