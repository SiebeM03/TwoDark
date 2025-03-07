package TDA.entities.tools;

import TDA.entities.resources.nodes.harvesting.HarvestableComp;
import TDA.entities.resources.nodes.types.Metal;
import TDA.entities.resources.nodes.types.Stone;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

import java.util.Map;

public class Pickaxe extends Tool {


    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/pickaxe.png");
    }

    @Override
    protected Map<Class<? extends HarvestableComp<?, ?>>, Integer> toolStats() {
        return Map.of(
                Stone.class, 4,
                Metal.class, 3
        );
    }
}
