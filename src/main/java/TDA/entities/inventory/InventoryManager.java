package TDA.entities.inventory;

import TDA.entities.ecs.components.Hotbar;
import TDA.entities.ecs.components.Inventory;
import TDA.entities.player.Player;
import TDA.main.GameManager;
import TDA.scene.SceneSystem;
import TDA.ui.menus.inventory.itemList.InventoryItemUi;
import TDA.ui.menus.inventory.itemList.InventorySlot;
import org.joml.Vector2f;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.main.Ui;
import woareXengine.util.Id;

public class InventoryManager extends SceneSystem {
    private static final Id ID = new Id();

    private InventoryItemUi holding = null;
    private InventorySlot lastParent;

    private Inventory playerInventory;
    private Hotbar hotbarInventory;
    private Inventory externalInventory;

    public InventoryManager() {
        super(ID);
        playerInventory = Player.inventory;
        hotbarInventory = Player.hotbar;
        externalInventory = null;
    }

    @Override
    protected void update() {
        if (holding == null) return;

        snapToMouse();

        if (Engine.mouse().isClickEvent(MouseButton.RIGHT)) {
            resetHolding();
        }
    }

    @Override
    protected void end() {

    }

    @Override
    protected void cleanUp() {

    }

    public static InventoryManager getFromCurrentScene() {
        return (InventoryManager) GameManager.currentScene.getSystem(ID);
    }

    public void setHolding(InventoryItemUi item) {
        if (holding != null) {
            resetHolding();
        }
        System.out.println("set holding");
        Vector2f size = item.transform.getDimensions().mul(1.5f, new Vector2f());

        holding = item;

        holding.show(false);
        lastParent = (InventorySlot) item.getParent();
        lastParent.children.remove(item);
        Ui.getContainer().add(item);
        holding.transform.setDimensions(size);
        snapToMouse();

        holding.show(true);
    }

    public void resetHolding() {
        System.out.println("Reset");
        holding.setParent(lastParent);
        holding = null;
    }

    private void snapToMouse() {
        holding.transform.setPosition(
                Engine.mouse().getX() * Ui.getContainer().transform.getWidth() - holding.transform.getWidth() / 2,
                Engine.mouse().getY() * Ui.getContainer().transform.getHeight() - holding.transform.getHeight() / 2
        );
    }

    public InventoryItemUi getHolding() {
        return holding;
    }


    public void swapSlot(InventorySlot target) {
        if (target.getInventoryItem() == null) {
            target.add(holding);
        } else {
            target.getInventoryItem().setParent(lastParent);
            holding.setParent(target);
        }
        holding = null;
    }
}
