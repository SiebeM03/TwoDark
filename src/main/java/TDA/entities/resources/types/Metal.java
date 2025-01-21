package TDA.entities.resources.types;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.drops.MetalDrop;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class Metal extends HarvestableResource<Metal, MetalDrop> {

    public Metal() {
        super(Metal.class, MetalDrop.class);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/metal2.png");
    }
}
