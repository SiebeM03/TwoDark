package TDA.entities.components.inventory;

import TDA.entities.components.inventory.items.ItemStack;
import TDA.entities.components.player.HotbarComp;
import TDA.entities.prefabs.PlayerPrefab;
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

/**
 * Manages the inventory system, allows players to move items around in their inventory or across inventories.
 */
public class InventoryManager extends SceneSystem {
    private static final Id ID = new Id();

    private final InventoryComp playerInventory;
    private final HotbarComp hotbarInventory;
    private InventoryComp externalInventory;

    /** The array of items from which the item is being dragged */
    private ItemStack[] sourceArray;
    /** The index of the item being dragged, refers to the {@link #sourceArray} */
    private int sourceIndex;
    /** The slot (UI component) from which the item is being dragged */
    private InventorySlot sourceSlot;

    /** The UI component representing the item being dragged */
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

    /**
     * Sets the item being held to the item in the specified slot.
     * The item is then removed from the slot.
     *
     * @param index  The index of the item in the source array
     * @param slotUi The source slot UI component
     */
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

    /**
     * Places the item being held into the target slot.
     * If the target slot is empty, the item is placed there.
     * If the target slot is not empty, the items are swapped.
     *
     * @param index      The index of the target slot
     * @param targetSlot The target slot UI component
     */
    public void placeHolding(int index, InventorySlot targetSlot) {
        ItemStack[] targetArray = getInventoryRelatedToSlot(targetSlot);
        if (targetArray == null) {
            resetHolding();
            return;
        }

        ItemStack tempStack = targetArray[index];
        targetArray[index] = sourceArray[sourceIndex];
        sourceArray[sourceIndex] = tempStack;

        if (targetSlot.getInventoryItem() != null) {
            targetSlot.getInventoryItem().setParent(sourceSlot);
        }
        holdingUi.setParent(targetSlot);

        sourceArray = null;
        sourceIndex = -1;
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

    /** Resets the holding state, placing the item back to its original slot ({@link #sourceSlot}) */
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

    public void setExternalInventory(InventoryComp inventory) {
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
