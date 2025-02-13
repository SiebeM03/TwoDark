package TDA.entities.ecs.components;

import TDA.main.GameManager;
import TDA.ui.TDAUi;
import woareXengine.util.Assets;

public class Storage extends ClickableEntity {
    private Inventory inventory;

    public Storage(int storageCapacity) {
        inventory = new Inventory(storageCapacity);
    }

    @Override
    public void update() {
        if (GameManager.gameControls.inventoryControls.shouldCloseInventory()) {
            entity.getComponent(QuadComponent.class).quad.texture = Assets.getTexture("src/assets/images/pixelArt/barrel_closed.png");
        }
    }

    public void open() {
        entity.getComponent(QuadComponent.class).quad.texture = Assets.getTexture("src/assets/images/pixelArt/barrel_open.png");
        TDAUi.get().gameUi.showStorage(true, this);

    }


    public Inventory getInventory() {
        return inventory;
    }
}
