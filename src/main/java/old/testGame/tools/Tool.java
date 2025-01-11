package old.testGame.tools;

import old.engine.ecs.GameObject;
import old.testGame.resources.Resource;
import old.testGame.resources.ResourceManager;
import woareXengine.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class Tool {
    private String name;
    private int level = 1;
    private Map<Class<? extends Resource>, Integer> resourceIncreases;
    private GameObject tooltipGo;

    public Tool() {
        resourceIncreases = new HashMap<>();
    }

    private void upgrade() {
        this.level++;
        Logger.info(this.name + " has been upgraded to level " + this.level);

        for (Class<? extends Resource> resource : resourceIncreases.keySet()) {
            ResourceManager.getResource(resource).setAmountPerClick(resourceIncreases.get(resource) * level);
        }
    }

    public String name() {
        return name;
    }

    public int level() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getResourceIncrease(Class<? extends Resource> resource) {
        return resourceIncreases.get(resource);
    }

    /**
     * Adds a resource increase to the tool. This will put the resource in the map of {@link #resourceIncreases resource increases}
     * and increase the amount per click of the resource by the amount passed.
     */
    public void addResourceIncrease(Class<? extends Resource> resource, int increase) {
        resourceIncreases.put(resource, increase);
        ResourceManager.getResource(resource).setAmountPerClick(ResourceManager.getResource(resource).amountPerClick() + increase);
    }
}
