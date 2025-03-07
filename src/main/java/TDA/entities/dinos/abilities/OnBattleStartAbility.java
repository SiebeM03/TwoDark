package TDA.entities.dinos.abilities;

import TDA.entities.dinos.Dino;
import TDA.scene.systems.battle.BattleContext;

public interface OnBattleStartAbility {
    void onBattleStart(Dino dino, BattleContext context);
    void onBattleEnd(Dino dino, BattleContext context);
}
