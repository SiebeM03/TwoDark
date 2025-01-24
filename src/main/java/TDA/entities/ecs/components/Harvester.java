package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.player.Player;
import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.tools.Tool;
import TDA.main.GameManager;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.scene.Scene;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.rendering.pickingRenderer.PickingRenderer;
import woareXengine.util.Color;
import woareXengine.util.Logger;
import woareXengine.util.MathUtils;
import woareXengine.util.Transform;

public class Harvester extends Component {
    public final static int HARVEST_RADIUS = 200;
    private final Hotbar hotbar = Player.hotbar;

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), HARVEST_RADIUS);

        if (GameManager.gameControls.playerControls.isHarvestPressed()) {
            Entity hoveredEntity = GameManager.currentScene.getClickableEntityAtMouse();
            if (hoveredEntity == null) return;

            HarvestableResource<?, ?> harvestableResource = hoveredEntity.getComponent(HarvestableResource.class);
            if (harvestableResource == null) return;

            if (hotbar.getSelectedItem() != null && hotbar.getSelectedItem().item instanceof Tool tool) {
                harvestableResource.harvest(tool);
            } else {
                harvestableResource.harvest(null);
            }
        }
    }
}
