package woareXengine.util.collisions;

import org.joml.Vector2f;
import org.joml.Vector3f;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Transform;

public abstract class CollisionBox {
    protected final float topOffsetPercentage;
    protected final float bottomOffsetPercentage;
    protected final float leftOffsetPercentage;
    protected final float rightOffsetPercentage;
    protected final Transform parentTransform;

    protected boolean isColliding = false;

    protected Vector2f center;
    protected Vector2f dimensions;

    public CollisionBox(Transform parentTransform) {
        this(parentTransform, 0, 0, 0, 0);
    }

    public CollisionBox(Transform parentTransform, float topOffsetPercentage, float bottomOffsetPercentage, float leftOffsetPercentage, float rightOffsetPercentage) {
        this.parentTransform = parentTransform;

        this.topOffsetPercentage = topOffsetPercentage;
        this.bottomOffsetPercentage = bottomOffsetPercentage;
        this.leftOffsetPercentage = leftOffsetPercentage;
        this.rightOffsetPercentage = rightOffsetPercentage;

        updateTransform();
    }

    public void drawBox() {
        updateTransform();

        DebugDraw.addBox2D(center, dimensions, 0, new Vector3f((isColliding ? 1 : 0), (isColliding ? 0 : 1), 0));
    }

    public boolean willXCollide(float newX) {
        float lastX = parentTransform.getX();
        parentTransform.setX(newX);
        boolean[] hasCollisions = hasCollisions();
        parentTransform.setX(lastX);
        return hasCollisions[0];
    }

    public boolean willYCollide(float newY) {
        float lastY = parentTransform.getY();
        parentTransform.setY(newY);
        boolean[] hasCollisions = hasCollisions();
        parentTransform.setY(lastY);
        return hasCollisions[1];
    }


    public abstract boolean[] hasCollisions();

    boolean isColliding(CollisionBox other) {
        float thisLeft = parentTransform.getX() + parentTransform.getWidth() * leftOffsetPercentage;
        float thisRight = parentTransform.getX() + parentTransform.getWidth() * (1 - rightOffsetPercentage);
        float thisTop = parentTransform.getY() + parentTransform.getHeight() * (1 - topOffsetPercentage);
        float thisBottom = parentTransform.getY() + parentTransform.getHeight() * bottomOffsetPercentage;

        float otherLeft = other.parentTransform.getX() + other.parentTransform.getWidth() * other.leftOffsetPercentage;
        float otherRight = other.parentTransform.getX() + other.parentTransform.getWidth() * (1 - other.rightOffsetPercentage);
        float otherTop = other.parentTransform.getY() + other.parentTransform.getHeight() * (1 - other.topOffsetPercentage);
        float otherBottom = other.parentTransform.getY() + other.parentTransform.getHeight() * other.bottomOffsetPercentage;

        return thisLeft < otherRight
                       && thisRight > otherLeft
                       && thisTop > otherBottom
                       && thisBottom < otherTop;
    }

    protected void updateTransform() {
        this.dimensions = getDimensions();
        this.center = getCenter();
    }

    protected Vector2f getCenter() {
        return new Vector2f(
                parentTransform.getX() + parentTransform.getWidth() * leftOffsetPercentage + dimensions.x / 2,
                parentTransform.getY() + parentTransform.getHeight() * bottomOffsetPercentage + dimensions.y / 2
        );
    }

    protected Vector2f getDimensions() {
        return new Vector2f(
                parentTransform.getWidth() * (1 - (leftOffsetPercentage + rightOffsetPercentage)),
                parentTransform.getHeight() * (1 - (topOffsetPercentage + bottomOffsetPercentage))
        );
    }


    protected Transform getTransform() {
        this.center = getCenter();
        this.dimensions = getDimensions();

        return new Transform(
                new Vector2f(parentTransform.getX() + dimensions.x * leftOffsetPercentage, parentTransform.getY() + parentTransform.getHeight() * bottomOffsetPercentage),
                getDimensions()
        );
    }

    public abstract boolean isOnScreen();


}
