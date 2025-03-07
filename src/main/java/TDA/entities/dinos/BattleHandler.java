package TDA.entities.dinos;

import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.DamageEvent;

public class BattleHandler {
    private Dino dino;

    private double health;
    private double damage;
    private double speed;

    private double currentHealth;

    public BattleHandler(Dino dino) {
        this.dino = dino;

        this.health = dino.getActualHealth();
        this.damage = dino.getActualDamage();
        this.speed = dino.getActualSpeed();

        this.currentHealth = this.health;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void takeDamage(DamageEvent damageEvent, BattleContext context) {
        currentHealth -= damageEvent.damage();

        if (!isAlive()) {
            dino.abilityHandler().onDeath(context);
            context.handleDeath(dino);
            return;
        }

        if (damageEvent.canTriggerOnDamage()) {
            dino.abilityHandler().onDamage(context, damageEvent);
        }
    }

    public void startBattle(BattleContext context) {
        dino.abilityHandler().onBattleStart(context);
        this.currentHealth = this.health;
    }

    public void finishBattle(BattleContext context) {
        dino.abilityHandler().onBattleEnd(context);
    }
}
