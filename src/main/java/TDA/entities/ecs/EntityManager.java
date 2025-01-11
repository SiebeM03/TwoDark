package TDA.entities.ecs;


import TDA.entities.ecs.components.Collider;
import TDA.main.GameManager;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    public List<Entity> entities;

    public EntityManager() {
        this.entities = new ArrayList<>();
    }

    public void update() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    public List<Collider> getColliders() {
        List<Collider> colliders = new ArrayList<>();
        for (Entity entity : entities) {
            Collider collider = entity.getComponent(Collider.class);
            if (collider == null) continue;
            if (!GameManager.currentScene.camera.isOnScreen(entity.transform)) continue;

            colliders.add(collider);
        }
        return colliders;
    }
}