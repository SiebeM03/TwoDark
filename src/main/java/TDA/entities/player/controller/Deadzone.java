package TDA.entities.player.controller;

import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.rendering.debug.DebugDraw;

public class Deadzone {
    private final float relativeWidth;
    private final float relativeHeight;

    public Deadzone(float relativeWidth, float relativeHeight) {
        this.relativeWidth = relativeWidth;
        this.relativeHeight = relativeHeight;
    }

    public boolean[] isWithin(Vector2f coordinate) {
        Vector2f actualDimensions = calculateActualDimensions();
        Camera camera = GameManager.currentScene.camera;

        Vector2f center = new Vector2f(
                (camera.getMaxX() + camera.getMinX()) / 2,
                (camera.getMaxY() + camera.getMinY()) / 2
        );

        if (Engine.instance().debugging) {
            DebugDraw.addBox2D(center, actualDimensions, 0);
        }

        boolean[] result = new boolean[2];
        result[0] = coordinate.x > center.x - actualDimensions.x / 2 &&
                            coordinate.x < center.x + actualDimensions.x / 2;
        result[1] = coordinate.y > center.y - actualDimensions.y / 2 &&
                            coordinate.y < center.y + actualDimensions.y / 2;
        return result;
    }

    private Vector2f calculateActualDimensions() {
        return new Vector2f(
                Engine.window().getPixelWidth() * relativeWidth,
                Engine.window().getPixelHeight() * relativeHeight
        );
    }

}
