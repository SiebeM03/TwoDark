package TDA.entities.components.resources.items.types;

import TDA.entities.components.resources.items.ItemDropComp;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class MetalItem extends ItemDropComp {

    @Override
    public Texture getTexture() {
        return  Assets.getTexture("src/assets/images/seperateImages/Ingot-iron.png");
    }
}
