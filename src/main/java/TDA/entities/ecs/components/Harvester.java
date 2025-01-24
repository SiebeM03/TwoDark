package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.player.Player;
import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.tools.Tool;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;

public class Harvester extends Component {
    public final static int HARVEST_RADIUS = 200;
    private final Hotbar hotbar = Player.hotbar;

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), HARVEST_RADIUS);

        Entity hoveredEntity = getHoveredHarvestableEntity();
        if (hoveredEntity == null) return;

        if (GameManager.gameControls.playerControls.isHarvestPressed()) {
            harvest(hoveredEntity.getComponent(HarvestableResource.class));
        }
    }

    /** Harvests the hovered entity using the hotbar item that is currently selected. */
    private void harvest(HarvestableResource<?, ?> harvestableResource) {
        if (harvestableResource == null) return;

        if (hotbar.getSelectedItem() != null && hotbar.getSelectedItem().item instanceof Tool tool) {
            harvestableResource.harvest(tool);
        } else {
            harvestableResource.harvest(null);
        }
    }

    /** Returns an entity that is currently hovered by the mouse and has a {@link HarvestableResource} component. Otherwise, returns null. */
    private Entity getHoveredHarvestableEntity() {
        Entity hoveredEntity = GameManager.currentScene.getClickableEntityAtMouse();
        if (hoveredEntity == null) return null;
        if (hoveredEntity.getComponent(HarvestableResource.class) == null) return null;
        return hoveredEntity;
    }
}
