package game.tools;

import game.resources.Resource;

public class Pickaxe extends Tool {

    public Pickaxe(Resource... affectedResources) {
        super(affectedResources);
    }

    public double getBonus(int level) {
        double bonus = 0.2 * level + 1;
        return Math.round(bonus * 10.0) / 10.0;
    }

    @Override
    public void levelUp() {
        level++;
        updateResourceRates();
    }
}
