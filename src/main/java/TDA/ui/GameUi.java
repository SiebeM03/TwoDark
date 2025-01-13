package TDA.ui;

import TDA.entities.player.Player;
import TDA.main.GameManager;
import TDA.ui.hotbar.HotbarUi;
import TDA.ui.menus.inventory.InventoryUi;
import TDA.ui.menus.pause.PauseUi;
import woareXengine.ui.components.UiComponent;

public class GameUi extends UiComponent {

    private InventoryUi playerInventoryUi = new InventoryUi(Player.inventory);
    private HotbarUi hotbarUi = new HotbarUi();

    private PauseUi pauseUi = new PauseUi();

    @Override
    protected void init() {
        setTransform(0);
        add(hotbarUi);
        add(playerInventoryUi);
        playerInventoryUi.show(false);
        add(pauseUi);
        pauseUi.show(false);
    }

    @Override
    protected void updateSelf() {

    }

    public void update() {
        if (GameManager.gameControls.windowControls.isEscapeKeyPressed() && !playerInventoryUi.isVisible()) showPauseMenu(!pauseUi.isShown());

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
        playerInventoryUi.show(show);
    }
}
