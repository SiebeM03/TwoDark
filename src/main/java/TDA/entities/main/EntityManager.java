package TDA.entities.main;


import TDA.entities.components.interactions.ColliderComp;
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

    public List<ColliderComp> getColliders() {
        List<ColliderComp> colliders = new ArrayList<>();
        for (Entity entity : entities) {
            ColliderComp collider = entity.getComponent(ColliderComp.class);
            if (collider == null) continue;
            if (!GameManager.currentScene.camera.isOnScreen(entity.transform)) continue;

            colliders.add(collider);
        }
        return colliders;
    }

}