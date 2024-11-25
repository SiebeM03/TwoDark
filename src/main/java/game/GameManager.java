package game;

import game.resources.ResourceManager;

public class GameManager {
    private static GameManager instance;
    private ResourceManager resourceManager;

    public static GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void init() {
        resourceManager = new ResourceManager();
        resourceManager.init();
    }

    public ResourceManager getResourceManager() {
        if (resourceManager == null) init();
        return resourceManager;
    }
}
