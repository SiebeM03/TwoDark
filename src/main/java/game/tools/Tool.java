package game.tools;

import game.resources.Resource;

import java.util.Map;

public abstract class Tool {
    protected int level;
    protected Map<Class<? extends Resource>, Double> boosts;

    public double getMultiplier(Class<? extends Resource> resourceClass) {
        return boosts.getOrDefault(resourceClass, 1.0);
    }

    public abstract void levelUp();
}
