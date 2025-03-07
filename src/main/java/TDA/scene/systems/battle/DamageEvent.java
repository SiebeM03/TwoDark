package TDA.scene.systems.battle;

import TDA.entities.dinos.Dino;

public record DamageEvent(Dino caster, int damage, boolean canTriggerOnDamage) {
    public DamageEvent {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage must be non-negative but was " + damage);
        }

        if (caster == null) {
            throw new IllegalArgumentException("Caster cannot be null");
        }
    }

    public DamageEvent(Dino caster, int damage) {
        this(caster, damage, true);
    }
}
