package TDA.scene;

import woareXengine.util.Id;

public abstract class SceneSystem {
    private final Id id;

    public SceneSystem(Id id) {
        this.id = id;
    }

    protected abstract void update();

    protected abstract void end();

    protected abstract void cleanUp();

    public Id getId() {
        return id;
    }
}
