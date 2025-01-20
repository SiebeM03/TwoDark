package TDA.entities.resources.drops;

import TDA.entities.resources.DropResource;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class MetalDrop extends DropResource {

    @Override
    public Texture getTexture() {
        return  Assets.getTexture("src/assets/images/seperateImages/Ingot-iron.png");
    }
}
