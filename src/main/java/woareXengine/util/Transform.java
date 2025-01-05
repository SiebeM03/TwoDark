package woareXengine.util;

import org.joml.Vector2f;

public class Transform {
    private Vector2f position;
    private Vector2f dimensions;

    /**
     * Creates a new Transform with a position of (0, 0) and dimensions of (1, 1).
     */
    public Transform() {
        this(new Vector2f(0), new Vector2f(1));
    }

    /**
     * Creates a new Transform with the specified position and a dimensions of (1, 1).
     *
     * @param position the position of the Transform
     */
    public Transform(Vector2f position) {
        this(position, new Vector2f(1));
    }

    /**
     * Creates a new Transform with the specified position and dimensions.
     *
     * @param position   the position of the Transform
     * @param dimensions the dimensions of the Transform
     */
    public Transform(Vector2f position, Vector2f dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2f getDimensions() {
        return dimensions;
    }

    public float getWidth() {
        return dimensions.x;
    }

    public float getHeight() {
        return dimensions.y;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public void setDimensions(float width, float height) {
        this.dimensions.set(width, height);
    }

    public void setX(float x) {
        this.position.x = x;
    }

    public void setY(float y) {
        this.position.y = y;
    }

    public void setDimensions(Vector2f dimensions) {
        this.dimensions = dimensions;
    }

    public void setWidth(float width) {
        this.dimensions.x = width;
    }

    public void setHeight(float height) {
        this.dimensions.y = height;
    }

    public void addX(float x) {
        this.position.x += x;
    }

    public void addY(float y) {
        this.position.y += y;
    }
}
