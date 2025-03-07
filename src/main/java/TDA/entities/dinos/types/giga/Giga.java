package TDA.entities.dinos.types.giga;

import TDA.entities.dinos.StatsComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.Dino;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Giga extends Dino {
    public Giga(float x, float y, StatsComp stats) {
        super(new Transform(new Vector2f(x, y), new Vector2f(256, 144)),
                null,
                null,
                null,
                new QuadComp(Assets.getTexture("src/assets/images/seperateImages/dinos/giga.png"), Layer.FOREGROUND),
                stats
        );
    }

    @Override
    public double getActualHealth() {
        int base = 1000;
        int points = getStats().getHealthPoints();
        return base + 100 * points + 10000 * points * (points / (points + 5000f));
    }

    @Override
    public double getActualDamage() {
        int base = 200;
        int points = getStats().getDamagePoints();
        return base + 30 * points + 1000 * points * (points / (points + 5000f));
    }
}
