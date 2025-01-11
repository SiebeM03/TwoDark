package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.resources.Resource;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.MathUtils;

public class Harvester extends Component {
    private final static int HARVEST_RADIUS = 150;

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), HARVEST_RADIUS);

        if (GameManager.gameControls.playerControls.isHarvestPressed()) {
            float closestDistance = HARVEST_RADIUS;
            Resource closestResource = null;
            for (Entity e2 : GameManager.currentScene.entityManager.entities) {
                if (e2 == entity) continue;
                float distance = MathUtils.dist(entity.transform.getCenter(), e2.transform.getCenter());
                if (distance < HARVEST_RADIUS) {
                    if (e2.getComponent(Resource.class) != null) {
                        Resource resource = e2.getComponent(Resource.class);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestResource = resource;
                        }
                    }
                }
            }
            if (closestResource != null) {
                closestResource.harvest();
            }
        }
    }
}
