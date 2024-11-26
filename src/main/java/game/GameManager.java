package game;

import game.resources.ResourceManager;

public class GameManager {
    private static GameManager instance;
    private ResourceManager resourceManager;

    /**
     * @return Returns GameManager instance, creates instance if none was found.
     */
    public static GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void initResourceManager() {
        resourceManager = new ResourceManager();
        resourceManager.init();
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}
