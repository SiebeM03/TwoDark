package TDA.entities.resources.tools;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.types.Metal;
import TDA.entities.resources.types.Stone;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

import java.util.Map;

public class Pickaxe extends Tool {


    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/pickaxe.png");
    }

    @Override
    protected Map<Class<? extends HarvestableResource<?, ?>>, Integer> toolStats() {
        return Map.of(
                Stone.class, 3,
                Metal.class, 2
        );
    }
}
