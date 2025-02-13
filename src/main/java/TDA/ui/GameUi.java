package TDA.ui;

import TDA.entities.ecs.components.Storage;
import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.entities.inventory.InventoryManager;
import TDA.main.GameManager;
import TDA.ui.hotbar.HotbarUi;
import TDA.ui.menus.inventory.PlayerInventoryUi;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import TDA.ui.menus.inventory.itemList.InventorySlot;
import TDA.ui.menus.pause.PauseUi;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;

public class GameUi extends UiComponent {

    private PlayerInventoryUi playerInventoryUi = new PlayerInventoryUi();
    private HotbarUi hotbarUi = new HotbarUi();

    private PauseUi pauseUi = new PauseUi();

    private Text fps = Assets.getFont("src/assets/fonts/rounded.fnt").createText("000", 0.8f);
    private double timeSinceFpsUpdate = 0;
    private double delayForFpsUpdate = 0.1f;

    @Override
    protected void init() {
        Assets.getFont("src/assets/fonts/rounded.fnt");

        int hotbarSize = PlayerPrefab.getHotbar().getHotbarItems().length;
        add(hotbarUi, new UiConstraints(
                new CenterConstraint(),
                new PixelConstraint(0),
                new PixelConstraint((InventorySlot.SLOT_WIDTH + InventorySlot.SLOT_SPACING) * hotbarSize),
                new PixelConstraint(InventorySlot.SLOT_HEIGHT + InventorySlot.SLOT_SPACING)
        ));
        add(playerInventoryUi, ConstraintUtils.margin(100, 100));
        playerInventoryUi.show(false);
        add(pauseUi, ConstraintUtils.fill());
        pauseUi.show(false);

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
        if (GameManager.gameControls.windowControls.isEscapeKeyPressed() && !playerInventoryUi.isVisible())
            showPauseMenu(!pauseUi.isShown());

        if (GameManager.gameControls.inventoryControls.shouldOpenInventory()) showInventory(true);
        if (GameManager.gameControls.inventoryControls.shouldCloseInventory()) showInventory(false);
    }

    /** Used to check if there is UI that should close when ESC is pressed */
    public boolean isUiShown() {
        return playerInventoryUi.isShown();
    }

    public void showPauseMenu(boolean show) {
        GameManager.gameControls.playerControls.enableKeyboardUse(!show);
        GameManager.gameControls.playerControls.enableMouseUse(!show);
        GameManager.gameControls.inventoryControls.enableKeyboardUse(!show);
        GameManager.gameControls.inventoryControls.enableMouseUse(!show);
        pauseUi.show(show);
    }

    public void showInventory(boolean show) {
        GameManager.gameControls.playerControls.enableKeyboardUse(!show);
        GameManager.gameControls.playerControls.enableMouseUse(!show);
        GameManager.gameControls.windowControls.enableKeyboardUse(!show);
        GameManager.gameControls.windowControls.enableMouseUse(!show);

        if (!show) {
            InventoryManager.getFromCurrentScene().setExternalInventory(null);
        }
        playerInventoryUi.show(show);
    }

    public void showStorage(boolean show, Storage storage) {
        playerInventoryUi.storageInventoryItemList = new InventoryItemList(storage.getInventory());
        InventoryManager.getFromCurrentScene().setExternalInventory(storage.getInventory());

        showInventory(show);
    }
}
