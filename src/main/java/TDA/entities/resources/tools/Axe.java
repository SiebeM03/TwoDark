package TDA.entities.resources.tools;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.types.Stone;
import TDA.entities.resources.types.Tree;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

import java.util.Map;

public class Axe extends Tool {

    @Override
    protected Map<Class<? extends HarvestableResource<?, ?>>, Integer> toolStats() {
        return Map.of(
                Tree.class, 4,
                Stone.class, 2
        );
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/axe.png");
    }
}
