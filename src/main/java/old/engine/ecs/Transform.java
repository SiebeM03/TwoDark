package old.engine.ecs;

import org.joml.Vector2f;

public class Transform {

    public Vector2f position;
    public Vector2f scale;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setX(float x) {
        this.position.set(x, this.position.y());
    }

    public void setY(float y) {
        this.position.set(this.position.x(), y);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void addX(float x) {
        this.position.add(x, 0);
    }

    public void addY(float y) {
        this.position.add(0, y);
    }

    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Transform)) return false;

        Transform t = (Transform) o;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }
}
