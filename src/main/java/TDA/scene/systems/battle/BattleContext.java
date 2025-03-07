package TDA.scene.systems.battle;

import TDA.entities.dinos.BattleHandler;
import TDA.entities.dinos.Dino;
import TDA.scene.SceneSystem;
import TDA.scene.systems.battle.Team.*;
import woareXengine.util.Id;

import java.util.*;

public class BattleContext extends SceneSystem {
    private static final Id ID = new Id();
    private final Random random = new Random();

    private final Team alliedTeam;
    private final Team enemyTeam;

    private final List<Dino> dinosInAttackOrder = new ArrayList<>();

    private BattleState state = BattleState.INITIALIZING;


    public BattleContext(AlliedTeam alliedTeam, EnemyTeam enemyTeam) {
        super(ID);
        this.alliedTeam = alliedTeam;
        this.enemyTeam = enemyTeam;
    }

    @Override
    protected void update() {
        if (state == BattleState.INITIALIZING) {
            start();
            state = BattleState.IN_PROGRESS;
            return;
        }

        if (state == BattleState.IN_PROGRESS) {
            if (alliedTeam.isDead()) {
                state = BattleState.DEFEAT;
                return;
            }

            if (enemyTeam.isDead()) {
                state = BattleState.VICTORY;
                return;
            }

            handleRound();
            return;
        }

        if (battleEnded()) {
            finish();
        }
    }

    @Override
    protected void end() {

    }

    @Override
    protected void cleanUp() {

    }

    // =========================================== Battle Lifecycle Methods ============================================
    public void start() {
        for (Dino dino : orderedDinosList()) {
            dino.startBattle(this);
        }
    }

    public void handleRound() {
        for (Dino dino : orderedDinosList()) {
            if (getEnemyTeam(dino).isDead()) return;        // If the enemy team is dead, the battle is over
            if (!dino.battleHandler().isAlive()) continue;  // Used for dinos that have died during the round

            System.out.println((getAlliedTeam(dino).isAlliedTeam() ? "Allied" : "Enemy") + " dino " + dino + " is attacking");
            dino.abilityHandler().onActive(this);
        }
    }

    public void finish() {
        for (Dino dino : orderedDinosList()) {
            dino.finishBattle(this);
        }
    }


    // =========================================== Battle Utility Methods ==============================================
    public List<Dino> orderedDinosList() {
        dinosInAttackOrder.clear();
        dinosInAttackOrder.addAll(Arrays.asList(alliedTeam.getDinos()));
        dinosInAttackOrder.addAll(Arrays.asList(enemyTeam.getDinos()));
        dinosInAttackOrder.sort(Comparator.comparingDouble(Dino::getActualSpeed).reversed());
        return dinosInAttackOrder;
    }

    public void handleDeath(Dino dino) {
        getAlliedTeam(dino).removeDino(dino);
    }

    /**
     * Returns the team of which the dino is a part of
     *
     * @param dino the dino to check for
     * @return the team of the given dino
     */
    public Team getAlliedTeam(Dino dino) {
        return Arrays.asList(alliedTeam.getDinos()).contains(dino) ? alliedTeam : enemyTeam;
    }

    /**
     * Returns the enemy team of the given dino
     *
     * @param dino the dino to check for
     * @return the enemy team of the given dino
     */
    public Team getEnemyTeam(Dino dino) {
        return Arrays.asList(alliedTeam.getDinos()).contains(dino) ? enemyTeam : alliedTeam;
    }

    public boolean battleEnded() {
        return state == BattleState.VICTORY || state == BattleState.DEFEAT;
    }
}
