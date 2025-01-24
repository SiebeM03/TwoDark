package TDA.entities.inventory;

import woareXengine.openglWrapper.textures.Texture;

public interface InventoryObject {

    Texture getTexture();

    boolean isStackable();
}
