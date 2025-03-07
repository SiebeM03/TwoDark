package TDA.entities.resources.items.types;

import TDA.entities.resources.items.ItemDropComp;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class TreeItem extends ItemDropComp {

    public TreeItem() {

    }

    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/tree2.png");
    }
}
