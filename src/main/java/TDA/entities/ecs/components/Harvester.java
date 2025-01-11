package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.resources.Resource;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.MathUtils;

public class Harvester extends Component {
    public final static int HARVEST_RADIUS = 150;

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), HARVEST_RADIUS);

        if (GameManager.gameControls.playerControls.isHarvestPressed()) {
            Resource closestResource = getClosestResource(entity);

            if (closestResource != null) {
                closestResource.harvest();
            }
        }
    }

    private Resource getClosestResource(Entity harvester) {
        float closestDistance = Harvester.HARVEST_RADIUS;
        Resource closestResource = null;
        for (Entity eResource : GameManager.currentScene.getEntitiesWithComponents(Resource.class)) {
            Resource cResource = eResource.getComponent(Resource.class);
            if (cResource == null) continue;

            float distance = MathUtils.dist(harvester.transform.getCenter(), eResource.transform.getCenter());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestResource = cResource;
            }
        }
        return closestResource;
    }
}
