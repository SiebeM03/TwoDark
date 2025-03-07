package TDA.entities.dinos.abilities;

import TDA.entities.dinos.Dino;
import TDA.scene.systems.battle.BattleContext;

public interface OnDeathAbility {
    void onDeath(Dino dino, BattleContext context);
}
