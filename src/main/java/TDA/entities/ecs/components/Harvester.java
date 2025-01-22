package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.player.Player;
import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.tools.Tool;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.MathUtils;

public class Harvester extends Component {
    public final static int HARVEST_RADIUS = 150;

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), HARVEST_RADIUS);

        if (GameManager.gameControls.playerControls.isHarvestPressed()) {
            HarvestableResource<?, ?> closestResource = getClosestResource(entity);

            final Hotbar inventory = Player.hotbar;
            if (inventory.hotbar.length == 0) return;

            final var tool = inventory.hotbar[0] == null ? null : inventory.hotbar[0].item ;
            if (closestResource == null) return;

            closestResource.harvest(tool instanceof Tool t ? t : null);
        }
    }

    private HarvestableResource<?, ?> getClosestResource(Entity harvester) {
        float closestDistance = Harvester.HARVEST_RADIUS;
        HarvestableResource<?, ?> closestResource = null;
        for (Entity eResource : GameManager.currentScene.getEntitiesWithComponents(HarvestableResource.class)) {
            HarvestableResource<?, ?> cResource = eResource.getComponent(HarvestableResource.class);
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
