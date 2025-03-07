package TDA.entities.dinoInventory;

import TDA.entities.main.Component;
import TDA.entities.dinos.Dino;

public class DinoInventoryComp extends Component {
    private final Dino[] dinos;

    public DinoInventoryComp(int size) {
        this.dinos = new Dino[size];
    }

    public void addDino(Dino dino) {
        for (int i = 0; i < dinos.length; i++) {
            if (dinos[i] != null) continue;
            dinos[i] = dino;
            return;
        }
    }

    public void putDino(Dino dino, int index) {
        if (index < 0 || index >= dinos.length) return;
        if (dinos[index] == null) {
            dinos[index] = dino;
        }
    }

    public void removeDino(int index) {
        if (index < 0 || index >= dinos.length) return;
        dinos[index] = null;
    }
}
