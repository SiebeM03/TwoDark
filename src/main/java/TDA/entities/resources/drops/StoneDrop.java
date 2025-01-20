package TDA.entities.resources.drops;

import TDA.entities.resources.DropResource;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class StoneDrop extends DropResource {

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/stone2.png");
    }
}
