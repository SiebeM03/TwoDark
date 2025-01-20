package TDA.entities.resources.types;

import TDA.entities.resources.Resource;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class Iron extends Resource<Iron> {

    public Iron() {
        super(Iron.class);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/iron_ore.png");
    }
}
