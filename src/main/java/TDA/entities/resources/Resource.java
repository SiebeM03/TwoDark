package TDA.entities.resources;

import TDA.entities.ecs.Component;
import woareXengine.util.Logger;

public abstract class Resource extends Component {
    private int health = 5;

    public void harvest() {
        Logger.debug("Harvested " + this.getClass().getSimpleName() + "!");
        spawnItem();
        health--;

        if (health <= 0) {
            entity.destroy();
        }
    }

    protected abstract void spawnItem();
}
