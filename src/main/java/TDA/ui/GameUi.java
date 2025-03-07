package TDA.ui;

import TDA.entities.storage.StorageComp;
import TDA.main.GameManager;
import TDA.ui.states.InventoryUiState;
import TDA.ui.states.MainGameUiState;
import TDA.ui.states.PauseUiState;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;

public class GameUi extends UiComponent {

    public final MainGameUiState mainGame = new MainGameUiState();
    public final InventoryUiState inventory = new InventoryUiState();
    public final PauseUiState pause = new PauseUiState();

    private Text fps = Assets.getDefaultFont().createText("000", 0.8f);
    private double timeSinceFpsUpdate = 0;
    private double delayForFpsUpdate = 0.1f;

    @Override
    protected void init() {
        Assets.getDefaultFont();

        add(mainGame, ConstraintUtils.fill());
        add(inventory, ConstraintUtils.fill());
        add(pause, ConstraintUtils.fill());

        add(fps, new UiConstraints(
                new PixelConstraint((int) fps.calculateWidth() + 10, true),
                new PixelConstraint((int) fps.calculateHeight() + 10, true),
                new PixelConstraint((int) fps.calculateWidth()),
                new PixelConstraint((int) fps.calculateHeight())
        ));
    }

    @Override
    protected void updateSelf() {
        timeSinceFpsUpdate += Engine.getDelta();
        if (timeSinceFpsUpdate >= delayForFpsUpdate) {
            fps.textString = (int) Engine.getFps() + "";
            timeSinceFpsUpdate = 0;
        }
    }

    public void update() {
        if (GameManager.gameControls.windowControls.isEscapeKeyPressed()) {
            pause.enableState(!pause.isVisible());
        }

        if (GameManager.gameControls.inventoryControls.shouldToggleInventory()) {
            inventory.enableState(!inventory.isVisible());
        }
        if (GameManager.gameControls.inventoryControls.shouldCloseInventory()) {
            inventory.enableState(false);
        }
    }


    public void showStorage(boolean show, StorageComp storage) {
//        playerInventoryUi.storageInventoryItemList = new InventoryItemList(storage.getInventory());
//        InventoryManager.getFromCurrentScene().setExternalInventory(storage.getInventory());
//
//        showInventory(show);
    }
}
