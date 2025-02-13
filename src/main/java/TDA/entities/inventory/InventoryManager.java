package TDA.entities.inventory;

import TDA.entities.ecs.components.Hotbar;
import TDA.entities.ecs.components.Inventory;
import TDA.entities.ecs.prefabs.PlayerPrefab;
import TDA.main.GameManager;
import TDA.scene.SceneSystem;
import TDA.ui.hotbar.HotbarSlotWrapper;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import TDA.ui.menus.inventory.itemList.InventoryItemUi;
import TDA.ui.menus.inventory.itemList.InventorySlot;
import org.joml.Vector2f;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.main.Ui;
import woareXengine.util.Id;

public class InventoryManager extends SceneSystem {
    private static final Id ID = new Id();

    private Inventory playerInventory;
    private Hotbar hotbarInventory;
    private Inventory externalInventory;

    private ItemStack[] sourceArray;
    private int sourceIndex;
    private InventorySlot sourceSlot;

    private ItemStack[] targetArray;
    private int targetIndex;

    private InventoryItemUi holdingUi;

    public InventoryManager() {
        super(ID);
        playerInventory = PlayerPrefab.getInventory();
        hotbarInventory = PlayerPrefab.getHotbar();
        externalInventory = null;
    }

    @Override
    protected void update() {
        if (sourceArray == null) return;

        snapToMouse();

        if (Engine.mouse().isClickEvent(MouseButton.RIGHT)) {
            resetHolding();
        }
    }

    public void setHolding(int index, InventorySlot slotUi) {
        sourceArray = getInventoryRelatedToSlot(slotUi);
        if (sourceArray == null) return;
        sourceIndex = index;

        sourceSlot = slotUi;
        holdingUi = slotUi.getInventoryItem();
        holdingUi.show(false);

        Vector2f size = holdingUi.transform.getDimensions().mul(1.3f, new Vector2f());
        holdingUi.setParent(Ui.getContainer());
        holdingUi.transform.setDimensions(size);
        snapToMouse();

        holdingUi.show(true);
    }

    public void placeHolding(int index, InventorySlot targetSlot) {
        targetArray = getInventoryRelatedToSlot(targetSlot);
        if (targetArray == null) {
            resetHolding();
            return;
        }
        targetIndex = index;

        ItemStack tempStack = targetArray[targetIndex];
        targetArray[targetIndex] = sourceArray[sourceIndex];
        sourceArray[sourceIndex] = tempStack;

        if (targetSlot.getInventoryItem() != null) {
            targetSlot.getInventoryItem().setParent(sourceSlot);
        }
        holdingUi.setParent(targetSlot);

        sourceArray = null;
        sourceIndex = -1;
        targetArray = null;
        targetIndex = -1;
        holdingUi = null;
    }

    private ItemStack[] getInventoryRelatedToSlot(InventorySlot slot) {
        if (slot.getParent() instanceof HotbarSlotWrapper) {
            return hotbarInventory.inventoryItems;
        } else if (slot.getParent() instanceof InventoryItemList && ((InventoryItemList) slot.getParent()).isPlayerInventory()) {
            return playerInventory.inventoryItems;
        } else {
            if (externalInventory == null) return null;
            return externalInventory.inventoryItems;
        }
    }

    public void resetHolding() {
        sourceArray = null;
        sourceIndex = -1;
        if (holdingUi != null) {
            holdingUi.setParent(sourceSlot);
            holdingUi = null;
        }
    }

    private void snapToMouse() {
        holdingUi.transform.setX(Engine.mouse().getX() * Ui.getContainer().transform.getWidth() - holdingUi.transform.getWidth() / 2);
        holdingUi.transform.setY(Engine.mouse().getY() * Ui.getContainer().transform.getHeight() - holdingUi.transform.getHeight() / 2);
    }

    public boolean isHolding() {
        return sourceArray != null;
    }

    public static InventoryManager getFromCurrentScene() {
        return (InventoryManager) GameManager.currentScene.getSystem(ID);
    }

    public void setExternalInventory(Inventory inventory) {
        this.externalInventory = inventory;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void cleanUp() {
        resetHolding();
    }


}
