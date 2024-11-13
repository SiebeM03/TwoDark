package game.tools;

import game.resources.Resource;

import java.util.Arrays;
import java.util.List;

public abstract class Tool {
    protected int level;
    protected List<Resource> affectedResources;

    public Tool(Resource... affectedResources) {
        level = 0;
        this.affectedResources = Arrays.asList(affectedResources);
        for (Resource resource : affectedResources) {
            resource.setRatePerSecond(resource.getRatePerSecond() * getBonus(level));
        }
    }

    public abstract double getBonus(int level);

    public abstract void levelUp();

    public void updateResourceRates() {
        for (Resource resource : affectedResources) {
            resource.setRatePerSecond(resource.getRatePerSecond() * (getBonus(level) / getBonus(level - 1)));
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(level " + level + "): bonus: " + getBonus(level) + "x";
    }
}
