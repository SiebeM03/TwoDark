
package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Transform;

import javax.swing.plaf.ColorUIResource;

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

    public boolean willXCollide(float newX) {
        Transform testTransform = entity.transform.copy();
        testTransform.setX(newX);
        return isColliding(testTransform);
    }

    public boolean willYCollide(float newY) {
        Transform testTransform = entity.transform.copy();
        testTransform.setY(newY);
        return isColliding(testTransform);
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

    private boolean isColliding(Transform transformToCheck) {
        float thisLeft = transformToCheck.getX() + (transformToCheck.getWidth() * leftOffsetPercentage);
        float thisRight = transformToCheck.getX() + (transformToCheck.getWidth() * (1 - rightOffsetPercentage));
        float thisTop = transformToCheck.getY() + (transformToCheck.getHeight() * (1 - topOffsetPercentage));
        float thisBottom = transformToCheck.getY() + (transformToCheck.getHeight() * bottomOffsetPercentage);

        for (Collider other : GameManager.currentScene.entityManager.getColliders()) {
            if (other == this) continue;

            float otherLeft = other.currentTransform.getX() + (other.currentTransform.getWidth() * other.leftOffsetPercentage);
            float otherRight = other.currentTransform.getX() + (other.currentTransform.getWidth() * (1 - other.rightOffsetPercentage));
            float otherTop = other.currentTransform.getY() + (other.currentTransform.getHeight() * (1 - other.topOffsetPercentage));
            float otherBottom = other.currentTransform.getY() + (other.currentTransform.getHeight() * other.bottomOffsetPercentage);

            if (thisLeft < otherRight
                        && thisRight > otherLeft
                        && thisTop > otherBottom
                        && thisBottom < otherTop) {
                return true;
            }
        }
        return false;
    }
}