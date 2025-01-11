package TDA.scene.prefabs;

import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Color;

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

    public static class Deadzone {
        private final float relativeWidth;
        private final float relativeHeight;
        public Camera camera;


        public Deadzone(float relativeWidth, float relativeHeight) {
            this.relativeWidth = relativeWidth;
            this.relativeHeight = relativeHeight;
        }

        public boolean[] isWithin(Vector2f coordinate) {
            Vector2f actualDimensions = calculateActualDimensions();

            Vector2f center = new Vector2f(
                    (camera.getMaxX() + camera.getMinX()) / 2,
                    (camera.getMaxY() + camera.getMinY()) / 2
            );

            if (Engine.instance().debugging) {
                DebugDraw.addBox2D(center, actualDimensions, 0, Color.RED);
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
}