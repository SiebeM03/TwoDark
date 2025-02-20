package TDA.entities.components.storage;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.components.inventory.InventoryComp;
import TDA.entities.components.interactions.ClickableComp;
import TDA.main.GameManager;
import TDA.ui.TDAUi;
import woareXengine.util.Assets;

public class StorageComp extends ClickableComp {
    private final InventoryComp inventory;

    public StorageComp(int storageCapacity) {
        inventory = new InventoryComp(storageCapacity);
    }

    @Override
    public void update() {
        if (GameManager.gameControls.inventoryControls.shouldCloseInventory()) {
            entity.getComponent(QuadComp.class).quad.texture = Assets.getTexture("src/assets/images/pixelArt/barrel_closed.png");
        }
    }

    public void open() {
        entity.getComponent(QuadComp.class).quad.texture = Assets.getTexture("src/assets/images/pixelArt/barrel_open.png");
        TDAUi.get().gameUi.showStorage(true, this);
    }


    public InventoryComp getInventory() {
        return inventory;
    }
}
