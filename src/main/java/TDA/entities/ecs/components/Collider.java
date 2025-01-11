
package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Transform;

public class Collider extends Component {
    protected final float topOffsetPercentage;
    protected final float bottomOffsetPercentage;
    protected final float leftOffsetPercentage;
    protected final float rightOffsetPercentage;

    private Transform boxTransform;

    private Transform currentTransform;
    private Transform lastTransform;

    public Collider() {
        this(0, 0, 0, 0);
    }

    public Collider(float topOffsetPercentage, float bottomOffsetPercentage, float leftOffsetPercentage, float rightOffsetPercentage) {
        this.topOffsetPercentage = topOffsetPercentage;
        this.bottomOffsetPercentage = bottomOffsetPercentage;
        this.leftOffsetPercentage = leftOffsetPercentage;
        this.rightOffsetPercentage = rightOffsetPercentage;
    }

    @Override
    public void init() {
        this.boxTransform = new Transform();

        this.currentTransform = entity.transform;

        updateTransform();
    }

    @Override
    public void update() {
        updateTransform();
        DebugDraw.addBox2D(boxTransform.getCenter(), boxTransform.getDimensions());
    }

    private void updateTransform() {
        this.boxTransform.setDimensions(
                currentTransform.getWidth() * (1 - leftOffsetPercentage - rightOffsetPercentage),
                currentTransform.getHeight() * (1 - topOffsetPercentage - bottomOffsetPercentage)
        );
        this.boxTransform.setPosition(
                currentTransform.getX() + currentTransform.getWidth() * leftOffsetPercentage,
                currentTransform.getY() + currentTransform.getHeight() * bottomOffsetPercentage
        );
    }

    public float getXCollisionAdjustment(float newX) {
        Transform testTransform = entity.transform.copy();
        testTransform.setX(newX);

        for (Entity other : GameManager.currentScene.getEntitiesWithComponents(Collider.class)) {
            Collider otherCollider = other.getComponent(Collider.class);
            if (otherCollider == this) continue;

            if (isColliding(testTransform, otherCollider)) {
                if (newX > otherCollider.entity.transform.getX()) {
                    // Collision on the right
                    return otherCollider.entity.transform.getX() + otherCollider.entity.transform.getWidth() * (1 - otherCollider.rightOffsetPercentage) - testTransform.getWidth() * leftOffsetPercentage;
                } else {
                    // Collision on the left
                    return otherCollider.entity.transform.getX() + otherCollider.entity.transform.getWidth() * otherCollider.leftOffsetPercentage - testTransform.getWidth() * (1 - rightOffsetPercentage);
                }
            }
        }
        return newX; // No collision, return original newX
    }

    public float getYCollisionAdjustment(float newY) {
        Transform testTransform = entity.transform.copy();
        testTransform.setY(newY);

        for (Collider other : GameManager.currentScene.entityManager.getColliders()) {
            if (other == this) continue;

            if (isColliding(testTransform, other)) {
                if (newY > other.entity.transform.getY()) {
                    // Collision on the top
                    return other.entity.transform.getY() + other.entity.transform.getHeight() * (1 - other.topOffsetPercentage) - testTransform.getHeight() * bottomOffsetPercentage;
                } else {
                    // Collision on the bottom
                    return other.entity.transform.getY() + other.entity.transform.getHeight() * other.bottomOffsetPercentage - testTransform.getHeight() * (1 - topOffsetPercentage);
                }
            }
        }
        return newY; // No collision, return original newY
    }

    private boolean isColliding(Transform transformToCheck, Collider other) {
        float thisLeft = transformToCheck.getX() + (transformToCheck.getWidth() * leftOffsetPercentage);
        float thisRight = transformToCheck.getX() + (transformToCheck.getWidth() * (1 - rightOffsetPercentage));
        float thisTop = transformToCheck.getY() + (transformToCheck.getHeight() * (1 - topOffsetPercentage));
        float thisBottom = transformToCheck.getY() + (transformToCheck.getHeight() * bottomOffsetPercentage);

        float otherLeft = other.currentTransform.getX() + (other.currentTransform.getWidth() * other.leftOffsetPercentage);
        float otherRight = other.currentTransform.getX() + (other.currentTransform.getWidth() * (1 - other.rightOffsetPercentage));
        float otherTop = other.currentTransform.getY() + (other.currentTransform.getHeight() * (1 - other.topOffsetPercentage));
        float otherBottom = other.currentTransform.getY() + (other.currentTransform.getHeight() * other.bottomOffsetPercentage);

        return thisLeft < otherRight
                       && thisRight > otherLeft
                       && thisTop > otherBottom
                       && thisBottom < otherTop;
    }
}