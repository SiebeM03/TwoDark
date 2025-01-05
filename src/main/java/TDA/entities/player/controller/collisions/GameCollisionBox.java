package TDA.entities.player.controller.collisions;

import TDA.main.GameManager;
import woareXengine.util.Transform;
import woareXengine.util.collisions.CollisionBox;

public class GameCollisionBox extends CollisionBox {
    public GameCollisionBox(Transform parentTransform) {
        super(parentTransform);

        GameCollisionManager.get().addCollision(this);
    }

    public GameCollisionBox(Transform parentTransform, float topOffsetPercentage, float bottomOffsetPercentage, float leftOffsetPercentage, float rightOffsetPercentage) {
        super(parentTransform, topOffsetPercentage, bottomOffsetPercentage, leftOffsetPercentage, rightOffsetPercentage);

        GameCollisionManager.get().addCollision(this);
    }

    @Override
    public boolean[] hasCollisions() {
        return GameCollisionManager.get().checkCollisions(this);
    }

    @Override
    public boolean isOnScreen() {
        return GameManager.currentScene.camera.isOnScreen(getTransform());
    }
}
