package TDA.entities.ecs;


import TDA.entities.ecs.components.Collider;
import TDA.main.GameManager;
import woareXengine.util.SafeList;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    public SafeList<Entity> entities;

    public EntityManager() {
        this.entities = new SafeList<>();
    }

    public void update() {
        for (Entity entity : entities) {
            entity.update();
        }
        entities.applyChanges();
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