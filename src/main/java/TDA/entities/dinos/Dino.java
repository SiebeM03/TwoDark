package TDA.entities.dinos;

import TDA.entities.dinos.abilities.Ability;
import TDA.entities.dinos.abilities.AbilityHandler;
import TDA.entities.main.Component;
import TDA.entities.main.Entity;
import TDA.scene.systems.battle.BattleContext;
import woareXengine.util.Transform;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class Dino extends Entity {
    private final AbilityHandler abilityHandler;

    private StatsComp statsComp;
    private BattleHandler battleHandler;

    public Dino(Transform transform, Ability activeAbility, Ability passiveAbility, Ability statsAbility, Component... components) {
        super(transform, components);

        List<Ability> abilities = Stream.of(activeAbility, passiveAbility, statsAbility).filter(Objects::nonNull).toList();
        abilityHandler = new AbilityHandler(this, abilities);
    }

    public StatsComp getStats() {
        if (statsComp == null) {
            return getComponent(StatsComp.class);
        }
        return statsComp;
    }

    /** @return the actual health of the dinosaur based on the points from {@link #getStats()} */
    public abstract double getActualHealth();

    /** @return the actual damage of the dinosaur based on the points from {@link #getStats()} */
    public abstract double getActualDamage();

    /** @return the actual speed of the dinosaur based on the points from {@link #getStats()} */
    public int getActualSpeed() {
        int points = getStats().getSpeedPoints();
        return Math.round(100 + points);
    }

    public AbilityHandler abilityHandler() {
        return abilityHandler;
    }

    public BattleHandler battleHandler() {
        return battleHandler;
    }

    public void startBattle(BattleContext context) {
        this.battleHandler = new BattleHandler(this);
        this.battleHandler.startBattle(context);
    }

    public void finishBattle(BattleContext context) {
        this.battleHandler.finishBattle(context);
        this.battleHandler = null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                       "health=" + getStats().getHealthPoints() +
                       ", damage=" + getStats().getDamagePoints() +
                       ", speed=" + getStats().getSpeedPoints() +
                       '}';
    }
}
