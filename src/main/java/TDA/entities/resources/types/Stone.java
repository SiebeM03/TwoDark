package TDA.entities.resources.types;

import TDA.entities.resources.Resource;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class Stone extends Resource<Stone> {

    public Stone() {
        super(Stone.class);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/stone2.png");
    }
}