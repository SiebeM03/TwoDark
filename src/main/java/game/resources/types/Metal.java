package game.resources.types;

import engine.ecs.Sprite;
import engine.util.AssetPool;
import game.resources.Resource;

public class Metal extends Resource {
    private final static String TEXTURE_PATH = "assets/images/seperateImages/metal2.png";
    private final static int SPRITE_INDEX = 0;

    private transient Sprite sprite = null;


    @Override
    public int getSpriteIndex() {
        return SPRITE_INDEX;
    }

    @Override
    public Sprite getSprite() {
        if (sprite == null) {
            this.sprite = new Sprite();
            this.sprite.setTexture(AssetPool.getTexture(TEXTURE_PATH));
            this.sprite.setWidth(200);
            this.sprite.setHeight(200);
        }

        return this.sprite;
    }


    @Override
    public double calculateGain() {
        return 0;
    }

}
