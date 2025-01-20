package TDA.entities.resources.types;

import TDA.entities.resources.HarvestableResource;
import TDA.entities.resources.drops.TreeDrop;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class Tree extends HarvestableResource<Tree, TreeDrop> {

    public Tree() {
        super(Tree.class, TreeDrop.class);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/tree2.png");
    }
}