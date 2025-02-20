package TDA.entities.components.inventory.items;

import woareXengine.openglWrapper.textures.Texture;

public interface InventoryObject {

    Texture getTexture();

    /** Returns a boolean indicating if the object is stackable or not,  */
    boolean isStackable();
}
