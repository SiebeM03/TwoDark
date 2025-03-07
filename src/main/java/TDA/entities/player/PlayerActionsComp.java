package TDA.entities.player;

import TDA.entities.main.Component;
import TDA.entities.main.Entity;
import TDA.entities.storage.StorageComp;
import TDA.entities.resources.nodes.harvesting.HarvestableComp;
import TDA.entities.tools.Tool;
import TDA.main.GameManager;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.MathUtils;

public class PlayerActionsComp extends Component {
    private final static int RANGE = 200;
    private final HotbarComp hotbar = PlayerPrefab.getHotbar();

    @Override
    public void update() {
        DebugDraw.addCircle(entity.transform.getCenter(), RANGE);

        if (GameManager.gameControls.playerControls.isAttackPressed()) {
            Entity hoveredEntity = GameManager.currentScene.getClickableEntityAtMouse();
            if (hoveredEntity == null) return;
            if (MathUtils.dist(hoveredEntity.transform.getCenter(), entity.transform.getCenter()) >= RANGE) return;

            if (hoveredEntity.getComponent(HarvestableComp.class) != null) {
                harvestResource(hoveredEntity.getComponent(HarvestableComp.class));
            }
        } else if (GameManager.gameControls.playerControls.isInteractPressed()) {
            Entity hoveredEntity = GameManager.currentScene.getClickableEntityAtMouse();
            if (hoveredEntity == null) return;
            if (MathUtils.dist(hoveredEntity.transform.getCenter(), entity.transform.getCenter()) >= RANGE) return;

            if (hoveredEntity.getComponent(StorageComp.class) != null) {
                openStorage(hoveredEntity.getComponent(StorageComp.class));
            }
        }
    }

    private void harvestResource(HarvestableComp<?, ?> harvestableResource) {
        if (harvestableResource == null) return;

        if (hotbar.getSelectedItem() != null && hotbar.getSelectedItem().item instanceof Tool tool) {
            harvestableResource.harvest(tool);
        } else {
            harvestableResource.harvest(null);
        }
    }

    private void openStorage(StorageComp storage) {
        if (storage == null) return;
        storage.open();
    }

}
