package TDA.ui;

import TDA.entities.ecs.components.Storage;
import TDA.entities.inventory.InventoryManager;
import TDA.main.GameManager;
import TDA.ui.hotbar.HotbarUi;
import TDA.ui.menus.inventory.PlayerInventoryUi;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import TDA.ui.menus.pause.PauseUi;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;

public class GameUi extends UiComponent {

    private PlayerInventoryUi playerInventoryUi = new PlayerInventoryUi();
    private HotbarUi hotbarUi = new HotbarUi();

    private PauseUi pauseUi = new PauseUi();
    private Text fps = Assets.getFont("src/assets/fonts/rounded.fnt").createText(Engine.getFps() + "", 0.8f);
    private double timeSinceFpsUpdate = 0;
    private double delayForFpsUpdate = 0.1f;

    @Override
    protected void init() {
        Assets.getFont("src/assets/fonts/rounded.fnt");
        setTransform(0);
        add(hotbarUi);
        add(playerInventoryUi);
        playerInventoryUi.show(false);
        add(pauseUi);
        pauseUi.show(false);

        add(fps);
        fps.setTransform(0);
    }

    @Override
    protected void updateSelf() {
        if (timeSinceFpsUpdate >= delayForFpsUpdate) {
            fps.setTransform(10);
            fps.transform.setX(parent.transform.getWidth() - fps.transform.getWidth());
            fps.transform.setY(parent.transform.getHeight() - fps.transform.getHeight());
            fps.textString = (int) Engine.getFps() + "";
            timeSinceFpsUpdate = 0;
        } else {
            timeSinceFpsUpdate += Engine.getDelta();
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
