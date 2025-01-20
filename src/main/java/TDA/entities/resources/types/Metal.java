package TDA.entities.resources.types;

import TDA.entities.resources.Resource;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class Metal extends Resource<Metal> {

    public Metal() {
        super(Metal.class);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/metal2.png");
    }
}
