package TDA.entities.dinos.types.rex;

import TDA.entities.dinos.StatsComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.Dino;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Rex extends Dino {

    public Rex(float x, float y, StatsComp stats) {
        super(new Transform(new Vector2f(x, y), new Vector2f(266, 188)),
                null,
                null,
                null,
                new QuadComp(Assets.getTexture("src/assets/images/seperateImages/dinos/trex.png"), Layer.FOREGROUND),
                stats
        );
        this.getComponent(QuadComp.class).quad.horizontalFlip();
    }

    @Override
    public double getActualHealth() {
        int base = 1500;
        int points = getStats().getHealthPoints();
        return base + 180 * points + 10000 * points * (points / (points + 5000f));
    }

    @Override
    public double getActualDamage() {
        int base = 140;
        int points = getStats().getDamagePoints();
        return base + 14 * points + 1000 * points * (points / (points + 5000f));
    }
}
