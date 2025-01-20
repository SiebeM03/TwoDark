package TDA.entities.resources.types;

import TDA.entities.resources.Resource;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

public class Tree extends Resource<Tree> {

    public Tree() {
        super(Tree.class);
    }

    @Override
    public Texture getTexture() {
        return Assets.getTexture("src/assets/images/seperateImages/tree2.png");
    }
}