package TDA.entities.resources.drops;

import TDA.entities.resources.DropResource;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class TreeDrop extends DropResource {

    public TreeDrop() {

    }

    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/tree2.png");
    }
}
