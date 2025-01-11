package woareXengine.rendering.debug;

import org.joml.Vector2f;
import org.joml.Vector3f;
import woareXengine.util.Color;

public class Line2D {
    private Vector2f from;
    private Vector2f to;
    private Vector3f color;
    /**
     * The lifetime of the Line2D defined in frames
     */
    private int lifetime;

    public Line2D(Vector2f from, Vector2f to, Color color, int lifetime) {
        this.from = from;
        this.to = to;
        this.color = color.toVec3();
        this.lifetime = lifetime;
    }

    public int beginFrame() {
        this.lifetime--;
        return this.lifetime;
    }

    public Vector2f getFrom() {
        return from;
    }

    public Vector2f getTo() {
        return to;
    }

    public Vector3f getColor() {
        return color;
    }

    public int getLifetime() {
        return lifetime;
    }
}
