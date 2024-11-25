package game.tools.types;

import game.resources.Resource;
import game.tools.Tool;

public class Axe extends Tool {

    @Override
    public void levelUp() {
        level++;

        for (Class<? extends Resource> resource : boosts.keySet()) {
            double prevBoost = this.boosts.get(resource);
            this.boosts.put(resource, (prevBoost / (level - 1) * level));
        }
    }
}
