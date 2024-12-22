package engine.util;

import org.joml.Vector2f;

/**
 * A utility class for mathematical operations, primarily focused on vector manipulation
 * and numerical comparison with precision handling.
 */
public class JMath {

    /**
     * Rotates a given vector around a specified origin by a given angle.
     *
     * @param vec      the vector to rotate
     * @param angleDeg the angle of rotation in degrees
     * @param origin   the point around which the vector is rotated
     */
    public static void rotate(Vector2f vec, float angleDeg, Vector2f origin) {
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = (float) Math.cos(Math.toRadians(angleDeg));
        float sin = (float) Math.sin(Math.toRadians(angleDeg));

        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y * cos);

        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
    }

    /**
     * Compares two float values for approximate equality within a specified epsilon.
     *
     * @param x       the first float value
     * @param y       the second float value
     * @param epsilon the allowable margin of error
     * @return {@code true} if the values are approximately equal, {@code false} otherwise
     */
    public static boolean compare(float x, float y, float epsilon) {
        return Math.abs(x - y) <= epsilon * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    /**
     * Compares two 2D vectors for approximate equality within a specified epsilon.
     *
     * @param vec1    the first vector
     * @param vec2    the second vector
     * @param epsilon the allowable margin of error
     * @return {@code true} if the vectors are approximately equal, {@code false} otherwise
     */
    public static boolean compare(Vector2f vec1, Vector2f vec2, float epsilon) {
        return compare(vec1.x, vec2.x, epsilon) && compare(vec1.y, vec2.y, epsilon);
    }

    /**
     * Compares two float values for approximate equality using a very small default epsilon.
     *
     * @param x the first float value
     * @param y the second float value
     * @return {@code true} if the values are approximately equal, {@code false} otherwise
     */
    public static boolean compare(float x, float y) {
        return Math.abs(x - y) <= Float.MIN_VALUE * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    /**
     * Compares two 2D vectors for approximate equality using a very small default epsilon.
     *
     * @param vec1 the first vector
     * @param vec2 the second vector
     * @return {@code true} if the vectors are approximately equal, {@code false} otherwise
     */
    public static boolean compare(Vector2f vec1, Vector2f vec2) {
        return compare(vec1.x, vec2.x) && compare(vec1.y, vec2.y);
    }

    /**
     * Checks if a set of X and Y coordinates are inside of a rectangle.
     *
     * @param in         physics.Vector2f containing coordinates of point to check
     * @param rectX      X position of rectangle
     * @param rectY      Y position of rectangle
     * @param rectWidth  Width of rectangle
     * @param rectHeight Height of rectangle
     * @return Returns true if the point is inside the rectangle, otherwise returns false.
     */
    public static boolean inRect(Vector2f in, float rectX, float rectY, float rectWidth, float rectHeight) {
        return in.x >= rectX && in.x <= (rectX + rectWidth) && in.y >= rectY && in.y <= (rectY + rectHeight);
    }
}
