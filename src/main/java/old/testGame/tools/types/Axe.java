package old.testGame.tools.types;

import old.testGame.resources.types.Stone;
import old.testGame.resources.types.Wood;
import old.testGame.tools.Tool;

public class Axe extends Tool {

    public Axe() {
        setName("Axe");
        addResourceIncrease(Stone.class, 3 * level());
        addResourceIncrease(Wood.class, 4 * level());

    }
}
