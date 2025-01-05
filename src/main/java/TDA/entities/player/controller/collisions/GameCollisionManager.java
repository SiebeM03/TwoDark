package TDA.entities.player.controller.collisions;

import woareXengine.mainEngine.Engine;
import woareXengine.util.collisions.CollisionBox;
import woareXengine.util.collisions.Collisions;

public class GameCollisionManager extends Collisions {

    public static Collisions get() {
        if (instance == null) {
            instance = new GameCollisionManager();
        }
        return instance;
    }

    @Override
    public void update() {
        for (CollisionBox collisionBox : collisionBoxes) {
            if (!collisionBox.isOnScreen()) {
                continue;
            }
            if (Engine.instance().debugging) {
                collisionBox.drawBox();
            }
        }
    }
}
