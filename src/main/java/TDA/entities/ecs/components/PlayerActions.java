package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.tools.Tool;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.MathUtils;

public class PlayerActions extends Component {
    private final static int RANGE = 200;
    private final Hotbar hotbar = PlayerPrefab.getHotbar();

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), RANGE);

        if (GameManager.gameControls.playerControls.isAttackPressed()) {
            Entity hoveredEntity = GameManager.currentScene.getClickableEntityAtMouse();
            if (hoveredEntity == null) return;
            if (MathUtils.dist(hoveredEntity.transform.getCenter(), entity.transform.getCenter()) >= RANGE) return;

            if (hoveredEntity.getComponent(HarvestableResource.class) != null) {
                harvestResource(hoveredEntity.getComponent(HarvestableResource.class));
            }
        } else if (GameManager.gameControls.playerControls.isInteractPressed()) {
            Entity hoveredEntity = GameManager.currentScene.getClickableEntityAtMouse();
            if (hoveredEntity == null) return;
            if (MathUtils.dist(hoveredEntity.transform.getCenter(), entity.transform.getCenter()) >= RANGE) return;

            if (hoveredEntity.getComponent(Storage.class) != null) {
                openStorage(hoveredEntity.getComponent(Storage.class));
            }
        }
    }

    private void harvestResource(HarvestableResource<?, ?> harvestableResource) {
        if (harvestableResource == null) return;

        if (hotbar.getSelectedItem() != null && hotbar.getSelectedItem().item instanceof Tool tool) {
            harvestableResource.harvest(tool);
        } else {
            harvestableResource.harvest(null);
        }
    }

    private void openStorage(Storage storage) {
        if (storage == null) return;
        storage.open();
    }

}
