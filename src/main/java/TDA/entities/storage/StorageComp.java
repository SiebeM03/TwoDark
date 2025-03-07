package TDA.entities.storage;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.inventory.InventoryComp;
import TDA.entities.components.interactions.ClickableComp;
import TDA.main.GameManager;
import TDA.ui.TDAUi;
import woareXengine.util.Assets;

public class StorageComp extends ClickableComp {
    public final String storageName;
    private final InventoryComp inventory;

    public StorageComp(int storageCapacity, String storageName) {
        this.inventory = new InventoryComp(storageCapacity);
        this.storageName = storageName;
    }

    public StorageComp(int storageCapacity) {
        this.inventory = new InventoryComp(storageCapacity);
        this.storageName = "Storage";
    }

    @Override
    public void update() {
        if (GameManager.gameControls.inventoryControls.shouldCloseInventory()) {
            entity.getComponent(QuadComp.class).quad.texture = Assets.getTexture("src/assets/images/pixelArt/barrel_closed.png");
        }
    }

    public void open() {
        entity.getComponent(QuadComp.class).quad.texture = Assets.getTexture("src/assets/images/pixelArt/barrel_open.png");
        TDAUi.get().gameUi.inventory.showStorage(this);
    }


    public InventoryComp getInventory() {
        return inventory;
    }
}
