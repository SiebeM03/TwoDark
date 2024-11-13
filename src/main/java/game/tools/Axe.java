package game.tools;

import game.resources.Resource;

public class Axe extends Tool {

    public Axe(Resource... affectedResources) {
        super(affectedResources);
    }

    public double getBonus(int level) {
        double bonus = 0.1 * level + 1;
        return Math.round(bonus * 10.0) / 10.0;
    }

    @Override
    public void levelUp() {
        level++;
        updateResourceRates();
    }
}
