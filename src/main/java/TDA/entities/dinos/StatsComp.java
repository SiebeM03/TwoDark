package TDA.entities.dinos;

import TDA.entities.main.Component;

/**
 * This class is a component that holds the underlying points of each stat of a dinosaur
 */
public class StatsComp extends Component {
    private int healthPoints;
    private int damagePoints;
    private int speedPoints;

    public StatsComp(int healthPoints, int damagePoints, int speedPoints) {
        this.healthPoints = healthPoints;
        this.damagePoints = damagePoints;
        this.speedPoints = speedPoints;
    }

    @Override
    public void init() {
        if (!(entity instanceof Dino)) {
            throw new IllegalArgumentException("Stats Component must be attached to a Dino entity");
        }
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getDamagePoints() {
        return damagePoints;
    }

    public int getSpeedPoints() {
        return speedPoints;
    }
}
