package TDA.entities.dinos.abilities;

import TDA.entities.dinos.Dino;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.DamageEvent;

public interface OnDamageAbility {
    void onDamageTaken(Dino dino, DamageEvent damageEvent, BattleContext context);
}
