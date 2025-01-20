package TDA.entities.resources.types;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.drops.StoneDrop;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class Stone extends HarvestableResource<Stone, StoneDrop> {

    public Stone() {
        super(Stone.class, StoneDrop.class);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/stone2.png");
    }
}