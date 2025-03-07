package TDA.entities.tools;

import TDA.entities.resources.nodes.harvesting.HarvestableComp;
import TDA.entities.resources.nodes.types.Stone;
import TDA.entities.resources.nodes.types.Tree;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

import java.util.Map;

public class Axe extends Tool {

    @Override
    protected Map<Class<? extends HarvestableComp<?, ?>>, Integer> toolStats() {
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
