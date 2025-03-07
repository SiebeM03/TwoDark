package TDA.scene.systems.battle;

import TDA.entities.dinos.Dino;

import java.util.Arrays;
import java.util.Objects;

public class Team {
    private final static int MAX_SIZE = 3;
    private final Dino[] dinos;

    public Team(Dino... dinos) {
        if (dinos.length != MAX_SIZE) {
            throw new IllegalArgumentException("Team size must be " + MAX_SIZE + " but was " + dinos.length);
        }
        this.dinos = dinos;
    }

    public Dino[] getDinos() {
        return Arrays.stream(dinos).filter(Objects::nonNull).toArray(Dino[]::new);
    }

    public boolean isDead() {
        for (Dino dino : dinos) {
            if (dino == null) continue;
            if (dino.battleHandler().isAlive()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAlliedTeam() {
        return this instanceof AlliedTeam;
    }

    public void removeDino(Dino dino) {
        for (int i = 0; i < dinos.length; i++) {
            if (dinos[i] == dino) {
                dinos[i] = null;
                return;
            }
        }
    }


    public static class AlliedTeam extends Team {
        public AlliedTeam(Dino... dinos) {super(dinos);}
    }

    public static class EnemyTeam extends Team {
        public EnemyTeam(Dino... dinos) {super(dinos);}
    }
}
