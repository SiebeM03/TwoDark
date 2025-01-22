package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.resources.HarvestableResource;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.MathUtils;

public class Harvester extends Component {
    public final static int HARVEST_RADIUS = 150;

    @Override
    public void update() {
        if (true) return;
        DebugDraw.addCircle(entity.transform.getCenter(), HARVEST_RADIUS);

        if (GameManager.gameControls.playerControls.isHarvestPressed()) {
            HarvestableResource closestResource = getClosestResource(entity);

            if (closestResource != null) {
                closestResource.harvest();
            }
        }
    }

    private HarvestableResource getClosestResource(Entity harvester) {
        float closestDistance = Harvester.HARVEST_RADIUS;
        HarvestableResource closestResource = null;
        for (Entity eResource : GameManager.currentScene.getEntitiesWithComponents(HarvestableResource.class)) {
            HarvestableResource cResource = eResource.getComponent(HarvestableResource.class);
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
