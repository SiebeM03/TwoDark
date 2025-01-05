package woareXengine.util.collisions;

import java.util.ArrayList;
import java.util.List;

public abstract class Collisions {
    // TODO refactor
    protected static Collisions instance = null;

    protected List<CollisionBox> collisionBoxes = new ArrayList<>();

    public void addCollision(CollisionBox collisionBox) {
        collisionBoxes.add(collisionBox);
    }

    public void removeCollision(CollisionBox collisionBox) {
        collisionBoxes.remove(collisionBox);
    }

    public abstract void update();


    /**
     * Check for collisions with other collision boxes
     *
     * @param collisionBox The collision box to check for collisions with
     * @return Whether the collision box is colliding with another collision box
     */
    public boolean[] checkCollisions(CollisionBox collisionBox) {
        boolean[] result = new boolean[2];
        for (CollisionBox other : collisionBoxes) {
            if (!other.isOnScreen()) {
                continue;
            }
            if (collisionBox != other) {
                if (Collisions.isColliding(collisionBox, other)) {
                    result[0] = true;
                    result[1] = true;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * Check if two collision boxes are colliding
     *
     * @return Whether the two collision boxes are colliding
     */
    public static boolean isColliding(CollisionBox a, CollisionBox b) {
        return a.isColliding(b);
    }
}
